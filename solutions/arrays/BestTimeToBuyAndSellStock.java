/** LeetCode 121: Best Time to Buy and Sell Stock.
 * Track the lowest price seen so far and the best achievable profit.
 * Time: O(n). Extra space: O(1).
 */
class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int best = 0;
        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            best = Math.max(best, price - minPrice);
        }
        return best;
    }
}
