package back.end.domain.posting.image;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageAfterUploadedUpdateRequest {
    private String content;
    private Integer idx;
}
