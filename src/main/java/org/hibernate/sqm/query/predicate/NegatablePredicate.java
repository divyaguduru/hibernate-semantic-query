/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.predicate;

/**
 * Contract for predicates that have a negated form, e.g. {@code x is not null}
 * as opposed to {@code not(x is null)}
 *
 * @author Steve Ebersole
 */
public interface NegatablePredicate extends Predicate {
	/**
	 * Is this predicate (currently) negated?
	 *
	 * @return {@code true} if we have a negated form currently
	 */
	boolean isNegated();

	/**
	 * Apply an external negation.  Called when we encounter a {@code NOT}
	 * grouping.
	 * <p/>
	 * For example, for {@code not(x is null)} we build the
	 * {@link NullnessPredicate} and then call its negate method which results
	 * in {@code x is not null}.
	 * <p/>
	 * Can be applied nested as well.  For example, {@code not(not(x is null))}
	 * becomes {@code x is null} because the double-negative cancel each other out.
	 */
	void negate();
}
