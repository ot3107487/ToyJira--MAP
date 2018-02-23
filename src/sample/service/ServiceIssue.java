package sample.service;

import sample.domain.Issue;
import sample.repository.Repository;

public class ServiceIssue extends Service<Issue> {

    public ServiceIssue(Repository<Issue> repository) {
        super(repository);
    }
}
