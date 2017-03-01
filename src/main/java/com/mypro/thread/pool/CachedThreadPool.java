package com.mypro.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class CachedThreadPool {
	protected final static ReentrantLock lock = new ReentrantLock();
	protected static CachedThreadPool pool;
	protected static ExecutorService service;
	
	private CachedThreadPool(){
		service = Executors.newCachedThreadPool();
	}
	
	public static CachedThreadPool getInstance(){
		lock.lock();
		try {
			if(pool == null)
				pool = new CachedThreadPool();
		} finally {
			lock.unlock();
		}
		return pool;
	}
	
	public void execute(Runnable command){
		service.execute(command);
	}
}
