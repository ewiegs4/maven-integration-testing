package org.apache.maven.it;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.util.Properties;

import org.apache.maven.it.util.ResourceExtractor;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-3808">MNG-3808</a>.
 * 
 * @author Brett Porter
 * @version $Id$
 */
public class MavenITmng3808ReportInheritenceOrderingTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng3808ReportInheritenceOrderingTest()
    {
        super( "[2.0.11,2.1.0-M1),[2.1.0-M2,)" );
    }

    /**
     * Test that 3 executions are run in the correct order.
     */
    public void testitMNG3808()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3808" );
        testDir = new File( testDir, "child" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier.assertFilePresent( "target/model.properties" );
        Properties props = verifier.loadProperties( "target/model.properties" );
        assertEquals( "maven-it-plugin-log-file", props.getProperty( "project.reporting.plugins.0.artifactId" ) );
        assertEquals( "maven-it-plugin-expression", props.getProperty( "project.reporting.plugins.1.artifactId" ) );
        assertEquals( "maven-it-plugin-configuration", props.getProperty( "project.reporting.plugins.2.artifactId" ) );
    }
}
