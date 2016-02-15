package org.nuxeo.ecm.platform.importer.queue;

public interface TaskRunner extends Runnable{

    public boolean isCompleted();

    public boolean isTerminated();

    public Exception getError();

    public long getNbProcessed();

    public void mustStop();

    public void canStop();



}
