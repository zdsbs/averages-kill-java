package org.abm.averageskill;

public interface WorkOrderEventSource {
	void doWork();

	int getCurrentTime();

	boolean hasMoreEvents();

	int run();
}