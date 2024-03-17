package net.molleafauss.loomtest.data.repository;

import net.molleafauss.loomtest.data.models.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long> {
}
