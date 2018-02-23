package sample.repository;

import java.util.Optional;

public interface Repository<T> {
    void save(T elem);

    boolean delete(T elem) throws Exception;
    Optional<T> deleteAt(int index);
    Optional<T> getOne(int index);

    boolean contains(T elem);
    void put(T elem);
    int size();

    Iterable<T> getAll();
}
