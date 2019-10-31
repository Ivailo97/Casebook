package app.web.mbeans;

import app.domain.view.UserFriendViewModel;
import app.service.UserService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class FriendsBean extends BaseBean {

    private List<UserFriendViewModel> friends;

    private UserService userService;

    private ModelMapper modelMapper;

    public FriendsBean() {
    }

    @Inject
    public FriendsBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {

        this.friends = userService.findAllFriends((String) getSession().getAttribute("id"))
                .stream()
                .map(u -> modelMapper.map(u, UserFriendViewModel.class))
                .collect(Collectors.toList());
    }

    public List<UserFriendViewModel> getFriends() {
        return friends;
    }

    public void setFriends(List<UserFriendViewModel> friends) {
        this.friends = friends;
    }

    public void unfriend(String friendId) throws IOException {

        String loggedUserId = (String) getSession().getAttribute("id");

        if (userService.removeFriend(loggedUserId,friendId)){
            redirect("home");
        }
    }
}
