package com.project.todoappbackend;

import com.project.todoappbackend.config.RateLimitingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.*;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class ToDoAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoAppBackendApplication.class, args);

        System.out.println("Hello World!");
        System.out.println("The Program has started");

        int[] n = {9, 5, 8};

        int[] numbers = {89, 76, 54, 76, 32, 54, 98};

        findLargestNumber(n);
        factorial(5);
        System.out.println(factorialRe(5));


        for (int i = 0; i <= 6; i++) {
            System.out.print(fibonacciSeries(i) + " ");
        }

        System.out.println("Reversed Number " + reverseNumber(593));

        palindromeCheck(4554);

        countVowels("Prashant");

        uniqueString("Tableable");

        checkAnagram("Tea", "eat");

        sortArray(numbers);

        findSecondLargestElement(numbers);

        for (int i = 2; i <= 100; i++) {
            if (isPrimeNumber(i)) {
                System.out.print(i + " ");
            }
        }

        System.out.println();

        countFrequency(numbers);

        linearSearch(numbers, 54);

        binarySearch(numbers, 89);

        findNotRepeatingCharacter("Manishmanish");


    }

    public static void findLargestNumber(int[] numbers) {

        int max = numbers[0];

        if (numbers[0] < numbers[1] && numbers[2] < numbers[1]) {
            max = numbers[1];
        } else if (numbers[0] < numbers[2] && numbers[1] < numbers[2]) {
            max = numbers[2];
        }
        System.out.println("Largest number among three numbers: " + max);
    }

    public static void factorial(int n) {
        int result = 1;

        for (int i = 2; i <= n; i++) {
            result = result * i;
        }

        System.out.println("Factorial of " + n + " is " + result);
    }

    public static int factorialRe(int n) {
        // Base case
        if (n == 1) {
            return 1;
        } else {
            return n * factorialRe(n - 1);
        }
    }

    public static int fibonacciSeries(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacciSeries(n - 1) + fibonacciSeries(n - 2);
        }
    }

    public static int reverseNumber(int n) {

        int reversed = 0;
        int remainder;

        while (n > 0) {
            remainder = n % 10;
            reversed = (reversed * 10) + remainder;
            n = n / 10;
        }
        return reversed;
    }

    public static void palindromeCheck(int n) {
        boolean isPalindrome = true;
        int positiveNumber = Math.abs(n);
        String number = String.valueOf(positiveNumber);
        char[] chars = number.toCharArray();

        for (int i = 0, j = number.length() - 1; i < j; i++, j--) {
            if (chars[i] != chars[j]) {
                isPalindrome = false;
                break;
            }
        }

        if (isPalindrome) {
            System.out.println(n + " is a palindrome");
        } else {
            System.out.println(n + " is not a palindrome");
        }

    }


    public static void countVowels(String word) {
        int vowels = 0;
        int consonants = 0;

        char[] chars = word.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'a' && chars[i] <= 'z') {
                if (chars[i] == 'a' || chars[i] == 'e' || chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }

            }
        }

        System.out.println(word + " contains " + vowels + " vowel letters");
        System.out.println(word + " contains " + consonants + " consonant letters");

    }

    public static void uniqueString(String word) {

        HashSet<Character> exists = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        char[] chars = word.toCharArray();

        for (char c : chars) {

            if (!exists.contains(c)) {
                exists.add(c);
                sb.append(c);
            }
        }

        System.out.println(sb);
    }

    public static void checkAnagram(String s1, String s2) {

        if (s1.length() != s2.length()) {
            System.out.println(s1 + " & " + s2 + " are not anagram");
            return;
        }

        char[] c1 = s1.toLowerCase().toCharArray();
        char[] c2 = s2.toLowerCase().toCharArray();

        Arrays.sort(c1);
        Arrays.sort(c2);

        boolean isAnagram = Arrays.equals(c1, c2);

        if (isAnagram) {
            System.out.println(s1 + " & " + s2 + " are anagram");
        } else {
            System.out.println(s1 + " & " + s2 + " are not anagram");
        }
    }

    public static void sortArray(int[] numbers) {

        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] > numbers[j]) {
                    int temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
        System.out.println("Sorted Array: ");

        for (int i : numbers) {
            System.out.print(i + " ");
        }

    }

    public static void findSecondLargestElement(int[] numbers) {

        Arrays.sort(numbers);

        // If second last element is not equal to last element
        for (int i = numbers.length - 2; i >= 0; i--) {
            if (numbers[i] != numbers[numbers.length - 1]) {
                System.out.println("Second Largest Element in array is " + numbers[i]);
                break;
            }
        }

    }

    public static boolean isPrimeNumber(int n) {

        if (n < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static void countFrequency(int[] number) {

        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int n : number) {
            frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
        }

        System.out.println("Frequency of each element");

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }

    public static void linearSearch(int[] numbers, int target) {

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                System.out.println("Linear Search Element found at index " + i);
                return;
            }
        }

        System.out.println("Linear Search Element not found ");
    }

    public static void binarySearch(int[] numbers, int target) {

        Arrays.sort(numbers);

        int left = 0;
        int right = numbers.length - 1;

        while (left <= right) {
            int midValue = (left + right) / 2;

            if (numbers[midValue] == target) {
                System.out.println("Binary Search Element found at index " + midValue);
                return;
            } else if (target > numbers[midValue]) {
                left = midValue + 1;
            } else {
                right = midValue - 1;
            }

        }

        System.out.println("Binary Search Element not found");
    }

    public static void findNotRepeatingCharacter(String word) {

        char[] chars = word.toLowerCase().toCharArray();

        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : chars) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        for (char c : chars) {
            if (frequencyMap.get(c) == 1) {
                System.out.println(c + " is the first non-repeating character");
                return;
            }
        }

        System.out.println("All the characters are repeated");

    }


    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilterFilter(RateLimitingFilter rateLimitingFilter) {
        FilterRegistrationBean<RateLimitingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(rateLimitingFilter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);  // 1 order means this filters run first before any other filters
        return registration;
    }

}
