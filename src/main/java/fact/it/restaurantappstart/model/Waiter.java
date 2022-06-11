package fact.it.restaurantappstart.model;

import javax.persistence.Entity;

@Entity
public class Waiter extends Staff {
    public Waiter() {
    }

    public Waiter(String name) {
        super(name);
    }

    public String job() {
        return "Waiter";
    }

    @Override
    public void update() {
        String zaalstring = "I am " + getName() +
                " and I start preparing the table for " + EntranceCounter.getInstance().getNumber() + " customers.";
        System.out.println(zaalstring);
    }
}
