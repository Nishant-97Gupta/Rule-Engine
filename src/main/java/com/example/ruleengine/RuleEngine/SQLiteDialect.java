package com.example.ruleengine.RuleEngine;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super();

        // Register column types
        registerColumnTypes(Types.BIT, "integer");
        registerColumnTypes(Types.TINYINT, "tinyint");
        registerColumnTypes(Types.SMALLINT, "smallint");
        registerColumnTypes(Types.INTEGER, "integer");
        registerColumnTypes(Types.BIGINT, "bigint");
        registerColumnTypes(Types.FLOAT, "float");
        registerColumnTypes(Types.REAL, "real");
        registerColumnTypes(Types.DOUBLE, "double");
        registerColumnTypes(Types.NUMERIC, "numeric");
        registerColumnTypes(Types.DECIMAL, "decimal");
        registerColumnTypes(Types.CHAR, "char");
        registerColumnTypes(Types.VARCHAR, "varchar");
        registerColumnTypes(Types.LONGVARCHAR, "longvarchar");
        registerColumnTypes(Types.DATE, "date");
        registerColumnTypes(Types.TIME, "time");
        registerColumnTypes(Types.TIMESTAMP, "timestamp");
        registerColumnTypes(Types.BLOB, "blob");
        registerColumnTypes(Types.CLOB, "clob");

        // Register functions using StandardSQLFunction
        registerFunction("concat", new StandardSQLFunction("concat", StandardBasicTypes.STRING));
        registerFunction("mod", new StandardSQLFunction("mod", StandardBasicTypes.INTEGER));
        registerFunction("substr", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
        registerFunction("substring", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
    }

    private void registerFunction(String string, StandardSQLFunction standardSQLFunction) {
		// TODO Auto-generated method stub
		
	}

	private void registerColumnTypes(int bigint, String string) {
		// TODO Auto-generated method stub
		
	}

	public boolean supportsIdentityColumns() {
        return true;
    }

    public boolean hasDataTypeInIdentityColumn() {
        return false; // As per SQLite
    }

    public String getIdentityColumnString() {
        return "integer"; // Auto-increment in SQLite
    }

    public String getIdentitySelectString() {
        return "select last_insert_rowid()"; // SQLite function to get the last inserted row ID
    }

    public boolean supportsLimit() {
        return true;
    }

    public String getLimitString(String query, boolean hasOffset) {
        return query + (hasOffset ? " limit ? offset ?" : " limit ?");
    }

    @Override
    public boolean supportsTemporaryTables() {
        return true;
    }

    public String getCreateTemporaryTableString() {
        return "create temporary table if not exists";
    }

    public boolean dropTemporaryTableAfterUse() {
        return false;
    }

    @Override
    public boolean supportsCurrentTimestampSelection() {
        return true;
    }

    @Override
    public boolean isCurrentTimestampSelectStringCallable() {
        return false;
    }

    @Override
    public String getCurrentTimestampSelectString() {
        return "select current_timestamp";
    }

    @Override
    public boolean supportsUnionAll() {
        return true;
    }

    @Override
    public boolean hasAlterTable() {
        return false; // SQLite doesn't support altering tables easily
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

    @Override
    public String getAddColumnString() {
        return "add column";
    }

    @Override
    public String getForUpdateString() {
        return "";
    }

    @Override
    public boolean supportsOuterJoinForUpdate() {
        return false;
    }

    @Override
    public String getDropForeignKeyString() {
        throw new UnsupportedOperationException("No drop foreign key syntax supported by SQLiteDialect");
    }

    @Override
    public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable,
                                                   String[] primaryKey, boolean referencesPrimaryKey) {
        throw new UnsupportedOperationException("No add foreign key syntax supported by SQLiteDialect");
    }

    @Override
    public String getAddPrimaryKeyConstraintString(String constraintName) {
        throw new UnsupportedOperationException("No add primary key syntax supported by SQLiteDialect");
    }

    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    @Override
    public boolean supportsCascadeDelete() {
        return false;
    }
}
