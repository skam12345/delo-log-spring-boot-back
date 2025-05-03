package back.end.domain.posting.threed;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreedNewPostingRequest {
    private Boolean visibled;
    private String title;
    private String writer;
    private List<String> imageList;
    private String thumbnail;
    private String editorContent;
    private String resultContent;
    private LocalDateTime createdAt;
    public List<String> getModelList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getModelList'");
    }
}
