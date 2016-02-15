package org.nuxeo.ecm.platform.importer.queue.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.ecm.platform.importer.filter.EventServiceConfiguratorFilter;
import org.nuxeo.ecm.platform.importer.filter.ImporterFilter;
import org.nuxeo.ecm.platform.importer.log.BufferredLogger;
import org.nuxeo.ecm.platform.importer.log.ImporterLogger;
import org.nuxeo.ecm.platform.importer.queue.QueueImporter;
import org.nuxeo.ecm.platform.importer.queue.consumer.ConsumerFactory;
import org.nuxeo.ecm.platform.importer.queue.consumer.ConsumerFactoryImpl;
import org.nuxeo.ecm.platform.importer.queue.manager.RandomQueuesManager;
import org.nuxeo.ecm.platform.importer.queue.producer.Producer;
import org.nuxeo.ecm.platform.importer.queue.producer.SourceNodeProducer;
import org.nuxeo.ecm.platform.importer.source.RandomTextSourceNode;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import com.google.inject.Inject;

@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@RepositoryConfig(cleanup = Granularity.METHOD)
public class TestImporter {

    protected static final Log log = LogFactory.getLog(TestImporter.class);

    @Inject
    CoreSession session;

    @Test
    public void shouldImport() {

        ImporterLogger logger = new BufferredLogger(log);
        QueueImporter importer = new QueueImporter(logger);

        ImporterFilter filter = new EventServiceConfiguratorFilter(true, false, true, true);
        importer.addFilter(filter);

        RandomQueuesManager qm = new RandomQueuesManager(logger,2,100);

        RandomTextSourceNode root = RandomTextSourceNode.init(1000, 1, true);

        Producer producer = new SourceNodeProducer(root,logger);

        ConsumerFactory fact = new ConsumerFactoryImpl();
        importer.importDocuments(producer, qm, "/",session.getRepositoryName(), 5, fact);
        System.out.println(importer.getCreatedDocsCounter());

    }

}
