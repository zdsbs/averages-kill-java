package org.abm.averageskill;

import lombok.Data;


@Data
public class WorkerConfig {
	//Danger don't re-arange the fields
	private final int completionTime;
	private final int transitionTime;
	public static final WorkerConfig NULL = new WorkerConfig(0, 0);
	
}
