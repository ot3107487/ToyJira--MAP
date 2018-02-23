package sample.repository;

import sample.domain.*;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class UserRepository extends FileRepository<User> {

    public UserRepository(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        Path path = Paths.get(getFileName());
        Stream<String> lines;
        try {
            lines = Files.lines(path);
            lines.forEach(line -> {
                try {
                    String[] fields = line.split(";");
                    if (fields.length != 3) {
                        throw new Exception("Fisier corupt!");
                    }
                    User user = new User(Integer.parseInt(fields[0]),fields[1], UserType.valueOf(fields[2]));
                    super.save(user);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu a fost gasit!");
        } catch (IOException e) {
            System.out.println("Eroare la citire");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String issueToFileString(User user) {
        int id=user.getId();
        String name=user.getNume();
        String type=user.getType().toString();
        return Integer.toString(id) + ";" + name+";"+type;
    }

    @Override
    protected void writeToFile() {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(getFileName()))) {
            List<User> list = (List<User>) getAll();
            for (User issue : list) {
                bf.write(issueToFileString(issue));
                bf.write('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
