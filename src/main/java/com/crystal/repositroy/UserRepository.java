package com.crystal.repositroy;

import com.crystal.model.entities.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User,Long>{

}
