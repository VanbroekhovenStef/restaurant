package fact.it.restaurantappstart.model;

public class HappyHourPayment implements PaymentStrategy {
    @Override
    public double getAppliedPrice(double currentPrice) {
        return currentPrice * 0.8;
    }
}
