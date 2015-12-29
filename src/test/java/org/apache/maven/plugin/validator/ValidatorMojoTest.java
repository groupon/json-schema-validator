/**
 * Copyright 2014 Groupon.com
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
package org.apache.maven.plugin.validator;

import java.io.File;

import org.apache.maven.model.Build;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;

import com.groupon.maven.plugin.json.ValidatorMojo;

/**
 * JSON validatorMojo Test.
 *
 * @author Namrata Lele (nlele at groupon dot com)
 * @since 1.0
 */
public class ValidatorMojoTest extends AbstractMojoTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Successful case of validator plugin
     * Tests all types of inputs supported by the plugin
     */
    public void testValidatorMojoSuccessCase() {
        final File testPom = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
        } catch (final Exception e) {
            e.printStackTrace();
            fail("Must not throw an exception");
        }
    }

    /**
     * Successful case of validator plugin without schema.
     */
    public void testValidatorMojoSuccessCaseWithoutSchema() {
        final File testPom = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/no-schema-pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
        } catch (final Exception e) {
            e.printStackTrace();
            fail("Must not throw an exception");
        }
    }

    /**
     * Tests failure case where the input schema file doesn't exist
     */
    public void testValidatorMojoMissingSchemaCase() {
        final File testPom = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/missing-schema-pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
            fail("Must throw an exception");
        } catch (final Exception e) {
            assertEquals(MojoExecutionException.class, e.getClass());
        }
    }

    /**
     * Tests failure case where the input schema file is not a valid JSON schema
     */
    public void testValidatorMojoInvalidSchemaCase() {
        final File testPom = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/invalid-schema-pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
            fail("Must throw an exception");
        } catch (final Exception e) {
            assertEquals(MojoFailureException.class, e.getClass());
        }
    }

    /**
     * Tests failure case where the input schema file is not valid JSON
     */
    public void testValidatorMojoNonJsonSchemaCase() {
        final File testPom = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/non-json-schema-pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
            fail("Must throw an exception");
        } catch (final Exception e) {
            assertEquals(MojoExecutionException.class, e.getClass());
        }
    }

    /**
     * Tests invalid json data case
     */
    public void testValidatorMojoInvalidJsonCase() {
        final File testPom = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/invalid-json-pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
            fail("Must throw an exception");
        } catch (final Exception e) {
            assertEquals(MojoFailureException.class, e.getClass());
        }
    }

    /**
     * Tests non-json data case
     */
    public void testValidatorMojoNonJsonCase() {
        final File testPom = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/non-json-pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
            fail("Must throw an exception");
        } catch (final Exception e) {
            assertEquals(MojoExecutionException.class, e.getClass());
        }
    }

    /**
     * Tests pom with directory directive and default exclude only.
     */
    public void testValidatorMojoDirectoryExcludeOnly() {
        final File testPom = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/directory-exclude-only-pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
            fail("Must throw an exception");
        } catch (final Exception e) {
            assertEquals(MojoFailureException.class, e.getClass());
        }
    }

    /**
     * Tests pom with both directory directive and file directive.
     */
    public void testValidatorMojoDirectoryAndFile() {
        final File testPom1 = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/directory-and-file-pom.xml");
        assertNotNull(testPom1);
        assertTrue(testPom1.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom1);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
            fail("Must throw an exception");
        } catch (final Exception e) {
            assertEquals(MojoFailureException.class, e.getClass());
        }
        final File testPom2 = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/file-and-directory-pom.xml");
        assertNotNull(testPom2);
        assertTrue(testPom2.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom2);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
            fail("Must throw an exception");
        } catch (final Exception e) {
            assertEquals(MojoFailureException.class, e.getClass());
        }
    }

    /**
     * Tests pom with no validation directives.
     */
    public void testValidatorMojoNothingToValidate() {
        final File testPom = new File(getBasedir(), "src/test/resources/plugin-test-pom-files/nothing-to-validate-pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        try {
            final ValidatorMojo mojo = (ValidatorMojo) lookupMojo("validate", testPom);
            assertNotNull(mojo);
            mojoSetup(mojo);
            mojo.execute();
        } catch (final Exception e) {
            e.printStackTrace();
            fail("Must not throw an exception");
        }
    }

    protected void mojoSetup(final Mojo mojo) throws Exception {
        setVariableValueToObject(mojo, "project", new TestMavenProjectStub());
    }

    private static final class TestMavenProjectStub extends MavenProjectStub {
        public File getFile() {
            return new File(getBasedir(), "target/classes");
        }

        public Build getBuild() {
            return new Build() {
                private static final long serialVersionUID = 1L;

                public String getDirectory() {
                    return getBasedir() + "/target/classes";
                }
            };
        }
    }
}
