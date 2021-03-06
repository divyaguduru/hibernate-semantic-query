/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sqm.parser.hql.internal.navigable;

import org.hibernate.sqm.domain.SqmExpressableTypeEntity;
import org.hibernate.sqm.query.expression.domain.SqmNavigableBinding;
import org.hibernate.sqm.query.expression.domain.SqmNavigableSourceBinding;
import org.hibernate.sqm.query.from.SqmFrom;

/**
 * Strategy for resolving attribute path expressions in a contextually pluggable
 * manner.  Pluggable because how we resolve path expressions as part of a selection
 * is very different from how we need to resolve path expressions in predicates is
 * very different from how we need to resolve path expressions in from-clause ...
 *
 * @author Steve Ebersole
 */
public interface NavigableBindingResolver {
	/**
	 * Resolve the given path.  Returns {@code null} if the initial parts do not indicate the
	 * path is an attribute path.
	 *
	 * @param pathParts The path parts to resolve
	 *
	 * @return The resolve path, or {@code null}.
	 */
	SqmNavigableBinding resolvePath(String... pathParts);

	/**
	 * Resolve the given path relative to a given left-hand side.
	 *
	 * @param sourceBinding The NavigableSource binding from which to start resolving the
	 * 		pathParts.
	 * @param pathParts The path parts to resolve
	 *
	 * @return The resolve path, or {@code null}.
	 */
	SqmNavigableBinding resolvePath(SqmNavigableSourceBinding sourceBinding, String... pathParts);

	/**
	 * Resolve the given path applying the specified "intrinsic" subclass indicator to the
	 * path terminal. Returns {@code null} if the initial parts do not indicate the
	 * path is an attribute path.
	 *
	 * @param subclassIndicator The "intrinsic" subclass indicator to apply to the path terminal.  See
	 * {@link SqmFrom#getIntrinsicSubclassIndicator()}
	 * @param pathParts The path parts to resolve
	 *
	 * @return The resolve path, or {@code null}.
	 */
	SqmNavigableBinding resolveTreatedPath(SqmExpressableTypeEntity subclassIndicator, String... pathParts);

	/**
	 * Resolve the given path relative to a given left-hand side applying the specified
	 * "intrinsic" subclass indicator to the path terminal
	 *
	 * @param sourceBinding The NavigableSource binding from which to start resolving the
	 * 		pathParts.
	 * @param subclassIndicator The "intrinsic" subclass indicator to apply to the path terminal.  See
	 * {@link SqmFrom#getIntrinsicSubclassIndicator()}
	 * @param pathParts The path parts to resolve
	 *
	 * @return The resolve path, or {@code null}.
	 */
	SqmNavigableBinding resolveTreatedPath(SqmNavigableSourceBinding sourceBinding, SqmExpressableTypeEntity subclassIndicator, String... pathParts);

	boolean canReuseImplicitJoins();
}
