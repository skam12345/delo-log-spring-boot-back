package back.end.domain.posting.music;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertMusicPostingRequest {
    private Boolean visibled;
    private String title;
    private String writer;
    private List<String> MusicList;
    private String thumbnail;
    private String editorContent;
    private String resultContent; 
}
