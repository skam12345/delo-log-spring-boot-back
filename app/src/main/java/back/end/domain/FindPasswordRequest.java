package back.end.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordRequest {
    private String userId;
    private String userNickname;
    private String userEmail;
}
