package week9;


public class LongestBudgetFriendlyStreak {
    static int[] longestStreak(int[] costs, long budget) {
        int left = 0, sum = 0;
        int maxLength = 0, startIndex = -1;

        for (int right = 0; right < costs.length; right++) {
            sum += costs[right];

            while (sum > budget && left <= right) {
                sum -= costs[left];
                left++;
            }

            int length = right - left + 1;

            if (length > maxLength) {
                maxLength = length;
                startIndex = left;
            }
        }

        if (maxLength == 0)
            return new int[]{0, -1};

        return new int[]{maxLength, startIndex};
    }

    public static void main(String[] args) {
        int[] costs = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        long budget = 8;

        int[] result = longestStreak(costs, budget);
        System.out.println("(" + result[0] + ", " + result[1] + ")");

        int[] costs2 = {9, 10};
        int[] result2 = longestStreak(costs2, 8);
        System.out.println("(" + result2[0] + ", " + result2[1] + ")");
    }
}
