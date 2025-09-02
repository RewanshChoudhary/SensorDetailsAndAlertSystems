package com.example_Real_Time_Data_Streaming.model;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
public class AvgCount {


        private double sum;
        private long count;



        public AvgCount add(double value) {
            this.sum += value;
            this.count += 1;
            return this;
        }

        public double getAverage() {
            return count == 0 ? 0 : sum / count;
        }


}
