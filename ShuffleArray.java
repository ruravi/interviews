import java.util.*;
/**
 * Perform an in-place shuffle of an array. Each element has equal 
 * probability of being in every position of the array.
 */ 
class ShuffleArray {

    Random r = new Random();

    private int getRandom(int max) {
        return r.nextInt(max);
    }

    /**
     * Think of the solution as a permutation of numbers 0 to n-1.
     * Eg. [5, 1, 3, 4, 0, 2]
     * "5" in position 0 implies a[5] is to be placed at position 0.
     * Once a[5] is swapped in, the other n-1 numbers have an equal chance of 
     * ending up in position 1. This gives the total number of possibilities to 
     * be (n-1)!
     */
    private void shuffle(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int position = getRandom(n-i) + i;

            // Swap i and position.
            int temp = a[i];
            a[i] = a[position];
            a[position] = temp;
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,2,3,4};
        ShuffleArray s = new ShuffleArray();

        Map<String, Double> counts = new HashMap<>();
        int n = 1000;
        for (int i = 0; i < n; i++) {
            s.shuffle(a);
            String key = Arrays.toString(a);
            if (!counts.containsKey(key)) {
                counts.put(key, 1.0);
            }
            counts.put(key, counts.get(key) + 1);
        }
        for (String each: counts.keySet()) {
            counts.put(each, counts.get(each) / n);
        }
        System.out.println(counts.values());
    }
}