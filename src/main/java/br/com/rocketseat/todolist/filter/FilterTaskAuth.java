package br.com.rocketseat.todolist.filter;

import br.com.rocketseat.todolist.error.UnauthenticatedUserException;
import br.com.rocketseat.todolist.error.UserNotFoundException;
import br.com.rocketseat.todolist.user.IUserRepository;
import br.com.rocketseat.todolist.user.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Optional;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    private IUserRepository iUserRepository;

    @Autowired
    public FilterTaskAuth(IUserRepository iUserRepository){
        this.iUserRepository = iUserRepository;
    }

    private String getAuthorizationFromHeaderRequest(HttpServletRequest request){
        String NAME_HEADER = "credentials";
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (headerName.toLowerCase().startsWith(NAME_HEADER)) {
                return request.getHeader(headerName);
            }
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String EVALUATED_PATH = "/tasks";

        var servletPath = request.getServletPath();
        System.out.println("PATH: " + servletPath);

        if (servletPath.contains(EVALUATED_PATH)) {
            // Get authentication (username and password)
            var authorization = getAuthorizationFromHeaderRequest(request);

            if(authorization == null){
                throw new UnauthenticatedUserException("Authorizations not found.");
            }

            String[] credentials = authorization.split(":");
            String username = credentials[0];
            String password = credentials[1];

            /*
            var authEncoded = authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecode);
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];
            */

            // User validation
            Optional<UserModel> userModelOpt = this.iUserRepository.findByUsername(username);
            if (userModelOpt.isEmpty()) {
                throw new UserNotFoundException("There is no record for the username " + username + ".");
            } else {
                UserModel userModel = userModelOpt.get();
                // Password validation
                if (password.equals(userModel.getPassword())) {
                    request.setAttribute("idUser", userModel.getId());
                    filterChain.doFilter(request, response);
                } else {
                    throw new UnauthenticatedUserException("Wrong password for user " + username + ".");
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
