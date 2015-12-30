package com.crystal.repository.shared;

import com.crystal.model.shared.LogPostRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogPostRequestRepository extends JpaRepository<LogPostRequest, Long> {
}
