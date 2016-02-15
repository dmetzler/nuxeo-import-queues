package org.nuxeo.ecm.platform.importer.queue.manager;

import java.util.concurrent.BlockingQueue;

import org.nuxeo.ecm.platform.importer.source.SourceNode;

public interface QueuesManager {

    BlockingQueue<SourceNode> getQueue(int idx);

    int dispatch(SourceNode node) throws InterruptedException;

    int getNBConsumers();

    boolean isQueueEmpty(int idQueue);

}