package back.end.domain.posting.image;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageUploadRequest {
    private Integer creationIdx;
    private MultipartFile[] files;
    private MultipartFile thumbnail;
}
