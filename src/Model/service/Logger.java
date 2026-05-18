package Model.service;

import Model.users.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Logger {

    private static final Logger instance = new Logger();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger() {
    }

    public static Logger getInstance() {
        return instance;
    }

    public void log(String action) {
        write(null, action);
    }

    public void log(User user, String action) {
        write(user, action);
    }

    public List<String> getLogs() {
        return DataStorage.getInstance().getSystemLogs();
    }

    private void write(User user, String action) {
        if (action == null || action.isEmpty()) {
            return;
        }

        String actor = user == null ? "SYSTEM" : user.getFullName() + " <" + user.getEmail() + ">";
        DataStorage.getInstance().addSystemLog(formatter.format(LocalDateTime.now()) + " | " + actor + " | " + action);
    }
}
