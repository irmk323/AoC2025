import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        String filePath = Paths.get("Day1", "day1.txt").toString();
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

    private static int solvePart1(List<String> lines) {
        // TODO: Implement Part 1 solution
        int position = 50;
        int counter = 0;
        for (String line : lines) {
            if (line.startsWith("L") ){

                int value = Integer.parseInt(line.replaceAll("\\D", ""));
                int tempPosition =  position - (value%100 ) ;

                if(tempPosition < 0){
                    position = 100 - Math.abs(tempPosition) ;
                }else{
                    position = tempPosition;
                }
            }else if (line.startsWith("R") ){
                int value = Integer.parseInt(line.replaceAll("\\D", ""));
                int tempPosition = position + (value % 100);
                if(tempPosition  == 100){
                    position =  0;
                }else if(tempPosition > 100){
                    position =  tempPosition -100;
                }else{
                    position = tempPosition;
                }
            }
            System.out.println("position" + position + " ");
            if (position == 0){
                counter++;
            }
        }
        return counter;
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
