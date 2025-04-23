package back.end.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUpdatePasswordRequest {
    private String userId;
    private String newPassword;
}
