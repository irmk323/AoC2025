import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        String filePath = Paths.get("Day2", "day2.txt").toString();
        List<String> lines = readInput(filePath);

        System.out.println("Part 1: " + solvePart1(lines));
//        System.out.println("Part 2: " + solvePart2(lines));
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

    private static int solvePart2(List<String> lines) {
        // TODO: Implement Part 2 solution
        int position = 50;
        int counter = 0;
        for (String line : lines) {
            if (line.startsWith("L") ){

                int value = Integer.parseInt(line.replaceAll("\\D", ""));
                int rounds = value / 100;

                int tempPosition =  position - (value%100 ) ;

                if(tempPosition < 0){
                    position = 100 - Math.abs(tempPosition) ;
                    counter++;

                }else{
                    position = tempPosition;
                }
                counter+=rounds;


            }else if (line.startsWith("R") ){
                int value = Integer.parseInt(line.replaceAll("\\D", ""));
                int rounds = value / 100;
                int tempPosition = position + (value % 100);
                if(tempPosition  == 100){
                    position =  0;
                    counter++;
                }else if(tempPosition > 100){
                    position =  tempPosition -100;
                    counter++;
                }else{
                    position = tempPosition;
                }
                counter+=rounds;

            }

        }
        return counter;
    }
}
