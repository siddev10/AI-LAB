import java.util.*;

public class Prob30_CSP_Cryptarithmetic {
    static String word1, word2, result;
    static List<Character> charList;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Cryptarithmetic CSP ===");
        System.out.print("Enter first word (e.g., SEND): "); word1 = sc.next().toUpperCase();
        System.out.print("Enter second word (e.g., MORE): "); word2 = sc.next().toUpperCase();
        System.out.print("Enter result word (e.g., MONEY): "); result = sc.next().toUpperCase();

        System.out.println("\nSolving " + word1 + " + " + word2 + " = " + result + " ...\n");
        if (!solve()) {
            System.out.println("No solution exists.");
        }
    }

    static boolean solve() {
        Set<Character> chars = new HashSet<>();
        for (char c : (word1 + word2 + result).toCharArray()) chars.add(c);
        
        charList = new ArrayList<>(chars);
        if (charList.size() > 10) {
            System.out.println("Too many unique characters (Max 10).");
            return false;
        }
        
        int[] map = new int[256];
        Arrays.fill(map, -1);
        boolean[] used = new boolean[10];
        
        return backtrack(0, map, used);
    }

    static boolean backtrack(int idx, int[] map, boolean[] used) {
        if (idx == charList.size()) {
            // Check if leading letters are 0
            if (map[word1.charAt(0)] == 0 || map[word2.charAt(0)] == 0 || map[result.charAt(0)] == 0) return false;
            
            long v1 = getVal(word1, map), v2 = getVal(word2, map), vr = getVal(result, map);
            if (v1 + v2 == vr) {
                System.out.println("=== Solution Found! ===");
                System.out.println(v1 + " + " + v2 + " = " + vr);
                System.out.println("\nCharacter Mapping:");
                for (char c : charList) {
                    System.out.println(c + " = " + map[c]);
                }
                return true;
            }
            return false;
        }

        char c = charList.get(idx);
        for (int i = 0; i < 10; i++) {
            if (!used[i]) {
                map[c] = i;
                used[i] = true;
                if (backtrack(idx + 1, map, used)) return true; // CSP Backtracking
                used[i] = false;
                map[c] = -1;
            }
        }
        return false;
    }

    static long getVal(String s, int[] map) {
        long val = 0;
        for (char c : s.toCharArray()) val = val * 10 + map[c];
        return val;
    }
}
