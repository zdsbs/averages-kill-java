package org.abm.averageskill;

import org.abm.averageskill.simulation.Config;

public class ConfigBuilder {
	private final int workOrders;
	private final int workers;
	private final int transitionTime;
	private final int completionTime;
	
	public ConfigBuilder(int workOrders, int workers, int transitionTime, int completionTime) {
		this.workOrders = workOrders;
		this.workers = workers;
		this.transitionTime = transitionTime;
		this.completionTime = completionTime;
	}

	public static ConfigBuilder workOrders(int workOrders) {
		return new ConfigBuilder(workOrders, 0, 0,0);
	}

	public ConfigBuilder workers(int workers) {
		return new ConfigBuilder(workOrders, workers, transitionTime,completionTime);
	}

	public ConfigBuilder transitionTime(int transitionTime) {
		return new ConfigBuilder(workOrders, workers, transitionTime,completionTime);
	}

	public ConfigBuilder completionTime(int completionTime) {
		return new ConfigBuilder(workOrders, workers, transitionTime,completionTime);
	}

	public Config build() {
		return new Config(workOrders, workers, transitionTime,completionTime);
	}

}