package fact.it.restaurantappstart.model;

public class EntranceCounter extends Subject {
    private static EntranceCounter entranceCounter;
    private int number;

    public EntranceCounter() {
        System.out.println("A singleton object is created.");
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        notifyObservers();
    }

    public static EntranceCounter getInstance() {
        if(entranceCounter == null) {
            entranceCounter = new EntranceCounter();
        }
        return entranceCounter;
    }
}
