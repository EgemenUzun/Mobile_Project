package DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Data.iller;

public class SehirRepository extends JdbcEntityRepositoryBase<iller> {
    public SehirRepository(String connectionString, String userName, String password) {
        super(connectionString, userName, password);
    }

    @Override
    protected iller mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        iller iller = new iller();
        iller.id=(resultSet.getInt("id"));
        iller.sehir_adi=(resultSet.getString("sehir_adi"));
        return iller;
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM iller";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM iller WHERE id=?";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO iller (sehir_adi) VALUES (?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE iller SET sehir_adi=? WHERE id=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM iller WHERE id=?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, iller entity) throws SQLException {
        statement.setString(1, entity.sehir_adi);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, iller entity) throws SQLException {
        statement.setString(1, entity.sehir_adi);
        statement.setInt(2, entity.id);
    }
    public List<iller> getAllSehir() {
        return getAll();
    }

    public iller getSehirById(int id) {
        return getById(id);
    }

    public void addSehir(iller iller) {
        insert(iller);
    }

    public void updateSehir(iller iller) {
        update(iller);
    }

    public void deleteSehir(int id) {
        delete(id);
    }
}
