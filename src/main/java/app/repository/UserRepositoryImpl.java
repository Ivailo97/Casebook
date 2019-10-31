package app.repository;

import app.domain.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        entityManager.getTransaction().begin();

        User user;

        try {
            user = entityManager
                    .createQuery("SELECT u FROM User u WHERE u.username =:username AND u.password =:password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception ex) {
            user = null;
        }

        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public User findByUsername(String username) {
        entityManager.getTransaction().begin();

        User user;

        try {
            user = entityManager
                    .createQuery("SELECT u FROM User u WHERE u.username =:username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception ex) {
            user = null;
        }

        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public User save(User entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public User findById(String id) {
        entityManager.getTransaction().begin();

        User user;
        try {

            user = entityManager
                    .createQuery("SELECT u FROM User u WHERE u.id =:id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception ex) {
            user = null;
        }

        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public List<User> findAll() {
        entityManager.getTransaction().begin();

        List<User> users = entityManager
                .createQuery("SELECT u FROM User u", User.class)
                .getResultList();

        entityManager.getTransaction().commit();
        return users;
    }

    @Override
    public Long count() {
        entityManager.getTransaction().begin();

        Long count = entityManager
                .createQuery("SELECT count(u) FROM User u", Long.class)
                .getSingleResult();

        entityManager.getTransaction().commit();
        return count;
    }

    @Override
    public void refresh(User entity) {

        entityManager.getTransaction().begin();

        entityManager.merge(entity);

        entityManager.getTransaction().commit();
    }

    @Override
    public List<User> findAllWhoHaveUserInFriends(String userId) {

        return findById(userId).getFriends();
    }
}
