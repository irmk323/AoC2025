# Day 8: Playground

## Problem Description

You arrive at an underground playground where Elves are setting up a Christmas decoration project involving suspended electrical junction boxes. The task is to connect these boxes with light strings so electricity can reach every junction.

## The Challenge

The Elves have junction box positions in 3D space and want to connect pairs that are closest together according to straight-line (Euclidean) distance. Each connection creates circuits—groups of electrically connected boxes.

### Key Mechanics
- Connecting two boxes directly joins them into one circuit
- Connecting a bo  x to a circuit adds it to that circuit
- Connecting two boxes already in the same circuit does nothing

## Example Walkthrough

Given 20 junction boxes, the closest pair is `162,817,812` and `425,690,689`. Connecting them creates one circuit of 2 boxes and 18 individual circuits.

The next closest unconnected pair is `162,817,812` and `431,825,988`, expanding the first circuit to 3 boxes.

After ten connections, there are 11 circuits with sizes: 5, 4, 2, 2, 1, 1, 1, 1, 1, 1, 1.

The three largest circuits are 5, 4, and 2, giving: **5 × 4 × 2 = 40**

## Your Task (Part 1)

Connect the **1000 nearest junction box pairs** from your input. Then multiply together the sizes of the **three largest resulting circuits**.

---

## Hints

1. **Input Parsing**: Each line contains three integers representing x, y, z coordinates of a junction box. Parse these into a list of 3D points.

2. **Distance Calculation**: You need to calculate Euclidean distance between all pairs of junction boxes. The formula for distance between points (x1,y1,z1) and (x2,y2,z2) is:
   ```
   distance = sqrt((x2-x1)² + (y2-y1)² + (z2-z1)²)
   ```

3. **Finding Nearest Pairs**: Create a list of all possible pairs with their distances, then sort by distance. You'll need to process the first 1000 pairs.

4. **Union-Find (Disjoint Set Union)**: This is the perfect data structure for managing circuits! It efficiently:
   - Tracks which boxes belong to the same circuit
   - Merges circuits when you connect boxes
   - Counts circuit sizes

   Consider implementing:
   - `find(x)`: Find which circuit a box belongs to
   - `union(x, y)`: Connect two boxes (merge their circuits)
   - A way to track the size of each circuit

5. **Algorithm Flow**:
   - Parse all junction box coordinates
   - Generate all pairs with their distances
   - Sort pairs by distance (ascending)
   - For the first 1000 pairs, union the boxes
   - Count the sizes of all resulting circuits
   - Find the three largest circuits and multiply their sizes

6. **Edge Cases**:
   - Two boxes already in the same circuit (union does nothing)
   - Make sure you're using the correct distance metric
   - Consider using squared distances to avoid floating point precision issues

7. **Optimization Tips**:
   - You can avoid sqrt() by comparing squared distances
   - Use efficient data structures (HashMap, PriorityQueue, etc.)
   - Union-Find with path compression and union by rank is very efficient

## Data Structure Suggestion

```java
class UnionFind {
    private int[] parent;
    private int[] size;

    // Initialize each box as its own circuit
    public UnionFind(int n) { ... }

    // Find the root/circuit ID of a box
    public int find(int x) { ... }

    // Connect two boxes (merge circuits)
    public void union(int x, int y) { ... }

    // Get size of a circuit
    public int getSize(int x) { ... }
}
```

Good luck! This is a classic graph connectivity problem with a twist of finding k-nearest connections.
