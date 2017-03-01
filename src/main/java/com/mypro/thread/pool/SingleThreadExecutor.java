package com.mypro.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class SingleThreadExecutor {
	protected final static ReentrantLock lock = new ReentrantLock();
	protected static SingleThreadExecutor pool;
	protected static ExecutorService service;
	
	private SingleThreadExecutor(){
		service = Executors.newSingleThreadExecutor();
	}
	
	public static SingleThreadExecutor getInstance(){
		lock.lock();
		try {
			if(pool == null)
				pool = new SingleThreadExecutor();
		} finally {
			lock.unlock();
		}
		return pool;
	}
	
	public void execute(Runnable command){
		service.execute(command);
	}
}
