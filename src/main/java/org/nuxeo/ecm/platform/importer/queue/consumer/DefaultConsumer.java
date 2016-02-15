package org.nuxeo.ecm.platform.importer.queue.consumer;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.blobholder.BlobHolder;
import org.nuxeo.ecm.core.api.model.PropertyNotFoundException;
import org.nuxeo.ecm.platform.filemanager.api.FileManager;
import org.nuxeo.ecm.platform.importer.log.ImporterLogger;
import org.nuxeo.ecm.platform.importer.source.SourceNode;
import org.nuxeo.runtime.api.Framework;

public class DefaultConsumer extends AbstractConsumer {

    protected FileManager fileManager;

    protected final String rootPath;

    public DefaultConsumer(ImporterLogger log, DocumentModel root, int batchSize, BlockingQueue<SourceNode> queue) {
        super(log, root, batchSize, queue);
        fileManager = Framework.getService(FileManager.class);
        rootPath = root.getPathAsString();
    }

    protected String getType() {
        return "File";
    }

    @Override
    protected void process(CoreSession session, SourceNode src) throws IOException {

        String fileName = null;
        String name = null;
        BlobHolder bh = src.getBlobHolder();
        if (bh != null) {
            Blob blob = bh.getBlob();
            if (blob != null) {
                fileName = blob.getFilename();
            }
            Map<String, Serializable> props = bh.getProperties();
            if (props != null) {
                name = (String) props.get("name");
            }
            if (name == null) {
                name = fileName;
            } else if (fileName == null) {
                fileName = name;
            }

            DocumentModel doc = session.createDocumentModel(rootPath, name, getType());

            doc.setProperty("dublincore", "title", name);
            doc.setProperty("file", "filename", fileName);
            doc.setProperty("file", "content", bh.getBlob());

            if (bh != null) {
                doc = setDocumentProperties(session, bh.getProperties(), doc);
            }

            doc = session.createDocument(doc);
            importStat.increaseStat(doc.getType(), 1);
        }
    }

    protected DocumentModel setDocumentProperties(CoreSession session, Map<String, Serializable> properties,
            DocumentModel doc) throws ClientException {
        if (properties != null) {

            for (Map.Entry<String, Serializable> entry : properties.entrySet()) {
                try {
                    doc.setPropertyValue(entry.getKey(), entry.getValue());
                } catch (PropertyNotFoundException e) {
                    String message = String.format("Property '%s' not found on document type: %s. Skipping it.",
                            entry.getKey(), doc.getType());
                    log.error(message,e);
                }
            }
            doc = session.saveDocument(doc);
        }
        return doc;
    }

    @Override
    public double getNbDocsCreated() {
        return getNbProcessed();
    }

}
