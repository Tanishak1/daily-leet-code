// LeetCode 206: https://leetcode.com/problems/reverse-linked-list/
// Time: O(n); auxiliary space: O(1).
// LeetCode provides the ListNode definition.
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }
}
