package com.yumu.eventsapiserv.tasks;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

@Component
public class YumuTaskExecutor {

	private ExecutorService service = null;
	
	public YumuTaskExecutor() {
		System.out.println("Executor service started");
		this.service = Executors.newCachedThreadPool();
	}
	
	public <T> void addTask(Callable<T> tsk){
		this.service.submit(tsk);
	}
	
	public void addTask(Runnable r){
		this.service.submit(r);
	}
	
	public void shutdown(){
		System.out.println("Shutting down");
		this.service.shutdown();
	}
	

}
