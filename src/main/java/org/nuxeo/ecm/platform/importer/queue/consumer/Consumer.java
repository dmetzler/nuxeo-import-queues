package org.nuxeo.ecm.platform.importer.queue.consumer;

import org.nuxeo.ecm.platform.importer.queue.TaskRunner;

public interface Consumer extends TaskRunner {

    public double getImmediateThroughput();

    public double getThroughput();

    public double getNbDocsCreated();

    public ImportStat getImportStat();

}
