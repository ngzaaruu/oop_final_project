package controllers;

import Model.enums.Language;
import Model.enums.UserType;
import Model.exceptions.UserNotFoundException;
import Model.service.AdminService;
import Model.service.UserFactory;
import Model.users.Admin;
import Model.users.User;
import views.AdminView;

public class AdminController {

    private final AdminService service;
    private final AdminView view;

    public AdminController(AdminService service, AdminView view) {
        this.service = service;
        this.view = view;
    }

    public void openMenu(Admin admin) {
        boolean running = true;

        while (running) {
            try {
                switch (view.showMenu()) {
                    case 1 -> addUser(admin);
                    case 2 -> removeUser(admin);
                    case 3 -> updateUser(admin);
                    case 4 -> view.showUsers(service.getUsers(admin));
                    case 5 -> view.showLogs(service.viewSystemLogs(admin));
                    case 6 -> saveData(admin);
                    case 7 -> loadData(admin);
                    case 8 -> running = false;
                    default -> view.showError("Unknown option");
                }
            } catch (IllegalArgumentException | IllegalStateException | UserNotFoundException e) {
                view.showError(e.getMessage());
            }
        }
    }

    private void addUser(Admin admin) {
        User user = readUser();
        service.addUser(admin, user);
        view.showSuccess("User added.");
    }

    private void removeUser(Admin admin) throws UserNotFoundException {
        service.removeUser(admin, view.readUserId());
        view.showSuccess("User removed.");
    }

    private void updateUser(Admin admin) throws UserNotFoundException {
        User user = readUser();
        service.updateUser(admin, user);
        view.showSuccess("User updated.");
    }

    private void saveData(Admin admin) {
        service.saveData(admin, view.readFilename());
        view.showSuccess("Data saved.");
    }

    private void loadData(Admin admin) {
        service.loadData(admin, view.readFilename());
        view.showSuccess("Data loaded.");
    }

    private User readUser() {
        UserType type = view.readUserType();
        int id = view.readUserId();
        String fullName = view.readFullName();
        String email = view.readEmail();
        String password = view.readPassword();
        String phoneNumber = view.readPhoneNumber();
        Language language = view.readLanguage();

        return UserFactory.createUser(type, id, fullName, email, password, phoneNumber, language);
    }
}
