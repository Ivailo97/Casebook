package app.web.mbeans;

import app.domain.view.UserAllViewModel;
import app.service.UserService;
import org.modelmapper.ModelMapper;
import org.w3c.dom.ls.LSOutput;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@ManagedBean
@RequestScoped
public class HomeBean extends BaseBean {

    private List<UserAllViewModel> models;

    private UserService userService;

    private ModelMapper modelMapper;

    public HomeBean() {
    }

    @Inject
    public HomeBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        this.models = userService
                .findAllAvailableFriendsToAdd((String) getSession().getAttribute("id"))
                .stream()
                .map(u -> {
                    UserAllViewModel userAllViewModel = modelMapper.map(u, UserAllViewModel.class);
                    userAllViewModel.setGender(u.getGender().toLowerCase());
                    return userAllViewModel;
                })
                .collect(Collectors.toList());
    }

    public List<UserAllViewModel> getModels() {

        return models;
    }

    public void setModels(List<UserAllViewModel> models) {
        this.models = models;
    }

    public void addFriend(String friendId) throws IOException {

        String loggedUserId = (String) getSession().getAttribute("id");

        boolean addedSuccessfully = userService.addFriend(loggedUserId, friendId);

        redirect("home");
    }
}
