package back.end.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import back.end.domain.account.UserAccount;
import back.end.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    
    private final UserAccountRepository userAccountRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> getLoginInfo(String userId, String userPassword) {
        UserAccount userAccount = userAccountRepository.getLoginInfo(userId);

        if(userAccount == null) {
            System.out.println("여기서 실행 되나요?");
            return Map.of("account", "인증 실패", "certificate", false);
        }

        return Map.of("account", userAccount, "certificate", passwordEncoder.matches(userPassword, userAccount.getUserPassword()));
    }

    public void signUpUserAccount(String userAuthority, String userId, String userPassword, String userNickname, String userReasonForJoin, String userEmail) {
        String encodedPassword = passwordEncoder.encode(userPassword);

        userAccountRepository.insertSignUpUserAccount(userAuthority, userId, encodedPassword, userNickname, userReasonForJoin, userEmail);
    }

    public void newUpdatePassword(String newPassword, String userId) {
        Integer userIdx = userAccountRepository.getLoginInfo(userId).getUserIdx();
        String encodedPassword = passwordEncoder.encode(newPassword);

        userAccountRepository.newUpdatePassword(userIdx, encodedPassword);
    }

    public void sendUserIdToEmail(String userNickname, String userEmail) throws MessagingException, UnsupportedEncodingException {
        UserAccount userAccount = userAccountRepository.findIdByUserNicknameAndUserEmail(userNickname, userEmail)
                .orElseThrow(() -> new IllegalArgumentException("아이디를 찾을 수 없습니다."));
        
        // 이메일 내용 작성
        String subject = "[아이디 찾기 안내]";
        String text = String.format("DeLo Blog에 찾아와 주신 것에 정말 감사합니다. \n  요청하신 아이디는 [%s] 입니다.", userAccount.getUserId());

         // 이메일 객체 생성
         MimeMessage message = mailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

         helper.setTo(userEmail);
         helper.setFrom(new InternetAddress("edsshjy3@naver.com", "[DELO DEVELOPER CENTER]"));
         helper.setSubject(subject);
         helper.setText(text, false);
 
         // 이메일 전송
         mailSender.send(message);
    }

    public void sendUserPasswordToEmail(String userId,String userNickname, String userEmail) throws MessagingException, UnsupportedEncodingException {
        UserAccount userAccount = userAccountRepository.findPassowrdByUserIdAndUserNicknameAndUserEmail(userId, userNickname, userEmail)
                .orElseThrow(() -> new IllegalArgumentException("비밀번호를 찾을 수 없습니다."));
        
        // 이메일 내용 작성
        String subject = "[비밀번호 찾기 안내]";
        String text = String.format("DeLo Blog에 찾아와 주신 것에 정말 감사합니다.\n사용자님께서 요청하신 비밀번호 찾기 인증은 완료하였습니다.\n잊어버린 비밀번호는 보안을 위해 변경이 필요합니다. \n요청하신 정보를 확인하기 위해 정보를 제공합니다. [요청하신 정보]\n\n--------------------------------------------------------\n\n    아이디: %s\n    닉네임: %s\n    이메일: %s\n\n--------------------------------------------------------\n\n비밀번호를 변경하길 원하신 다면 아래 링크로 가셔서 비밀번호를 변경하시기 바랍니다.\n\nhttps://de-lo-log.site/ACCOUNT/NEWPASSWORD\n\n----------------------------------------------\n\n**주의**\n링크를 들어가셨다가 링크를 나오면 다시 비밀번호 찾기를 해야할 수 있으므로 주의하시기 바랍니다.", userAccount.getUserId(), userAccount.getUserNickname(), userAccount.getUserEmail());
        
        // 이메일 객체 생성
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

        helper.setTo(userEmail);
        helper.setFrom(new InternetAddress("edsshjy3@naver.com", "[DELO DEVELOPER CENTER]"));
        helper.setSubject(subject);
        helper.setText(text, false);

        // 이메일 전송
        mailSender.send(message);
    }
}
