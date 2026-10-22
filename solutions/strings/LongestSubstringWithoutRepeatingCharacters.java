import java.util.HashMap;
import java.util.Map;

/** LeetCode 3: Longest Substring Without Repeating Characters.
 * Time O(n), auxiliary space O(min(n, distinct characters)).
 */
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int left = 0;
        int best = 0;
        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);
            if (lastSeen.containsKey(current)) {
                left = Math.max(left, lastSeen.get(current) + 1);
            }
            lastSeen.put(current, right);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
