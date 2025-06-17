package back.end.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import back.end.domain.posting.threed.ThreedPosting;
import back.end.repository.ModelPostingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModelPostingService {
    private final ModelPostingRepository modelPostingRepository;


    public void insertModelPosting(Boolean visibled, String title, String writer, String thumbnail, String models, String editorContent, String resultContent, LocalDateTime createdAt) {
        modelPostingRepository.insertModelPosting(visibled, title, writer, models, thumbnail, editorContent, resultContent, createdAt);
    }

    public List<ThreedPosting> getAllPosting() {
        return modelPostingRepository.getAllPosting();
    }

    public void deleteModelPosting(Integer idx) {
        modelPostingRepository.deleteModelPosting(idx);
    }

    public LocalDateTime getCreatedAt(Integer idx) {
        return modelPostingRepository.getCreatedAt(idx);
    }

    public String getModels(Integer idx) {
        return modelPostingRepository.getModels(idx);
    }

    public ThreedPosting selectOnePosting(Integer idx) {
        return modelPostingRepository.selectOnePosting(idx);
    }
}
