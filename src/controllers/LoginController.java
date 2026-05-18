package controllers;

import Model.exceptions.AuthenticationException;
import Model.service.AuthService;
import Model.users.User;
import views.LoginView;

public class LoginController {

    private final AuthService authService;
    private final LoginView view;

    public LoginController(AuthService authService, LoginView view) {
        this.authService = authService;
        this.view = view;
    }

    public User login() {
        try {
            User user = authService.login(view.readEmail(), view.readPassword());
            view.showLoginSuccess(authService.detectRole(user).name());
            return user;
        } catch (AuthenticationException | IllegalArgumentException e) {
            view.showError(e.getMessage());
            return null;
        }
    }

    public void logout() {
        authService.logout();
    }
}
