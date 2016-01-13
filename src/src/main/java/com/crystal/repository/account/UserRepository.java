package com.crystal.repository.account;

import com.crystal.model.entities.account.User;
import com.crystal.model.entities.account.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.username=:username")
    Long findIdByUsername(@Param("username") String username);

    @Query("SELECT u.enabled FROM User u WHERE u.id=:id")
    Boolean isEnabled(@Param("id") Long userId);

    @Query("SELECT new com.crystal.model.entities.account.User(u.id, u.enabled) FROM User u WHERE u.username=:username")
    User getInfoToValidate(@Param("username") String sUsername);

    @Query("SELECT u.password FROM User u WHERE u.id=:id")
    String getEncodedPassword(@Param("id") Long userId);

    @Query("SELECT new com.crystal.model.entities.account.UserDto(u.id, u.username, u.fullName, u.email, u.role.id, u.auditedEntity.id) FROM User u WHERE u.id=:id AND u.enabled = 1")
    UserDto findOneDto(@Param("id") Long id);

    @Query("SELECT COUNT(u) FROM User u WHERE u.username=:username AND u.id <> :id")
    Long anyUsernameWithNotId(@Param("username")String username, @Param("id")Long userId);

    Long countByUsername(String username);

    User findByIdAndEnabled(Long id, boolean b);

    @Query("SELECT u.username FROM User u WHERE u.id = :id AND u.enabled = 1")
    String getUsernameById(@Param("id") Long id);

    @Query("SELECT u.auditedEntity.id FROM User u WHERE u.id = :id AND u.enabled = 1")
    Long getAuditedEntityIdByUserId(@Param("id") Long id);

    @Query("SELECT COUNT(u) FROM User u INNER JOIN u.role r WHERE u.id=:userId AND r.code IN :roles")
    Long isUserInRoles(@Param("userId") Long userId, @Param("roles") List<String> roles);

    @Query("SELECT r.id FROM User u INNER JOIN u.role r WHERE u.id=:userId ")
    Long getRoleIdForUser(@Param("userId") Long userId);
}
