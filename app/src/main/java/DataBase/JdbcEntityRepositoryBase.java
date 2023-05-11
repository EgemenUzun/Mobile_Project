package DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DataBase.IEntityRepository;

public abstract class JdbcEntityRepositoryBase<T> implements IEntityRepository<T> {
    private String connectionString;
    private String userName;
    private String password;

    public JdbcEntityRepositoryBase(String connectionString, String userName, String password) {
        this.connectionString = connectionString;
        this.userName = userName;
        this.password = password;
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, userName, password);
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getSelectAllQuery())) {
            while (resultSet.next()) {
                T entity = mapResultSetToEntity(resultSet);
                entities.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }
    public List<T> getListById(int Id, String collumn) {
        List<T> entities = new ArrayList<>();
        String query = getSelectAllQuery() + " WHERE "+ collumn +" = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T entity = mapResultSetToEntity(resultSet);
                    entities.add(entity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }


    @Override
    public T getById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSelectByIdQuery())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    T entity = mapResultSetToEntity(resultSet);
                    return entity;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(T entity) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getInsertQuery())) {
            setInsertParameters(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T entity) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getUpdateQuery())) {
            setUpdateParameters(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getDeleteQuery())) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract String getSelectAllQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract void setInsertParameters(PreparedStatement statement, T entity) throws SQLException;

    protected abstract void setUpdateParameters(PreparedStatement statement, T entity) throws SQLException;
}