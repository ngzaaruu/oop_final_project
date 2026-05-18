package Model.service;

import Model.enums.UserType;
import Model.exceptions.AuthenticationException;
import Model.users.Admin;
import Model.users.GraduateStudent;
import Model.users.Manager;
import Model.users.ResearchEmployee;
import Model.users.Student;
import Model.users.Teacher;
import Model.users.TechSupportSpecialist;
import Model.users.User;

public class AuthService {

    private DataStorage storage;
    private final Logger logger;
    private User currentUser;

    public AuthService() {
        this(DataStorage.getInstance(), Logger.getInstance());
    }

    public AuthService(DataStorage storage, Logger logger) {
        this.storage = storage;
        this.logger = logger;
    }

    public User login(String email, String password) throws AuthenticationException {
        storage = DataStorage.getInstance();
        User user = storage.findUserByEmail(email);
        if (user == null || !user.login(email, password)) {
            logger.log("Failed login attempt for email: " + email);
            throw new AuthenticationException();
        }

        currentUser = user;
        logger.log(user, "Logged in as " + detectRole(user));
        return user;
    }

    public void logout() {
        if (currentUser == null) {
            return;
        }

        logger.log(currentUser, "Logged out");
        currentUser.logout();
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public UserType detectRole(User user) {
        if (user instanceof Admin) return UserType.ADMIN;
        if (user instanceof GraduateStudent) return UserType.GRADUATE_STUDENT;
        if (user instanceof Student) return UserType.STUDENT;
        if (user instanceof Teacher) return UserType.TEACHER;
        if (user instanceof Manager) return UserType.MANAGER;
        if (user instanceof TechSupportSpecialist) return UserType.TECH_SUPPORT_SPECIALIST;
        if (user instanceof ResearchEmployee) return UserType.RESEARCHER;
        throw new IllegalArgumentException("Unknown user role");
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
