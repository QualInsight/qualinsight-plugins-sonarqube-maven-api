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
package com.qualinsight.plugins.sonarqube.maven.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.google.common.annotations.Beta;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.debt.DebtRemediationFunction.Type;
import com.qualinsight.plugins.sonarqube.maven.api.model.AbstractMavenCheck;

/**
 * Allows to provide debt metadata for a {@link AbstractMavenCheck}
 *
 * @author Michel Pawlak
 */
@Beta
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DebtComputationMetadata {

    /**
     * {@link RuleType}
     *
     * @return {@link RuleType} as a String
     */
    RuleType ruleType();

    /**
     * {@link org.sonar.api.batch.debt.DebtRemediationFunction.Type} function to be used in order to compute debt.
     *
     * @return technical debt computation function
     */
    Type debtRemediationFunctionType();

    /**
     * Offset that defines the base effort debt of a SonarQube issue.
     *
     * @return debt remediation cost base effort
     */
    String remediationCostBaseEffort() default "";

    /**
     * Remediation cost multiplier.
     *
     * @return technical debt remediation cost multiplier
     */
    String remediationCostMultiplier() default "";

    /**
     * Effort to fix explanation.
     *
     * @return explanation
     */
    String effortToFixExplanation() default "";
}
