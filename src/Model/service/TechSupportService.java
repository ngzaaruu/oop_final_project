package Model.service;

import Model.communication.Request;
import Model.enums.RequestStatus;
import Model.users.TechSupportSpecialist;
import Model.users.User;

import java.util.ArrayList;
import java.util.List;

public class TechSupportService {

    private final DataStorage storage;

    public TechSupportService() {
        this.storage = DataStorage.getInstance();
    }

    public List<Request> viewRequests(TechSupportSpecialist specialist) {
        if (specialist == null) {
            throw new IllegalArgumentException("Specialist cannot be null");
        }

        List<Request> requests = specialist.viewNewRequests(getAllRequests());
        for (Request request : requests) {
            request.markViewed();
        }
        return requests;
    }

    public void acceptRequest(TechSupportSpecialist specialist, Request request) {
        validateSpecialistAndRequest(specialist, request);
        specialist.acceptRequest(request);
    }

    public void rejectRequest(TechSupportSpecialist specialist, Request request, String reason) {
        validateSpecialistAndRequest(specialist, request);
        specialist.rejectRequest(request, reason);
    }

    public void markDone(TechSupportSpecialist specialist, Request request) {
        validateSpecialistAndRequest(specialist, request);
        specialist.markRequestDone(request);
    }

    public List<Request> getRequestsByStatus(RequestStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        List<Request> result = new ArrayList<>();
        for (Request request : getAllRequests()) {
            if (request.getStatus() == status) {
                result.add(request);
            }
        }
        return result;
    }

    private List<Request> getAllRequests() {
        List<Request> result = new ArrayList<>();
        for (User user : storage.getUsers()) {
            result.addAll(user.getRequests());
        }
        return result;
    }

    private void validateSpecialistAndRequest(TechSupportSpecialist specialist, Request request) {
        if (specialist == null || request == null) {
            throw new IllegalArgumentException("Specialist and request cannot be null");
        }
    }
}

