package app.service;

import app.domain.entity.User;
import app.domain.model.service.UserServiceModel;
import app.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Inject
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean register(UserServiceModel user) {


        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }

        user.setGender(user.getGender().toUpperCase());

        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));

        if (!user.getGender().equals("MALE") && !user.getGender().equals("FEMALE")) {
            return false;
        }

        User entity = modelMapper.map(user, User.class);

        userRepository.save(entity);

        return true;
    }

    @Override
    public boolean login(UserServiceModel user) {

        if (userRepository.findByUsernameAndPassword(user.getUsername(), DigestUtils.sha256Hex(user.getPassword())) == null) {
            return false;
        }

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("username", user.getUsername());
        session.setAttribute("id", userRepository.findByUsername(user.getUsername()).getId());

        return true;
    }

    @Override
    public boolean addFriend(String loggedUserId, String friendId) {

        User loggedUser = userRepository.findById(loggedUserId);

        User friendToAdd = userRepository.findById(friendId);

        if (loggedUser != null && friendToAdd != null) {

            loggedUser.getFriends().add(friendToAdd);
            friendToAdd.getFriends().add(loggedUser);
            userRepository.refresh(loggedUser);
            userRepository.refresh(friendToAdd);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeFriend(String loggedUserId, String friendId) {

        User loggedUser = userRepository.findById(loggedUserId);

        User friendToRemove = userRepository.findById(friendId);

        if (loggedUser != null && friendToRemove != null) {

            loggedUser.getFriends().remove(friendToRemove);
            friendToRemove.getFriends().remove(loggedUser);

            userRepository.refresh(loggedUser);
            userRepository.refresh(friendToRemove);

            return true;
        }

        return false;
    }

    @Override
    public List<UserServiceModel> findAllAvailableFriendsToAdd(String loggedUserId) {
        return userRepository.findAll()
                .stream()
                .filter(u -> !u.getId().equals(loggedUserId))
                .filter(u -> !u.getFriends().stream().map(User::getId).collect(Collectors.toList()).contains(loggedUserId))
                .map(u -> modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserServiceModel> findAllFriends(String userId) {

        return userRepository.findAllWhoHaveUserInFriends(userId)
                .stream()
                .map(u -> modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findById(String id) {
        return modelMapper.map(userRepository.findById(id), UserServiceModel.class);
    }
}
