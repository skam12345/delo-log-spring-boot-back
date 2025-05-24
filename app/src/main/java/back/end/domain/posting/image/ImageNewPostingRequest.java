package back.end.domain.posting.image;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageNewPostingRequest {
    private Boolean visibled;
    private String title;
    private String writer;
    private MultipartFile[] file;
    private String thumbnail;
    private String editorContent;
    private String resultContent;
    private LocalDateTime createdAt;
}
