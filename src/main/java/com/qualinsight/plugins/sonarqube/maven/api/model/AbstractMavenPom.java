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
import org.apache.maven.model.Model;
import org.sonar.api.batch.debt.DebtRemediationFunction;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;
import com.qualinsight.plugins.sonarqube.maven.api.model.AbstractMavenCheck.MavenIssueLocation;

/**
 * Abstract representation of a Maven pom.xml file, provides both ccess to a raw and resolved Maven {@link Model} corresponding to the pom.xml file.
 *
 * @author Michel Pawlak
 */
@Beta
public abstract class AbstractMavenPom {

    /**
     * SonarQube {@link InputFile} under analysis.
     *
     * @return the {@link InputFile} pointing to the pom.xml file.
     */
    public abstract InputFile inputFile();

    /**
     * Provides access to a resolved {@link Model} representation of the Maven pom.xml file.
     *
     * @return resolved Maven {@link Model}
     */
    public abstract Model resolvedMavenModel();

    /**
     * Provides access to a raw {@link Model} representation of the Maven pom.xml file.
     *
     * @return raw Maven {@link Model}
     */
    public abstract Model rawMavenModel();

    /**
     * SonarQube {@link Project} the pom.xml file is part of.
     *
     * @return {@link Project} the pom.xml file is part of.
     */
    public abstract Project project();

    /**
     * Adds an issue to the {@link AbstractMavenPom}.
     *
     * @param ruleKey {@link RuleKey} having raised the issue
     * @param location location at which the issue has been found
     * @param message Message that has to be appended to the issue
     */
    protected abstract void addIssue(RuleKey ruleKey, MavenIssueLocation location, String message);

    /**
     * Adds a weighted issue to the {@link AbstractMavenPom}.
     *
     * @param ruleKey {@link RuleKey} having raised the issue
     * @param location location at which the issue has been found
     * @param message Message that has to be appended to the issue
     * @param effortToFix multiplier of the effort to fix (for rules with LINEAR or LINEAR_OFFSET {@link DebtRemediationFunction.Type})
     */
    protected abstract void addIssue(RuleKey ruleKey, MavenIssueLocation location, String message, Double effortToFix);

}
