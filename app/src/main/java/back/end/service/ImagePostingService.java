package back.end.service;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import back.end.domain.posting.image.ImagePosting;
import back.end.repository.ImagePostingRepository;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@Service
@RequiredArgsConstructor
public class ImagePostingService {
    private final ImagePostingRepository imagePostingRepository;
    private final S3Client s3Client;

    private final String bucketName = "delo-s3";


    public void insertImagePosting(Boolean visibled, String title, String writer, String images, String thumbnail, String editorContent, String resultContent, LocalDateTime createdAt) {
        imagePostingRepository.insertImagePosting(visibled, title, writer, images, thumbnail, editorContent, resultContent, createdAt);
    }

    public List<ImagePosting> getAllPosting() {
        return imagePostingRepository.getAllPosting();
    }

    public void deleteImagePosting(Integer idx) {
        imagePostingRepository.deleteImagePosting(idx);
    }

    public LocalDateTime getCreatedAt(Integer idx) {
        return imagePostingRepository.getCreatedAt(idx);
    }

    public String getImages(Integer idx) {
        return imagePostingRepository.getImages(idx);
    }

    public void deleteFile(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(request);
    }

    public ImagePosting selectOnePosting(Integer idx) {
        return imagePostingRepository.selectOnePosting(idx);
    }
}
