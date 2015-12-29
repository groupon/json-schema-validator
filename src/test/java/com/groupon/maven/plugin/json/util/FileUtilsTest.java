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
package com.groupon.maven.plugin.json.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.resource.ResourceManager;
import org.codehaus.plexus.resource.loader.FileResourceCreationException;
import org.codehaus.plexus.resource.loader.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.groupon.maven.plugin.json.Validation;

/**
 * Tests for class <code>FileUtils</code>.
 *
 * @author Ville Koskela (vkoskela at groupon dot com)
 */
public class FileUtilsTest {

    @Test(expected = MojoExecutionException.class)
    @SuppressFBWarnings("DMI_HARDCODED_ABSOLUTE_FILENAME")
    public void testGetListOfFilesDirectoryDoesNotExist() throws MojoExecutionException {
        final String directory = "/directory/does/not/exist";
        final Validation validation = new Validation();
        validation.setDirectory(directory);
        FileUtils.getListOfFiles(validation);
    }

    @Test
    public void testGetListOfFilesDirectory() throws MojoExecutionException {
        final String directory = "src/test/resources/input-json-files/";
        final Validation validation = new Validation();
        validation.setDirectory(directory);
        final List<String> files = FileUtils.getListOfFiles(validation);
        Assert.assertEquals(2, files.size());
        Assert.assertTrue(files.contains(directory + "data.json"));
        Assert.assertTrue(files.contains(directory + "invalid_data.json"));
    }

    @Test
    public void testGetListOfFilesDirectoryWithExclude() throws MojoExecutionException {
        final String directory = "src/test/resources/input-json-files/";
        final Validation validation = new Validation();
        validation.setDirectory(directory);
        validation.setExcludes(Collections.singletonList("invalid_data.json"));
        final List<String> files = FileUtils.getListOfFiles(validation);
        Assert.assertEquals(1, files.size());
        Assert.assertTrue(files.contains(directory + "data.json"));
    }

    @Test
    public void testGetListOfFilesDirectoryWithInclude() throws MojoExecutionException {
        final String directory = "src/test/resources/input-json-files/";
        final Validation validation = new Validation();
        validation.setDirectory(directory);
        validation.setIncludes(Collections.singletonList("data.json"));
        final List<String> files = FileUtils.getListOfFiles(validation);
        Assert.assertEquals(1, files.size());
        Assert.assertTrue(files.contains(directory + "data.json"));
    }

    @Test
    public void testGetListOfFilesDirectoryWithIncludeAndExclude() throws MojoExecutionException {
        final String directory = "src/test/resources/input-schema-files/";
        final Validation validation = new Validation();
        validation.setDirectory(directory);
        validation.setIncludes(Collections.singletonList("data.json"));
        validation.setExcludes(Collections.singletonList("data.json"));
        final List<String> files = FileUtils.getListOfFiles(validation);
        Assert.assertTrue(files.isEmpty());
    }

    @Test
    @SuppressFBWarnings("DMI_HARDCODED_ABSOLUTE_FILENAME")
    public void testLocateInputFile() throws ResourceNotFoundException, FileResourceCreationException, MojoExecutionException {
        final String resource = "/some/resource";
        final ResourceManager resourceManager = Mockito.mock(ResourceManager.class);
        Mockito.doReturn(new File(resource)).when(resourceManager).getResourceAsFile(resource);
        Mockito.verifyNoMoreInteractions(resourceManager);
        Assert.assertEquals(resource, FileUtils.locateInputFile(resource, resourceManager));
    }

    @Test(expected = MojoExecutionException.class)
    @SuppressFBWarnings("DMI_HARDCODED_ABSOLUTE_FILENAME")
    public void testLocateInputFileFailure() throws ResourceNotFoundException, FileResourceCreationException, MojoExecutionException {
        final String resource = "/some/resource";
        final ResourceManager resourceManager = Mockito.mock(ResourceManager.class);
        Mockito.doThrow(new ResourceNotFoundException("Mock Failure")).when(resourceManager).getResourceAsFile(resource);
        Mockito.verifyNoMoreInteractions(resourceManager);
        FileUtils.locateInputFile(resource, resourceManager);
    }

    @Test
    public void testPrivateConstructor() throws Exception {
        final Constructor<FileUtils> constructor = FileUtils.class.getDeclaredConstructor();
        Assert.assertNotNull(constructor);
        try {
            constructor.newInstance();
            Assert.fail("Static helper class should have private no-args constructor");
        } catch (IllegalAccessException e) {
            constructor.setAccessible(true);
            final FileUtils fileUtils = constructor.newInstance();
            Assert.assertNotNull(fileUtils);
        }
    }
}
