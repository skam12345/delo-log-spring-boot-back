package back.end.domain.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String userAuthority;
    private String userId;
    private String userPassword;
    private String userNickname;
    private String userReasonForJoin;
    private String userEmail;
}