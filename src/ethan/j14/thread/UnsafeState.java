package ethan.j14.thread;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @since 2014-7-16
 * @author v2
 */

public class UnsafeState {

	private String state="safe";
	private ConcurrentHashMap<String, String> mConCurrentHashMap ;
	private HashMap<String, String> mHashMap;
	public String getState(){
		return state;
	}
	
	public static void main(String[] args) {
		UnsafeState unsafeState =  new UnsafeState();
		String state = unsafeState.getState();
		System.out.println(state);
	}
}
