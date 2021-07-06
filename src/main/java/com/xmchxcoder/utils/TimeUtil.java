package com.xmchxcoder.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class TimeUtil {
	public static void displayExecutionTime(long time) {
		System.out.println("Execution time: " + time + " ns" + " (" +
				TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS) + " ms)");
	}
}
