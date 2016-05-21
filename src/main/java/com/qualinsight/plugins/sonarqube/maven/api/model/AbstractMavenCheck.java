/*
 * qualinsight-plugins-sonarqube-maven-api
 * Copyright (c) 2015-2016, QualInsight
 * http://www.qualinsight.com/
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program. If not, you can retrieve a copy
 * from <http://www.gnu.org/licenses/>.
 */
/*
 * This file is part of qualinsight-plugins-sonarqube-maven-api.
 *
 * qualinsight-plugins-sonarqube-maven-api is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * qualinsight-plugins-sonarqube-maven-api is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with qualinsight-plugins-sonarqube-maven-api.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.qualinsight.plugins.sonarqube.maven.api.model;

import com.google.common.annotations.Beta;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;
import org.apache.maven.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.sonar.api.batch.debt.DebtRemediationFunction;
import org.sonar.api.batch.fs.InputComponent;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.rule.RuleKey;
import com.qualinsight.plugins.sonarqube.maven.api.annotations.DebtComputationMetadata;

/**
 * Root class of all Maven checks that have to be executed by the plugin.
 *
 * @author Michel Pawlak
 */
@Beta
public abstract class AbstractMavenCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMavenCheck.class);

    private RuleKey ruleKey;

    private AbstractMavenPom mavenPOM;

    /**
     * Adds a SonarQube issue
     *
     * @param location location at which the issue has occurred
     * @param messagePattern message pattern in SLF4J format
     * @param messageParameters String parameters that will be used to fill the message pattern
     */
    protected final void addIssue(final MavenIssueLocation location, final String messagePattern, final String... messageParameters) {
        this.mavenPOM.addIssue(this.ruleKey, location, MessageFormatter.arrayFormat(messagePattern, messageParameters)
            .getMessage());
    }

    /**
     * Adds a SonarQube issue having a specified effort to fix. To be used along with LINEAR and LINEAR_OFFSET {@link DebtRemediationFunction.Type}.
     *
     * @param location location at which the issue has occurred
     * @param messagePattern message pattern in SLF4J format
     * @param effortToFix weight of the issue, i.e. for each effort to fix, the {@link DebtComputationMetadata}
     * @param messageParameters String parameters that will be used to fill the message pattern
     */
    protected final void addIssue(final MavenIssueLocation location, final String messagePattern, final Double effortToFix, final String... messageParameters) {
        this.mavenPOM.addIssue(this.ruleKey, location, MessageFormatter.arrayFormat(messagePattern, messageParameters)
            .getMessage(), effortToFix);
    }

    /**
     * Retrieves the {@link AbstractMavenPom} instance that needs to be checked.
     *
     * @return the {@link AbstractMavenPom} to be checked.
     */
    protected final AbstractMavenPom mavenPOM() {
        return this.mavenPOM;
    }

    /**
     * Check execution method that needs to be implemented by each {@link AbstractMavenCheck} implementation.
     */
    protected abstract void execute();

    /**
     * Configures the {@link AbstractMavenCheck} then executes it.
     *
     * @param mavenPOM {@link AbstractMavenPom} that needs to be checked.
     * @param ruleKey {@link RuleKey} of the {@link AbstractMavenCheck}
     */
    public final void execute(final AbstractMavenPom mavenPOM, final RuleKey ruleKey) {
        this.mavenPOM = mavenPOM;
        this.ruleKey = ruleKey;
        LOGGER.debug("Executing check: {}", ruleKey.toString());
        execute();
    }

    /**
     * Locates the line in the {@link Model} POM where an {@link InputLocationTracker} is located.
     *
     * @param locationTracker Maven {@link Model} {@link InputLocationTracker} for which the line has to be retrieved.
     * @return an IssueLocation representing the line and column in the {@link Model} where the {@link InputLocationTracker} is located.
     */
    public final MavenIssueLocation location(final InputLocationTracker locationTracker) {
        return location(locationTracker, "");
    }

    /**
     * Locates the line in the {@link Model} POM where XML tags below an {@link InputLocationTracker} is located.
     *
     * @param locationTracker Maven {@link Model} {@link InputLocationTracker} for which the line has to be retrieved.
     * @param tags list of tags to drill down, the last one representing the tag for which the line has to be retrieved.
     * @return an IssueLocation representing the line and column in the {@link Model} where the last tag of the {@code tags} list below {@link InputLocationTracker} is located.
     */
    public final MavenIssueLocation location(final InputLocationTracker locationTracker, final String... tags) {
        InputLocation inputLocation = null;
        for (final String tag : tags) {
            if (inputLocation == null) {
                inputLocation = locationTracker.getLocation(tag);
            } else {
                inputLocation = inputLocation.getLocation(tag);
            }
        }
        return inputLocation == null ? null : new MavenIssueLocation(inputLocation.getLineNumber());
    }

    public class MavenIssueLocation {

        private Integer lineNumber;

        public MavenIssueLocation(final Integer lineNumber) {
            this.lineNumber = lineNumber;
        }

        public InputComponent inputComponent() {
            return AbstractMavenCheck.this.mavenPOM.inputFile();
        }

        public TextRange textRange() {
            return AbstractMavenCheck.this.mavenPOM.inputFile()
                .selectLine(this.lineNumber);
        }
    }
}
