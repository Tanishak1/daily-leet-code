import java.nio.file.*;
import java.util.*;
import javax.tools.*;

public class JavaSolutionTest {
    private static void check(boolean ok, String label) {
        if (!ok) throw new AssertionError(label);
        System.out.println("PASS " + label);
    }
    public static void main(String[] args) throws Exception {
        // Test each LeetCode Solution in isolation because each uses class Solution.
        testTwoSum();
        testLongestSubstring();
        testBestTimeToBuyAndSellStock();
        testContainsDuplicate();
        testValidAnagram();
    }
    private static void testTwoSum() throws Exception {
        try (var loader = compile("solutions/arrays/TwoSum.java")) {
            var constructor = loader.loadClass("Solution").getDeclaredConstructor();
            constructor.setAccessible(true);
            var instance = constructor.newInstance();
            var method = instance.getClass().getDeclaredMethod("twoSum", int[].class, int.class);
            method.setAccessible(true);
            check(Arrays.equals((int[])method.invoke(instance, new int[]{2,7,11,15}, 9), new int[]{0,1}), "two sum standard");
            check(Arrays.equals((int[])method.invoke(instance, new int[]{3,3}, 6), new int[]{0,1}), "two sum duplicates");
        }
    }
    private static void testLongestSubstring() throws Exception {
        try (var loader = compile("solutions/strings/LongestSubstringWithoutRepeatingCharacters.java")) {
            var constructor = loader.loadClass("Solution").getDeclaredConstructor();
            constructor.setAccessible(true);
            var instance = constructor.newInstance();
            var method = instance.getClass().getDeclaredMethod("lengthOfLongestSubstring", String.class);
            method.setAccessible(true);
            check((int)method.invoke(instance, "abcabcbb") == 3, "substring repeated");
            check((int)method.invoke(instance, "") == 0, "substring empty");
            check((int)method.invoke(instance, "bbbbb") == 1, "substring identical");
        }
    }
    private static Object invoke(String path, String methodName, Class<?>[] types, Object... args) throws Exception {
        try (var loader = compile(path)) {
            var constructor = loader.loadClass("Solution").getDeclaredConstructor();
            constructor.setAccessible(true);
            var instance = constructor.newInstance();
            var method = instance.getClass().getDeclaredMethod(methodName, types);
            method.setAccessible(true);
            return method.invoke(instance, args);
        }
    }
    private static void testBestTimeToBuyAndSellStock() throws Exception {
        String path = "solutions/arrays/BestTimeToBuyAndSellStock.java";
        check((int) invoke(path, "maxProfit", new Class<?>[]{int[].class}, new int[]{7,1,5,3,6,4}) == 5, "stock profitable");
        check((int) invoke(path, "maxProfit", new Class<?>[]{int[].class}, new int[]{7,6,4,3,1}) == 0, "stock decreasing");
        check((int) invoke(path, "maxProfit", new Class<?>[]{int[].class}, new int[]{5}) == 0, "stock single day");
    }
    private static void testContainsDuplicate() throws Exception {
        String path = "solutions/arrays/ContainsDuplicate.java";
        check((boolean) invoke(path, "containsDuplicate", new Class<?>[]{int[].class}, new int[]{1,2,3,1}), "duplicate present");
        check(!(boolean) invoke(path, "containsDuplicate", new Class<?>[]{int[].class}, new int[]{1,2,3,4}), "duplicate absent");
        check(!(boolean) invoke(path, "containsDuplicate", new Class<?>[]{int[].class}, new int[]{}), "duplicate empty");
    }
    private static void testValidAnagram() throws Exception {
        String path = "solutions/strings/ValidAnagram.java";
        check((boolean) invoke(path, "isAnagram", new Class<?>[]{String.class,String.class}, "anagram", "nagaram"), "anagram valid");
        check(!(boolean) invoke(path, "isAnagram", new Class<?>[]{String.class,String.class}, "rat", "car"), "anagram invalid");
        check(!(boolean) invoke(path, "isAnagram", new Class<?>[]{String.class,String.class}, "a", "aa"), "anagram unequal length");
    }
    private static java.net.URLClassLoader compile(String source) throws Exception {
        var directory = Files.createTempDirectory("leetcode-test");
        var compiler = ToolProvider.getSystemJavaCompiler();
        check(compiler.run(null, null, null, "-d", directory.toString(), source) == 0, "compile " + source);
        return new java.net.URLClassLoader(new java.net.URL[]{directory.toUri().toURL()});
    }
}
