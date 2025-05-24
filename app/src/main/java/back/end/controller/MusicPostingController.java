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
import back.end.domain.posting.music.MusicNewPostingRequest;
import back.end.domain.posting.music.MusicPosting;
import back.end.domain.posting.music.ViewMusicOnePostingRequest;
import back.end.service.MusicPostingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
@CrossOrigin(origins = {"https://de-lo-log.site", "http://localhost:3002", "https://aks.delog-back.space"}, allowedHeaders =  "*")
public class MusicPostingController {

    private final MusicPostingService musicPostingService;

    @PostMapping("/write/music-posting")
    public ResponseEntity<Map<String, String>> musicPosting(@RequestBody MusicNewPostingRequest request) {
        ResponseEntity<Map<String, String>> returnValue = null;
        
        try {
            List<String> musicList = request.getMusicList();
            String music = "";
            String thumbnail = "";
            if(musicList != null && !request.getThumbnail().equals("")) {
                for(int i = 0; i < musicList.size(); i++) {
                    music += musicList.get(i) + "::"; 
                }
                thumbnail = request.getThumbnail();
            }else {
                music = "";
                thumbnail = "none";
            }

            musicPostingService.insertMusicPosting(
                request.getVisibled(),
                request.getTitle(),
                request.getWriter(),
                music,
                thumbnail,
                request.getEditorContent(),
                request.getResultContent(),
                request.getCreatedAt() != null ? request.getCreatedAt(): LocalDateTime.now()
            );

            returnValue = ResponseEntity.ok(Map.of("message", "포스팅이 완료되었습니다."));
        } catch (IllegalAccessError e) {
            returnValue = ResponseEntity.ok(Map.of("error", "잘못된 요청입니다. 다시 시도해 주시기 바랍니다."));
        }

        return returnValue;
    }

    @PostMapping("/list/music-all-posting")
    public ResponseEntity<?> getAllPosting() {
        List<MusicPosting> allPosting = musicPostingService.getAllPosting();

        if(allPosting != null) {
            return ResponseEntity.ok(Map.of("allPosting", allPosting));
        }

        return ResponseEntity.ok(Map.of("text", "업로드한 게시물이 없습니다."));
    }

    @PostMapping("/view/music-posting")
    public ResponseEntity<?> viewOnePosting(@RequestBody ViewMusicOnePostingRequest request) {
        MusicPosting posting = musicPostingService.selectOnePosting(request.getIdx());
        
        if(posting != null) {
            return ResponseEntity.ok(Map.of("posting", posting));
        }

        return ResponseEntity.ok(Map.of("message", "잘못된 요청입니다.\n 개발자에게 문의하시기 바랍니다."));
    }

     @PostMapping("/delete/music-posting")
    public ResponseEntity<Map<String, String>> deletePosting(@RequestBody ViewImageOnePostingRequest request) {
        ResponseEntity<Map<String, String>> returnValue = null;
        try {
            musicPostingService.deleteMusicPosting(request.getIdx());
            returnValue = ResponseEntity.ok(Map.of("message", "게시물 삭제 완료"));
        }catch(IllegalArgumentException e) {
            returnValue = ResponseEntity.ok(Map.of("error", "잘못된 요청입니다. 다시 시도해주시기 바랍니다."));
        }

        return returnValue;
    }

    @PostMapping("/delete/music-new-posting")
    public ResponseEntity<?> deleteNewPosting(@RequestBody ViewImageOnePostingRequest request) {
        try {
            LocalDateTime createdAt = musicPostingService.getCreatedAt(request.getIdx());
            String keys = musicPostingService.getMusic(request.getIdx());
            String[] keyList = keys.split("::");
            for(int i = 0; i < keyList.length; i++) {
                musicPostingService.deleteFile(keyList[i]);
            }
            musicPostingService.deleteMusicPosting(request.getIdx());

            return ResponseEntity.ok(Map.of("message", "게시물 삭제 완료", "createdAt", createdAt));
        }catch(IllegalArgumentException e) {
            return ResponseEntity.ok(Map.of("error", "잘못된 요청입니다. 다시 시도해주시기 바랍니다."));
        }
    }
}
