package back.end.domain.posting.music;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "delo_music_creation_table")
public class MusicPosting {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creation_idx")
    private Integer creationIdx;

    @Column(name = "creation_visible")
    private Boolean creationVisible = true;

    @Column(name = "creation_title", length = 255, nullable = false)
    private String creationTitle;

    @Column(name = "creation_writer", length = 50)
    private String creationWriter = "DeLo";
    
    @Lob
    @Column(name = "creation_music" , columnDefinition = "TEXT")
    private String creationMusic;

    @Column(name = "creation_thumbnail", length = 50, nullable = false)
    private String creationThumbnail;

    @Lob
    @Column(name = "creation_editor_content", columnDefinition = "TEXT", nullable = false)
    private String creationEditorContent;
    
    @Lob
    @Column(name = "creation_result_content", columnDefinition = "LONGTEXT", nullable = false)
    private String creationResultContent;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
