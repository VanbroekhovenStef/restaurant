package fact.it.restaurantappstart.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name="table_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="orderdate")
    private LocalDate date;
    private boolean payed;

    @ManyToOne
    private Waiter waiter;
    @ManyToOne
    private Table table;
    @OneToMany(mappedBy="order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItemsList = new ArrayList<>();

    @Transient
    private PaymentStrategy paymentStrategy = new NormalPayment();

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public List<OrderItem> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItem> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void addItem(Dish dish, int aantal) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(aantal);
        orderItem.setDish(dish);
        orderItem.setOrder(this);
        orderItem.setAppliedPrice(paymentStrategy.getAppliedPrice(dish.getCurrentPrice()));
        orderItemsList.add(orderItem);
    }

    public double getTotal() {
        double total = 0;
        for(OrderItem bi : orderItemsList) {
            total += bi.getQuantity()*bi.getAppliedPrice();
        }
        return total;
    }
}
