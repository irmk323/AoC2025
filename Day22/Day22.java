import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day22 {
    public static void main(String[] args) {
        String filePath = Paths.get("Day22", "day22.txt").toString();
        List<String> lines = readInput(filePath);

        System.out.println("Part 1: " + solvePart1(lines));
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

    private static long solvePart1(List<String> lines) {
        // TODO: Implement Part 1 logic
        return 0;
    }
}
