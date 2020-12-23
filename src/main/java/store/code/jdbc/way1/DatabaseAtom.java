package store.code.jdbc.way1;

import java.sql.SQLException;

/**
 * Database atom.
 * @author Kahle
 */
public interface DatabaseAtom {

    /**
     * Code run in atom.
     * @return Run success or failure
     * @throws SQLException Sql run error
     */
    boolean run() throws SQLException;

}
