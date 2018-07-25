package com.lawbot.reco.speed;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Cloud Lau
 *
 */
public class FutureCachedLoader<T> {
	
	private static long TIMEOUT = TimeUnit.MINUTES.toMillis(30);
	
	public class FutureWrapper<T>{
		
		public FutureWrapper(Future<T> future , Date updated){
			this.future = future;
			this.updated = updated;
		}
		
		private Future<T> future;
		
		private Date updated;
		
	}
	
	private ExecutorService execute = Executors.newCachedThreadPool(); 
	
	private ConcurrentHashMap<String, FutureWrapper<T>> cached = new ConcurrentHashMap<>();
	
	
	public Future<T> load(String key , FutureCachedRunnable<T> task) throws InterruptedException, ExecutionException{
		
		FutureWrapper<T> fw = cached.get(key);
		
		if(fw == null || isTimeout(fw.updated)){
			fw = new FutureWrapper<T>(execute.submit(new Callable<T>() {

				@Override
				public T call() throws Exception {
					// TODO Auto-generated method stub
					return task.run(key);
				}
			}) , new Date()) ;
			cached.put(key, fw);
		}
		
		fw.updated = new Date();
		
		return fw.future;
	}
	
	private boolean isTimeout(Date updated){
		return System.currentTimeMillis() - updated.getTime() > TIMEOUT;
	}
	

}
