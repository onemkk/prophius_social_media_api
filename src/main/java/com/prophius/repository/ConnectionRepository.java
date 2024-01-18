package com.prophius.repository;

import com.prophius.entity.Connections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connections, Long> {

    @Query("select c from Connections c where c.followerId = ?1 or c.followeeId = ?2")
    List<Connections> findAllByFollowerIdAndFollowee_Id(long followerId, long followeeId);
}
