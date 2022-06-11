package fact.it.restaurantappstart.model;

import java.util.ArrayList;

public abstract class Subject {
    private ArrayList<Staff> observers = new ArrayList<>();

    public Subject() {
    }

    public ArrayList<Staff> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Staff> observers) {
        this.observers = observers;
    }

    public void attachObserver(Staff obs) {
        observers.add(obs);
    }

    public void detachObserver(Staff obs) {
        observers.remove(obs);
    }

    public void notifyObservers() {
        for(Staff o:observers) {
            o.update();
        }
    }
}
