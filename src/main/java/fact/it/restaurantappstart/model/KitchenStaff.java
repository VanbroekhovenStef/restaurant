package fact.it.restaurantappstart.model;

import javax.persistence.Entity;

@Entity
public class KitchenStaff extends Staff{
    public KitchenStaff() {
    }

    public KitchenStaff(String name) {
        super(name);
    }

    public String job() {
        return "Kitchen";
    }

    @Override
    public void update() {
        String keukenstring = "I am " + getName() +" and I start now with preparing "+ EntranceCounter.getInstance().getNumber() + " appetizers!";
        System.out.println(keukenstring);
    }
}
