package back.end.domain.posting.image;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageNewPostingRequest {
    private Boolean visibled;
    private String title;
    private String writer;
    private List<String> imageList;
    private String thumbnail;
    private String editorContent;
    private String resultContent;
    private LocalDateTime createdAt;
}
