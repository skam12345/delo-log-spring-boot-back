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
    @Query(value = "Insert into  delo_2d_creation_table(creation_visible, creation_title, creation_writer, creation_thumbnail, creation_editor_content, creation_result_content, created_at) values(:creationVisible, :creationTitle, :creationWriter, :creationThumbnail, :creationEditorContent, :creationResultContent, :createdAt)", nativeQuery = true)
    public void insertImagePosting(
            @Param("creationVisible") Boolean visibled,
            @Param("creationTitle") String title,
            @Param("creationWriter") String writer,
            @Param("creationThumbnail") String thumbnail,
            @Param("creationEditorContent") String editorContent,
            @Param("creationResultContent") String resultContent,
            @Param("createdAt") LocalDateTime createdAt);
    
    @Modifying
    @Transactional
    @Query(value = "Insert into creation_images_table(post_creation_idx, creation_image_url_path, creation_image_status, uploaded_at) values(:postCreationIdx, :creationImageUrlPath, :creationImageStatus, :uploadedAt)", nativeQuery = true)
    public void insertImageData(
            @Param("postCreationIdx") Integer postCreationIdx,
            @Param("creationImageUrlPath") String creationImageUrlPath,
            @Param("creationImageStatus") String creationImageStatus,
            @Param("uploadedAt") LocalDateTime uploadedAt);

    @Query(value = "Select creation_idx from delo_2d_creation_table order by creation_idx desc limit 1", nativeQuery = true)
    public Integer getImagePostingLastIdx();

    @Modifying
    @Transactional
    @Query(value = "Update delo_2d_creation_table set creation_result_content = :creationResultContent creation_thumbnail = :creationThumbnail where creation_idx = :creationIdx", nativeQuery = true)
    public void afterUpload(
            @Param("creationResultContent") String creationResultContent,
            @Param("creationThumbnail") String creationThumbnail,
            @Param("creationIdx") Integer creationIdx);

    @Query(value = "Select creation_image_url_path from creation_images_table_where post_creation_idx = :postCreationIdx and creation_image_status = :thumbnail", nativeQuery = true)
    public String getThumbnail(@Param("postCreationIdx") Integer postCreationIdx, @Param("thumbnail") String thumbnail);

    @Query(value = "Select creation_image_url path from creation_images_table where post_creation_idx = :postCreationIdx", nativeQuery = true)
    public List<String> getImagesData(@Param("postCreationIdx") Integer postCreationIdx);


    @Query("Select ip from ImagePosting ip")
    public List<ImagePosting> getAllPosting();

    @Modifying
    @Transactional
    @Query("Delete from ImagePosting ip where ip.creationIdx = :idx")
    public void deleteImagePosting(@Param("idx") Integer idx);

    @Query(value = "Select created_at from delo_2d_creation_table where creation_idx = :idx", nativeQuery = true)
    public LocalDateTime getCreatedAt(@Param("idx") Integer idx);

    @Query(value = "Select creation_image from delo_2d_creation_table where creation_idx  = :idx", nativeQuery = true)
    public String getImages(@Param("idx") Integer idx);

    @Query(value = "SELECT * FROM delo_2d_creation_table  WHERE creation_idx = :idx", nativeQuery = true)
    public ImagePosting selectOnePosting(@Param("idx") Integer idx);

}