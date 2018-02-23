package sample.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryRepository<T> implements Repository<T> {
    //List implementation
    private List<T> list;

    public InMemoryRepository() {
        this.list = new ArrayList<T>();
    }

    /**
     * Saves an elem into list. If elem already exists , throws exception
     * @param elem - the element that has to be saved
     */
    public void save(T elem) {
        list.add(elem);
    }
    public void put(T elem){
        list.set(list.indexOf(elem),elem);
    }

    /**
     * Function that deletes an elem from list
     *
     * @param elem - the element that has to be deleted
     * @return true if the specified element was found into the list
     * @throws Exception if the element is null
     */
    public boolean delete(T elem) throws Exception {
        return list.remove(elem);
    }

    /**
     * Deletes an element on a given position
     * @param index - position in list
     * @return deleted element
     * @throws Exception if index is invalid
     */
    public Optional<T> deleteAt(int index){
        return Optional.ofNullable(list.remove(index));
    }

    /**
     * Get an element from sample.repository at specified index
     *
     * @param index
     * @return element found at position index
    */
    public Optional<T> getOne(int index){
        return Optional.ofNullable(list.get(index));
    }

    /**
     * Verify if an element exists in list
     *
     * @param elem - the verified elemt
     * @return true - if elem is in list
     * false - otherwise
     */
    public boolean contains(T elem) {
        return list.contains(elem);
    }

    /**
     * Retrieve all elements from list
     *
     * @return Iterable - an iterable with all copied elements from list
     */
    public Iterable<T> getAll(){
        return list;
    }

    /**
     * See the number of elements in list
     *
     * @return sample.repository's size as int
     */
    public int size() {
        return list.size();
    }

}
