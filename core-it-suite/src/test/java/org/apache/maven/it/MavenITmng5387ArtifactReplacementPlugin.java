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

import org.apache.maven.it.util.ResourceExtractor;
import org.apache.maven.shared.utils.io.FileUtils;

import java.io.File;

public class MavenITmng5387ArtifactReplacementPlugin
    extends AbstractMavenIntegrationTestCase
{

    private File testDir;

    public MavenITmng5387ArtifactReplacementPlugin()
    {
        super( "[3.1,)" );
    }

    public void setUp()
        throws Exception
    {
        super.setUp();

        testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-5387" );

    }

    protected void tearDown()
        throws Exception
    {

        super.tearDown();
    }

    public void testArtifactReplacementExecution()
        throws Exception
    {
        Verifier v0 = newVerifier( testDir.getAbsolutePath(), "remote" );
        v0.setAutoclean( false );
        v0.deleteDirectory( "target" );
        v0.deleteArtifacts( "org.apache.maven.its.mng5387" );
        v0.executeGoal( "install" );
        v0.verifyErrorFreeLog();
        v0.resetStreams();

        String path = v0.getArtifactPath( "org.apache.maven.its.mng5387", "mng5387-it", "0.0.1-SNAPSHOT", "txt", "c" );
        String contents = FileUtils.fileRead( new File( path ), "utf-8" );
        assertTrue( contents.contains( "This is the second file" ) );
    }
}
