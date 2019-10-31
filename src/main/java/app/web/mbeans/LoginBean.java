package app.web.mbeans;

import app.domain.model.binding.UserLoginBindingModel;
import app.domain.model.service.UserServiceModel;
import app.service.UserService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class LoginBean extends BaseBean {

    private UserService userService;

    private ModelMapper modelMapper;

    private UserLoginBindingModel model;

    public LoginBean() {
    }

    @Inject
    public LoginBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        this.model = new UserLoginBindingModel();
    }

    public UserLoginBindingModel getModel() {
        return model;
    }

    public void login() throws IOException {

        UserServiceModel user = modelMapper.map(model, UserServiceModel.class);

        boolean successfullyLogged = userService.login(user);

        if (successfullyLogged) {
            redirect("home");
        }
    }
}
