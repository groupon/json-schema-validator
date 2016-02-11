json-schema-validator
=====================

<a href="https://raw.githubusercontent.com/groupon/json-schema-validator/master/LICENSE">
    <img src="https://img.shields.io/hexpm/l/plug.svg"
         alt="License: Apache 2">
</a>
<a href="https://travis-ci.org/groupon/json-schema-validator/">
    <img src="https://travis-ci.org/groupon/json-schema-validator.png"
         alt="Travis Build">
</a>
<a href="http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.groupon.maven.plugin.json%22%20a%3A%22json-schema-validator%22">
    <img src="https://img.shields.io/maven-central/v/com.groupon.maven.plugin.json/json-schema-validator.svg"
         alt="Maven Artifact">
</a>

Maven plugin to validate json files against a json schema. Uses https://github.com/fge/json-schema-validator library under the covers.

Usage
-----

Determine the latest version of the validator in [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.groupon.maven.plugin.json%22%20a%3A%22json-schema-validator%22).

Add the plugin to your pom either in the __plugins__ or __pluginManagement__ block:

```xml
<plugin>
    <groupId>com.groupon.maven.plugin.json</groupId>
    <artifactId>json-schema-validator</artifactId>
    <version>VERSION</version>
</plugin>
```

Configure one or more __validation__ blocks for the plugin in the __plugins__ block:

```xml
<plugin>
    <artifactId>json-schema-validator</artifactId>
    <groupId>com.groupon.maven.plugin.json</groupId>
    <executions>
        <execution>
            <phase>verify</phase>
            <goals>
                <goal>validate</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <validations>
            <validation>
                <directory>${basedir}/src/main/resources/data</directory>
                <jsonSchema>${basedir}/src/test/resources/data.schema</jsonSchema>
                <includes>
                    <include>**/*.json</include>
                </includes>
            </validation>
        </validations>
    </configuration>
</plugin>
```

Each __validation__ block specifies the __jsonSchema__ file to validate with as well as the json file(s) to validate from a root __directory__ with standard __includes__ and __excludes__ to select specific file(s).

Building
--------

Prerequisites:
* [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3.3.3+](http://maven.apache.org/download.cgi)

Building:

    json-schema-validator> mvn verify

To use the local version you must first install it locally:

    json-schema-validator> mvn install

You can determine the version of the local build from the pom file.  Using the local version is intended only for testing or development.

License
-------

Published under Apache Software License 2.0, see LICENSE

&copy; Groupon Inc., 2014
