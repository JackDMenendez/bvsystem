package org.bv.com.system;

import java.util.Timer;
import java.util.TimerTask;

public class TimeCalibration extends TimerTask {
	public static final long timeToRunMs = 1;
	public static long countOfRuns = 1000;
	public static long countOfRunsLeft = countOfRuns;
    private long accumulator = 0;
    private long start;
    private long count = 0;
    public static long CalculatedNanosPerMS = 1000000;
    public static double error = 0.0;
    public static Timer calibrationTimer = new Timer();
    public TimeCalibration() {
    	calibrationTimer.schedule(this, 1, timeToRunMs);
    }
	@Override
	public void run() {
		if (start == 0) {
				start = System.nanoTime();
				return;
		}
		else {
			accumulator += System.nanoTime() - start;
			start = System.nanoTime();
			if (--countOfRunsLeft == 0) {
				calibrationTimer.cancel();
			}
			count++;
			final var averageNanosPerMS = accumulator /
				((double) timeToRunMs * (double) count);
			error = (double) (accumulator - count*timeToRunMs*1000000) / (double) (count*timeToRunMs*1000000);
			CalculatedNanosPerMS = (long) (averageNanosPerMS - error * averageNanosPerMS);
		}
	}
}