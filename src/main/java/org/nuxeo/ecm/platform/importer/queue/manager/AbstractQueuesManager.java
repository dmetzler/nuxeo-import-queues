package org.nuxeo.ecm.platform.importer.queue.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.nuxeo.ecm.platform.importer.log.ImporterLogger;
import org.nuxeo.ecm.platform.importer.source.SourceNode;

public abstract class AbstractQueuesManager implements QueuesManager {

    List<BlockingQueue<SourceNode>> queues = new ArrayList<BlockingQueue<SourceNode>>();

    protected int maxQueueSize = 1000;

    protected ImporterLogger log = null;

    public AbstractQueuesManager(ImporterLogger logger, int queuesNb,int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        for (int i = 0; i < queuesNb; i++) {
            queues.add(new ArrayBlockingQueue<SourceNode>(maxQueueSize));
        }
        log = logger;
    }

    @Override
    public BlockingQueue<SourceNode> getQueue(int idx) {
        return queues.get(idx);
    }

    @Override
    public boolean isQueueEmpty(int idQueue) {
        return queues.get(idQueue).isEmpty();
    }



    @Override
    public int dispatch(SourceNode bh) throws InterruptedException {
        int idx = getTargetQueue(bh, queues.size());

        boolean accepted = getQueue(idx).offer(bh,1,TimeUnit.SECONDS);



//        if(!accepted)
//        {
//            System.out.println("WARNING!!!!!!! -------->timeout while waiting for an available queue");
//            idx = getTargetQueue(bh, queues.size());
//            getQueue(idx).offer(bh,5,TimeUnit.SECONDS);
//        }
        return idx;
    }

    protected abstract int getTargetQueue(SourceNode bh, int nbQueues);

    @Override
    public int getNBConsumers() {
        return queues.size();
    }
}
