package com.mypro.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class FixedThreadPool {
	protected final static ReentrantLock lock = new ReentrantLock();
	protected static FixedThreadPool pool;
	protected static ExecutorService service;
	
	private FixedThreadPool(){
		service = Executors.newFixedThreadPool(10);
	}
	
	public static FixedThreadPool getInstance(){
		lock.lock();
		try {
			if(pool == null)
				pool = new FixedThreadPool();
		} finally {
			lock.unlock();
		}
		return pool;
	}
	
	public void execute(Runnable command){
		service.execute(command);
	}
}
