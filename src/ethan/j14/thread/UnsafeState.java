package ethan.j14.thread;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ethan
 * @author vin
 * @since 2014-7-16
 */

public class UnsafeState {

    private String state = "safe";
    private ConcurrentHashMap<String, String> mConCurrentHashMap;
    private HashMap<String, String> mHashMap;

    public String getState() {
        return state;
    }

    public static void main(String[] args) {
        UnsafeState unsafeState = new UnsafeState();
        String state = unsafeState.getState();
        System.out.println(state);
    }
}
