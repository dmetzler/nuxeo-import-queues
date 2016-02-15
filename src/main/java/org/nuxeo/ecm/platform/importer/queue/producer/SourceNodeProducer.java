package org.nuxeo.ecm.platform.importer.queue.producer;

import java.util.List;

import org.nuxeo.ecm.platform.importer.log.ImporterLogger;
import org.nuxeo.ecm.platform.importer.source.SourceNode;

public class SourceNodeProducer extends AbstractProducer implements Producer {

    protected final SourceNode root;

    public SourceNodeProducer(SourceNode root, ImporterLogger log) {
        super(log);
        this.root = root;
    }

    @Override
    public void run() {
        try {
            started = true;
            submit(root);
            completed = true;
        } catch (Exception e) {
            log.error("Error during sourceNode processing", e);
            error = e;
        }
    }

    protected void submit(SourceNode node) throws Exception {
        if (node!=null) {
            dispatch(node);
        }
        List<SourceNode> children = node.getChildren();
        if (children!=null) {
            for (SourceNode child : children) {
                submit(child);
            }
        }
    }

}
