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
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.debt.DebtRemediationFunction;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import com.qualinsight.plugins.sonarqube.maven.api.annotations.DebtComputationMetadata;
import com.qualinsight.plugins.sonarqube.maven.api.model.AbstractMavenCheck;

/**
 * Eases the creation of a {@link RulesDefinition} instance that checks that {@link AbstractMavenCheck} instances are correctly annotated and that loads those annotations in order to feed SonarQube UI as well as SQALE model.
 *
 * @author Michel Pawlak
 */
public abstract class AbstractMavenRulesDefinition implements RulesDefinition {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMavenRulesDefinition.class);

    /**
     * Checks that specified {@link AbstractMavenCheck} classes are annotated with {@link org.sonar.check.Rule} and {@link DebtComputationMetadata} then loads information held by those annotations to feed the specified {@link NewRepository}.
     *
     * @param repository {@link NewRepository} instance in which {@link AbstractMavenCheck} metadata should be loaded.
     * @param checkClasses {@link List} of {@link AbstractMavenCheck} classes that need to be defined.
     */
    protected final void define(final NewRepository repository, final List<Class<? extends AbstractMavenCheck>> checkClasses) {
        final RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();
        annotationLoader.load(repository, checkClasses.toArray(new Class[checkClasses.size()]));
        for (final Class<? extends AbstractMavenCheck> checkClass : checkClasses) {
            final DebtComputationMetadata debtComputationMetadata = checkClass.getAnnotation(DebtComputationMetadata.class);
            final org.sonar.check.Rule rule = checkClass.getAnnotation(org.sonar.check.Rule.class);
            Preconditions.checkNotNull(rule, "MavenCheck class '{}' must be annotated with '{}'.", checkClass.getSimpleName(), org.sonar.check.Rule.class.getSimpleName());
            if (debtComputationMetadata != null) {
                final String key = rule.key();
                final RuleType ruleType = debtComputationMetadata.ruleType();
                final String multiplier = debtComputationMetadata.remediationCostMultiplier();
                final String baseEffort = debtComputationMetadata.remediationCostBaseEffort();
                final DebtRemediationFunction.Type debtRemediationFunctionType = debtComputationMetadata.debtRemediationFunctionType();
                LOGGER.debug("key= '{}', baseEffort: '{}', multiplier: '{}'", key, baseEffort, multiplier.equals("") ? null : multiplier);
                final NewRule newRule = repository.rule(key);
                if (newRule != null) {
                    newRule.setType(ruleType);
                    DebtRemediationFunction debtRemediationFunction = null;
                    switch (debtRemediationFunctionType) {
                        case CONSTANT_ISSUE:
                            debtRemediationFunction = newRule.debtRemediationFunctions()
                                .create(debtRemediationFunctionType, null, baseEffort);
                            break;
                        case LINEAR:
                            debtRemediationFunction = newRule.debtRemediationFunctions()
                                .create(debtRemediationFunctionType, multiplier, null);
                            break;
                        case LINEAR_OFFSET:
                            debtRemediationFunction = newRule.debtRemediationFunctions()
                                .create(debtRemediationFunctionType, multiplier, baseEffort);
                            break;
                        default:
                            LOGGER.warn("Unexpected debt remediation function type :'{}'", debtRemediationFunctionType);
                            break;
                    }
                    newRule.setDebtRemediationFunction(debtRemediationFunction);
                } else {
                    LOGGER.error("Could not obtain new rule for key '{}'.", key);
                }
            }
        }
        repository.done();
    }

}
