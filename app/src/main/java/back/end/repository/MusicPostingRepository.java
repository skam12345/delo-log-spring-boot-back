package back.end.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import back.end.domain.posting.music.MusicPosting;
import jakarta.transaction.Transactional;

@Repository
public interface MusicPostingRepository extends JpaRepository<MusicPosting, Integer> {
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO delo_music_creation_table(creation_visible, creation_title, creation_writer, creation_music, creation_thumbnail, creation_editor_content, creation_result_content, created_at) " +
       "VALUES(:creationVisible, :creationTitle, :creationWriter, :creationMusic, :creationThumbnail, :creationEditorContent, :creationResultContent, :createdAt)" , nativeQuery =  true)
    public void insertMusicPosting(
        @Param("creationVisible") Boolean visibled,
            @Param("creationTitle") String title,
            @Param("creationWriter") String writer,
            @Param("creationMusic") String models,
            @Param("creationThumbnail") String thumbnail,
            @Param("creationEditorContent") String editorContent,
            @Param("creationResultContent") String resultContent,
            @Param("createdAt") LocalDateTime createdAt);

    @Query("Select mp from MusicPosting mp")
    public List<MusicPosting> getAllPosting();
    
    @Modifying
    @Transactional
    @Query("Delete from MusicPosting mp where mp.creationIdx = :idx")
    public void deleteMusicPosting(@Param("idx") Integer idx);

    @Query(value = "Select created_at from delo_music_creation_table where creation_idx = :idx", nativeQuery = true)
    public LocalDateTime getCreatedAt(@Param("idx") Integer idx);

    @Query(value = "Select creation_image from delo_music_creation_table where creation_idx  = :idx", nativeQuery = true)
    public String getMusic(@Param("idx") Integer idx);


    @Query(value = "SELECT * FROM delo_music_creation_table  WHERE creation_idx = :idx", nativeQuery = true)
    public MusicPosting selectOnePosting(@Param("idx") Integer idx);
}
