package app.service;

import app.domain.model.service.UserServiceModel;

import java.util.List;

public interface UserService {

    boolean register(UserServiceModel user);

    boolean login(UserServiceModel user);

    boolean addFriend(String loggedUserId, String friendId);

    boolean removeFriend(String loggedUserId, String friendId);

    List<UserServiceModel> findAllAvailableFriendsToAdd(String loggedUserId);

    List<UserServiceModel> findAllFriends(String userId);

    UserServiceModel findById(String id);
}
