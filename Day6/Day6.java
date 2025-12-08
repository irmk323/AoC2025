import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Comparator;

public class Day6 {
    public static void main(String[] args) {
        String filePath = Paths.get("Day6", "day6.txt").toString();
        List<String> strings = new ArrayList<>();
        readInput(filePath,strings);

        System.out.println("\nPart 1: " + solvePart1(strings));
//        System.out.println("Part 2: " + solvePart2(strings));
    }

    private static void readInput(String filePath, List<String> strings) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                strings.add(line);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static long solvePart1(List<String> strings) {
        List<List<String>> parsedStrings = new ArrayList<>();
        for(String line : strings) {
            String[] split = line.trim().split(" ");
            List<String> oneLine = new ArrayList<>();
            for(String element : split) {
                if(element.isEmpty()){
                    continue;
                }
                System.out.println(oneLine);
                oneLine.add(element);
            }
            parsedStrings.add(oneLine);
        }

        long sum =0;
        for(int i =0; i< parsedStrings.get(0).size(); i++){
            String mark = parsedStrings.get(parsedStrings.size()-1).get(i);
            if(mark.equals("+")){
                sum += added(parsedStrings , i);
            }else if (mark.equals("*")){
                sum += multiplied(parsedStrings, i);
            }
        }
        return sum;

    }
    private static long added(List<List<String>> strings, int idx) {
        long added =0;
        for(int i =0; i< strings.size()-1; i++){
            added += Long.valueOf(strings.get(i).get(idx));
        }
        System.out.println(added);
        return added;
    }
    private static long multiplied(List<List<String>> strings, int idx) {
        long multiplied =1;
        for(int i =0; i< strings.size()-1; i++){
            multiplied = multiplied * Long.valueOf(strings.get(i).get(idx));
        }
        System.out.println(multiplied);
        return multiplied;
    }



    private static long solvePart2(List<String> strings) {
    return 0L;
    }






}
