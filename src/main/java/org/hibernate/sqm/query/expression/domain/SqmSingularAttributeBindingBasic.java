/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sqm.query.expression.domain;

import org.hibernate.sqm.domain.SqmSingularAttribute;
import org.hibernate.sqm.domain.type.SqmDomainTypeBasic;
import org.hibernate.sqm.query.from.SqmAttributeJoin;

/**
 * @author Steve Ebersole
 */
public class SqmSingularAttributeBindingBasic extends AbstractSqmSingularAttributeBinding {
	public SqmSingularAttributeBindingBasic(
			SqmNavigableSourceBinding sourceBinding,
			SqmSingularAttribute boundNavigable) {
		super( sourceBinding, boundNavigable );
	}

	public SqmSingularAttributeBindingBasic(SqmAttributeJoin fromElement) {
		super( fromElement );
	}

	@Override
	public SqmDomainTypeBasic getExportedDomainType() {
		return (SqmDomainTypeBasic) getBoundNavigable().getExportedDomainType();
	}
}
