package fact.it.restaurantappstart.model;

public class NormalPayment implements PaymentStrategy {
    @Override
    public double getAppliedPrice(double currentPrice) {
        return currentPrice;
    }
}
