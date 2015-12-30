package com.crystal.repository.shared;

import com.crystal.model.shared.LogGetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogGetRequestRepository extends JpaRepository<LogGetRequest, Long> {
}
