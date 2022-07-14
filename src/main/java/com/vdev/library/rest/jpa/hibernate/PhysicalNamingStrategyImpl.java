package com.vdev.library.rest.jpa.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serializable;
import java.util.Locale;

public class PhysicalNamingStrategyImpl extends PhysicalNamingStrategyStandardImpl implements Serializable {

    @Override
    public Identifier toPhysicalTableName(final Identifier name, final JdbcEnvironment context) {
        return new Identifier(addUnderscores(name.getText()), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(final Identifier name, final JdbcEnvironment context) {
        return new Identifier(addUnderscores(name.getText()), name.isQuoted());
    }

    /**
     * Add underscores to table and column names.
     *
     * @param name name to converted
     * @return converted name
     */
    protected static String addUnderscores(final String name) {
        final StringBuilder stringBuilder = new StringBuilder(name.replace('.', '_'));
        for (int i = 1; i < stringBuilder.length() - 1; i++) {
            if (Character.isLowerCase(stringBuilder.charAt(i - 1))
                    && Character.isUpperCase(stringBuilder.charAt(i))
                    && Character.isLowerCase(stringBuilder.charAt(i + 1))) {
                stringBuilder.insert(i++, '_');
            }
        }
        return stringBuilder.toString().toLowerCase(Locale.ROOT);
    }

}
