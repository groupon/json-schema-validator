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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for class <code>ValidatorRequest</code>.
 *
 * @author Ville Koskela (vkoskela at groupon dot com)
 */
public class ValidatorRequestTest {

    @Test
    public void testValidatorRequestDefaults() {
        final ValidatorRequest validatorRequest = new ValidatorRequest();
        Assert.assertNull(validatorRequest.getLog());
        Assert.assertNull(validatorRequest.getProject());
        Assert.assertNotNull(validatorRequest.getValidations());
        Assert.assertTrue(validatorRequest.getValidations().isEmpty());
    }

    @Test
    public void testValidatorRequestLog() {
        final Log log = Mockito.mock(Log.class);
        final ValidatorRequest validatorRequest = new ValidatorRequest();
        validatorRequest.setLog(log);
        Assert.assertSame(log, validatorRequest.getLog());
    }

    @Test
    public void testValidatorRequestProject() {
        final MavenProject project = Mockito.mock(MavenProject.class);
        final ValidatorRequest validatorRequest = new ValidatorRequest();
        validatorRequest.setProject(project);
        Assert.assertSame(project, validatorRequest.getProject());
    }

    @Test
    public void testValidatorRequestValidations() {
        final Validation validation = new Validation();
        final List<Validation> validations = new ArrayList<>();
        validations.add(validation);
        final ValidatorRequest validatorRequest = new ValidatorRequest();
        validatorRequest.setValidations(validations);
        Assert.assertEquals(validations, validatorRequest.getValidations());
    }

    @Test
    public void testValidatorRequestValidationsNull() {
        final ValidatorRequest validatorRequest = new ValidatorRequest();
        validatorRequest.setValidations(null);
        Assert.assertNotNull(validatorRequest.getValidations());
        Assert.assertTrue(validatorRequest.getValidations().isEmpty());
    }

    @Test
    public void testToString() {
        final ValidatorRequest request = new ValidatorRequest();
        request.setProject(Mockito.mock(MavenProject.class));
        request.setLog(Mockito.mock(Log.class));
        request.setValidations(Collections.emptyList());
        final String asString = request.toString();
        Assert.assertNotNull(asString);
        Assert.assertFalse(asString.isEmpty());

        final ValidatorRequest emptyRequest = new ValidatorRequest();
        final String emptyAsString = emptyRequest.toString();
        Assert.assertNotNull(emptyAsString);
        Assert.assertFalse(emptyAsString.isEmpty());
    }
}
