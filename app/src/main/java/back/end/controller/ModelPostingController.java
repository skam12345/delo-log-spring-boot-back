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

import back.end.domain.posting.threed.InsertThreedPostingRequest;
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
    public ResponseEntity<Map<String, String>> modelPosting(@RequestBody InsertThreedPostingRequest request) {
        ResponseEntity<Map<String, String>> returnValue = null;

        try {
            List<String> modelList = request.getModelList();
            String models = "";
            String thumbnail = "";
            System.out.println(modelList);
            if(modelList != null && !request.getThumbnail().equals("")) {
                for(int i = 0; i < modelList.size(); i++) {
                    models += modelList.get(i) + "|"; 
                }
                thumbnail = request.getThumbnail();
            }else {
                models = "none";
                thumbnail = "none";
            }
            modelPostingService.insertModelPosting(
                request.getVisibled(),
                request.getTitle(),
                request.getWriter(),
                models,
                thumbnail,
                request.getEditorContent(),
                request.getResultContent(),
                LocalDateTime.now()
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
}
