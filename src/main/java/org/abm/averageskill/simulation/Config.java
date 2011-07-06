package org.abm.averageskill.simulation;

import lombok.Data;

@Data
public class Config {
	private final int workOrders;
	private final int workers;
	private final int transitionTime;
	private final int completionTime;
}