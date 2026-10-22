import java.util.HashMap;
import java.util.Map;

/** LeetCode 242: Valid Anagram.
 * Frequency map supports arbitrary Java char values.
 * Time: O(n). Extra space: O(k) for distinct characters.
 */
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i), b = t.charAt(i);
            counts.merge(a, 1, Integer::sum);
            counts.merge(b, -1, Integer::sum);
        }
        for (int count : counts.values()) {
            if (count != 0) return false;
        }
        return true;
    }
}
