/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tuscany.sdo.test;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;

import commonj.sdo.DataObject;
import commonj.sdo.Sequence;
import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XMLHelper;
import commonj.sdo.helper.XSDHelper;
import junit.framework.TestCase;


public class MixedTypeTestCase extends TestCase {
    private static final String TEST_MODEL = "/mixed.xsd";
    static final String TEST_NAMESPACE = "http://www.example.com/mixed";

    /**
     * Sequenced type SDO 2 test.
     */
    public void testSequencedType() throws IOException {
        Type quoteType = TypeHelper.INSTANCE.getType(TEST_NAMESPACE, "MixedQuote");
        DataObject quote = DataFactory.INSTANCE.create(quoteType);

        System.out.println("quote type isOpen: " + quoteType.isOpen());
        System.out.println("quote type isSequenced: " + quoteType.isSequenced());

        Sequence sequence = quote.getSequence();

        sequence.add("\n  ");

        quote.setString("symbol", "fbnt");

        sequence.add("\n  ");

        quote.setString("companyName", "FlyByNightTechnology");

        sequence.add("\n  some text\n  ");

        DataObject child = quote.createDataObject("quotes");
        child.setBigDecimal("price", new BigDecimal("2000.0"));

        sequence.add("\n  more text\n  ");

        // quote.setBigDecimal("price", new BigDecimal("1000.0"));
        sequence.add("price", new BigDecimal("1000.0"));

        sequence.add("\n");

        XMLHelper.INSTANCE.save(quote, TEST_NAMESPACE, "mixedStockQuote", System.out);

        // TODO verify that what was saved was correct
    }

    protected void setUp() throws Exception {
        super.setUp();

        // Populate the meta data for the test (Stock Quote) model
        URL url = getClass().getResource(TEST_MODEL);
        InputStream inputStream = url.openStream();
        XSDHelper.INSTANCE.define(inputStream, url.toString());
        inputStream.close();
    }
}
