package back.end.service;

import java.util.List;
import java.util.UUID;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import back.end.domain.posting.image.ImagePosting;
import back.end.repository.ImagePostingRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ImagePostingService {
    private final ImagePostingRepository imagePostingRepository;
    
    @Value("${spring.cloud.gcp.storage.bucket}")
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



    public void gcsUpload(Integer creationIdx, MultipartFile[] files, MultipartFile thumbnail) throws IOException {
        String keyFileName = "delog-blog-0a17598f6736.json";
        InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();

        Storage storage = StorageOptions.newBuilder()
            .setCredentials(GoogleCredentials.fromStream(keyFile))
            .build()
            .getService();

        for (MultipartFile file : files) {
            String targetName ="image/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

            BlobId blobId = BlobId.of(bucketName, targetName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

            storage.create(blobInfo, file.getBytes());
            String compTargetName = String.format("https://storage.googleapis.com/%s/%s", bucketName, targetName);
            if(file.getOriginalFilename().equals((thumbnail.getOriginalFilename()))) {
                imagePostingRepository.insertImageData(creationIdx, compTargetName, "thumbnail", LocalDateTime.now());
                continue;
            }
            imagePostingRepository.insertImageData(creationIdx, compTargetName, "basic", LocalDateTime.now());
        }
    }
}
