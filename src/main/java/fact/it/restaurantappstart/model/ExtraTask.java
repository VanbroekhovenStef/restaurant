package fact.it.restaurantappstart.model;

public abstract class ExtraTask extends Staff {
    private Staff staff;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public void update() {
        staff.update();
    };
}
