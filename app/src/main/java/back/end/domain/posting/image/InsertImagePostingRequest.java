package back.end.domain.posting.image;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InsertImagePostingRequest {
    private Boolean visibled;
    private String title;
    private String writer;
    private List<String> imageList;
    private String thumbnail;
    private String editorContent;
    private String resultContent;
}
