package com.example_Real_Time_Data_Streaming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface SensorStatistics extends JpaRepository<SensorStatistics, Long> {
    @Query()
    public
}
