package com.sequence.generator.repository;

import com.sequence.generator.entity.Sequence;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")
    })
    @Query("SELECT s FROM Sequence s WHERE s.sequenceName = :sequenceName")
    Optional<Sequence> findBySequenceNameWithLock(String sequenceName);

    Optional<Sequence> findBySequenceName(String sequenceName);

    boolean existsBySequenceName(String sequenceName);
}