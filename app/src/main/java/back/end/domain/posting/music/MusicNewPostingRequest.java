package back.end.domain.posting.music;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicNewPostingRequest {
    private Boolean visibled;
    private MultipartFile[] file;
    private String title;
    private String writer;
    private List<String> MusicList;
    private String thumbnail;
    private String editorContent;
    private String resultContent;
    private LocalDateTime createdAt;
}
