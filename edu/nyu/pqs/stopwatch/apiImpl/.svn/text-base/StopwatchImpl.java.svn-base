package edu.nyu.pqs.stopwatch.apiImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
*
* A thread-safe class implements from IStopwatch interface. Different threads can
* share a single stopwatch object and safely call any of the stopwatch methods.
*
*/
public class StopwatchImpl implements IStopwatch {
	
	 private final String id;
	 private final ArrayList <Long> timeList = new ArrayList <Long> ();
	 private final byte[] lock = new byte[0]; // Lock of timeList
	 private volatile Boolean statue = false; // false means stop watch is not running; 

	 public StopwatchImpl (String ID){
		this.id = ID;
	}
	
	/**
	 * Returns the Id of this stopwatch
	 * @return the Id of this stopwatch.  Will never be empty or null.
	 */
	public String getId(){
		return this.id;
	}

	/**
	 * Starts the stopwatch.
	 * @throws IllegalStateException if called when the stopwatch is already running
	 */
	public void start(){
		
		if(this.statue){
			throw(new IllegalStateException("Stop watch is already running"));
		}
		
		synchronized (lock){
			timeList.add(new Date().getTime());	
		}
		
		statue = true;
	}

	/**
	 * Stores the time elapsed since the last time lap() was called
	 * or since start() was called if this is the first lap.
	 * @throws IllegalStateException if called when the stopwatch isn't running
	 */
	public void lap(){
		if(!this.statue){
			throw(new IllegalStateException("Stop watch isn't running"));
		}
		
		synchronized(lock){
			timeList.add(new Date().getTime());
		}
	}

	/**
	 * Stops the stopwatch (and records one final lap).
	 * @throws IllegalStateException if called when the stopwatch isn't running
	 */
	public void stop(){
		synchronized (lock){
			lap();
		}
		
		statue = false;
	}

	/**
	 * Resets the stopwatch.  If the stopwatch is running, this method stops the
	 * watch and resets it.  This also clears all recorded laps.
	 */
	public void reset(){
		synchronized(lock){
			timeList.clear();
		}
		
		statue = false;
	}

	/**
	 * Returns a list of lap times (in milliseconds).  This method can be called at
	 * any time and will not throw an exception.
	 * @return a list of recorded lap times or an empty list if no times are recorded.
	 */
	public List<Long> getLapTimes(){
		List <Long> snapshot = null;
		CopyOnWriteArrayList <Long> result = new CopyOnWriteArrayList <Long> ();
		
		synchronized(lock){
			snapshot = new ArrayList <Long> (timeList);
		}
		
		for(int i = 0; i<snapshot.size()-1; i++){
			result.add(snapshot.get(i+1) - snapshot.get(i));
		}
		
		return result;
	}
}
