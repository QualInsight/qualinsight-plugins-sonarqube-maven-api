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
package com.qualinsight.plugins.sonarqube.maven.api;

import com.google.common.annotations.Beta;
import org.sonar.api.resources.Language;

/**
 * Constants used by the QualInsight SonarQube Maven language plugin.
 *
 * @author Michel Pawlak
 */
@Beta
public final class MavenLanguageConstants {

    /**
     * Plugin {@link Language} key.
     */
    public static final String LANGUAGE_KEY = "maven";

    /**
     * Plugin {@link Language} name.
     */
    public static final String LANGUAGE_NAME = "Maven";

    private MavenLanguageConstants() {
    }

}
