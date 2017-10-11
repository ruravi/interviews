import java.util.*;

/**
 * A sack can hold upto W kgs of cake. You as a thief need to maximize 
 * the value of cakes that you put into the sack.
 */ 
class Knapsack {

    private static class CakeType {
        int weight;
        int value;

        CakeType(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    CakeType[] cakeTypes = new CakeType[]{
        new CakeType(7, 160),
        new CakeType(3, 90),
        new CakeType(2, 15)
    };

    int maxValue(int W, Map<Integer, Integer> memo) {
        if (W == 0) {
            return 0;
        }
        int m = Integer.MIN_VALUE;
        // Try each type of cake and recurse on the remaining weight.
        // The best total value wins.
        for (CakeType each : cakeTypes) {
            int remaining = W - each.weight;
            int currValue = each.value;
            if (remaining >= 0) {
                if (memo.containsKey(remaining)) {
                    currValue += memo.get(remaining);
                } else {
                    int t = maxValue(remaining, memo);
                    memo.put(remaining, t);
                    currValue += t;
                }
                if (currValue > m) {
                    m = currValue;
                }
            }
        }
        return Math.max(0, m);
    }

    public static void main(String[] args) {
        Knapsack k = new Knapsack();
        System.out.println(k.maxValue(20, new HashMap<>()));
    }

}