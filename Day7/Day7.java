import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day7 {
    public static void main(String[] args) {
        String filePath = Paths.get("Day7", "day7.txt").toString();
        char[][] grid = readInputAsGrid(filePath);

        System.out.println("Part 1: " + solvePart1(grid));
        System.out.println("Part 2: " + solvePart2(grid));
    }

    private static char[][] readInputAsGrid(String filePath) {
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

        int rows = lines.size();
        int cols = lines.isEmpty() ? 0 : lines.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        return grid;
    }

    private static long solvePart1(char[][] grid) {
        // TODO: Implement Part 1 logic
        long count =0;
        for (int i = 0; i < grid.length; i++) { // row
            for (int j = 0; j < grid[i].length; j++) { // column 
                if (grid[i][j] == 'S') {
                    grid[i+1][j] = '|';
                }else if(grid[i][j] == '|' && 
                        i+1 < grid.length  && 
                        i+1 < grid.length && 0 <= j-1 && j+1 < grid[i].length &&
                        grid[i+1][j] == '^'
                ){
                    grid[i+1][j-1] = '|';
                    grid[i+1][j+1] = '|';
                    count++;
                }else if (grid[i][j] == '|'  && i+1 < grid.length && 0 <= j-1 && 
                        j+1 < grid[i].length  && grid[i+1][j] == '.'){
                    grid[i+1][j] = '|';
                }
                
            }
        }
//        // print Grid
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
    //                System.out.print(grid[i][j]);
    //            }
    //            System.out.println();
    //        }
            return count;
    }


    private static long solvePart2(char[][] grid) {
        int H = grid.length;
        int W = grid[0].length;
        long[][] dp = new long[H][W];
        int startCol = 0;
        for (int i = 0; i < W; i++) {
            if (grid[0][i] == 'S') {
                startCol = i;
            }
        }
        dp[0][startCol] = 1;
        for (int i = 0; i < H - 1; i++) {
            for (int j = 0; j < W; j++) {
                long cnt = dp[i][j];
                if (cnt == 0) continue;
                char cell = grid[i][j];

                if (cell == '^') {
                    if (j - 1 >= 0) {
                        dp[i + 1][j - 1] += cnt;
                    }
                    if (j + 1 < W) {
                        dp[i + 1][j + 1] += cnt;
                    }
                } else {
                    dp[i + 1][j] += cnt;
                }
            }
        }
        // print Grid
//        for (int i = 0; i < dp.length; i++) {
//            for (int j = 0; j < dp[i].length; j++) {
//                        System.out.print(dp[i][j]);
//                System.out.print(' ');
//                    }
//                    System.out.println();
//                }
        // 一番下の行に残っているタイムライン本数の合計が答え
        long total = 0;
        for (int j = 0; j < W; j++) {
            total += dp[H - 1][j];
        }
        return total;
    }
    
}
