package back.end.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import back.end.domain.posting.image.ViewImageOnePostingRequest;
import back.end.domain.posting.threed.ThreedNewPostingRequest;
import back.end.domain.posting.threed.ThreedPosting;
import back.end.domain.posting.threed.ViewModelOnePostingRequest;
import back.end.service.ModelPostingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
@CrossOrigin(origins = {"https://de-lo-log.site", "http://localhost:3002","https://aks.delog-back.space"}, allowedHeaders = "*")
public class ModelPostingController {
    
    private final ModelPostingService modelPostingService;

    @PostMapping("/write/model-posting")
    public ResponseEntity<Map<String, String>> modelPosting(@RequestBody ThreedNewPostingRequest request) {
        ResponseEntity<Map<String, String>> returnValue = null;

        try {
            String models = "";
            List<String> modelList = request.getModelList();
            for(int i = 0; i < modelList.size(); i++) {
                if(i < modelList.size() - 1) {
                    models += modelList.get(i) + "::";
                }else {
                    models += modelList.get(i);
                }
            }
            modelPostingService.insertModelPosting(
                request.getVisibled(),
                request.getTitle(),
                request.getWriter(),
                request.getThumbnail(),
                models,
                request.getEditorContent(),
                request.getResultContent(),
                request.getCreatedAt() != null ? request.getCreatedAt(): LocalDateTime.now()
            );

            returnValue = ResponseEntity.ok(Map.of("message", "포스팅이 완료되었습니다."));
        } catch(IllegalAccessError e) {
            returnValue = ResponseEntity.ok(Map.of("error", "잘못된 요청입니다. 다시 시도해주시기 바랍니다."));
        }
        
        return returnValue;
    }

    @PostMapping("/list/model-all-posting")
    public ResponseEntity<?> getAllPosting() {
        List<ThreedPosting> allPosting = modelPostingService.getAllPosting();

        if(allPosting != null) {
            return ResponseEntity.ok(Map.of("allPosting", allPosting));
        }

        return ResponseEntity.ok(Map.of("text", "업로드한 게시물이 없습니다."));
    }

    @PostMapping("/view/model-posting")
    public ResponseEntity<?> viewOnePosting(@RequestBody ViewModelOnePostingRequest request) {
        ThreedPosting posting = modelPostingService.selectOnePosting(request.getIdx());
        
        if(posting != null) {
            return ResponseEntity.ok(Map.of("posting", posting));
        }

        return ResponseEntity.ok(Map.of("message", "잘못된 요청입니다.\n 개발자에게 문의하시기 바랍니다."));
    }
    
     @PostMapping("/delete/model-posting")
    public ResponseEntity<Map<String, String>> deletePosting(@RequestBody ViewImageOnePostingRequest request) {
        ResponseEntity<Map<String, String>> returnValue = null;
        try {
            modelPostingService.deleteModelPosting(request.getIdx());
            returnValue = ResponseEntity.ok(Map.of("message", "게시물 삭제 완료"));
        }catch(IllegalArgumentException e) {
            returnValue = ResponseEntity.ok(Map.of("error", "잘못된 요청입니다. 다시 시도해주시기 바랍니다."));
        }

        return returnValue;
    }

    @PostMapping("/delete/model-new-posting")
    public ResponseEntity<?> deleteNewPosting(@RequestBody ViewImageOnePostingRequest request) {
        try {
            LocalDateTime createdAt = modelPostingService.getCreatedAt(request.getIdx());
            String keys = modelPostingService.getModels(request.getIdx());
            String[] keyList = keys.split("::");
            for(int i = 0; i < keyList.length; i++) {
            }
            modelPostingService.deleteModelPosting(request.getIdx());

            return ResponseEntity.ok(Map.of("message", "게시물 삭제 완료", "createdAt", createdAt));
        }catch(IllegalArgumentException e) {
            return ResponseEntity.ok(Map.of("error", "잘못된 요청입니다. 다시 시도해주시기 바랍니다."));
        }
    }
}
