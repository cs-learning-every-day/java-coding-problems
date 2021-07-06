package com.xmchxcoder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class TestUnicode {
	@Test
	void test() {
		// unicode: 需要表示的东西远远 超过16位能代表的，需要两个char 代表 1char=2byte=16bit
		// 💕: \uD83D\uDC95
		String str = String.valueOf(Character.toChars(128149));
		assertEquals("\uD83D\uDC95", str);
		assertEquals(2, Character.charCount(128149));
		assertEquals(2, str.length());
		assertEquals(1, str.codePointCount(0, str.length()));
		// 代表💕
		assertEquals(128149, str.codePointAt(0));
		// 代表💕中的一个char
		assertEquals(0xDC95, str.codePointAt(1));
	}
}

