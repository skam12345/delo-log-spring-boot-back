package back.end.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import back.end.domain.posting.threed.ThreedPosting;
import jakarta.transaction.Transactional;

@Repository
public interface ModelPostingRepository extends JpaRepository<ThreedPosting, Integer> {
    
    @Modifying
    @Transactional
    @Query(value = "Insert into  delo_3d_creation_table(creation_visible, creation_title, creation_writer, creation_models, creation_thumbnail, creation_editor_content, creation_result_content, created_at) values(:creationVisible, :creationTitle, :creationWriter, :creationModels, :creationThumbnail, :creationEditorContent, :creationResultContent, :createdAt)", nativeQuery = true)
    public void insertModelPosting(
        @Param("creationVisible") Boolean visibled,
            @Param("creationTitle") String title,
            @Param("creationWriter") String writer,
            @Param("creationModels") String models,
            @Param("creationThumbnail") String thumbnail,
            @Param("creationEditorContent") String editorContent,
            @Param("creationResultContent") String resultContent,
            @Param("createdAt") LocalDateTime createdAt);
    
    @Query("Select tp from ThreedPosting tp")
    public List<ThreedPosting> getAllPosting();

    @Query("Update ThreedPosting tp set tp.creationVisible = :visibled, tp.creationTitle = :title, tp.creationModels = :models,  tp.creationThumbnail = :thumbnail, tp.creationEditorContent = :editorContent, tp.creationResultContent = :resultContent, tp.updatedAt = :updatedAt where tp.creationIdx = :idx")
    public void updateModelPosting(@Param("visibled") Boolean visibled,
            @Param("title") String title,
            @Param("models") String models,
            @Param("thumbnail") String thumbnail,
            @Param("editorContent") String editorContent,
            @Param("resultContent") String resultContent,
            @Param("updatedAt") LocalDateTime updatedAt,
            @Param("idx") Integer idx);
            
    @Query("Delete from ThreedPosting tp where tp.creationIdx = :idx")
    public void deleteModelPosting(@Param("idx") Integer idx);

    @Query(value = "SELECT * FROM delo_3d_creation_table  WHERE creation_idx = :idx", nativeQuery = true)
    public ThreedPosting selectOnePosting(@Param("idx") Integer idx);
}