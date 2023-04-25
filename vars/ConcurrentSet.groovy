import java.util.concurrent.ConcurrentHashMap

class ConcurrentSet {

    Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    void add(String stageName) {
        set.add(stageName);
    }

    void remove(String stageName) {
        set.remove(stageName);
    }

    /**
    * Returns true if set is empty
    */
    boolean isEmpty() {
        return set.empty;
    }

    /**
    * returns list of items in set
    */
    List<String> toList() {
        return set.toList();
    }

}
