package back.end.controller;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import back.end.domain.account.FindIdRequest;
import back.end.domain.account.FindPasswordRequest;
import back.end.domain.account.LoginRequest;
import back.end.domain.account.NewUpdatePasswordRequest;
import back.end.domain.account.SignUpRequest;
import back.end.service.UserAccountService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userAccount")
@CrossOrigin(origins = {"https://de-lo-log.site", "localhost:3002", "https://aks.delog-back.space"}, allowedHeaders = "*")
public class UserAccountController {
    
    private final UserAccountService userAccountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("userId: " + loginRequest.getUserId());
        System.out.println("userPassword: " + loginRequest.getUserPassword());

        Map<String, Object> finalCertification = userAccountService.getLoginInfo(
            loginRequest.getUserId(), 
            loginRequest.getUserPassword()
        );
        
        if(finalCertification.get("certificate").equals(true)) {
            return ResponseEntity.ok(finalCertification.get("account"));
        }else {
            return ResponseEntity.ok(Map.of("message", "로그인 실패"));
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody SignUpRequest request) {
        ResponseEntity<Map<String, String>> returnValue = null;
        try {
            userAccountService.signUpUserAccount(
                request.getUserAuthority(), 
                request.getUserId(), 
                request.getUserPassword(), 
                request.getUserNickname(),
                request.getUserReasonForJoin(), 
                request.getUserEmail()
            );
            returnValue = ResponseEntity.ok(Map.of("message","회원가입 완료"));
        } catch (IllegalArgumentException e) {
            // 오류 메시지 출력
            returnValue = ResponseEntity.ok(Map.of("error", "잘못된 요청이 발생했습니다. 다시 시도해 주세요."));
        } catch (DataIntegrityViolationException e) {
            // 키 중복 예외
            Map<String, String> errorMessages = Map.of(
                "user_account_table.user_id", "아이디 중복 입니다. 다른 아이디로 시도해 주세요.",
                "user_account_table.user_nickname", "닉네임 중복 입니다. 다른 닉네임으로 시도해 주세요.",
                "user_account_table.user_email", "이메일 중복 입니다. 다른 이메일로 시도해 주세요."
            );

            for (Map.Entry<String, String> entry : errorMessages.entrySet()) {
                if (e.getMessage().contains(entry.getKey())) {
                    returnValue = ResponseEntity.ok(Map.of("error", entry.getValue()));
                }
            }
        }
        return returnValue;
    }

    @PostMapping("/new-password")
    public ResponseEntity<Map<String, String>> newUpdatePassword(@RequestBody NewUpdatePasswordRequest request) {
        try {
            Map<String, Object> compareBeforePasword = userAccountService.getLoginInfo(request.getUserId(), request.getNewPassword());
            if(compareBeforePasword.get("certificate").equals(false)) {
                userAccountService.newUpdatePassword(request.getNewPassword(), request.getUserId());

                return ResponseEntity.ok(Map.of("message", "패스워드 변경 완료"));
            }else {
                return ResponseEntity.ok(Map.of("error", "이전과 동일한 비밀번호입니다.\n 다른 비밀번호로 시도해 주시기 바랍니다."));
            }
        } catch (IllegalArgumentException e) {
            // 오류 메시지 출력
            return ResponseEntity.ok(Map.of("error", "잘못된 요청이 발생했습니다. 다시 시도해 주세요."));
        }
    }

    @PostMapping("/find-id")
    public ResponseEntity<Map<String, String>> findIdAndSendEmail(@RequestBody FindIdRequest request) throws UnsupportedEncodingException, MessagingException {
        try {
            // userAccountService.sendUserIdToEmail() 메소드에서 문제가 있을 수 있으므로 
            // 해당 메소드에서 인코딩을 처리하는 부분도 체크해야 할 수 있습니다.
            userAccountService.sendUserIdToEmail(request.getUserNickname(), request.getUserEmail());
            return ResponseEntity.ok(Map.of("message", "아이디를 이메일로 전송했습니다."));
        } catch (IllegalArgumentException e) {
            // 오류 메시지 출력
            return ResponseEntity.ok(Map.of("error", "잘못된 요청이 발생했습니다. 다시 시도해 주세요."));
        }
    }

    @PostMapping("/find-password")
    public ResponseEntity<Map<String, String>> findPasswordAndSendEmail(@RequestBody FindPasswordRequest request) throws UnsupportedEncodingException, MessagingException {
        try {
            userAccountService.sendUserPasswordToEmail(request.getUserId(), request.getUserNickname(), request.getUserEmail());
            return ResponseEntity.ok(Map.of("message", "패스워드를 이메일로 전송했습니다."));
        } catch (IllegalArgumentException e) {
            // 오류 메시지 출력
            return ResponseEntity.ok(Map.of("error", "잘못된 요청이 발생했습니다. 다시 시도해 주세요."));
        }
    }
}
