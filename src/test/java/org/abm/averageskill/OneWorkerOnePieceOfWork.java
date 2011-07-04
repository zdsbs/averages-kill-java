package org.abm.averageskill;

import static org.junit.Assert.assertEquals;
import lombok.Data;

import org.abm.averageskill.simulation.Simulation;
import org.junit.Test;


public class OneWorkerOnePieceOfWork {

	@Data
	public static class Results {

		public final int numberOfWorkOrdersCompleted;
		public final float simulationTimeRun;

	}

	@Data
	public static class Config {
		private final int workOrders;
		private final int workers;
		private final int transitionTime;
	}

	public static class ConfigBuilder {
		private final int workOrders;
		private final int workers;
		private final int transitionTime;

		public ConfigBuilder(int workOrders, int workers, int transitionTime) {
			this.workOrders = workOrders;
			this.workers = workers;
			this.transitionTime = transitionTime;
		}

		public static ConfigBuilder workOrders(int workOrders) {
			return new ConfigBuilder(workOrders, 0, 0);
		}

		public ConfigBuilder workers(int workers) {
			return new ConfigBuilder(workOrders, workers, transitionTime);
		}

		public ConfigBuilder transitionTime(int transitionTime) {
			return new ConfigBuilder(workOrders, workers, transitionTime);
		}

		public Config build() {
			return new Config(workOrders, workers, transitionTime);
		}

	}

	@Test
	public void one_worker_one_piece_of_work() throws Exception {
		Config config = ConfigBuilder.workOrders(1).workers(1).transitionTime(1).build();
		Results results = new Simulation().run(config);
		assertEquals(1, results.numberOfWorkOrdersCompleted);
		assertEquals(12f, results.simulationTimeRun,0f);
	}
}
