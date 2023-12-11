import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * The {@code RatingsMap} class represents the distribution of ratings in a node.
 * Each RatingsMap contains the possible ratings and the number of entries corresponding to each rating.
 */
public class RatingsMap extends LinkedHashMap<String, Integer> implements Serializable {

    /**
     * Default constructor for an empty {@code RatingsMap}.
     */
    public RatingsMap() {
    }

    /**
     * Constructs a {@code RatingsMap} object with the specified list of possible ratings.
     *
     * @param possibleRatings a list of possible ratings to initialize the map
     */
    public RatingsMap(ArrayList<String> possibleRatings) {
        // Initialize all ratings to zero
        for (String rating : possibleRatings) {
            put(rating, 0);
        }
    }

    /**
     * Initializes the RatingsMap with possible ratings from the settings loaded in the main program.
     */
    public void initialize() {
        // Get possible ratings from settings loaded in main
        UserConfig settings = Main.settings();
        ArrayList<String> possibleRatings = settings.ratings();

        // Initialize all ratings to zero
        for (String rating : possibleRatings) {
            put(rating, 0);
        }
    }

    /**
     * Get the key at the specified index in the LinkedHashMap.
     *
     * @param k the index of the key
     * @return the key at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public String keyAt(int k) {
        // Check that the specified index is not out of bounds
        if (k < 0 || k >= size()) {
            throw new IndexOutOfBoundsException("Index " + k + " is out of bounds for the LinkedHashMap");
        }

        // Get the key at that index
        Iterator<String> iterator = keySet().iterator();
        for (int i = 0; i < k; i++) {
            iterator.next();
        }

        return iterator.next();
    }

    /**
     * Get the value at the specified index in the LinkedHashMap.
     *
     * @param k the index of the value
     * @return the value at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public Integer valueAt(int k) {
        // Check that the specified index is not out of bounds
        if (k < 0 || k >= size()) {
            throw new IndexOutOfBoundsException("Index " + k + " is out of bounds for the LinkedHashMap");
        }

        // Get the value at that index
        Iterator<Integer> iterator = values().iterator();
        for (int i = 0; i < k; i++) {
            iterator.next();
        }

        return iterator.next();
    }

    /**
     * Increments the value associated with the specified key.
     *
     * @param key the key whose associated value is to be incremented
     */
    public void increment(String key) {
        if(get(key) == null){
            System.out.println(key);
        }
        merge(key, 1, Integer::sum);
    }

    /**
     * Increments the value at the specified index in the LinkedHashMap.
     *
     * @param k the index of the value to be incremented
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void incrementAt(int k) {
        String key = keyAt(k);
        increment(key);
    }
}