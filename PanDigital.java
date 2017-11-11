import java.util.*;

/**
 * ProjectEuler problem: https://projecteuler.net/problem=43
 * 
 * Solution approach:
 * This solution generates all pandigital numbers that also satisfy the property.
 * If any number during its construction doesn't satisy the property, we throw it away.
 * time java PanDigital
    16695334890

    real	0m0.234s
    user	0m0.164s
    sys	0m0.035s
 */ 
class PanDigital {

    /**
     * This function performs the meat of the logic.
     * For each number, say "406", it generates a new number by appending one digit,
     * checks if this new number's last three digits is divisible by d, and then 
     * recurses untill we use 9 digits.
     */  
    void genAndCheck(long start, int divisibleBy, List<Integer> bag, int msb, List<Long> result) {
        for (int i = 0; i < bag.size(); i++) {
            int each = bag.get(i);
            long next = start * 10 + each;
            long lastThree = getLastThree(next);
            if (divisibleBy == 17 && lastThree % divisibleBy == 0) {
                int index = i == 0 ? 1: 0;
                result.add(bag.get(index) * 1000000000L + next);
            } else if (lastThree % divisibleBy == 0) {
                List<Integer> copy = new ArrayList<Integer>();
                copy.addAll(bag);
                copy.remove(Integer.valueOf(each));
                genAndCheck(next, getNextD(divisibleBy), copy, msb + 1, result);
            }
        }
    }

    long getLastThree(long a) {
        long result = a % 10;
        a /= 10;
        result = result + 10 * (a % 10);
        a /= 10;
        result = result + 100 * (a % 10);
        return result;
    }

    int getNextD(int d) {
        switch(d) {
            case 3:
                return 5;
            case 5:
                return 7;
            case 7:
                return 11;
            case 11:
                return 13;
            case 13:
                return 17;
        }
        return 0;
    }

    /**
     * Generate all three digit integers that are divisible by 2 and have no
     * repeated digits.
     * Each result integer is accompanied by a list containing all the integers 
     * from 0-9 that weren't used in that number.
     */ 
    void gen3(Map<Long, List<Integer>> result, long curr, int n, List<Integer> bag) {
        if (n == 0) {
            if (curr % 2 == 0) {
                List<Integer> bagCopy = new ArrayList<>();
                bagCopy.addAll(bag);
                result.put(curr, bagCopy);
            }
            return;
        }
        List<Integer> copy = new ArrayList<Integer>();
        copy.addAll(bag);
        for (int each: bag) {    
            copy.remove(Integer.valueOf(each));
            gen3(result, curr * 10 + each, n-1, copy);
            copy.add(each);
        }
    }

    void find() {
        long start = System.currentTimeMillis();
        List<Integer> bag = new ArrayList<>();
        bag.add(0);
        bag.add(1);
        bag.add(2);
        bag.add(3);
        bag.add(4);
        bag.add(5);
        bag.add(6);
        bag.add(7);
        bag.add(8);
        bag.add(9);
        Map<Long, List<Integer>> twos = new HashMap<>();
        gen3(twos, 0, 3, bag);
        List<Long> result = new ArrayList<>();
        for (long key : twos.keySet()) {
            genAndCheck(key, 3 , twos.get(key), 3, result);
        }
        long sum = 0;
        for (long each : result) {
            sum += each;
        }
        System.out.println(sum);
    }

    public static void main(String[] args) {
        new PanDigital().find();
    }
}