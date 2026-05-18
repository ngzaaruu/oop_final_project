package Model.users;

import Model.communication.Request;
import Model.enums.RequestStatus;
import Model.enums.Language;

import java.math.BigDecimal;
import java.util.*;

public class TechSupportSpecialist extends Employee {

    private static final long serialVersionUID = 1L;

    private String specialistId;
    private List<Request> handledRequests;

    public TechSupportSpecialist(int id, String fullName, String email, String password,
                                 String phoneNumber, Language language,
                                 String employeeId, BigDecimal salary,
                                 String officeNumber, String department,
                                 String specialistId) {

        super(id, fullName, email, password, phoneNumber, language,
              employeeId, salary, officeNumber, department);

        this.specialistId = specialistId;
        this.handledRequests = new ArrayList<>();
    }

    public List<Request> viewNewRequests(List<Request> requests) {
        List<Request> result = new ArrayList<>();

        for (Request r : requests) {
            if (r.getStatus() == RequestStatus.NEW) {
                result.add(r);
            }
        }

        return result;
    }

    public void acceptRequest(Request request) {
        if (request == null) return;

        request.accept();
        if (!handledRequests.contains(request)) {
            handledRequests.add(request);
        }
    }

    public void rejectRequest(Request request, String reason) {
        if (request == null) return;

        request.reject();
        System.out.println("Reason: " + reason);
    }

    public void markRequestDone(Request request) {
        if (request == null) return;

        request.complete();
    }

    public void updateRequestStatus(Request request, RequestStatus status) {
        if (request == null || status == null) return;

        switch (status) {
            case NEW -> {} 
            case VIEWED -> request.markViewed();
            case ACCEPTED -> request.accept();
            case REJECTED -> request.reject();
            case DONE -> request.complete();
        }
    }

    public List<Request> getHandledRequests() {
        return new ArrayList<>(handledRequests);
    }

    public String getSpecialistId() {
        return specialistId;
    }
}
