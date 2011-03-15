package org.abm.averageskill;

import org.abm.averageskill.event.WorkOrderCompletedEvent;

public interface WorkOrderCompletedListener {

	void notifyOfCompletedEvent(WorkOrderCompletedEvent... events);

}