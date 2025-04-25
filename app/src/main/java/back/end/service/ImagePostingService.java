package back.end.service;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import back.end.domain.posting.image.ImagePosting;
import back.end.repository.ImagePostingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagePostingService {
    private final ImagePostingRepository imagePostingRepository;


    public void insertImagePosting(Boolean visibled, String title, String writer, String images, String thumbnail, String editorContent, String resultContent, LocalDateTime createdAt) {
        imagePostingRepository.insertImagePosting(visibled, title, writer, images, thumbnail, editorContent, resultContent, createdAt);
    }

    public List<ImagePosting> getAllPosting() {
        return imagePostingRepository.getAllPosting();
    }

    public ImagePosting selectOnePosting(Integer idx) {
        return imagePostingRepository.selectOnePosting(idx);
    }
}
