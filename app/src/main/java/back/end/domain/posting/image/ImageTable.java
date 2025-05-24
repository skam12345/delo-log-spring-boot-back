package back.end.domain.posting.image;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="creation_images_table")
public class ImageTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creation_image_idx")
    private Integer creationImageIdx;

    @Column(name = "post_creation_idx")
    private Integer postCreationIdx;
    
    @Lob
    @Column(name = "creation_image_url_path", columnDefinition = "TEXT")
    private String creationImageUrlPath;

    @Column(name = "crreation_image_status")
    private String creationImageStatus;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}
