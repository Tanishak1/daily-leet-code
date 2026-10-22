// LeetCode 238: https://leetcode.com/problems/product-of-array-except-self/
// Time: O(n), Auxiliary space: O(1), excluding output
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] answer = new int[nums.length];
        int prefix = 1;
        for (int i = 0; i < nums.length; i++) {
            answer[i] = prefix;
            prefix *= nums[i];
        }
        int suffix = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            answer[i] *= suffix;
            suffix *= nums[i];
        }
        return answer;
    }
}
