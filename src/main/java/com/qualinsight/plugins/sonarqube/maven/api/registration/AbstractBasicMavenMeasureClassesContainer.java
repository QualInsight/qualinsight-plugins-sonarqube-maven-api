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
package com.qualinsight.plugins.sonarqube.maven.api.registration;

import java.util.List;
import com.google.common.annotations.Beta;
import org.sonar.api.batch.BatchSide;
import com.qualinsight.plugins.sonarqube.maven.api.model.BasicMavenMeasure;

/**
 * Container holding a {@link List} of custom {@link BasicMavenMeasure} classes to be loaded by QualInsight's SonarQube Maven plugin.
 *
 * @author Michel Pawlak
 */
@Beta
@BatchSide
public abstract class AbstractBasicMavenMeasureClassesContainer {

    /**
     * Provides the {@link BasicMavenMeasure} classes the {@link AbstractBasicMavenMeasureClassesContainer} is holding.
     *
     * @return custom {@link BasicMavenMeasure} classes to be loaded by the plugin.
     */
    public abstract List<Class<? extends BasicMavenMeasure>> classes();

}
