package Model.users;

import Model.exceptions.UserNotFoundException;
import Model.enums.Language;

import java.util.*;

public class Admin extends Employee {

    private String adminId;
    private String accessLevel;
    private List<User> users;
    private List<String> logs;

    public Admin(int id, String fullName, String email, String password,
                 String phoneNumber, Language language,
                 String employeeId, String adminId, String accessLevel) {

        super(id, fullName, email, password, phoneNumber, language,
              employeeId, null, null, null);

        this.adminId = adminId;
        this.accessLevel = accessLevel;
        this.users = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    public void addUser(User user) {
        if (user == null) return;

        if (!users.contains(user)) {
            users.add(user);
            logs.add("Added user: " + user.getFullName());
        }
    }

    public void removeUser(int userId) throws UserNotFoundException {
        User found = null;

        for (User user : users) {
            if (user.getId() == userId) {
                found = user;
                break;
            }
        }

        if (found == null) {
            throw new UserNotFoundException("User not found: " + userId);
        }

        users.remove(found);
        logs.add("Removed user: " + found.getFullName());
    }

    public void updateUser(User updatedUser) throws UserNotFoundException {
        if (updatedUser == null) return;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == updatedUser.getId()) {
                users.set(i, updatedUser);
                logs.add("Updated user: " + updatedUser.getFullName());
                return;
            }
        }

        throw new UserNotFoundException("User not found: " + updatedUser.getId());
    }

    public List<String> viewLogs() {
        return new ArrayList<>(logs);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
    public String getAdminId() {
        return adminId;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    @Override
    public String toString() {
        return "Admin with " + users.size() + " users";
    }
}
