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

import java.util.List;
import org.apache.maven.model.Model;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.resources.Resource;

/**
 * Interface to be implemented by classes representing a Maven compound measure. Such compound measures are detected and executed by the {@link MeasureComputer} provided by the plugin and are used to create measures on top of {@link BasicMavenMeasure} measurments. {@link CompoundMavenMeasure} cannot access the Maven {@link Model} corresponding to the {@link Resource} being measured.
 *
 * @author Michel Pawlak
 */
public abstract interface CompoundMavenMeasure {

    /**
     * Input metrics keys the {@link CompoundMavenMeasure} is based on.
     * 
     * @return input metrics keys
     */
    List<String> inputMetricsKeys();

    /**
     * Output metrics key the {@link CompoundMavenMeasure} produces
     * 
     * @return
     */
    String outputMetricKey();

    /**
     * Indicates if zero value must be saved or not.
     * 
     * @return true if zero value must be saved, false otherwise.
     */
    boolean saveZeroValue();

}
