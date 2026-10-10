package week9;
import java.util.*;

public class SpiralStockAuditRoute {
    static List<Integer> auditRoute(int[][] grid) {
        List<Integer> result = new ArrayList<>();

        if (grid == null || grid.length == 0
                || grid[0].length == 0)
            return result;

        int top = 0, bottom = grid.length - 1;
        int left = 0, right = grid[0].length - 1;

        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++)
                result.add(grid[top][j]);
            top++;

            for (int i = top; i <= bottom; i++)
                result.add(grid[i][right]);
            right--;

            if (top <= bottom) {
                for (int j = right; j >= left; j--)
                    result.add(grid[bottom][j]);
                bottom--;
            }

            if (left <= right) {
                for (int i = bottom; i >= top; i--)
                    result.add(grid[i][left]);
                left++;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] grid = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };

        System.out.println(auditRoute(grid));
    }
}
