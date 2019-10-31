package app.repository;

import app.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends GenericRepository<User, String> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    void refresh(User user);

    List<User> findAllWhoHaveUserInFriends(String userId);
}
