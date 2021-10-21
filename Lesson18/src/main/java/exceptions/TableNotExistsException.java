package exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;

public class TableNotExistsException extends SQLErrorCodeSQLExceptionTranslator {
    @Override
    protected DataAccessException customTranslate(String task, String sql, SQLException sqlEx) {
        if (sqlEx.getErrorCode() == 42102) {
            return new DataAccessException("Unknown table. Sent request:" + sql) {};
        }
        return super.customTranslate(task, sql, sqlEx);
    }
}
