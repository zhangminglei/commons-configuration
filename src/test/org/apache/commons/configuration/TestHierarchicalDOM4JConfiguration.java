package org.apache.commons.configuration;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
 
import java.io.File;
import java.util.Collection;

import junit.framework.TestCase;

/**
 * Test class for HierarchicalDOM4JConfiguration,
 *
 * @author <a href="mailto:oliver.heger@t-online.de">Oliver Heger</a>
 * @version $Id: TestHierarchicalDOM4JConfiguration.java,v 1.2 2004/01/16 14:23:39 epugh Exp $
 */
public class TestHierarchicalDOM4JConfiguration extends TestCase
{
    private static final String TEST_DIR = "conf";
    
    private static final String TEST_FILENAME = "testHierarchicalDOM4JConfiguration.xml";
    
    private static final String TEST_FILE = TEST_DIR + File.separator + TEST_FILENAME;
    
    private HierarchicalDOM4JConfiguration config;
    
    protected void setUp() throws Exception
    {
        config = new HierarchicalDOM4JConfiguration();
    }

    private void configTest(HierarchicalDOM4JConfiguration config)
    {
        assertEquals(1, config.getMaxIndex("tables.table"));
        assertEquals("system", config.getProperty("tables.table(0)[@tableType]"));
        assertEquals("application", config.getProperty("tables.table(1)[@tableType]"));
        
        assertEquals("users", config.getProperty("tables.table(0).name"));
        assertEquals("documents", config.getProperty("tables.table(1).name"));
        
        Object prop = config.getProperty("tables.table.fields.field.name");
        assertTrue(prop instanceof Collection);
        assertEquals(10, ((Collection) prop).size());
        
        prop = config.getProperty("tables.table(0).fields.field.type");
        assertTrue(prop instanceof Collection);
        assertEquals(5, ((Collection) prop).size());
        
        prop = config.getProperty("tables.table(1).fields.field.type");
        assertTrue(prop instanceof Collection);
        assertEquals(5, ((Collection) prop).size());
    }
    
    public void testGetProperty() throws Exception
    {
        config.setFileName(TEST_FILE);
        config.load();

        configTest(config);
    }
    
    public void testLoadURL() throws Exception
    {
        config.load(new File(TEST_FILE).getAbsoluteFile().toURL());
        configTest(config);
    }
    
    public void testLoadBasePath1() throws Exception
    {
        config.setBasePath(TEST_DIR);
        config.setFileName(TEST_FILENAME);
        config.load();
        configTest(config);
    }
    
    public void testLoadBasePath2() throws Exception
    {
        config.setBasePath(new File(TEST_FILE).getAbsoluteFile().toURL().toString());
        config.setFileName(TEST_FILENAME);
        config.load();
        configTest(config);
    }
}
