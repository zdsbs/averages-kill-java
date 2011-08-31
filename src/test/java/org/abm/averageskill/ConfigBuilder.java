package org.abm.averageskill;

import org.abm.averageskill.simulation.Config;

public class ConfigBuilder {
	private final int workOrders;
	private final WorkerConfig[] workerConfigs;
	
	public ConfigBuilder(int workOrders, WorkerConfig[] workerConfigs) {
		this.workOrders = workOrders;
		this.workerConfigs = workerConfigs;
	}
	
	public static ConfigBuilder workOrders(int workOrders) {
		return new ConfigBuilder(workOrders, null);
	}
	
	public ConfigBuilder workers(WorkerConfig... workerConfigs) {
		return new ConfigBuilder(workOrders, workerConfigs);
	}
	
	public Config build() {
		WorkerConfig workerConfigProtoType = workerConfigs[0];
		return new Config(workOrders, workerConfigs.length, workerConfigProtoType.getTransitionTime(), workerConfigProtoType.getCompletionTime());
	}
	
	public static WorkerConfig worker(int completionTime, int transitionTime) {
		return new WorkerConfig(completionTime, transitionTime);
	}
}