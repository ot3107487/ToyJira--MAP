package sample.service;

import sample.domain.User;
import sample.repository.Repository;
import sample.service.Service;

public class ServiceUser extends Service<User>{
    public ServiceUser(Repository<User> repository) {
        super(repository);
    }
}
