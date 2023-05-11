package DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Data.ilceler;
import Data.iller;

public class İlceRepository extends JdbcEntityRepositoryBase<ilceler> {

    public İlceRepository(String connectionString, String userName, String password) {
        super(connectionString, userName, password);
    }

    @Override
    protected ilceler mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        ilceler ilce = new ilceler();
        ilce.id=(resultSet.getInt("id"));
        ilce.ilce_adi=(resultSet.getString("ilce_adi"));
        ilce.sehirid = resultSet.getInt("sehirid");
        return ilce;
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM ilceler";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM ilceler WHERE id=?";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO ilceler (ilce_adi,sehirid) VALUES (?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE ilceler SET ilce_adi=? ,sehirid=? WHERE id=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM ilceler WHERE id=?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, ilceler entity) throws SQLException {
        statement.setString(1, entity.ilce_adi);
        statement.setInt(2,entity.sehirid);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, ilceler entity) throws SQLException {
        statement.setString(1, entity.ilce_adi);
        statement.setInt(2,entity.sehirid);
        statement.setInt(3,entity.id);
    }
    public List<ilceler> getIlceList(int id) {

        return getListById(id,"sehirid");
    }

    public void addIlce(ilceler ilce) {

        insert(ilce);
    }

    public void updateIlce(ilceler ilce) {

        update(ilce);
    }

    public void deleteIlce(int id) {

        delete(id);
    }
    public List<ilceler> getIlce() {

        return getAll();
    }
}
