import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class Day8 {

    static class Position {
        final long x, y, z;
        Position(long x, long y, long z) { this.x = x; this.y = y; this.z = z; }
    }

    static class Edge {
        final int a, b;
        final long d2;
        Edge(int a, int b, long d2) { this.a = a; this.b = b; this.d2 = d2; }
    }

    static class DSU {
        final int[] parent;
        final int[] size;
        int components;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            components = n;
            for (int i = 0; i < n; i++) { parent[i] = i; size[i] = 1; }
        }

        int find(int x) {
            if (parent[x] == x) return x;
            parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (size[ra] < size[rb]) { int t = ra; ra = rb; rb = t; }
            parent[rb] = ra;
            size[ra] += size[rb];
            components--;
            return true;
        }
    }

    public static void main(String[] args) {
        String filePath = Paths.get("Day8", "day8.txt").toString();
        List<String> lines = readInput(filePath);

        List<Position> points = parsePoints(lines);
        List<Edge> edges = buildSortedEdges(points);

        System.out.println("Part 1: " + solvePart1(points.size(), edges)); // あなたの仮Part1
        System.out.println("Part 2: " + solvePart2(points, edges));
    }

    private static List<String> readInput(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    private static List<Position> parsePoints(List<String> lines) {
        List<Position> points = new ArrayList<>(lines.size());
        for (String line : lines) {
            String[] p = line.trim().split(",");
            long x = Long.parseLong(p[0]);
            long y = Long.parseLong(p[1]);
            long z = Long.parseLong(p[2]);
            points.add(new Position(x, y, z));
        }
        return points;
    }

    private static List<Edge> buildSortedEdges(List<Position> points) {
        int n = points.size();
        List<Edge> edges = new ArrayList<>(n * (n - 1) / 2);

        for (int i = 0; i < n - 1; i++) {
            Position pa = points.get(i);
            for (int j = i + 1; j < n; j++) {
                Position pb = points.get(j);
                long dx = pa.x - pb.x;
                long dy = pa.y - pb.y;
                long dz = pa.z - pb.z;
                long d2 = dx * dx + dy * dy + dz * dz;
                edges.add(new Edge(i, j, d2));
            }
        }

        edges.sort((e1, e2) -> {
            int c = Long.compare(e1.d2, e2.d2);
            if (c != 0) return c;
            c = Integer.compare(e1.a, e2.a);
            if (c != 0) return c;
            return Integer.compare(e1.b, e2.b);
        });

        return edges;
    }

    // あなたの「仮 Part1」：最初の1000本だけ採用して、成分サイズ上位3つの積
    private static long solvePart1(int n, List<Edge> edges) {
        DSU dsu = new DSU(n);

        int limit = Math.min(1000, edges.size());
        for (int k = 0; k < limit; k++) {
            Edge e = edges.get(k);
            dsu.union(e.a, e.b); // union成功/失敗どっちでもOK（Part1仕様がこれなら）
        }

        // rootごとのサイズを集める
        Map<Integer, Integer> rootToSize = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int r = dsu.find(i);
            rootToSize.put(r, dsu.size[r]);
        }

        List<Integer> sizes = new ArrayList<>(rootToSize.values());
        sizes.sort(Comparator.reverseOrder());
        return 1L * sizes.get(0) * sizes.get(1) * sizes.get(2);
    }

    // Part2：全部が1成分になるまでKruskal、最後に採用した辺の両端Xを掛ける
    private static long solvePart2(List<Position> points, List<Edge> edges) {
        DSU dsu = new DSU(points.size());
        Edge last = null;

        for (Edge e : edges) {
            if (dsu.union(e.a, e.b)) {
                last = e;
                if (dsu.components == 1) break;
            }
        }

        if (last == null) return -1;
        return points.get(last.a).x * points.get(last.b).x;
    }
}
