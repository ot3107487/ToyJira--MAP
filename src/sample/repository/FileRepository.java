package sample.repository;

import java.util.Optional;

public  abstract class FileRepository<T> extends InMemoryRepository<T> {
    private String fileName;

    public FileRepository(String fileName) {
        super();
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * read entities from specified file
     */

    public abstract void readFromFile();

    /**
     * write entitites to specified file . Rewrites all
     */
    protected abstract void writeToFile();

    @Override
    public void save(T elem){
        super.save(elem);
        writeToFile();
    }

    @Override
    public boolean delete(T elem) throws Exception {
        boolean response = super.delete(elem);
        writeToFile();
        return response;
    }

    @Override
    public Optional<T> deleteAt(int index){
        Optional<T> deletedElement=super.deleteAt(index);
        writeToFile();
        return deletedElement;
    }

    @Override
    public void put(T elem){
        super.put(elem);
        writeToFile();
    }
}
