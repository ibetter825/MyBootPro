package com.mypro.thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledThreadPool {
	protected final static ReentrantLock lock = new ReentrantLock();
	protected static ScheduledThreadPool pool;
	private static ScheduledExecutorService service;
	
	private ScheduledThreadPool(){
		service = Executors.newScheduledThreadPool(10);
	}
	
	public static ScheduledThreadPool getInstance(){
		lock.lock();
		try {
			if(pool == null)
				pool = new ScheduledThreadPool();
		} finally {
			lock.unlock();
		}
		return pool;
	}
	//延迟3秒执行
	public void schedule(Runnable command){
		service.schedule(command, 3, TimeUnit.SECONDS);
	}
	//延迟1秒后每3秒执行一次
	public void scheduleAtFixedRate(Runnable command){
		service.scheduleAtFixedRate(command, 1, 3, TimeUnit.SECONDS);
	}
}
