# Comprehensive String Problems Guide - Java Solutions

## Table of Contents
8. [Find Last Index of a Given Character in String](#8-find-last-index-of-a-given-character-in-string)
11. [Remove Spaces and Rewrite in Camel Case](#11-remove-spaces-and-rewrite-in-camel-case)
12. [Find Smallest and Largest Word in a String](#12-find-smallest-and-largest-word-in-a-string)
13. [Sort a String in Descending Order](#13-sort-a-string-in-descending-order)
14. [Split String Based on a Delimiter](#14-split-string-based-on-a-delimiter)
15. [String Compression](#15-string-compression)
16. [Check Strength of a Password](#16-check-strength-of-a-password)

---

## **8. Find Last Index of a Given Character in String**

### Problem Statement
Find the last occurrence index of a given character in a string. Return -1 if character is not found.

### Brute Force Approach: Linear Search from End
```java
public class LastIndexBruteForce {
    public static int lastIndexOf(String str, char ch) {
        if (str == null || str.length() == 0) {
            return -1;
        }
        
        // Search from end to beginning
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == ch) {
                return i;
            }
        }
        
        return -1; // Character not found
    }
    
    public static void main(String[] args) {
        String str = "programming";
        char ch = 'g';
        System.out.println(lastIndexOf(str, ch)); // Output: 9
    }
}
```
**Time Complexity:** O(n) - may need to scan entire string  
**Space Complexity:** O(1) - only using constant extra space  
**Explanation:** Simple reverse iteration through string until character is found

### Optimized Approach: Built-in Method
```java
public class LastIndexOptimized {
    public static int lastIndexOf(String str, char ch) {
        if (str == null) {
            return -1;
        }
        
        // Using built-in lastIndexOf method
        return str.lastIndexOf(ch);
    }
    
    // Alternative: Using StringBuilder reverse (if modification allowed)
    public static int lastIndexOfReverse(String str, char ch) {
        if (str == null || str.length() == 0) {
            return -1;
        }
        
        StringBuilder sb = new StringBuilder(str).reverse();
        int index = sb.indexOf(ch);
        
        return index == -1 ? -1 : str.length() - 1 - index;
    }
}
```
**Time Complexity:** O(n) - built-in method also searches linearly  
**Space Complexity:** O(1) for built-in, O(n) for reverse approach  
**Explanation:** Built-in method is optimized at implementation level; reverse approach creates new string

---

## **11. Remove Spaces and Rewrite in Camel Case**

### Problem Statement
Convert a string with spaces to camelCase format. First word remains lowercase, subsequent words have first letter capitalized.

### Brute Force Approach: Character by Character Processing
```java
public class CamelCaseBruteForce {
    public static String toCamelCase(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        
        String result = "";
        boolean capitalizeNext = false;
        boolean firstWord = true;
        
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            
            if (ch == ' ') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext && !firstWord) {
                    result += Character.toUpperCase(ch);
                } else {
                    result += Character.toLowerCase(ch);
                }
                capitalizeNext = false;
                firstWord = false;
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        String str = "hello world java programming";
        System.out.println(toCamelCase(str)); // Output: helloWorldJavaProgramming
    }
}
```
**Time Complexity:** O(n²) - string concatenation creates new strings each time  
**Space Complexity:** O(n²) - multiple intermediate strings created  
**Explanation:** String concatenation in loop is inefficient due to immutability

### Optimized Approach: StringBuilder with Split
```java
public class CamelCaseOptimized {
    public static String toCamelCase(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        
        String[] words = str.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 0) {
                if (i == 0) {
                    // First word - all lowercase
                    result.append(words[i].toLowerCase());
                } else {
                    // Subsequent words - capitalize first letter
                    result.append(Character.toUpperCase(words[i].charAt(0)));
                    result.append(words[i].substring(1).toLowerCase());
                }
            }
        }
        
        return result.toString();
    }
    
    // Alternative: Single pass with StringBuilder
    public static String toCamelCaseSinglePass(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;
        boolean firstWord = true;
        
        for (char ch : str.toCharArray()) {
            if (ch == ' ') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext && !firstWord) {
                    result.append(Character.toUpperCase(ch));
                } else {
                    result.append(Character.toLowerCase(ch));
                }
                capitalizeNext = false;
                firstWord = false;
            }
        }
        
        return result.toString();
    }
}
```
**Time Complexity:** O(n) - single pass through string  
**Space Complexity:** O(n) - StringBuilder grows linearly  
**Explanation:** StringBuilder avoids string concatenation overhead; split approach handles multiple spaces

---

## **12. Find Smallest and Largest Word in a String**

### Problem Statement
Find the shortest and longest words in a string. Return both words along with their lengths.

### Brute Force Approach: Multiple Passes
```java
public class SmallestLargestWordBruteForce {
    public static class WordResult {
        String smallest, largest;
        int smallestLength, largestLength;
        
        WordResult(String smallest, String largest) {
            this.smallest = smallest;
            this.largest = largest;
            this.smallestLength = smallest.length();
            this.largestLength = largest.length();
        }
        
        @Override
        public String toString() {
            return String.format("Smallest: %s (%d), Largest: %s (%d)", 
                               smallest, smallestLength, largest, largestLength);
        }
    }
    
    public static WordResult findSmallestLargest(String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        
        String[] words = str.trim().split("\\s+");
        if (words.length == 0) return null;
        
        // First pass: find minimum length
        int minLength = Integer.MAX_VALUE;
        for (String word : words) {
            if (word.length() < minLength) {
                minLength = word.length();
            }
        }
        
        // Second pass: find maximum length
        int maxLength = Integer.MIN_VALUE;
        for (String word : words) {
            if (word.length() > maxLength) {
                maxLength = word.length();
            }
        }
        
        // Third pass: find actual words
        String smallest = "", largest = "";
        for (String word : words) {
            if (word.length() == minLength && smallest.isEmpty()) {
                smallest = word;
            }
            if (word.length() == maxLength && largest.isEmpty()) {
                largest = word;
            }
        }
        
        return new WordResult(smallest, largest);
    }
}
```
**Time Complexity:** O(3n) = O(n) - three passes through words array  
**Space Complexity:** O(n) - words array storage  
**Explanation:** Multiple passes are inefficient; could be done in single pass

### Optimized Approach: Single Pass
```java
public class SmallestLargestWordOptimized {
    public static class WordResult {
        String smallest, largest;
        int smallestLength, largestLength;
        
        WordResult(String smallest, String largest) {
            this.smallest = smallest;
            this.largest = largest;
            this.smallestLength = smallest.length();
            this.largestLength = largest.length();
        }
        
        @Override
        public String toString() {
            return String.format("Smallest: %s (%d), Largest: %s (%d)", 
                               smallest, smallestLength, largest, largestLength);
        }
    }
    
    public static WordResult findSmallestLargest(String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        
        String[] words = str.trim().split("\\s+");
        if (words.length == 0) return null;
        
        String smallest = words[0];
        String largest = words[0];
        
        // Single pass to find both smallest and largest
        for (String word : words) {
            if (word.length() < smallest.length()) {
                smallest = word;
            }
            if (word.length() > largest.length()) {
                largest = word;
            }
        }
        
        return new WordResult(smallest, largest);
    }
    
    // Alternative: Without split (memory efficient)
    public static WordResult findSmallestLargestNoSplit(String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        
        String smallest = "";
        String largest = "";
        StringBuilder currentWord = new StringBuilder();
        
        str = str.trim() + " "; // Add space at end for processing last word
        
        for (char ch : str.toCharArray()) {
            if (ch == ' ') {
                if (currentWord.length() > 0) {
                    String word = currentWord.toString();
                    
                    if (smallest.isEmpty() || word.length() < smallest.length()) {
                        smallest = word;
                    }
                    if (largest.isEmpty() || word.length() > largest.length()) {
                        largest = word;
                    }
                    
                    currentWord.setLength(0); // Reset for next word
                }
            } else {
                currentWord.append(ch);
            }
        }
        
        return new WordResult(smallest, largest);
    }
    
    public static void main(String[] args) {
        String str = "I love Java programming language";
        WordResult result = findSmallestLargest(str);
        System.out.println(result); // Smallest: I (1), Largest: programming (11)
    }
}
```
**Time Complexity:** O(n) - single pass through string/words  
**Space Complexity:** O(n) for split approach, O(k) for no-split where k is longest word length  
**Explanation:** Single pass is more efficient; no-split approach saves memory by not storing all words

---

## **13. Sort a String in Descending Order**

### Problem Statement
Sort all characters in a string in descending order (highest to lowest ASCII value).

### Brute Force Approach: Selection Sort
```java
public class SortStringBruteForce {
    public static String sortDescending(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        char[] chars = str.toCharArray();
        
        // Selection sort in descending order
        for (int i = 0; i < chars.length - 1; i++) {
            int maxIndex = i;
            
            // Find maximum element in remaining array
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[j] > chars[maxIndex]) {
                    maxIndex = j;
                }
            }
            
            // Swap maximum element with current position
            if (maxIndex != i) {
                char temp = chars[i];
                chars[i] = chars[maxIndex];
                chars[maxIndex] = temp;
            }
        }
        
        return new String(chars);
    }
    
    public static void main(String[] args) {
        String str = "programming";
        System.out.println(sortDescending(str)); // Output: rrogamminpg
    }
}
```
**Time Complexity:** O(n²) - nested loops for selection sort  
**Space Complexity:** O(n) - char array creation  
**Explanation:** Selection sort is simple but inefficient for large strings

### Optimized Approach: Built-in Sort
```java
import java.util.Arrays;
import java.util.Collections;

public class SortStringOptimized {
    public static String sortDescending(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        // Convert to Character array for Collections.sort
        Character[] chars = new Character[str.length()];
        for (int i = 0; i < str.length(); i++) {
            chars[i] = str.charAt(i);
        }
        
        // Sort in descending order
        Arrays.sort(chars, Collections.reverseOrder());
        
        // Convert back to string
        StringBuilder result = new StringBuilder();
        for (char ch : chars) {
            result.append(ch);
        }
        
        return result.toString();
    }
    
    // Alternative: Using char array with Arrays.sort
    public static String sortDescendingChar(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        char[] chars = str.toCharArray();
        Arrays.sort(chars); // Sort ascending first
        
        // Reverse to get descending order
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        return new String(chars);
    }
    
    // Most efficient: Direct reverse sort with custom comparator
    public static String sortDescendingStream(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        return str.chars()
                 .mapToObj(c -> (char) c)
                 .sorted(Collections.reverseOrder())
                 .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                 .toString();
    }
}
```
**Time Complexity:** O(n log n) - efficient sorting algorithms  
**Space Complexity:** O(n) - temporary arrays/collections  
**Explanation:** Built-in sort uses optimized algorithms like TimSort; stream approach is functional but may have overhead

---

## **14. Split String Based on a Delimiter**

### Problem Statement
Split a string into an array of substrings based on a given delimiter character or string.

### Brute Force Approach: Manual Character Processing
```java
import java.util.ArrayList;
import java.util.List;

public class SplitStringBruteForce {
    public static String[] splitString(String str, String delimiter) {
        if (str == null || delimiter == null) {
            return new String[0];
        }
        
        List<String> parts = new ArrayList<>();
        String currentPart = "";
        
        // Process character by character
        for (int i = 0; i < str.length(); i++) {
            boolean foundDelimiter = false;
            
            // Check if delimiter starts at current position
            if (i + delimiter.length() <= str.length()) {
                String substring = str.substring(i, i + delimiter.length());
                if (substring.equals(delimiter)) {
                    foundDelimiter = true;
                    parts.add(currentPart);
                    currentPart = "";
                    i += delimiter.length() - 1; // Skip delimiter characters
                }
            }
            
            if (!foundDelimiter) {
                currentPart += str.charAt(i);
            }
        }
        
        // Add the last part
        parts.add(currentPart);
        
        return parts.toArray(new String[0]);
    }
    
    // Single character delimiter version
    public static String[] splitByChar(String str, char delimiter) {
        if (str == null) {
            return new String[0];
        }
        
        List<String> parts = new ArrayList<>();
        String currentPart = "";
        
        for (char ch : str.toCharArray()) {
            if (ch == delimiter) {
                parts.add(currentPart);
                currentPart = "";
            } else {
                currentPart += ch;
            }
        }
        
        parts.add(currentPart); // Add last part
        
        return parts.toArray(new String[0]);
    }
    
    public static void main(String[] args) {
        String str = "apple,banana,cherry,date";
        String[] result = splitByChar(str, ',');
        System.out.println(Arrays.toString(result)); // [apple, banana, cherry, date]
    }
}
```
**Time Complexity:** O(n²) - string concatenation in loop creates new strings  
**Space Complexity:** O(n²) - multiple string objects created  
**Explanation:** Manual approach is educational but inefficient due to string immutability

### Optimized Approach: Built-in Split and StringBuilder
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitStringOptimized {
    // Using built-in split method
    public static String[] splitString(String str, String delimiter) {
        if (str == null || delimiter == null) {
            return new String[0];
        }
        
        // Built-in split method (delimiter is regex)
        return str.split(delimiter);
    }
    
    // Custom implementation with StringBuilder for efficiency
    public static String[] splitStringCustom(String str, String delimiter) {
        if (str == null || delimiter == null) {
            return new String[0];
        }
        
        List<String> parts = new ArrayList<>();
        StringBuilder currentPart = new StringBuilder();
        
        int i = 0;
        while (i < str.length()) {
            // Check if delimiter starts at current position
            if (i + delimiter.length() <= str.length() && 
                str.substring(i, i + delimiter.length()).equals(delimiter)) {
                
                parts.add(currentPart.toString());
                currentPart.setLength(0); // Reset StringBuilder
                i += delimiter.length(); // Skip entire delimiter
            } else {
                currentPart.append(str.charAt(i));
                i++;
            }
        }
        
        parts.add(currentPart.toString()); // Add last part
        
        return parts.toArray(new String[0]);
    }
    
    // Optimized for single character delimiter
    public static String[] splitByCharOptimized(String str, char delimiter) {
        if (str == null) {
            return new String[0];
        }
        
        List<String> parts = new ArrayList<>();
        StringBuilder currentPart = new StringBuilder();
        
        for (char ch : str.toCharArray()) {
            if (ch == delimiter) {
                parts.add(currentPart.toString());
                currentPart.setLength(0);
            } else {
                currentPart.append(ch);
            }
        }
        
        parts.add(currentPart.toString());
        
        return parts.toArray(new String[0]);
    }
    
    // Handle multiple consecutive delimiters
    public static String[] splitIgnoreEmpty(String str, String delimiter) {
        if (str == null || delimiter == null) {
            return new String[0];
        }
        
        return Arrays.stream(str.split(delimiter))
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);
    }
    
    public static void main(String[] args) {
        String str = "apple,,banana,cherry,,date";
        System.out.println(Arrays.toString(splitString(str, ",")));
        // Output: [apple, , banana, cherry, , date]
        
        System.out.println(Arrays.toString(splitIgnoreEmpty(str, ",")));
        // Output: [apple, banana, cherry, date]
    }
}
```
**Time Complexity:** O(n) - single pass through string  
**Space Complexity:** O(n) - StringBuilder and result list  
**Explanation:** StringBuilder avoids string concatenation overhead; built-in split is highly optimized

---

## **15. String Compression**

### Problem Statement
Compress a string by replacing consecutive identical characters with the character followed by its count. For example: "SSSSSTTPPQ" → "5S2T2P1Q"

### Brute Force Approach: Multiple String Operations
```java
public class StringCompressionBruteForce {
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        
        String result = "";
        int i = 0;
        
        while (i < str.length()) {
            char currentChar = str.charAt(i);
            int count = 1;
            
            // Count consecutive characters
            while (i + 1 < str.length() && str.charAt(i + 1) == currentChar) {
                count++;
                i++;
            }
            
            // Add count and character to result
            result += count + "" + currentChar;
            i++;
        }
        
        return result;
    }
    
    // Alternative: Count first approach
    public static String compressCountFirst(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        
        String result = "";
        
        for (int i = 0; i < str.length(); ) {
            char ch = str.charAt(i);
            int count = 0;
            
            // Count occurrences of current character
            while (i < str.length() && str.charAt(i) == ch) {
                count++;
                i++;
            }
            
            result = result + count + ch;
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        String str = "SSSSSTTPPQ";
        System.out.println(compress(str)); // Output: 5S2T2P1Q
    }
}
```
**Time Complexity:** O(n²) - string concatenation creates new objects each time  
**Space Complexity:** O(n²) - multiple intermediate strings  
**Explanation:** String concatenation in loop is inefficient; creates many temporary objects

### Optimized Approach: StringBuilder
```java
public class StringCompressionOptimized {
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        
        StringBuilder compressed = new StringBuilder();
        int count = 1;
        
        for (int i = 0; i < str.length(); i++) {
            // If next character is same, increment count
            if (i + 1 < str.length() && str.charAt(i) == str.charAt(i + 1)) {
                count++;
            } else {
                // Add count and character to result
                compressed.append(count).append(str.charAt(i));
                count = 1; // Reset count
            }
        }
        
        return compressed.toString();
    }
    
    // Alternative: Two pointer approach
    public static String compressTwoPointer(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        
        StringBuilder result = new StringBuilder();
        int i = 0;
        
        while (i < str.length()) {
            char currentChar = str.charAt(i);
            int j = i;
            
            // Find end of current character sequence
            while (j < str.length() && str.charAt(j) == currentChar) {
                j++;
            }
            
            // Add count and character
            int count = j - i;
            result.append(count).append(currentChar);
            
            i = j; // Move to next different character
        }
        
        return result.toString();
    }
    
    // Return original if compression doesn't reduce size
    public static String compressOptimal(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        
        StringBuilder compressed = new StringBuilder();
        int count = 1;
        
        for (int i = 0; i < str.length(); i++) {
            if (i + 1 < str.length() && str.charAt(i) == str.charAt(i + 1)) {
                count++;
            } else {
                compressed.append(count).append(str.charAt(i));
                count = 1;
            }
        }
        
        // Return original string if compression doesn't help
        return compressed.length() < str.length() ? compressed.toString() : str;
    }
    
    public static void main(String[] args) {
        System.out.println(compress("SSSSSTTPPQ")); // 5S2T2P1Q
        System.out.println(compress("ABCDEF")); // 1A1B1C1D1E1F
        System.out.println(compressOptimal("ABCDEF")); // ABCDEF (original returned)
    }
}
```
**Time Complexity:** O(n) - single pass through string  
**Space Complexity:** O(n) - StringBuilder grows with result  
**Explanation:** StringBuilder avoids string concatenation overhead; optimal version prevents unnecessary compression

---



## **17. Missing Characters to Make a String Pangram**

### Problem Statement
A pangram is a sentence containing every letter of the alphabet at least once. Find which letters are missing from a string to make it a pangram.

### Brute Force Approach: Check Each Letter Individually
```java
import java.util.ArrayList;
import java.util.List;

public class PangramBruteForce {
    public static class PangramResult {
        boolean isPangram;
        List<Character> missingChars;
        int missingCount;
        
        PangramResult(boolean isPangram, List<Character> missing) {
            this.isPangram = isPangram;
            this.missingChars = missing;
            this.missingCount = missing.size();
        }
        
        @Override
        public String toString() {
            if (isPangram) {
                return "String is a pangram!";
            } else {
                return String.format("Missing %d letters: %s", 
                                   missingCount, missingChars);
            }
        }
    }
    
    public static PangramResult findMissingChars(String str) {
        if (str == null) {
            str = "";
        }
        
        String input = str.toLowerCase();
        List<Character> missing = new ArrayList<>();
        
        // Check each letter of alphabet separately
        for (char ch = 'a'; ch <= 'z'; ch++) {
            boolean found = false;
            
            // Search for current letter in string
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == ch) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                missing.add(ch);
            }
        }
        
        return new PangramResult(missing.isEmpty(), missing);
    }
    
    // Alternative: Using contains method
    public static PangramResult findMissingCharsContains(String str) {
        if (str == null) str = "";
        
        String input = str.toLowerCase();
        List<Character> missing = new ArrayList<>();
        
        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (input.indexOf(ch) == -1) { // Character not found
                missing.add(ch);
            }
        }
        
        return new PangramResult(missing.isEmpty(), missing);
    }
}
```
**Time Complexity:** O(26n) = O(n) - 26 searches through string  
**Space Complexity:** O(1) - constant space for missing list (max 26 chars)  
**Explanation:** Multiple searches through string are inefficient; each alphabet letter checked separately

### Optimized Approach: Single Pass with Boolean Array
```java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PangramOptimized {
    public static class PangramResult {
        boolean isPangram;
        List<Character> missingChars;
        int missingCount;
        double completionPercentage;
        
        PangramResult(boolean isPangram, List<Character> missing) {
            this.isPangram = isPangram;
            this.missingChars = missing;
            this.missingCount = missing.size();
            this.completionPercentage = ((26 - missing.size()) / 26.0) * 100;
        }
        
        @Override
        public String toString() {
            if (isPangram) {
                return "String is a pangram! (100% complete)";
            } else {
                return String.format("Missing %d letters: %s (%.1f%% complete)", 
                                   missingCount, missingChars, completionPercentage);
            }
        }
    }
    
    // Most efficient: Single pass with boolean array
    public static PangramResult findMissingChars(String str) {
        if (str == null) str = "";
        
        boolean[] present = new boolean[26]; // Track presence of each letter
        
        // Single pass through string
        for (char ch : str.toCharArray()) {
            if (ch >= 'a' && ch <= 'z') {
                present[ch - 'a'] = true;
            } else if (ch >= 'A' && ch <= 'Z') {
                present[ch - 'A'] = true;
            }
        }
        
        // Find missing characters
        List<Character> missing = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            if (!present[i]) {
                missing.add((char)('a' + i));
            }
        }
        
        return new PangramResult(missing.isEmpty(), missing);
    }
    
    // Alternative: Using HashSet
    public static PangramResult findMissingCharsSet(String str) {
        if (str == null) str = "";
        
        Set<Character> foundChars = new HashSet<>();
        
        // Add all alphabetic characters to set
        for (char ch : str.toLowerCase().toCharArray()) {
            if (ch >= 'a' && ch <= 'z') {
                foundChars.add(ch);
            }
        }
        
        // Find missing characters
        List<Character> missing = new ArrayList<>();
        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (!foundChars.contains(ch)) {
                missing.add(ch);
            }
        }
        
        return new PangramResult(missing.isEmpty(), missing);
    }
    
    // Advanced: With statistics and suggestions
    public static PangramResult analyzeString(String str) {
        if (str == null) str = "";
        
        int[] charCount = new int[26]; // Count frequency of each letter
        
        // Count occurrences of each letter
        for (char ch : str.toLowerCase().toCharArray()) {
            if (ch >= 'a' && ch <= 'z') {
                charCount[ch - 'a']++;
            }
        }
        
        // Find missing and rare characters
        List<Character> missing = new ArrayList<>();
        List<Character> rare = new ArrayList<>(); // Letters with count = 1
        
        for (int i = 0; i < 26; i++) {
            if (charCount[i] == 0) {
                missing.add((char)('a' + i));
            } else if (charCount[i] == 1) {
                rare.add((char)('a' + i));
            }
        }
        
        PangramResult result = new PangramResult(missing.isEmpty(), missing);
        
        // Add additional info for rare characters
        if (!rare.isEmpty() && missing.isEmpty()) {
            System.out.println("Rare characters (only 1 occurrence): " + rare);
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        String[] testStrings = {
            "The quick brown fox jumps over the lazy dog", // Complete pangram
            "Hello World",
            "Pack my box with five dozen liquor jugs", // Missing some letters
            ""
        };
        
        for (String test : testStrings) {
            System.out.println("String: \"" + test + "\"");
            System.out.println(findMissingChars(test));
            System.out.println("---");
        }
    }
}
```
**Time Complexity:** O(n) - single pass through string  
**Space Complexity:** O(1) - boolean array of fixed size 26  
**Explanation:** Single pass is optimal; boolean array provides O(1) lookup; HashSet approach uses more memory but is also efficient

---

## **Summary Table**

| Problem | Brute Force | Optimized | Key Optimization |
|---------|-------------|-----------|------------------|
| Last Index of Character | O(n), O(1) | O(n), O(1) | Built-in methods, reverse iteration |
| Camel Case Conversion | O(n²), O(n²) | O(n), O(n) | StringBuilder instead of concatenation |
| Smallest/Largest Word | O(3n), O(n) | O(n), O(n) | Single pass instead of multiple |
| Sort String Descending | O(n²), O(n) | O(n log n), O(n) | Built-in sort algorithms |
| Split String | O(n²), O(n²) | O(n), O(n) | StringBuilder + built-in methods |
| String Compression | O(n²), O(n²) | O(n), O(n) | StringBuilder for concatenation |
| Password Strength | O(5n), O(1) | O(n), O(1) | Single pass with early termination |
| Missing Pangram Chars | O(26n), O(1) | O(n), O(1) | Boolean array for tracking |

## **Key Optimization Techniques**

1. **StringBuilder Usage**: Avoid string concatenation in loops
2. **Single Pass**: Combine multiple checks in one iteration
3. **Early Termination**: Stop when all criteria are met
4. **Built-in Methods**: Use optimized library functions
5. **Appropriate Data Structures**: Boolean arrays, HashSets for lookups
6. **Space-Time Tradeoffs**: Sometimes use more memory for better time complexity

This comprehensive guide covers various string manipulation problems with both educational brute force approaches and efficient optimized solutions, helping understand the evolution from basic to advanced problem-solving techniques.
            return new StrengthResult("Invalid", new String[]{"Password is null"}, 0);
        }
        
        // Check each requirement separately (multiple passes)
        boolean hasMinLength = password.length() >= 8;
        boolean hasUppercase = hasUppercaseChar(password);
        boolean hasLowercase = hasLowercaseChar(password);
        boolean hasDigit = hasDigitChar(password);
        boolean hasSpecial = hasSpecialChar(password);
        
        // Calculate score
        int score = 0;
        if (hasMinLength) score++;
        if (hasUppercase) score++;
        if (hasLowercase) score++;
        if (hasDigit) score++;
        if (hasSpecial) score++;
        
        // Determine strength
        String strength;
        switch (score) {
            case 5: strength = "Very Strong"; break;
            case 4: strength = "Strong"; break;
            case 3: strength = "Medium"; break;
            case 2: strength = "Weak"; break;
            default: strength = "Very Weak"; break;
        }
        
        // Find missing requirements
        String[] missing = findMissingRequirements(hasMinLength, hasUppercase, 
                                                 hasLowercase, hasDigit, hasSpecial);
        
        return new StrengthResult(strength, missing, score);
    }
    
    private static boolean hasUppercaseChar(String password) {
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) return true;
        }
        return false;
    }
    
    private static boolean hasLowercaseChar(String password) {
        for (char ch : password.toCharArray()) {
            if (Character.isLowerCase(ch)) return true;
        }
        return false;
    }
    
    private static boolean hasDigitChar(String password) {
        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) return true;
        }
        return false;
    }
    
    private static boolean hasSpecialChar(String password) {
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        for (char ch : password.toCharArray()) {
            if (specialChars.indexOf(ch) != -1) return true;
        }
        return false;
    }
    
    private static String[] findMissingRequirements(boolean... checks) {
        String[] requirements = {"Minimum 8 characters", "Uppercase letter", 
                               "Lowercase letter", "Digit", "Special character"};
        
        StringBuilder missing = new StringBuilder();
        for (int i = 0; i < checks.length; i++) {
            if (!checks[i]) {
                if (missing.length() > 0) missing.append(",");
                missing.append(requirements[i]);
            }
        }
        
        return missing.length() == 0 ? new String[0] : missing.toString().split(",");
    }
}
```
**Time Complexity:** O(5n) = O(n) - five separate passes through password  
**Space Complexity:** O(1) - constant extra space for flags  
**Explanation:** Multiple passes through string are inefficient; should combine checks

### Optimized Approach: Single Pass
```java
import java.util.ArrayList;
import java.util.List;

public class PasswordStrengthOptimized {
    public static class StrengthResult {
        String strength;
        List<String> missingRequirements;
        int score;
        boolean isValid;
        
        StrengthResult(String strength, List<String> missing, int score, boolean valid) {
            this.strength = strength;
            this.missingRequirements = missing;
            this.score = score;
            this.isValid = valid;
        }
        
        @Override
        public String toString() {
            if (!isValid) return "Invalid password";
            
            String missing = missingRequirements.isEmpty() ? "None" : 
                           String.join(", ", missingRequirements);
            return String.format("Strength: %s (Score: %d/5)\nMissing: %s", 
                               strength, score, missing);
        }
    }
    
    public static StrengthResult checkPasswordStrength(String password) {
        if (password == null) {