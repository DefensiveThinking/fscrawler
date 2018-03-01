/*
 * Licensed to David Pilato (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Author licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package fr.pilato.elasticsearch.crawler.fs.test.integration;

import fr.pilato.elasticsearch.crawler.fs.client.ElasticsearchClientManager;
import fr.pilato.elasticsearch.crawler.fs.framework.TimeValue;
import fr.pilato.elasticsearch.crawler.fs.settings.FsSettings;
import org.elasticsearch.action.search.SearchRequest;
import org.junit.Test;

/**
 * Test crawler with zip files
 */
public class FsCrawlerTestZipFilesIT extends AbstractFsCrawlerITCase {

    /**
     * Test case for #230: https://github.com/dadoonet/fscrawler/issues/230 : Add support for compressed files
     * It's a long job, so we let it run up to 2 minutes
     */
    @Test
    public void test_zip() throws Exception {
        FsSettings fsSettings = FsSettings.builder(getCrawlerName())
                .setElasticsearch(elasticsearchBuilder())
                .setFs(fsBuilder().build())
                .build();
        ElasticsearchClientManager elasticsearchClientManager = createElasticsearchClientManager(fsSettings);
        startCrawler(getCrawlerName(), fsSettings, createParser(fsSettings, elasticsearchClientManager), elasticsearchClientManager, false, TimeValue.timeValueMinutes(2));

        // We expect to have one file
        countTestHelper(new SearchRequest(getCrawlerName()), 1L, null);
    }
}
