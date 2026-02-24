    import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day9 {
    public static void main(String[] args) {
        String filePath = Paths.get("Day9", "day9.txt").toString();
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
    
    static class P {
        long x, y;
        P(long x, long y){
            this.x = x;
            this.y = y;
        }
    }
    
    private static long solvePart1(List<String> lines) {
        List<P> ps = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            ps.add(new P(
                    Long.parseLong(parts[0]),
                    Long.parseLong(parts[1])));
        }
        long max  = 0;
        for(int i = 0;i<ps.size()-1;i++){
            P a = ps.get(i);
            for (int j = i+1;j<ps.size();j++){
                P b = ps.get(j);
                long dx = Math.abs(a.x - b.x) +1;
                long dy = Math.abs(a.y - b.y) +1;
                long area = dx * dy;
                if(area > max){
                    max = area;
                }
            }
        }
        return max;
    }
}
