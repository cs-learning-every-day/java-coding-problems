package com.xmchxcoder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.xmchxcoder.utils.TimeUtil.displayExecutionTime;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class FirstNonRepeatedCharacter {

	// http://www.alansofficespace.com/unicode/unicd99.htm
	private static final int EXTENDED_ASCII_CODES = 256; // can be increased to 65535

	public static char firstNonRepeatedCharacterV1(String str) {
		if (str == null || str.isBlank()) {
			return Character.MIN_VALUE;
		}

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			int count = 0;
			for (int j = 0; j < str.length(); j++) {
				if (ch == str.charAt(j) && j != i) {
					count++;
					break;
				}
			}

			if (count == 0) {
				return ch;
			}
		}
		return Character.MIN_VALUE;
	}

	public static char firstNonRepeatedCharacterV2(String str) {
		if (str == null || str.isBlank()) {
			return Character.MIN_VALUE;
		}

		int[] flags = new int[EXTENDED_ASCII_CODES];
		for (int i = 0; i < flags.length; i++) {
			flags[i] = -1;
		}

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (flags[ch] == -1) {
				flags[ch] = i;
			} else {
				flags[ch] = -2;
			}
		}

		int position = Integer.MAX_VALUE;
		for (int i = 0; i < flags.length; i++) {
			if (flags[i] >= 0) {
				position = Math.min(position, flags[i]);
			}
		}
		return position == Integer.MAX_VALUE ? Character.MIN_VALUE : str.charAt(position);
	}

	public static char firstNonRepeatedCharacterV3(String str) {

		if (str == null || str.isBlank()) {
			// or throw IllegalArgumentException
			return Character.MIN_VALUE;
		}

		// LinkedHashMap 维护Key的插入顺序
		Map<Character, Integer> map = new LinkedHashMap<>();

		for (char ch : str.toCharArray()) {
			map.compute(ch, (k, v) -> (v == null) ? 1 : v + 1);
		}

		for (Map.Entry<Character, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				return entry.getKey();
			}
		}
		return Character.MIN_VALUE;
	}

	public static char firstNonRepeatedCharacterV4(String str) {

		if (str == null || str.isBlank()) {
			// or throw IllegalArgumentException
			return Character.MIN_VALUE;
		}

		Map<Integer, Long> chs = str.chars()
				.mapToObj(cp -> cp)
				.collect(Collectors.groupingBy(Function.identity(),
						LinkedHashMap::new, Collectors.counting()));

		return (char) (int) chs.entrySet().stream()
				.filter(e -> e.getValue() == 1L)
				.findFirst()
				.map(Map.Entry::getKey)
				.orElse(Integer.valueOf(Character.MIN_VALUE));
	}

	public static String firstNonRepeatedCharacterVCP4(String str) {

		if (str == null || str.isBlank()) {
			// or throw IllegalArgumentException
			return String.valueOf(Character.MIN_VALUE);
		}

		Map<Integer, Long> chs = str.codePoints()
				.mapToObj(cp -> cp)
				.collect(Collectors.groupingBy(Function.identity(),
						LinkedHashMap::new, Collectors.counting()));

		int cp = chs.entrySet().stream()
				.filter(e -> e.getValue() == 1L)
				.findFirst()
				.map(Map.Entry::getKey)
				.orElse(Integer.valueOf(Character.MIN_VALUE));

		return String.valueOf(Character.toChars(cp));
	}


	private static final String TEXT = "My high school, the Illinois Mathematics and Science Academy, "
			+ "showed me that anything is possible and that you're never too young to think big. "
			+ "At 15, I worked as a computer programmer at the Fermi National Accelerator Laboratory, "
			+ "or Fermilab. After graduating, I attended Stanford for a degree in economics and "
			+ "computer science.";

	// Ӝ -> Unicode: \u04DC, Code Point: 1244
	// 💕 -> Unicode: \uD83D\uDC95, Code Point: 128149
	private static final String TEXT_CP = "😍 💕 I Ӝ love you Ӝ so much 😍";

	public static void main(String[] args) {

		System.out.println("Input text: \n" + TEXT + "\n");

		System.out.println("\n\nASCII or 16 bits Unicode characters (less than 65,535 (0xFFFF)) examples:\n");

		System.out.println("Loop and break solution:");
		long startTimeV1 = System.nanoTime();

		char firstcharV1 = FirstNonRepeatedCharacter.firstNonRepeatedCharacterV1(TEXT);

		displayExecutionTime(System.nanoTime() - startTimeV1);
		System.out.println("Found character: " + firstcharV1);

		System.out.println();
		System.out.println(" 256 ASCII codes solution:");
		long startTimeV2 = System.nanoTime();

		char firstcharV2 = FirstNonRepeatedCharacter.firstNonRepeatedCharacterV2(TEXT);

		displayExecutionTime(System.nanoTime() - startTimeV2);
		System.out.println("Found character: " + firstcharV2);

		System.out.println();
		System.out.println("LinkedHashMap based solution:");
		long startTimeV3 = System.nanoTime();

		char firstcharV3 = FirstNonRepeatedCharacter.firstNonRepeatedCharacterV3(TEXT);

		displayExecutionTime(System.nanoTime() - startTimeV3);
		System.out.println("Found character: " + firstcharV3);

		System.out.println();
		System.out.println("Java 8, functional-style solution:");
		long startTimeV4 = System.nanoTime();

		char firstcharV4 = FirstNonRepeatedCharacter.firstNonRepeatedCharacterV4(TEXT);

		displayExecutionTime(System.nanoTime() - startTimeV4);
		System.out.println("Found character: " + firstcharV4);

		System.out.println("\n---------------------------------------------\n");

		System.out.println("Input text: \n" + TEXT_CP + "\n");

		System.out.println("\n\nIncluding Unicode surrogate pairs examples:\n");

		System.out.println();
		System.out.println("Java 8, functional-style solution:");
		long startTimeV5 = System.nanoTime();

		String firstcharV5 = FirstNonRepeatedCharacter.firstNonRepeatedCharacterVCP4(TEXT_CP);

		displayExecutionTime(System.nanoTime() - startTimeV5);
		System.out.println("Found character: " + firstcharV5);
	}


}
