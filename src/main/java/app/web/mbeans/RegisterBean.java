package app.web.mbeans;

import app.domain.model.binding.UserRegisterBindingModel;
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
public class RegisterBean extends BaseBean {

    private UserRegisterBindingModel model;

    private UserService userService;

    private ModelMapper modelMapper;

    public RegisterBean() {
    }

    @Inject
    public RegisterBean(UserRegisterBindingModel model, UserService userService, ModelMapper modelMapper) {
        this.model = model;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        this.model = new UserRegisterBindingModel();
    }

    public UserRegisterBindingModel getModel() {
        return model;
    }

    public void setModel(UserRegisterBindingModel model) {
        this.model = model;
    }

    public void register() throws IOException {

        if (!model.getPassword().equals(model.getConfirmPassword())) {
            redirect("/register");
        }

        UserServiceModel user = modelMapper.map(model, UserServiceModel.class);

        boolean registeredSuccessfully = userService.register(user);

        if (registeredSuccessfully) {
            redirect("/login");
        }
    }
}
