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

        System.out.println("Part 1: " + solvePart1(strings));
        System.out.println("Part 2: " + solvePart2(strings));
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

        return added;
    }
    private static long multiplied(List<List<String>> strings, int idx) {
        long multiplied =1;
        for(int i =0; i< strings.size()-1; i++){
            multiplied = multiplied * Long.valueOf(strings.get(i).get(idx));
        }
        return multiplied;
    }


    private static long solvePart2(List<String> strings) {
        //get last line as operator
        int charLength = strings.get(0).length();
        String[] ops = strings.get(strings.size() -1).trim().split("\\s+");

        // set all to chars
        char[][] chr = new char[strings.size()-1][charLength];
        for(int i =0; i< strings.size()-1; i++) {
            char[] chars = strings.get(i).toCharArray();
            chr[i] = chars;
        }
        ArrayList<Long> columnResults = new ArrayList<>();

        boolean newProblem = true;
        long colTotal = 0;

        int opsIndex = 0;
        for(int i =0; i< charLength; i++) {
            boolean allSpaceColumn = true;

            if(newProblem){
                colTotal = 0;
                newProblem = false;
                if(ops[opsIndex].equals("*")){
                    colTotal = 1;
                }
            }
            int verticalNum = 0;
            // pick up char from every row
            for(int j =0; j<  chr.length; j++) {
//                [[1, 2, 3,  , 3, 2, 8,  ,  , 5, 1,  , 6, 4],
//                [ , 4, 5,  , 6, 4,  ,  , 3, 8, 7,  , 2, 3],
//                [ ,  , 6,  , 9, 8,  ,  , 2, 1, 5,  , 3, 1, 4]]

                char cell = chr[j][i];
                if (cell == ' ') {
                } else {
                    allSpaceColumn = false;
                    verticalNum = verticalNum * 10 + Character.getNumericValue(cell);
                }
            }

                if (allSpaceColumn) {
                    columnResults.add(colTotal);
                    newProblem = true;
                    opsIndex++;
                    continue;
                }
//            System.out.println("ops[opsIndex]" + ops[opsIndex]);
                if (ops[opsIndex].equals("+")) {
                    colTotal += verticalNum;
                } else {
                    colTotal *= verticalNum;
                }
        }
  
        columnResults.add(colTotal);

//        System.out.println(Arrays.deepToString(chr));
    return columnResults.stream().reduce(0L, Long::sum);

    }
}
