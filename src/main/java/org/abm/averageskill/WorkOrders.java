package org.abm.averageskill;

import static java.util.Arrays.asList;

import java.util.List;

public class WorkOrders {
	private final List<WorkOrder> workOrders;

	public WorkOrders(WorkOrder... workOrders) {
		this.workOrders = asList(workOrders);
	}

	public boolean isAllWorkTotallyDone() {
		for (WorkOrder order : workOrders) {
			if (order.isTotallyDone() == false) {
				return false;
			}
		}
		return true;

	}

	public List<WorkOrder> getAll() {
		return workOrders;
	}
}