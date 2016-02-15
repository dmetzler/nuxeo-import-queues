package org.nuxeo.ecm.platform.importer.queue;

public abstract class AbstractTaskRunner implements TaskRunner {

    protected boolean completed = false;

    protected Exception error;

    protected long nbProcessed = 0;

    protected boolean mustStop;

    protected boolean canStop;

    protected boolean started = false;


    protected void incrementProcessed() {
        nbProcessed++;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean isTerminated() {
        return !started || (completed || getError()!=null);
    }

    @Override
    public Exception getError() {
        return error;
    }

    @Override
    public long getNbProcessed() {
        return nbProcessed;
    }

    @Override
    public void mustStop() {
        mustStop = true;
        started = false;
    }

    @Override
    public void canStop() {
        canStop = true;
    }

}
