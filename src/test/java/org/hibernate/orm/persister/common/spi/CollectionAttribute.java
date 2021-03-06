/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.orm.persister.common.spi;

import java.util.Collection;

/**
 * @author Steve Ebersole
 */
public interface CollectionAttribute<O,E> extends PluralAttribute<O,Collection<E>,E>,
		javax.persistence.metamodel.CollectionAttribute<O,E> {
}
