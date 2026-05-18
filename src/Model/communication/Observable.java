package Model.communication;

public interface Observable {

    void subscribe(Observer observer);

    void unsubscribe(Observer observer);

    void notifySubscribers(Object update);
}
