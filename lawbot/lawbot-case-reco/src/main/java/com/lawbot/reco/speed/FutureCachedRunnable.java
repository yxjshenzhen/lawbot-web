package com.lawbot.reco.speed;

/**
 * 
 * @author Cloud Lau
 *
 */
@FunctionalInterface
public interface FutureCachedRunnable<T> {

	T run(String key);
}
