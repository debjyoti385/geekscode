package com.deb.geeksforgeeks.greedy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/21/14
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActivitySelection {
    Job[] jobs;
    List<Job> selectedJobs;
    public ActivitySelection(int[] start, int[] finish){
        jobs = new Job[start.length];

        for(int i= 0 ; i<start.length ; i++){
            jobs[i] = new Job(start[i], finish[i]);
        }
    }

    public void runAlgorithm(){
        Arrays.sort(jobs);
        selectedJobs = new ArrayList<Job>();
        int tempFinish=0;
        for (int i =0; i< jobs.length ; i++){
            if (tempFinish <= jobs[i].start){
                selectedJobs.add(jobs[i]);
                tempFinish = jobs[i].finish;
            }
        }
    }

    public void show(){
        for(Job j : selectedJobs)
            System.out.println("start time = " + j.start + " finish time = " + j.finish);
    }

    private class Job implements Comparable<Job>{
        int start;
        int finish;


        private Job(int start, int finish) {
            this.start = start;
            this.finish = finish;
        }

        @Override
        public int compareTo(Job job) {
            return this.finish - job.finish;
        }
    }

    public static  void main(String args[]){
        int[] start = {1, 3, 0, 5, 8, 5,2};
        int[] finish = {2, 4, 6, 7, 9, 9,3};

        ActivitySelection as = new ActivitySelection(start,finish);
        as.runAlgorithm();
        as.show();
    }

}
