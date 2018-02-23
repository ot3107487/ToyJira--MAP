package sample.service;

import sample.repository.Repository;
import sample.utils.ListEvent;
import sample.utils.ListEventType;
import sample.utils.Observable;
import sample.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Service<T> implements Observable<T> {
    protected Repository<T> repository;
    ArrayList<Observer<T>> observers = new ArrayList<>();

    public Service(Repository<T> repository) {
        this.repository = repository;
    }

    /**
     * Saves an element in sample.repository
     *
     * @param elem - element to be saved
     * @throws Exception if the element is not valid
     */
    public void save(T elem) {
        repository.save(elem);
        ListEvent<T> ev = createEvent(ListEventType.ADD, elem, getAll());
        notifyObservers(ev);
    }

    /**
     * Deletes an element from sample.repository
     *
     * @param elem element to be deleted
     * @return true if the element was successfully deleted
     * false - otherwise
     * @throws Exception if elem is null
     */
    public boolean delete(T elem) throws Exception {
        if (repository.delete(elem)) {
            ListEvent<T> ev = createEvent(ListEventType.REMOVE, elem, getAll());
            notifyObservers(ev);
            return true;
        }
        return false;

    }

    public Optional<T> deleteAt(int index) {
        return repository.deleteAt(index);
    }

    /**
     * Get an element from sample.repository
     *
     * @param index - position of element in sample.repository
     * @return - retrieved element
     * @throws Exception if index is invalid
     */
    public Optional<T> getOne(int index) {
        return repository.getOne(index);
    }

    /**
     * Verify if sample.repository contains an element
     *
     * @param elem - element to verif
     * @return true - if sample.repository contains elem
     * false - otherwise
     */
    public boolean contains(T elem) {
        return repository.contains(elem);
    }

    /**
     * Retrievs all elements from sample.repository
     *
     * @return an an iterable with all copied elements from sample.repository
     */
    public List<T> getAll() {
        return (List<T>) repository.getAll();
    }

    public void put(T elem) {
        repository.put(elem);
        ListEvent<T> ev = createEvent(ListEventType.UPDATE, elem, getAll());
        notifyObservers(ev);
    }

    /**
     * See the number of elements in list
     *
     * @return sample.repository's size as int
     */

    public int size() {
        return repository.size();
    }


    @Override
    public void addObserver(Observer<T> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<T> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(ListEvent<T> event) {
        observers.forEach(x -> x.notifyEvent(event));
    }

    private ListEvent<T> createEvent(ListEventType type, final T elem, final Iterable<T> l) {
        return new ListEvent<T>(type) {
            @Override
            public Iterable<T> getList() {
                return l;
            }

            @Override
            public T getElement() {
                return elem;
            }
        };
    }
}