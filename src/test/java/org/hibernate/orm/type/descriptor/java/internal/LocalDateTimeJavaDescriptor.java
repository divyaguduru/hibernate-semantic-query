/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.orm.type.descriptor.java.internal;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.TemporalType;

import org.hibernate.orm.type.descriptor.java.spi.TemporalJavaTypeDescriptor;
import org.hibernate.type.LocalDateTimeType;

import org.hibernate.orm.type.descriptor.java.spi.AbstractBasicTypeDescriptor;
import org.hibernate.orm.type.descriptor.java.spi.ImmutableMutabilityPlan;
import org.hibernate.orm.type.descriptor.spi.JdbcRecommendedSqlTypeMappingContext;
import org.hibernate.orm.type.descriptor.spi.WrapperOptions;
import org.hibernate.orm.type.descriptor.sql.spi.SqlTypeDescriptor;
import org.hibernate.orm.type.spi.TypeConfiguration;

import org.jboss.logging.Logger;

/**
 * Java type descriptor for the LocalDateTime type.
 *
 * @author Steve Ebersole
 */
public class LocalDateTimeJavaDescriptor extends AbstractBasicTypeDescriptor<LocalDateTime> implements
		TemporalJavaTypeDescriptor<LocalDateTime > {
	private static final Logger log = Logger.getLogger( LocalDateTimeJavaDescriptor.class );

	/**
	 * Singleton access
	 */
	public static final LocalDateTimeJavaDescriptor INSTANCE = new LocalDateTimeJavaDescriptor();

	@SuppressWarnings("unchecked")
	public LocalDateTimeJavaDescriptor() {
		super( LocalDateTime.class, ImmutableMutabilityPlan.INSTANCE );
	}

	@Override
	public SqlTypeDescriptor getJdbcRecommendedSqlType(JdbcRecommendedSqlTypeMappingContext context) {
		return context.getTypeConfiguration().getSqlTypeDescriptorRegistry().getDescriptor( Types.TIMESTAMP );
	}

	@Override
	public String toString(LocalDateTime value) {
		return LocalDateTimeType.FORMATTER.format( value );
	}

	@Override
	public LocalDateTime fromString(String string) {
		return LocalDateTime.from( LocalDateTimeType.FORMATTER.parse( string ) );
	}

	@Override
	@SuppressWarnings("unchecked")
	public <X> X unwrap(LocalDateTime value, Class<X> type, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}

		if ( LocalDateTime.class.isAssignableFrom( type ) ) {
			return (X) value;
		}

		if ( Timestamp.class.isAssignableFrom( type ) ) {
			Instant instant = value.atZone( ZoneId.systemDefault() ).toInstant();
			return (X) Timestamp.from( instant );
		}

		if ( java.sql.Date.class.isAssignableFrom( type ) ) {
			Instant instant = value.atZone( ZoneId.systemDefault() ).toInstant();
			return (X) java.sql.Date.from( instant );
		}

		if ( java.sql.Time.class.isAssignableFrom( type ) ) {
			Instant instant = value.atZone( ZoneId.systemDefault() ).toInstant();
			return (X) java.sql.Time.from( instant );
		}

		if ( Date.class.isAssignableFrom( type ) ) {
			Instant instant = value.atZone( ZoneId.systemDefault() ).toInstant();
			return (X) Date.from( instant );
		}

		if ( Calendar.class.isAssignableFrom( type ) ) {
			return (X) GregorianCalendar.from( value.atZone( ZoneId.systemDefault() ) );
		}

		if ( Long.class.isAssignableFrom( type ) ) {
			Instant instant = value.atZone( ZoneId.systemDefault() ).toInstant();
			return (X) Long.valueOf( instant.toEpochMilli() );
		}

		throw unknownUnwrap( type );
	}

	@Override
	public <X> LocalDateTime wrap(X value, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}

		if ( LocalDateTime.class.isInstance( value ) ) {
			return (LocalDateTime) value;
		}

		if ( Timestamp.class.isInstance( value ) ) {
			final Timestamp ts = (Timestamp) value;
			return LocalDateTime.ofInstant( ts.toInstant(), ZoneId.systemDefault() );
		}

		if ( Long.class.isInstance( value ) ) {
			final Instant instant = Instant.ofEpochMilli( (Long) value );
			return LocalDateTime.ofInstant( instant, ZoneId.systemDefault() );
		}

		if ( Calendar.class.isInstance( value ) ) {
			final Calendar calendar = (Calendar) value;
			return LocalDateTime.ofInstant( calendar.toInstant(), calendar.getTimeZone().toZoneId() );
		}

		if ( Date.class.isInstance( value ) ) {
			final Timestamp ts = (Timestamp) value;
			final Instant instant = Instant.ofEpochMilli( ts.getTime() );
			return LocalDateTime.ofInstant( instant, ZoneId.systemDefault() );
		}

		throw unknownWrap( value.getClass() );
	}

	@Override
	public TemporalType getPrecision() {
		return TemporalType.TIMESTAMP;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <X> TemporalJavaTypeDescriptor<X> resolveTypeForPrecision(TemporalType precision, TypeConfiguration scope) {
		if ( precision == TemporalType.TIMESTAMP ) {
			return (TemporalJavaTypeDescriptor<X>) this;
		}
		if ( precision == TemporalType.DATE ) {
			return (TemporalJavaTypeDescriptor<X>)scope.getJavaTypeDescriptorRegistry().getDescriptor( LocalDate.class );
		}
		if ( precision == TemporalType.TIME ) {
			log.debugf( "No JPA TemporalType#TIME Java representation for LocalDateTime, using LocalDateTime" );
			return (TemporalJavaTypeDescriptor<X>) this;
		}

		throw new IllegalArgumentException( "Unrecognized JPA TemporalType precision [" + precision + "]" );
	}
}
