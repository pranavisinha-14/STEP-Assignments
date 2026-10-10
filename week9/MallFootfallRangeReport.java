package week9;

import java.util.*;

public class MallFootfallRangeReport {
    static List<Integer> footfallReport(int[] visitors, int[][] queries) {
        int n = visitors.length;
        int[] prefix = new int[n + 1];
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + visitors[i];
        }

        for (int[] q : queries) {
            int start = q[0];
            int end = q[1];
            result.add(prefix[end + 1] - prefix[start]);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};

        int[][] queries = {
            {0, 2},
            {2, 5},
            {4, 6},
            {3, 3}
        };

        System.out.println(footfallReport(visitors, queries));
    }
}