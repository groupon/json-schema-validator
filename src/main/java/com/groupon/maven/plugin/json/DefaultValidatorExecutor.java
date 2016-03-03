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
package com.groupon.maven.plugin.json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.resource.ResourceManager;
import org.codehaus.plexus.resource.loader.FileResourceLoader;
import org.codehaus.plexus.util.StringUtils;

import com.groupon.maven.plugin.json.util.FileUtils;

/**
 * Default implementation for the ValidatorExecutor.
 *
 * @author Namrata Lele (nlele at groupon dot com)
 * @since 1.0
 */
@Component(role = ValidatorExecutor.class, hint = "default", instantiationStrategy = "per-lookup")
public class DefaultValidatorExecutor implements ValidatorExecutor {

    @Requirement(hint = "default")
    private ResourceManager inputLocator;
    private ValidatorRequest request;

    public void executeValidator(final ValidatorRequest requestInput) throws MojoFailureException, MojoExecutionException {
        request = requestInput;
        configureInputLocator(request.getProject(), inputLocator);

        for (final Validation validation : request.getValidations()) {
            performValidation(validation);
        }
    }

    private void performValidation(final Validation validation) throws MojoExecutionException, MojoFailureException {
        final List<String> jsonFiles = new ArrayList<String>();
        String schemaFile = null;

        if (!StringUtils.isEmpty(validation.getDirectory())) {
            jsonFiles.addAll(FileUtils.getListOfFiles(validation));
        }
        if (!StringUtils.isEmpty(validation.getJsonFile())) {
            jsonFiles.add(validation.getJsonFile());
        }
        if (!StringUtils.isEmpty(validation.getJsonSchema())) {
            schemaFile = FileUtils.locateInputFile(validation.getJsonSchema(), inputLocator);
        }
        for (final String jsonFile : jsonFiles) {
            if (schemaFile != null) {
                validateAgainstSchema(jsonFile, schemaFile);
            } else {
                loadJson(jsonFile);
            }
        }
    }

    private void validateAgainstSchema(final String jsonDataFile, final String schemaFile) throws MojoExecutionException, MojoFailureException {
        final JsonNode schemaResource = loadJson(schemaFile);
        final JsonNode fileResouce = loadJson(jsonDataFile);
        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        try {
            final JsonSchema schema = factory.getJsonSchema(schemaResource);
            final ProcessingReport report = schema.validate(fileResouce);
            if (!report.isSuccess()) {
                request.getLog().error("Failed validating JSON from " + jsonDataFile + " against " + schemaFile);
                throw new MojoFailureException(report.toString());
            } else {
                request.getLog().info("Successfully validated JSON from " + jsonDataFile + " against " + schemaFile);
            }
        } catch (final ProcessingException e) {
            request.getLog().error(e);
            throw new MojoFailureException(e.getMessage());
        }
    }

    private JsonNode loadJson(final String file) throws MojoExecutionException {
        try {
            final JsonNode node = JsonLoader.fromPath(file);
            return node;
        } catch (final IOException io) {
            request.getLog().error("Failed to parse JSON from '" + file + "'");
            request.getLog().error(io);
            throw new MojoExecutionException("Failed to parse JSON from file '" + file + "'", io);
        }
    }

    // NOTE: Package private for testing.
    /* package private */ static void configureInputLocator(final MavenProject project, final ResourceManager inputLocator) {
        inputLocator.setOutputDirectory(new File(project.getBuild().getDirectory()));

        MavenProject parent = project;
        while (parent != null && parent.getFile() != null) {
            final File dir = parent.getFile().getParentFile();
            inputLocator.addSearchPath(FileResourceLoader.ID, dir.getAbsolutePath());
            parent = parent.getParent();
        }
        inputLocator.addSearchPath("url", "");
    }
}
