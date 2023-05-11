package DataBase;

import java.util.List;

public interface IEntityRepository<T> {
    List<T> getAll();
    T getById(int id);
    void insert(T entity);
    void update(T entity);
    void delete(int id);
}
