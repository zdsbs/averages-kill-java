package org.abm.averageskill.simulation;

import lombok.RequiredArgsConstructor;

import org.abm.averageskill.event.TimeoutEvent;

@RequiredArgsConstructor
public class TimeoutMonitor implements Tickee{
	private final int timeout;
	private final TimeoutListener timeoutListener;

	@Override
	public void tick(int atTime) {
		if (atTime >= timeout)
			timeoutListener.onTimeout(TimeoutEvent.at(atTime));
	}
}