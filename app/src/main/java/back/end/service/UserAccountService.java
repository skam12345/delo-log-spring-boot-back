package back.end.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import back.end.domain.UserAccount;
import back.end.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    
    private final UserAccountRepository userAccountRepository;
    private final JavaMailSender mailSender;

    public UserAccount getLoginInfo(String userId, String userPassword) {
        return userAccountRepository.getLoginInfo(userId, userPassword);
    }

    public void signUpUserAccount(String userAuthority, String userId, String userPassword, String userNickname, String userReasonForJoin, String userEmail) {
        userAccountRepository.insertSignUpUserAccount(userAuthority, userId, userPassword, userNickname, userReasonForJoin, userEmail);
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
        String text = String.format("DeLo Blog에 찾아와 주신 것에 정말 감사합니다. \n  요청하신 비밀번호는 [%s] 입니다.", userAccount.getUserPassword());
        
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
