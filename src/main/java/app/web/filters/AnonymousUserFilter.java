package app.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({
        "/faces/view/friends.xhtml",
        "/faces/view/home.xhtml",
        "/faces/view/profile.xhtml",
})
public class AnonymousUserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (httpServletRequest.getSession().getAttribute("username") == null) {
            httpServletResponse.sendRedirect("/faces/view/login.xhtml");
        } else {
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
