package Model.service;

import Model.exceptions.UserNotFoundException;
import Model.users.Admin;
import Model.users.User;

import java.util.List;

public class AdminService {

    private DataStorage storage;
    private final Logger logger;

    public AdminService() {
        this(DataStorage.getInstance(), Logger.getInstance());
    }

    public AdminService(DataStorage storage, Logger logger) {
        this.storage = storage;
        this.logger = logger;
    }

    public void addUser(Admin admin, User user) {
        requireAdmin(admin);
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (storage.findUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        storage.addUser(user);
        admin.addUser(user);
        logger.log(admin, "Added user: " + user.getFullName());
    }

    public void removeUser(Admin admin, int userId) throws UserNotFoundException {
        requireAdmin(admin);
        User user = storage.findUserById(String.valueOf(userId));
        if (user == null) {
            throw new UserNotFoundException("User not found: " + userId);
        }

        storage.removeUser(user);
        logger.log(admin, "Removed user: " + user.getFullName());
    }

    public void updateUser(Admin admin, User updatedUser) throws UserNotFoundException {
        requireAdmin(admin);
        if (updatedUser == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (!storage.updateUser(updatedUser)) {
            throw new UserNotFoundException("User not found: " + updatedUser.getId());
        }

        logger.log(admin, "Updated user: " + updatedUser.getFullName());
    }

    public List<User> getUsers(Admin admin) {
        requireAdmin(admin);
        logger.log(admin, "Viewed users");
        return storage.getUsers();
    }

    public List<String> viewSystemLogs(Admin admin) {
        requireAdmin(admin);
        logger.log(admin, "Viewed system logs");
        return logger.getLogs();
    }

    public void saveData(Admin admin, String filename) {
        requireAdmin(admin);
        storage.save(filename);
        logger.log(admin, "Saved data to " + filename);
    }

    public void loadData(Admin admin, String filename) {
        requireAdmin(admin);
        storage = DataStorage.load(filename);
        logger.log(admin, "Loaded data from " + filename);
    }

    private void requireAdmin(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Admin is required");
        }
    }
}
