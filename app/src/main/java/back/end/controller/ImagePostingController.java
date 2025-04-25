package back.end.controller;

import java.util.Map;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import back.end.domain.posting.image.InsertImagePostingRequest;
import back.end.domain.posting.image.ViewImageOnePostingRequest;
import back.end.domain.posting.image.ImagePosting;
import back.end.service.ImagePostingService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
@CrossOrigin(origins = {"https://de-lo-log.site", "http://localhost:3002"}, allowedHeaders = "*")
public class ImagePostingController {

    private final ImagePostingService imagePostingService;

    @PostMapping("/write/image-posting")
    public ResponseEntity<Map<String, String>> imagePosting(@RequestBody InsertImagePostingRequest request) {
        ResponseEntity<Map<String, String>> returnValue = null;
        try {
            List<String> imageList = request.getImageList();
            String images = "";
            String thumbnail = "";
            if(imageList != null && !request.getThumbnail().equals("")) {
                for(int i = 0 ; i < imageList.size(); i++) {
                    images += imageList.get(i) + "|";
                }
                thumbnail = request.getThumbnail();
            }else {
                images = "none";
                thumbnail = "none";
            }
            imagePostingService.insertImagePosting(
                request.getVisibled(),
                request.getTitle(),
                request.getWriter(),
                images,
                thumbnail,
                request.getEditorContent(),
                request.getResultContent(),
                LocalDateTime.now()
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

}
