package org.abm.averageskill;

import static java.util.Arrays.asList;
import static org.abm.averageskill.Lists.filter;

import java.util.List;

public class WorkOrders {
	private final List<WorkOrder> workOrders;

	public WorkOrders(WorkOrder... workOrders) {
		this.workOrders = asList(workOrders);
	}

	public boolean haveAllTiersHaveCompletedWorkingOnThis() {
		for (WorkOrder order : workOrders) {
			if (order.haveAllTiersHaveCompletedWorkingOnThis() == false) {
				return false;
			}
		}
		return true;
	}

	public List<WorkOrder> notComplete() {
		return filter(WorkOrder.isNotComplete(), workOrders);
	}
}