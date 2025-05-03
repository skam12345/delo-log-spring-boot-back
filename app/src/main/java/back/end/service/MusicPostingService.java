package back.end.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import back.end.domain.posting.music.MusicPosting;
import back.end.repository.MusicPostingRepository;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@Service
@RequiredArgsConstructor
public class MusicPostingService {
    private final S3Client s3Client;
    private final MusicPostingRepository musicPostingRepository;
    
    private final String bucketName = "delo-s3";

    public void insertMusicPosting(Boolean visibled, String title, String writer, String music, String thumbnail, String editorContent, String resultContent, LocalDateTime createdAt) {
        musicPostingRepository.insertMusicPosting(visibled, title, writer, music, thumbnail, editorContent, resultContent, createdAt);
    }

    public List<MusicPosting> getAllPosting() {
        return musicPostingRepository.getAllPosting();
    }

    public void deleteMusicPosting(Integer idx) {
        musicPostingRepository.deleteMusicPosting(idx);
    }

    public LocalDateTime getCreatedAt(Integer idx) {
        return musicPostingRepository.getCreatedAt(idx);
    }

    public String getMusic(Integer idx) {
        return musicPostingRepository.getMusic(idx);
    }

    public void deleteFile(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(request);
    }


    public MusicPosting selectOnePosting(Integer idx) {
        return musicPostingRepository.selectOnePosting(idx);
    }
}
