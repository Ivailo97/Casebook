package app.web.mbeans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class BaseBean {

    protected void redirect(String viewName) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/faces/view/" + viewName + ".xhtml");
    }

    protected HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    protected Map<String, String> getRequestParameterMap() {
        return FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
    }
}
