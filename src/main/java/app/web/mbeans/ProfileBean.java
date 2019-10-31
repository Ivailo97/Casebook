package app.web.mbeans;

import app.domain.view.UserProfileViewModel;
import app.service.UserService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ProfileBean extends BaseBean {

    private UserProfileViewModel model;

    private UserService userService;

    private ModelMapper modelMapper;

    public ProfileBean() {
    }

    @Inject
    public ProfileBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {

        String id = getRequestParameterMap().get("id");

        this.model = modelMapper.map(userService.findById(id), UserProfileViewModel.class);
        model.setGender(model.getGender().toLowerCase());
    }

    public UserProfileViewModel getModel() {
        return model;
    }

    public void setModel(UserProfileViewModel model) {
        this.model = model;
    }
}
