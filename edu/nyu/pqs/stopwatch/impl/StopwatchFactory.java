package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.nyu.pqs.stopwatch.api.IStopwatch;
import edu.nyu.pqs.stopwatch.apiImpl.StopwatchImpl;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
	
	private static final ConcurrentHashMap <String, IStopwatch> stopWatchMap = 
			new ConcurrentHashMap <String, IStopwatch> ();
	private static final ArrayList <IStopwatch> stopWatches = new ArrayList <IStopwatch> ();
	

	/**
	 * Creates and returns a new IStopwatch object
	 * @param id The identifier of the new object
	 * @return The new IStopwatch object
	 * @throws IllegalArgumentException if <code>id</code> is empty, null, or already taken
	 */
	public static IStopwatch getStopwatch(String id) {	
		
		if(id == null || id.isEmpty()){
			throw(new IllegalArgumentException("ID cann't be null or empty"));
		}
		
		synchronized(stopWatchMap){ 
		if(stopWatchMap.containsKey(id)){
			throw(new IllegalArgumentException("Id Already Taken"));
		}
		
		StopwatchImpl newInstance = new StopwatchImpl(id);
		stopWatchMap.put(id, newInstance);	
		stopWatches.add(newInstance);
		
		return newInstance;
		}
	}

	/**
	 * Returns a list of all created stopwatches
	 * @return a List of all creates IStopwatch objects.  Returns an empty
	 * list oi no IStopwatches have been created.
	 */
	public static synchronized List<IStopwatch> getStopwatches() {		
		return new ArrayList<IStopwatch> (stopWatches);
	}
}
