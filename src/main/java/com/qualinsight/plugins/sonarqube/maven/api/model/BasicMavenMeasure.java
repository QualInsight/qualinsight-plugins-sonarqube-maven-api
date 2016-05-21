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

import org.apache.maven.model.Model;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;

/**
 * Interface to be implemented by classes representing a Maven basic measure. Such basic measures are executed by a {@link Sensor} provided by the plugin. {@link BasicMavenMeasure} can access the {@link AbstractMavenPom} instance to manipulate both the raw and resolved {@link Model} corresponding to the Maven pom.xml under measurement.
 *
 * @author Michel Pawlak
 */
public interface BasicMavenMeasure {

    /**
     * Computes the {@link BasicMavenMeasure}.
     *
     * @param mavenPOM the {@link AbstractMavenPom} being measured.
     * @param context {@link SensorContext} providing the measurement context.
     */
    public void compute(final AbstractMavenPom mavenPOM, final SensorContext context);

}
