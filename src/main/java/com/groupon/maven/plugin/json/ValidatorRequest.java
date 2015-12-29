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

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

/**
 * Input request object for the <code>ValidatorMojo</code>.
 *
 * @author Namrata Lele (nlele at groupon dot com)
 * @since 1.0
 */
public class ValidatorRequest {
    private List<Validation> validations;
    private MavenProject project;
    private Log log;

    public List<Validation> getValidations() {
        if (validations == null) {
            validations = new ArrayList<>();
        }
        return validations;
    }

    public ValidatorRequest setValidations(final List<Validation> validationsInput) {
        validations = validationsInput;
        return this;
    }

    public MavenProject getProject() {
        return project;
    }

    public ValidatorRequest setProject(final MavenProject projectInput) {
        project = projectInput;
        return this;
    }

    public Log getLog() {
        return log;
    }

    public ValidatorRequest setLog(final Log logInput) {
        log = logInput;
        return this;
    }

    @Override
    public String toString() {
        return "ValidationRequest {log: " + getLog() + ", project: " + getProject() + ", validations: " + getValidations() + "}";
    }
}
