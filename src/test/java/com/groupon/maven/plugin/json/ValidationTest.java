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

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for class <code>Validation</code>.
 *
 * @author Ville Koskela (vkoskela at groupon dot com)
 */
public class ValidationTest {

    @Test
    public void testValidationDefaults() {
        final Validation validation = new Validation();
        Assert.assertNull(validation.getJsonFile());
        Assert.assertNull(validation.getJsonSchema());
    }

    @Test
    public void testValidationJsonFile() {
        final String jsonFile = "MyJsonFile";
        final Validation validation = new Validation();
        validation.setJsonFile(jsonFile);
        Assert.assertEquals(jsonFile, validation.getJsonFile());
    }

    @Test
    public void testValidationJsonSchema() {
        final String jsonSchema = "MyJsonSchema";
        final Validation validation = new Validation();
        validation.setJsonSchema(jsonSchema);
        Assert.assertEquals(jsonSchema, validation.getJsonSchema());
    }

    @Test
    public void testToString() {
        final Validation validation = new Validation();
        validation.setJsonFile("file.json");
        validation.setJsonSchema("file.schema");
        final String asString = validation.toString();
        Assert.assertNotNull(asString);
        Assert.assertFalse(asString.isEmpty());

        final Validation emptyValidation = new Validation();
        final String emptyAsString = emptyValidation.toString();
        Assert.assertNotNull(emptyAsString);
        Assert.assertFalse(emptyAsString.isEmpty());
    }
}
