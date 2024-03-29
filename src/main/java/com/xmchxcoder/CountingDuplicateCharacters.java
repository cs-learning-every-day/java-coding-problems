package com.xmchxcoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.xmchxcoder.utils.TimeUtil.displayExecutionTime;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class CountingDuplicateCharacters {
	private static final String TEXT = "Be strong, be fearless, be beautiful. "
			+ "And believe that anything is possible when you have the right "
			+ "people there to support you. ";
	// Ӝ -> Unicode: \u04DC, Code Point: 1244
	// 💕 -> Unicode: \\uDe3D\uDC95, Code Point: 128149
	// 🎼 -> \uD83C\uDFBC, Code Point: 127932
	// 😍 -> \uD83D\uDE0D, Code Point: 128525
	private static final String TEXT_CP = TEXT + "😍 I love 💕 you Ӝ so much 💕 😍 🎼🎼🎼!";

	public static Map<Character, Integer> solV1(String str) {
		Map<Character, Integer> result = new HashMap<>();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			result.compute(ch, (k, v) -> (v == null) ? 1 : v + 1);
		}

		return result;
	}

	public static Map<Character, Long> solV2(String str) {
		if (str == null || str.isBlank()) {
			return Collections.emptyMap();
		}
		return str.chars()
				.mapToObj(i -> (char) i)
				.collect(Collectors.groupingBy(c -> c, Collectors.counting()));

	}

	/**
	 * @see TestUnicode
	 */
	public static Map<String, Integer> solV3(String str) {
		Map<String, Integer> result = new HashMap<>();

		for (int i = 0; i < str.length(); i++) {
			int cp = str.codePointAt(i);
			String ch = String.valueOf(Character.toChars(cp));
			if (Character.charCount(cp) == 2) { // 2 means a surrogate pair
				i++;
			}
			result.compute(ch, (k, v) -> (v == null) ? 1 : v + 1);
		}
		return result;
	}

	public static Map<String, Long> solV4(String str) {
		if (str == null || str.isBlank()) {
			return Collections.emptyMap();
		}

		return str.codePoints()
				.mapToObj(cp -> String.valueOf(Character.toChars(cp)))
				.collect(Collectors.groupingBy(c -> c, Collectors.counting()));
	}

	public static void main(String[] args) {
		System.out.println("Input text: \n" + TEXT + "\n");

		System.out.println("\n\nASCII or 16 bits Unicode characters (less than 65,535 (0xFFFF)) examples:\n");

		System.out.println("HashMap based solution:");
		long startTimeV1 = System.nanoTime();

		Map<Character, Integer> duplicatesV1 = CountingDuplicateCharacters.solV1(TEXT);

		displayExecutionTime(System.nanoTime() - startTimeV1);
		System.out.println(Arrays.toString(duplicatesV1.entrySet().toArray()));
		// or: duplicatesV1.forEach( (k, v) -> System.out.print(k + "="+ v + ", "));

		System.out.println();
		System.out.println("Java 8, functional-style solution:");
		long startTimeV2 = System.nanoTime();

		Map<Character, Long> duplicatesV2 = CountingDuplicateCharacters.solV2(TEXT);

		displayExecutionTime(System.nanoTime() - startTimeV2);
		System.out.println(Arrays.toString(duplicatesV2.entrySet().toArray()));
		// or: duplicatesV2.forEach( (k, v) -> System.out.print(k + "="+ v + ", "));

		System.out.println("\n--------------------------------------\n");
		System.out.println("Input text: \n" + TEXT_CP + "\n");
		System.out.println("\n\nIncluding Unicode surrogate pairs examples:\n");
		System.out.println("HashMap based solution:");
		long startTimeV3 = System.nanoTime();

		Map<String, Integer> duplicatesV3 = CountingDuplicateCharacters.solV3(TEXT_CP);

		displayExecutionTime(System.nanoTime() - startTimeV3);
		System.out.println(Arrays.toString(duplicatesV3.entrySet().toArray()));
		// or: duplicatesV3.forEach( (k, v) -> System.out.print(k + "="+ v + ", "));

		System.out.println();
		System.out.println("Java 8, functional-style solution:");
		long startTimeV4 = System.nanoTime();

		Map<String, Long> duplicatesV4 = CountingDuplicateCharacters.solV4(TEXT_CP);

		displayExecutionTime(System.nanoTime() - startTimeV4);
		System.out.println(Arrays.toString(duplicatesV4.entrySet().toArray()));
		// or: duplicatesV4.forEach( (k, v) -> System.out.print(k + "="+ v + ", "));
	}


}
