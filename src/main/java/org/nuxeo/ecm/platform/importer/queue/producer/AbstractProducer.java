package org.nuxeo.ecm.platform.importer.queue.producer;

import org.nuxeo.ecm.platform.importer.log.ImporterLogger;
import org.nuxeo.ecm.platform.importer.queue.AbstractTaskRunner;
import org.nuxeo.ecm.platform.importer.queue.manager.QueuesManager;
import org.nuxeo.ecm.platform.importer.source.SourceNode;

public abstract class AbstractProducer extends AbstractTaskRunner implements Producer {

    protected  ImporterLogger log = null;

    protected QueuesManager qm;

    public AbstractProducer(ImporterLogger log)
    {
        this.log = log;
    }

    @Override
    public void init(QueuesManager qm) {
        this.qm = qm;
    }

    protected void dispatch(SourceNode node) throws InterruptedException {
        qm.dispatch(node);
        incrementProcessed();
    }

}