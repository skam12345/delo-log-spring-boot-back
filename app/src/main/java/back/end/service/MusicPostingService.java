package back.end.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import back.end.domain.posting.music.MusicPosting;
import back.end.repository.MusicPostingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MusicPostingService {
    private final MusicPostingRepository musicPostingRepository;

    public void insertMusicPosting(Boolean visibled, String title, String writer, String music, String thumbnail, String editorContent, String resultContent, LocalDateTime createdAt) {
        musicPostingRepository.insertMusicPosting(visibled, title, writer, music, thumbnail, editorContent, resultContent, createdAt);
    }

    public List<MusicPosting> getAllPosting() {
        return musicPostingRepository.getAllPosting();
    }

    public MusicPosting selectOnePosting(Integer idx) {
        return musicPostingRepository.selectOnePosting(idx);
    }
}
