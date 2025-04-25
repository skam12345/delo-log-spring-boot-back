package back.end.domain.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdRequest {
    private String userNickname;
    private String userEmail;
}
