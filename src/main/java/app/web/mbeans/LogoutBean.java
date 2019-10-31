package app.web.mbeans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class LogoutBean extends BaseBean {

    public void logout() throws IOException {
        getSession().invalidate();
        redirect("index");
    }
}
