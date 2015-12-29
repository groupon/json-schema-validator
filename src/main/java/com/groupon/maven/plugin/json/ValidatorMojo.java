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

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

/**
 * JSON validator. Performs the following:
 * <ol>
 *     <li>Syntax validation</li>
 *     <li>Validation against a schema</li>
 * </ol>
 *
 * @author Namrata Lele (nlele at groupon dot com)
 * @since 1.0
 */
@Mojo(name = "validate", defaultPhase = LifecyclePhase.VERIFY, requiresDependencyResolution = ResolutionScope.TEST)
public class ValidatorMojo extends AbstractMojo {

    @Parameter(property = "validate.validations")
    private List<Validation> validations;

    @Parameter (defaultValue = "${project}")
    protected MavenProject project;

    @Component(role = ValidatorExecutor.class, hint = "default")
    protected ValidatorExecutor validatorExecutor;

    public void execute() throws MojoExecutionException, MojoFailureException {
        ValidatorRequest req = new ValidatorRequest();
        req.setLog(getLog()).setValidations(validations).setProject(project);
        validatorExecutor.executeValidator(req);
    }
}
