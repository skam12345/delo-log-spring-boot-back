package back.end.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import back.end.domain.posting.image.ImagePosting;

@Repository
public interface ImagePostingRepository extends JpaRepository<ImagePosting, Integer>{
    /* 2D 포스팅 */
    @Modifying
    @Transactional
    @Query(value = "Insert into  delo_2d_creation_table(creation_visible, creation_title, creation_writer, creation_image, creation_thumbnail, creation_editor_content, creation_result_content, created_at) values(:creationVisible, :creationTitle, :creationWriter, :creationImage, :creationThumbnail, :creationEditorContent, :creationResultContent, :createdAt)", nativeQuery = true)
    public void insertImagePosting(
            @Param("creationVisible") Boolean visibled,
            @Param("creationTitle") String title,
            @Param("creationWriter") String writer,
            @Param("creationImage") String images,
            @Param("creationThumbnail") String thumbnail,
            @Param("creationEditorContent") String editorContent,
            @Param("creationResultContent") String resultContent,
            @Param("createdAt") LocalDateTime createdAt);
    
    @Query("Select ip from ImagePosting ip")
    public List<ImagePosting> getAllPosting();

    @Query("Update ImagePosting i set i.creationVisible = :visibled, i.creationTitle = :title, i.creationThumbnail = :thumbnail, i.creationEditorContent = :editorContent, i.creationResultContent = :resultContent, i.updatedAt = :updatedAt where i.creationIdx = :idx")
    public void updateImagePosting(@Param("visibled") Boolean visibled,
            @Param("title") String title,
            @Param("thumbnail") String thumbnail,
            @Param("editorContent") String editorContent,
            @Param("resultContent") String resultContent,
            @Param("updatedAt") LocalDateTime updatedAt,
            @Param("idx") Integer idx);
    
    @Query("Delete from ImagePosting i where i.creationIdx = :idx")
    public void deleteImagePosting(@Param("idx") Integer idx);

    @Query(value = "SELECT * FROM delo_2d_creation_table  WHERE creation_idx = :idx", nativeQuery = true)
    public ImagePosting selectOnePosting(@Param("idx") Integer idx);
}