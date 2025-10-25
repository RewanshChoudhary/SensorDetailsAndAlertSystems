package com.example_Real_Time_Data_Streaming.scheduler;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataRetentionScheduler {
    @Autowired
    private final EntityManager entityManager;

    @Transactional
    @Scheduled(fixedRate=1000 *60*60*24*7)
    // Runs every 7 days and keeps the old records in sensor_data_archive table and cleans sensor_data table
    public void retentionPolicy(){
        String deleteQuery="delete from sensor_data where timestamp< now()-INTERVAL '7 days'";
        String archiveQuery="insert into sensor_data_archive select * from sensor_data where timestamp<now()-INTERVAL '7 days'";

        try {
            entityManager.createNativeQuery(archiveQuery).executeUpdate();
            entityManager.createNativeQuery(deleteQuery).executeUpdate();


        }
        catch (Exception e){
            System.out.println("Error while running retention policy query");
            e.printStackTrace();

        }
    }

    @Transactional
    @Scheduled(fixedRate = 1000*60*60*24*7*30)
    public void retentionPolicyArchives(){
        String query="delete from sensor_data_archive where timestamp <now()- interval '30 days'";
        try{
            entityManager.createNativeQuery(query).executeUpdate();

        }
        catch (Exception e ){
            System.out.println("Error while running retention policy for archives");
            e.printStackTrace();

        }
    }
}
