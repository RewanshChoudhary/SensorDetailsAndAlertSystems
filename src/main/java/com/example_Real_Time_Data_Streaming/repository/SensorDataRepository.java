package com.example_Real_Time_Data_Streaming.repository;

import com.example_Real_Time_Data_Streaming.model.SensorDataModel;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorDataModel,Long> {



}
