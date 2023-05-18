package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Data.alan;
import Data.card;

public class CardRepository extends JdbcEntityRepositoryBase<card>{
    public CardRepository(String connectionString, String userName, String password) {
        super(connectionString, userName, password);
    }

    @Override
    protected card mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        card _card = new card();
        _card.id = resultSet.getInt("id");
        _card.il = resultSet.getInt("il");
        _card.ilce = resultSet.getInt("ilce");
        _card.alanid =resultSet.getInt("alanid");
        _card.title = resultSet.getString("title");
        return _card;
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM card";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM card WHERE id=?";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO card (title,il,ilce,alanid) VALUES (?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE card SET title=?,il=?,ilce=?,alanid=? WHERE id=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM card WHERE id=?" ;
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, card entity) throws SQLException {
        statement.setString(1,entity.title);
        statement.setInt(2,entity.il);
        statement.setInt(3,entity.ilce);
        statement.setInt(4,entity.ilce);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, card entity) throws SQLException {
        statement.setString(1,entity.title);
        statement.setInt(2,entity.il);
        statement.setInt(3,entity.ilce);
        statement.setInt(4,entity.ilce);
        statement.setInt(5,entity.id);
    }
    public List<card> getCards(int il,int ilce)
    {
        List<card> entities = new ArrayList<>();
        String query = getSelectAllQuery() + " WHERE il=? AND  ilce =?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, il);
            statement.setInt(2, ilce);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    card entity = mapResultSetToEntity(resultSet);
                    entities.add(entity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }
    public List<card> getAllAlan() {
        return getAll();
    }

    public card getAlanById(int id) {
        return getById(id);
    }

    public void addAlan(card _card) {
        insert(_card);
    }

    public void updateAlan(card _card) {
        update(_card);
    }

    public void deleteAlan(int id) {
        delete(id);
    }
}
