package back.end.controller;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import back.end.domain.posting.image.ViewImageOnePostingRequest;
import back.end.domain.posting.image.ImageAfterUploadedUpdateRequest;
import back.end.domain.posting.image.ImageNewPostingRequest;
import back.end.domain.posting.image.ImagePosting;
import back.end.service.ImagePostingService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
@CrossOrigin(origins = {"https://de-lo-log.site", "http://localhost:3002"}, allowedHeaders = "*")
public class ImagePostingController {

    private final ImagePostingService imagePostingService;

    @PostMapping("/write/insert-image-data")
    public ResponseEntity<Map<String, String>> imagePosting(
        @RequestBody ImageNewPostingRequest request
    ) throws IOException {
        ResponseEntity<Map<String, String>> returnValue = null;
        try {
            imagePostingService.insertImagePosting(
                request.getVisibled(),
                request.getTitle(),
                request.getWriter(),
                request.getThumbnail(),
                request.getEditorContent(),
                request.getResultContent(),
                request.getCreatedAt() != null ? request.getCreatedAt(): LocalDateTime.now()
            );

            returnValue = ResponseEntity.ok(Map.of("idx", String.valueOf(imagePostingService.getImagePostingLastIdx())));
        }catch (IllegalArgumentException e) {
            returnValue = ResponseEntity.ok(Map.of("error", "잘못된 요청입니다. 다시 시도해주시기 바랍니다."));
        }

        return returnValue;
    }

    @PostMapping("/write/image-upload")
    public ResponseEntity<Map<String, Object>> imageUpload(@RequestParam("creationIdx") String creationIdx, @RequestPart("files") MultipartFile[] files, @RequestPart("thumbnail") MultipartFile thumbnail) throws IOException {
        ResponseEntity<Map<String, Object>> returnValue = null;
        Integer idx = Integer.parseInt(creationIdx);
        try {
            imagePostingService.gcsUpload(
                idx,
                files,
                thumbnail
            );

            returnValue = ResponseEntity.ok(Map.of("result", 1, "uploadImages", imagePostingService.getImagesData(idx)));
        }catch (IllegalArgumentException e) {
            returnValue = ResponseEntity.ok(Map.of("result", 2));
        }

        return returnValue;
    }

    @PostMapping("/write/image-after-upload")
    public ResponseEntity<Map<String, String>> imageAfterUpload(@RequestBody ImageAfterUploadedUpdateRequest request) {
        ResponseEntity<Map<String, String>> returnValue = null;
        try {
            imagePostingService.afterUpload(request.getContent(), 
                imagePostingService.getThumbnailData(request.getIdx(), "thumbnail"), 
                request.getIdx()
            );

            returnValue = ResponseEntity.ok(Map.of("message", "포스팅이 완료되었습니다."));
        }catch (IllegalArgumentException e) {
            returnValue = ResponseEntity.ok(Map.of("error", "잘못된 요청입니다. 다시 시도해주시기 바랍니다."));
        }

        return returnValue;
    }

    @PostMapping("list/image-all-posting")
    public ResponseEntity<?> getAllPosting() {
        List<ImagePosting> allPosting = imagePostingService.getAllPosting();

        if(allPosting != null) {
            return ResponseEntity.ok(Map.of("allPosting", allPosting));
        }

        return ResponseEntity.ok(Map.of("text", "업로드한 게시물이 없습니다."));
    }

    @PostMapping("/view/image-posting")
    public ResponseEntity<?> viewOnePosting(@RequestBody ViewImageOnePostingRequest request) {
        ImagePosting posting = imagePostingService.selectOnePosting(request.getIdx());
        
        if(posting != null) {
            return ResponseEntity.ok(Map.of("posting", posting));
        }

        return ResponseEntity.ok(Map.of("message", "잘못된 요청입니다.\n 개발자에게 문의하시기 바랍니다."));
    }

    @PostMapping("/delete/image-posting")
    public ResponseEntity<Map<String, String>> deletePosting(@RequestBody ViewImageOnePostingRequest request) {
        ResponseEntity<Map<String, String>> returnValue = null;
        try {
            imagePostingService.deleteImagePosting(request.getIdx());
            returnValue = ResponseEntity.ok(Map.of("message", "게시물 삭제 완료"));
        }catch(IllegalArgumentException e) {
            returnValue = ResponseEntity.ok(Map.of("error", "잘못된 요청입니다. 다시 시도해주시기 바랍니다."));
        }

        return returnValue;
    }

    @PostMapping("/delete/image-new-posting")
    public ResponseEntity<?> deleteNewPosting(@RequestBody ViewImageOnePostingRequest request) {
        try {
            LocalDateTime createdAt = imagePostingService.getCreatedAt(request.getIdx());
            String keys = imagePostingService.getImages(request.getIdx());
            String[] keyList = keys.split("::");
            for(int i = 0;  i <  keyList.length; i++) {
                imagePostingService.deleteFile(keyList[i]);
            }
            imagePostingService.deleteImagePosting(request.getIdx());

            return ResponseEntity.ok(Map.of("message", "게시물 삭제 완료", "createdAt", createdAt));
        }catch(IllegalArgumentException e) {
            return ResponseEntity.ok(Map.of("error", "잘못된 요청입니다. 다시 시도해주시기 바랍니다."));
        }
    }
}
