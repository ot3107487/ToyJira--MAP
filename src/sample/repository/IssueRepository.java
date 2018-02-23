package sample.repository;

import sample.domain.Issue;
import sample.domain.IssueType;
import sample.domain.Status;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class IssueRepository extends FileRepository<Issue> {

    public IssueRepository(String fileName) {
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
                    if (fields.length != 8) {
                        throw new Exception("Fisier corupt!");
                    }
                    Issue issue = new Issue(Integer.parseInt(fields[0]),fields[1], fields[2], IssueType.valueOf(fields[3]),
                            fields[4],fields[5], Status.valueOf(fields[6]),fields[7]);
                    super.save(issue);

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

    private String issueToFileString(Issue issue) {
        long id = issue.getId();
        String summary = issue.getSummary();
        String desc = issue.getDescription();
        IssueType it=issue.getType();
        String ass=issue.getAssignTo();
        String reg=issue.getRegisteredBy();
        Status st=issue.getStatus();
        String date=issue.getRegisterDate();
        return Long.toString(id) + ";" + summary + ";" + desc
                + ";" + it + ";" + ass+";"+reg+";"+st+";"+date;
    }

    @Override
    protected void writeToFile() {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(getFileName()))) {
            List<Issue> list = (List<Issue>) getAll();
            for (Issue issue : list) {
                bf.write(issueToFileString(issue));
                bf.write('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
