package org.example.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
    @Query(value = "SELECT coalesce(max(id), 0) FROM requests", nativeQuery = true)
    Integer getMaxId();
}
