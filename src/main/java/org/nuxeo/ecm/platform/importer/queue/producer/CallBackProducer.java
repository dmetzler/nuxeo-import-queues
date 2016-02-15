package org.nuxeo.ecm.platform.importer.queue.producer;

import org.nuxeo.ecm.platform.importer.source.SourceNode;

public interface CallBackProducer extends Producer {

    public void terminate();

    public void submit(SourceNode node) throws Exception;
}