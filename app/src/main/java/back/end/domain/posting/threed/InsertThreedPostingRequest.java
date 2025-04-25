package back.end.domain.posting.threed;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertThreedPostingRequest {
    private Boolean visibled;
    private String title;
    private String writer;
    private List<String> modelList;
    private String thumbnail;
    private String editorContent;
    private String resultContent; 
}
