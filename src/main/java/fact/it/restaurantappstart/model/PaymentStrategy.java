package fact.it.restaurantappstart.model;

public interface PaymentStrategy {
    public double getAppliedPrice(double currentPrice);
}
