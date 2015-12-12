package com.crystal.repository.shared;

import com.crystal.model.shared.LogException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogExceptionRepository extends JpaRepository<LogException, Long> {
}
