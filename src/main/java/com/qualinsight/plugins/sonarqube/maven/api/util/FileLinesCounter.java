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
package com.qualinsight.plugins.sonarqube.maven.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.annotations.Beta;

/**
 * Utility class that helps counting file lines.
 *
 * @author Michel Pawlak
 */
@Beta
public final class FileLinesCounter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileLinesCounter.class);

    private FileLinesCounter() {
    }

    /**
     * Counts the number of lines of a file, including blank, empty lines as well as comment lines.
     *
     * @param file File for which the number of line needs to be counted
     * @return an Integer indicating the total number of lines the file contains.
     */
    public static Integer countLines(final File file) {
        int lines = 0;
        try {
            @Cleanup
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            lines = countLines(reader);
        } catch (final IOException e) {
            LOGGER.warn("Could not find file.", e);
        }
        return lines;
    }

    private static int countLines(final BufferedReader reader) {
        int lines = 0;
        try {
            String tempLine = reader.readLine();
            while (tempLine != null) {
                lines++;
                tempLine = reader.readLine();
            }
        } catch (final IOException e) {
            LOGGER.warn("A problem occured while counting lines.", e);
        }
        return lines;
    }

}
