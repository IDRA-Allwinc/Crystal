package com.crystal.repository.account;

import com.crystal.model.entities.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}
