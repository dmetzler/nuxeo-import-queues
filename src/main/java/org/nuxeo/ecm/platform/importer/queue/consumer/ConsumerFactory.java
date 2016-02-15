package org.nuxeo.ecm.platform.importer.queue.consumer;

import java.util.concurrent.BlockingQueue;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.platform.importer.log.ImporterLogger;
import org.nuxeo.ecm.platform.importer.source.SourceNode;

public interface ConsumerFactory {


    public Consumer createConsumer(ImporterLogger log, DocumentModel root, int batchSize, BlockingQueue<SourceNode> blockingQueue);
}
