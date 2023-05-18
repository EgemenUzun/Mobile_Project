package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Data.alan;
import Data.ilceler;
import Data.iller;

public class AlanRepository extends JdbcEntityRepositoryBase<alan> {
    public AlanRepository(String connectionString, String userName, String password) {
        super(connectionString, userName, password);
    }

    @Override
    protected alan mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        alan _alan = new alan();
        _alan.id =resultSet.getInt("id");
        _alan.giysi = resultSet.getInt("giysi");
        _alan.insangucu = resultSet.getInt("insangucu");
        _alan.yiyecek = resultSet.getInt("yiyecek");
        _alan.konum = resultSet.getString("konum");
        return _alan;
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM alan";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM alan WHERE id=?";
    }

    @Override
    protected String getInsertQuery() {
        return  "INSERT INTO alan (insangucu,yiyecek,giysi,konum) VALUES (?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "Update alan SET insangucu=?,yiyecek=?,giysi=?,konum=? where id=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM alan WHERE id=?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, alan entity) throws SQLException {
        statement.setInt(1,entity.insangucu);
        statement.setInt(2,entity.insangucu);
        statement.setInt(3,entity.insangucu);
        statement.setString(4,entity.konum);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, alan entity) throws SQLException {
        statement.setInt(1,entity.insangucu);
        statement.setInt(2,entity.insangucu);
        statement.setInt(3,entity.insangucu);
        statement.setString(4,entity.konum);
        statement.setInt(5,entity.id);
    }

    public List<alan> getAllAlan() {
        return getAll();
    }

    public alan getAlanById(int id) {
        return getById(id);
    }

    public void addAlan(alan _alan) {
        insert(_alan);
    }

    public void updateAlan(alan _alan) {
        update(_alan);
    }

    public void deleteAlan(int id) {
        delete(id);
    }
}
