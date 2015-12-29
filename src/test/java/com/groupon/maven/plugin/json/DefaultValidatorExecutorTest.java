/**
 * Copyright 2015 Groupon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.groupon.maven.plugin.json;

import java.io.File;

import org.apache.maven.model.Build;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.resource.ResourceManager;
import org.codehaus.plexus.resource.loader.FileResourceLoader;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for class <code>DefaultValidatorExecutor</code>.
 *
 * @author Ville Koskela (vkoskela at groupon dot com)
 */
public class DefaultValidatorExecutorTest {

    @Test
    public void testConfigureInputLocator() throws MojoFailureException, MojoExecutionException {
        final File projectFile = new File("src/test/resources/plugin-test-pom-files/non-json-schema-pom.xml");
        final File searchPath = new File("src/test/resources/plugin-test-pom-files");

        final Build build = Mockito.mock(Build.class);
        Mockito.doReturn("./target").when(build).getDirectory();

        final MavenProject parent = Mockito.mock(MavenProject.class);
        Mockito.doReturn(null).when(parent).getFile();

        final MavenProject project = Mockito.mock(MavenProject.class);
        Mockito.doReturn(parent).when(project).getParent();
        Mockito.doReturn(build).when(project).getBuild();
        Mockito.doReturn(projectFile).when(project).getFile();

        final ResourceManager inputValidator = Mockito.mock(ResourceManager.class);

        DefaultValidatorExecutor.configureInputLocator(project, inputValidator);
        Mockito.verify(project, Mockito.atLeast(1)).getFile();
        Mockito.verify(project, Mockito.atLeast(1)).getParent();
        Mockito.verify(parent, Mockito.atLeast(1)).getFile();

        Mockito.verify(inputValidator).addSearchPath(FileResourceLoader.ID, searchPath.getAbsolutePath());
        Mockito.verify(inputValidator).addSearchPath("url", "");
    }
}
