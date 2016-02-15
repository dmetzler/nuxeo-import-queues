package org.nuxeo.ecm.platform.importer.queue.consumer;

import java.util.concurrent.BlockingQueue;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.platform.importer.log.ImporterLogger;
import org.nuxeo.ecm.platform.importer.source.SourceNode;

public class ConsumerFactoryImpl implements ConsumerFactory {

    @Override
    public Consumer createConsumer(ImporterLogger log, DocumentModel root, int batchSize, BlockingQueue<SourceNode> queue) {
        return new DefaultConsumer(log, root, batchSize, queue);
    }

}
