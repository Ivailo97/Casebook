package app.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter({
        "/faces/view/register.xhtml",
        "/faces/view/login.xhtml",
        "/faces/view/index.xhtml"
})
public class LoggedUserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        if (session.getAttribute("username") != null) {
            res.sendRedirect("/faces/view/home.xhtml");
        }else {
            chain.doFilter(req, res);
        }
    }
}
