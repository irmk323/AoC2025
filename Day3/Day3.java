import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Day3 {
    public static void main(String[] args) {
        String filePath = Paths.get("Day3", "day4.txt").toString();
        List<String> lines = readInput(filePath);

        System.out.println("Part 1: " + solvePart1(lines));
        System.out.println("Part 2: " + solvePart2(lines));
    }

    private static List<String> readInput(String filePath) {
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

        return lines;
    }

    private static Long solvePart1(List<String> lines) {
        List<Long> numbers = new ArrayList<>();

        for (String line : lines) {

            int max = -1;
            int secMax = -1;

            int maxIndex = -1;
            int secondIndex = -1;

            char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                // convert char[i] to int
                int c = chars[i] - '0';

                if (c > max) {
                    secMax = max;
                    max = c;

                    secondIndex = maxIndex;
                    maxIndex = i;

                }else if (c > secMax) {
                    secMax = c;
                    secondIndex = i;
                }
            }
//            System.out.println("maxIndex" + maxIndex + " max" + max + " secMax" + secMax + " sec" + secondIndex);
            int jointed =0;
            // 987654321111111
            if(maxIndex < secondIndex){
                jointed = max * 10 + secMax;
            // 811111111111119
            }else if(secondIndex < maxIndex &&  maxIndex == chars.length -1 ){
                jointed = secMax * 10 + max;

//                818181911112111
            }else if( secondIndex < maxIndex && maxIndex < chars.length -1 ){

                int largetsNumInRight = findSecondLargestAfterMaxInRight(chars, maxIndex);

                jointed =  max * 10 + largetsNumInRight;
            }
            numbers.add((long) jointed);
        }
        return numbers.stream().mapToLong(Long::longValue).sum();

    }
    private static int findSecondLargestAfterMaxInRight(char[] chars, int maxIndex) {
        int max = -1;
        for (int i = maxIndex + 1; i < chars.length; i++) {
            int c = chars[i] - '0';
            if (c > max) {
                max = c;
            }
        }
        return max;
    }


    private static long solvePart2(List<String> lines) {
        List<Long> numbers = new ArrayList<>();
        for (String line : lines) {
            int remove = line.length() - 12; // the number we can stil remove
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < line.length(); i++) {
                char d = line.charAt(i);

                while (remove > 0
                        && result.length() > 0
                        && result.charAt(result.length() - 1) < d) {

                    result.deleteCharAt(result.length() - 1);
                    remove--; // yes we can remove this weak num!
                }

                result.append(d);
            }

            if (result.length() > 12) {
                // or while (result.length() > 12) result.deleteCharAt(result.length() - 1);
                result.setLength(12);
            }
            numbers.add(Long.parseLong(result.toString()));
        }
        return numbers.stream().mapToLong(Long::longValue).sum();
    }


}
