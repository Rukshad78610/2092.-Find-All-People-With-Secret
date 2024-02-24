# 2092.-Find-All-People-With-Secret
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class UnionFind {
    private int[] id;

    public UnionFind(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
    }

    public void connect(int a, int b) {
        id[find(b)] = find(a);
    }

    public int find(int a) {
        return id[a] == a ? a : (id[a] = find(id[a]));
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public void reset(int a) {
        id[a] = a;
    }
}

public class Solution {
    public List<Integer> findAllPeople(int n, int[][] A, int firstPerson) {
        Arrays.sort(A, (a, b) -> Integer.compare(a[2], b[2])); // Sort the meetings in ascending order of meeting time
        UnionFind uf = new UnionFind(n);
        uf.connect(0, firstPerson); // Connect person 0 with the first person
        List<Integer> ppl = new ArrayList<>();
        for (int i = 0, M = A.length; i < M; ) {
            ppl.clear();
            int time = A[i][2];
            for (; i < M && A[i][2] == time; ++i) { // For all the meetings happening at the same time
                uf.connect(A[i][0], A[i][1]); // Connect the two persons
                ppl.add(A[i][0]); // Add both persons into the pool
                ppl.add(A[i][1]);
            }
            for (int person : ppl) { // For each person in the pool, check if he/she's connected with person 0.
                if (!uf.connected(0, person)) uf.reset(person); // If not, this person doesn't have the secret, reset it.
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (uf.connected(0, i)) ans.add(i); // Push all the persons who are connected with person 0 into the answer array
        }
        return ans;
    }
}
