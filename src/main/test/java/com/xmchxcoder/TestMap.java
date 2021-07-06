package com.xmchxcoder;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class TestMap {

	@Test
	void testMap() {
		Map<String, Integer> map = new HashMap<>();
		map.put("ABC", 1);

		assertEquals(4, map.computeIfAbsent("ABCD", k -> k.length()));
		assertEquals(4, map.computeIfPresent("ABCD", (k, v) -> k.length()));
	}
}
