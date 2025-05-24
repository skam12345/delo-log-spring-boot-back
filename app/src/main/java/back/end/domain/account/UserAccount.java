package back.end.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_account_table")
public class UserAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Integer userIdx;

    @Column(name = "user_authority", length = 15, nullable = false)
    private String userAuthority = "common";
    
    @Column(name = "user_id", length = 30, nullable = false, unique = true)
    private String userId;

    @JsonIgnore
    @Column(name = "user_password", length = 255, nullable = false)
    private String userPassword;

    @Column(name = "user_nickname", length = 50, nullable = false, unique = true)
    private String userNickname;

    @Column(name = "user_reason_for_join", length = 300)
    private String userReasonForJoin;


    @Column(name = "user_email", length = 100, nullable= false, unique = true)
    private String userEmail;

    public UserAccount() {

    }
}
