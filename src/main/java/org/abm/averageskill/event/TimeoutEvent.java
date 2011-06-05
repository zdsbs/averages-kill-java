package org.abm.averageskill.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "at")
public class TimeoutEvent  {
	private final int ticks;
}