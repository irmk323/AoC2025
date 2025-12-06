import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        String filePath = Paths.get("Day2", "day4.txt").toString();
        List<String> lines = readInput(filePath);

//        System.out.println("Part 1: " + solvePart1(lines));
        System.out.println("Part 2: " + solvePart2(lines));
    }

    private static List<String> readInput(String filePath) {
        List<String> values = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                for (String part : parts) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        values.add(trimmed);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return values;
    }

    private static long solvePart1(List<String> lines) {
        List<Long> numbers = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
//            System.out.println(start + " " + end);

            for (long i = start; i <= end; i++) {
                String strI = Long.toString(i);
                if (strI.length() % 2 == 0) {

                    if(judge(strI)){
                        System.out.println(strI);
                        numbers.add(Long.parseLong(strI));
                    }
                }
            }
        }
        return numbers.stream().reduce(0L, Long::sum);

    }
    private static boolean judge(String strI) {
        for (int i = 0; i < strI.length() /2; i++) {
            if(strI.charAt(i) != strI.charAt((strI.length() /2 ) + i)) {
                return false;
            }
        }
        return true;
    }

    private static long solvePart2(List<String> lines) {
        List<Long> numbers = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            for (long i = start; i <= end; i++) {
                String strI = Long.toString(i);
                int n = strI.length();

                int[] pi = buildPrefixFunctions(strI);
                int L = pi[n-1];
                int period = n - L;
                if(L > 0 && n % period == 0){
                    System.out.println(strI);
                    numbers.add(Long.parseLong(strI));
                }
            }
        }
        return numbers.stream().reduce(0L, Long::sum);
    }
    private static int[] buildPrefixFunctions(String strI) {
        int n = strI.length();
        int[] pi = new int[n];
        // str 1 1 2 3 1 1 2 3
        // pi  0 1 0 0 1 0 0 0
        for (int i = 1; i < n; i++) {
            int j = pi[i-1];

            while(j> 0 && strI.charAt(j) != strI.charAt(i)) {
               j=pi[j-1];
            }
            if(strI.charAt(i) == strI.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        return pi;
    }

}
