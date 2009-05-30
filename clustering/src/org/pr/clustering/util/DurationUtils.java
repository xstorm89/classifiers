package org.pr.clustering.util;

import java.util.Calendar;

/**
 * @author Ahmad
 */
public class DurationUtils {

	private static long curTime = Calendar.getInstance().getTimeInMillis();
	
	public static void storeCurrentTime() {
		curTime = Calendar.getInstance().getTimeInMillis();
	}
	
	public static int getDurationInSec() {
		return (int) ((Calendar.getInstance().getTimeInMillis() - curTime) / 1000);
	}
	
}
