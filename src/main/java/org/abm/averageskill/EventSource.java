package org.abm.averageskill;

public interface EventSource {
	void doWork();

	int getCurrentTime();

	boolean hasMoreEvents();

	int run();
}