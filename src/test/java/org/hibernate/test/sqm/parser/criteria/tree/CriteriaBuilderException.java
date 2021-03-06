/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.test.sqm.parser.criteria.tree;

/**
 * Indicates a problem in calls to the CriteriaBuilder
 *
 * @author Steve Ebersole
 */
public class CriteriaBuilderException extends RuntimeException {
	public CriteriaBuilderException(String message) {
		super( message );
	}

	public CriteriaBuilderException(String message, Throwable cause) {
		super( message, cause );
	}
}
