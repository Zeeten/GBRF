package com.ncs.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ncs.model.ReadLikeAwardPartOneModel;


public class ReadAndLikePartOneLikeOneJob implements Job {
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
  //  System.out.println("Java web application + Quartz 2.2.1");
    ReadLikeAwardPartOneModel model=new ReadLikeAwardPartOneModel();
    try {
		model.addChapterOne();
		model.addChapterSecond();
		model.addChapterThird();
	} catch (Exception e) {
		
		e.printStackTrace();
	}
}

}
