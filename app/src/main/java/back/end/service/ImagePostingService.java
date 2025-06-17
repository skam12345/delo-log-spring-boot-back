package back.end.service;

import java.util.List;
import java.util.UUID;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import back.end.domain.posting.image.ImagePosting;
import back.end.repository.ImagePostingRepository;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@Service
@RequiredArgsConstructor
public class ImagePostingService {
    private final S3Client s3Client;
    private final ImagePostingRepository imagePostingRepository;
    
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    public Integer insertImagePosting(Boolean visibled, String title,  String writer, String thumbnail, String editorContent, String resultContent, LocalDateTime createdAt) {
        imagePostingRepository.insertImagePosting(visibled, title, writer, thumbnail, editorContent, resultContent, createdAt);
        return imagePostingRepository.getImagePostingLastIdx();
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
        
    }

    public ImagePosting selectOnePosting(Integer idx) {
        return imagePostingRepository.selectOnePosting(idx);
    }

    public Integer getImagePostingLastIdx() {
        return imagePostingRepository.getImagePostingLastIdx();
    }

    public void afterUpload(String resultContent, String thumbnail, Integer creationIdx) {
        imagePostingRepository.afterUpload(resultContent, thumbnail, creationIdx);
    }

    public String getThumbnailData(Integer postCreationIdx, String thumbnail) {
        return imagePostingRepository.getThumbnail(postCreationIdx, thumbnail);
    }

    public List<String> getImagesData(Integer postCreationIdx) {
        return imagePostingRepository.getImagesData(postCreationIdx);
    }



    public void s3Upload(Integer creationIdx, MultipartFile[] files, MultipartFile thumbnail) throws IOException {
        for (MultipartFile file : files) {
            String key = "image/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
            if(file.getOriginalFilename().equals(thumbnail.getOriginalFilename())) {
                imagePostingRepository.insertImageData(creationIdx, key, "thumbnail",  LocalDateTime.now());
                continue;
            }
            imagePostingRepository.insertImageData(creationIdx, key, "basic", LocalDateTime.now());
        }
    }
}
