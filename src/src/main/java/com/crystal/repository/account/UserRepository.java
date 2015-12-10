package com.crystal.repository.account;

import com.crystal.model.entities.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByUsername(String username);
}
