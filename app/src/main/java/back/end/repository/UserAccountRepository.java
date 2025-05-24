package back.end.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import back.end.domain.account.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    /* 로그인 */
    @Query(value = "SELECT * FROM user_account_table WHERE user_id = :userId", nativeQuery = true)
    public UserAccount getLoginInfo(@Param("userId") String userId);
    /* 회원가입 */
    @Modifying
    @Transactional
    @Query("Insert into UserAccount(userAuthority, userId, userPassword, userNickname, userReasonForJoin, userEmail) values(:userAuthority, :userId, :userPassword, :userNickname, :userReasonForJoin, :userEmail)")
    public void insertSignUpUserAccount(@Param("userAuthority") String userAuthority,
            @Param("userId") String userId,
            @Param("userPassword") String userPassword,
            @Param("userNickname") String userNickname,
            @Param("userReasonForJoin") String userReasonForJoin,
            @Param("userEmail") String userEmail);

    
    /*비밀번호 찾기 후 -> 새로운 비밀번호로 변경 */
    @Modifying
    @Transactional
    @Query("Update UserAccount u set u.userPassword = :newPassword where u.userIdx = :userIdx")
    public void newUpdatePassword(@Param("userIdx") Integer userIdx, @Param("newPassword") String newPassword);



    /* 아이디 찾기 */
    Optional<UserAccount> findIdByUserNicknameAndUserEmail(String userNickname, String userEmail);

    /* 비밀번호 찾기 */
    Optional<UserAccount> findPassowrdByUserIdAndUserNicknameAndUserEmail(String userId, String userNickname, String userEmail);
}