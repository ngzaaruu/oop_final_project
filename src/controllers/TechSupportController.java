package controllers;

import Model.communication.Request;
import Model.enums.RequestStatus;
import Model.service.TechSupportService;
import Model.users.TechSupportSpecialist;
import views.TechSupportView;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class TechSupportController {

    private final TechSupportService service;
    private final TechSupportView view;

    public TechSupportController(TechSupportService service, TechSupportView view) {
        this.service = service;
        this.view = view;
    }

    public void openMenu(TechSupportSpecialist specialist) {
        boolean running = true;

        while (running) {
            try {
                switch (view.showMenu()) {
                    case 1 -> viewRequests(specialist);
                    case 2 -> acceptRequest(specialist);
                    case 3 -> rejectRequest(specialist);
                    case 4 -> markDone(specialist);
                    case 5 -> viewRequestsByStatus();
                    case 6 -> running = false;
                    default -> view.showError("Unknown option");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                view.showError(e.getMessage());
            }
        }
    }

    public void viewRequests(TechSupportSpecialist specialist) {
        view.showRequests(service.viewRequests(specialist));
    }

    private void acceptRequest(TechSupportSpecialist specialist) {
        List<Request> requests = getViewableRequests(specialist);
        view.showRequests(requests);
        Request request = selectRequest(requests);
        service.acceptRequest(specialist, request);
        view.showSuccess("Request accepted.");
    }

    private void rejectRequest(TechSupportSpecialist specialist) {
        List<Request> requests = getViewableRequests(specialist);
        view.showRequests(requests);
        Request request = selectRequest(requests);
        service.rejectRequest(specialist, request, view.readReason());
        view.showSuccess("Request rejected.");
    }

    private void markDone(TechSupportSpecialist specialist) {
        List<Request> requests = service.getRequestsByStatus(RequestStatus.ACCEPTED);
        view.showRequests(requests);
        Request request = selectRequest(requests);
        service.markDone(specialist, request);
        view.showSuccess("Request marked as done.");
    }

    private void viewRequestsByStatus() {
        RequestStatus status = view.readStatus();
        view.showRequests(service.getRequestsByStatus(status));
    }

    private Request selectRequest(List<Request> requests) {
        int index = view.readRequestIndex();
        if (index < 0 || index >= requests.size()) {
            throw new IllegalArgumentException("Request not found");
        }
        return requests.get(index);
    }

    private List<Request> getViewableRequests(TechSupportSpecialist specialist) {
        LinkedHashSet<Request> requests = new LinkedHashSet<>();
        requests.addAll(service.viewRequests(specialist));
        requests.addAll(service.getRequestsByStatus(RequestStatus.VIEWED));
        return new ArrayList<>(requests);
    }
}

