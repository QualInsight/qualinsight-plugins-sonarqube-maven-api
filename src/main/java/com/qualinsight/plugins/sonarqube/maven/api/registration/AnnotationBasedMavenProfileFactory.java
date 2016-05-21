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
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.AnnotationUtils;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.check.Rule;
import com.qualinsight.plugins.sonarqube.maven.api.MavenLanguageConstants;
import com.qualinsight.plugins.sonarqube.maven.api.annotations.DefaultCheck;
import com.qualinsight.plugins.sonarqube.maven.api.model.AbstractMavenCheck;

/**
 * Builds a {@link RulesProfile} instance from a {@link List} of {@link AbstractMavenCheck} classes, only adding to the profiles classes annotated with {@link DefaultCheck}.
 *
 * @author Michel Pawlak
 */
@Beta
public final class AnnotationBasedMavenProfileFactory {

    private final RuleFinder ruleFinder;

    /**
     * Factory constructor.
     *
     * @param ruleFinder {@link RuleFinder} instance used to retrieve {@link org.sonar.api.rules.Rule} instances in specified rules repository.
     */
    public AnnotationBasedMavenProfileFactory(final RuleFinder ruleFinder) {
        this.ruleFinder = ruleFinder;
    }

    /**
     * Builds a SonarQube {@link RulesProfile} instance.
     *
     * @param repositoryKey repository where to search for {@link org.sonar.api.rules.Rule} instances
     * @param profileName the name of the profile to be created
     * @param checkClasses {@link List} of {@link AbstractMavenCheck} classes that need to be scanned for a {@link DefaultCheck} annotation
     * @param messages {@link ValidationMessages} instance to which error messages will be added.
     * @return
     */
    public RulesProfile build(final String repositoryKey, final String profileName, final List<Class<? extends AbstractMavenCheck>> checkClasses, final ValidationMessages messages) {
        final RulesProfile profile = RulesProfile.create(profileName, MavenLanguageConstants.LANGUAGE_KEY);
        for (final Class<? extends AbstractMavenCheck> checkClass : checkClasses) {
            addRule(checkClass, profile, repositoryKey, messages);
        }
        return profile;
    }

    private void addRule(final Class<?> checkClass, final RulesProfile profile, final String repositoryKey, final ValidationMessages messages) {
        if (AnnotationUtils.getAnnotation(checkClass, DefaultCheck.class) != null) {
            final Rule rule = AnnotationUtils.getAnnotation(checkClass, Rule.class);
            final String ruleKey = rule.key();
            final org.sonar.api.rules.Rule activableRule = this.ruleFinder.findByKey(repositoryKey, ruleKey);
            if (activableRule != null) {
                profile.activateRule(activableRule, null);
            } else {
                messages.addErrorText("Rule " + ruleKey + " not found in repository '" + repositoryKey + "'");
            }
        }
    }
}
