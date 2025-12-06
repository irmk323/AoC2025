import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class Day4 {
    public static void main(String[] args) {
        String filePath = Paths.get("Day5", "day5.txt").toString();
        char[][] grid = readInput(filePath);

        System.out.println("Part 1: " + solvePart1(grid));
        System.out.println("Part 2: " + solvePart2(grid));
    }

    private static char[][] readInput(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        // Convert to 2D char array
        int rows = lines.size();
        int cols = rows > 0 ? lines.get(0).length() : 0;
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        return grid;
    }

    private static int solvePart1(char[][] grid) {
        int count= 0;
        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '@') {
                   if (checkEightCorners(grid, i, j, copy)){
                       count++;
                   }
                }
            }
        }
        return count;
    }
    // row == up(-1)/donn(+1)
    // column == right(+1)/left(-1)
    // [row][col]
    private static boolean checkEightCorners(char[][] grid, int i, int j , char[][] copy) {
        int atMarkCount = 0;

        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                { 0, -1},          { 0, 1},
                { 1, -1}, { 1, 0}, { 1, 1}
        };
        for (int[] direction : directions) {
            int x = i + direction[0];
            int y = j + direction[1];
            if( 0 <= x && x < grid.length && 0 <= y && y < grid[0].length && grid[x][y] == '@') {
                atMarkCount++;
            }
        }

        return atMarkCount < 4? true : false;

    }
    // 1. take count, and do remove then return
    // 2. if count > 0, do again with copy
    // 3

    record GridResult(char[][] grid, long sum) {}

    private static long solvePart2(char[][] grid) {
        long sum = 0;
        GridResult ans = createNewGrid(grid, sum);
        return ans.sum();
    }

    private static GridResult createNewGrid(char[][] grid, long sum) {
        int count= 0;
        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '@') {
                    if (checkEightCorners(grid, i, j, copy)){
                        copy[i][j] = 'x';
                        count++;
                    }
                }
            }
        }
        sum += count;
        if(count == 0){
            return new GridResult(copy, sum);
        }else{
            return createNewGrid(copy, sum);
        }
    }


}
