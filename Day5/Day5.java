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

public class Day5 {
    public static void main(String[] args) {
        String filePath = Paths.get("Day5", "day5.txt").toString();

        List<FreshIdRange> ranges = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        readInput(filePath, ranges, ids);

        System.out.println("Ranges:");
        for (FreshIdRange range : ranges) {
            System.out.println(range);
        }

        System.out.println("\nIDs:");
        for (Long id : ids) {
            System.out.println(id);
        }

        System.out.println("\nPart 1: " + solvePart1(ranges, ids));
        System.out.println("Part 2: " + solvePart2(ranges, ids));
    }

    private static void readInput(String filePath, List<FreshIdRange> freshIdRange, List<Long> ids) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean readingRanges = true;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    readingRanges = false;
                    continue;
                }

                if (readingRanges) {
                    String strLine = line.trim();
                    String[] split = strLine.split("-");
                    long rangeStart = Long.valueOf(split[0]);
                    long rangeEnd = Long.valueOf(split[1]);
                    freshIdRange.add(new FreshIdRange(rangeStart, rangeEnd));
                } else {
                    ids.add(Long.parseLong(line.trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    record FreshIdRange (long start, long end) {}
    private static int solvePart1(List<FreshIdRange> freshIdRange, List<Long> ids) {

        List<FreshIdRange> merged = createMerge(freshIdRange);
        int ansCount = 0;
        for(Long id :ids){
            for(FreshIdRange range : merged){
                if( range.start <= id &&  id <=range.end ){
                    ansCount++;
                }
            }
        }
        return ansCount;
    }
    private static List<FreshIdRange> createMerge(List<FreshIdRange> freshIdRange) {
        // sort freshIdRange by start
        freshIdRange.sort(Comparator.comparingLong(FreshIdRange::start)
                .thenComparingLong(FreshIdRange::end));
        List<FreshIdRange> merged = new ArrayList<>();

        FreshIdRange cur = freshIdRange.get(0);

        // 2つ目以降の区間を順に見ていく
        for (int i = 1; i < freshIdRange.size(); i++) {
            FreshIdRange next = freshIdRange.get(i);

            // オーバーラップ or 接続しているならマージ
            if (next.start() <= cur.end()) {
                cur = new FreshIdRange(
                        cur.start(),
                        Math.max(cur.end(), next.end())
                );
            } else {
                // 重ならない → cur を確定して merged に入れる
                merged.add(cur);
                cur = next; // 新しい current
            }
        }

        // 最後の1個を追加
        merged.add(cur);
        return merged;
    }

    private static long solvePart2(List<FreshIdRange> freshIdRange, List<Long> ids) {
        List<FreshIdRange> merged = createMerge(freshIdRange);
        long sum = 0;
        for (FreshIdRange range : merged) {
//            System.out.println(range);
            sum += range.end-range.start +1;
        }
        return  sum;
    }




}
