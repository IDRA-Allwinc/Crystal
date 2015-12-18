package com.crystal.repository.account;

import com.crystal.model.entities.account.UserView;
import com.crystal.model.shared.GridResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 12/18/2015.
 */
@Repository
public interface UserViewRepository extends JpaRepository<UserView, Long> {
    GridResult<UserView> toGrid();
}
