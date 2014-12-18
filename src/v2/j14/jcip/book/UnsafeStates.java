package v2.j14.jcip.book;

/**
 * UnsafeStates
 * <p/>
 * Allowing internal mutable state to escape
 *
 * @author Brian Goetz and Tim Peierls
 */
class UnsafeStates {
    private String[] states = new String[]{
        "AK", "AL" /*...*/
    };

    public String[] getStates() {
        return states;
    }
}
