package back.end.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import back.end.domain.posting.threed.ThreedPosting;
import back.end.repository.ModelPostingRepository;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@Service
@RequiredArgsConstructor
public class ModelPostingService {
    private final S3Client s3Client;
    private final ModelPostingRepository modelPostingRepository;

    private final String bucketName = "delo-s3";

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

    public void deleteFile(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(request);
    }

    public ThreedPosting selectOnePosting(Integer idx) {
        return modelPostingRepository.selectOnePosting(idx);
    }
}
