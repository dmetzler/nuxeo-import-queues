package org.nuxeo.ecm.platform.importer.queue.producer;

import org.nuxeo.ecm.platform.importer.queue.TaskRunner;
import org.nuxeo.ecm.platform.importer.queue.manager.QueuesManager;

public interface Producer extends TaskRunner {

    public void init(QueuesManager qm);

}