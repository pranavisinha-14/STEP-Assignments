package week9;

import java.util.*;

public class NetBalancePeriodCounter {
    static int countPeriods(int[] transactions, int k) {
        Map<Long, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0L, 1);

        long sum = 0;
        int count = 0;

        for (int value : transactions) {
            sum += value;

            count += prefixCount.getOrDefault(sum - k, 0);
            prefixCount.put(sum,
                    prefixCount.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] transactions = {3, 4, -7, 1, 3, 3, 1, -4};
        System.out.println(countPeriods(transactions, 7));

        int[] transactions2 = {1, 2, 3};
        System.out.println(countPeriods(transactions2, 10));
    }
}