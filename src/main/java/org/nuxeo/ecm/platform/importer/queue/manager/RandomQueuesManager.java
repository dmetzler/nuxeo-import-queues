package org.nuxeo.ecm.platform.importer.queue.manager;

import java.util.Random;
import org.nuxeo.ecm.platform.importer.log.ImporterLogger;
import org.nuxeo.ecm.platform.importer.source.SourceNode;

public class RandomQueuesManager extends AbstractQueuesManager {

    protected final Random rand;


    public RandomQueuesManager(ImporterLogger logger, int queuesNb, int maxQueueSize) {
        super(logger,queuesNb,maxQueueSize);
        rand = new Random(System.currentTimeMillis());
    }


    @Override
    protected int getTargetQueue(SourceNode bh, int nbQueues) {
        return rand.nextInt(nbQueues);
    }

}
