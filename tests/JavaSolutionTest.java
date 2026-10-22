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
    private static java.net.URLClassLoader compile(String source) throws Exception {
        var directory = Files.createTempDirectory("leetcode-test");
        var compiler = ToolProvider.getSystemJavaCompiler();
        check(compiler.run(null, null, null, "-d", directory.toString(), source) == 0, "compile " + source);
        return new java.net.URLClassLoader(new java.net.URL[]{directory.toUri().toURL()});
    }
}
