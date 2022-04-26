package com.example.staffapi.repository;

import com.example.staffapi.enitity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    @Query( value = "SELECT * FROM work_logs " +
            "WHERE date(start_date) = ?1 AND " +
            "date(end_date) = ?1",
            nativeQuery = true
    )
    List<WorkLog> findAllForDate(Date date);
}
