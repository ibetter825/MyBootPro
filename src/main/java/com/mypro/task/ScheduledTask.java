package com.mypro.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Spring自带的定时任务
 * @author user
 *
 */
//@Component
public class ScheduledTask {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	//@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
	}
}
