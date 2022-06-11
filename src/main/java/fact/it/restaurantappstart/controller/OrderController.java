package fact.it.restaurantappstart.controller;

import fact.it.restaurantappstart.model.*;
import fact.it.restaurantappstart.repository.DishRepository;
import fact.it.restaurantappstart.repository.OrderRepository;
import fact.it.restaurantappstart.repository.StaffRepository;
import fact.it.restaurantappstart.repository.TableRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class OrderController {
    private OrderRepository orderRepository;
    private StaffRepository staffRepository;
    private DishRepository dishRepository;
    private TableRepository tableRepository;

    public OrderController(OrderRepository orderRepository, DishRepository dishRepository, StaffRepository staffRepository, TableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.staffRepository = staffRepository;
        this.tableRepository = tableRepository;
    }

    @RequestMapping("/orders")
    public String orders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @RequestMapping("/order-detail/{id}")
    public String orderDetail(Model model, @PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        model.addAttribute("order", order);
        model.addAttribute("additem", false);
        return "order-detail";
    }

    @RequestMapping("/search-order")
    public String searchOrder(Model model) {
        return "search-order";
    }

    @RequestMapping("/process-search")
    public String searchOrder(Model model, HttpServletRequest request) {
        String table = request.getParameter("table");
        String date = request.getParameter("date");
        String total = request.getParameter("total");

        List<Order> orders = orderRepository.findAll();
        if (!table.equals("")) {
            List<Order> ordersByTable = orders.stream()
                    .filter(e -> e.getTable().getCode().contains(table))
                    .toList();
            orders = ordersByTable;
        }

        if(!date.equals("")) {
            String operatordate = request.getParameter("operatordate");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate filterDate = LocalDate.parse(date, dtf);
            if (operatordate.equals("=")) {
                orders = orders.stream()
                        .filter(e -> e.getDate().isEqual(filterDate)).toList();
            } else if (operatordate.equals("<")) {
                orders = orders.stream()
                        .filter(e -> e.getDate().isBefore(filterDate)).toList();
            } else {
                orders = orders.stream()
                        .filter(e -> e.getDate().isAfter(filterDate)).toList();
            }
        }

        if (!total.equals("")) {
            String operatortotal = request.getParameter("operatortotal");
            double totalAmount = Double.parseDouble(total);
            if (operatortotal.equals("=")) {
                orders = orders.stream()
                        .filter(e -> e.getTotal() == totalAmount).toList();
            } else if (operatortotal.equals("<")) {
                orders = orders.stream()
                        .filter(e -> e.getTotal() < totalAmount).toList();
            } else {
                orders = orders.stream()
                        .filter(e -> e.getTotal() > totalAmount).toList();
            }
        }

        model.addAttribute("orders", orders);

        return "orders";
    }

    @RequestMapping("/order-new")
    public String orderNew(Model model) {
        List<Dish> dishes = dishRepository.findAll();
        model.addAttribute("dishes", dishes);
        List<Table> tables = tableRepository.findAll();
        model.addAttribute("tables", tables);
        List<Waiter> waiters = staffRepository.findWaiters();
        model.addAttribute("waiters", waiters);
        return "order-new";
    }

    @RequestMapping("/order-process")
    public String orderProcess(Model model, HttpServletRequest request) {
        Long waiterId = Long.parseLong(request.getParameter("waiter"));
        Long tableId = Long.parseLong(request.getParameter("table"));

        Waiter waiter = staffRepository.findStaffById(waiterId).get();
        Table table = tableRepository.findById(tableId).get();

        Order order = new Order();
        order.setPayed(false);
        order.setWaiter(waiter);
        order.setTable(table);
        order.setDate(LocalDate.now());
        orderRepository.save(order);

        List<Dish> dishes = dishRepository.findAll();
        model.addAttribute("dishes", dishes);

        model.addAttribute("order", order);
        model.addAttribute("additem", true);
        return "order-detail";
    }

    @RequestMapping("/add-orderitem/{id}")
    public String addOrderItem(Model model, @PathVariable Long id) {
        model.addAttribute("additem", true);
        List<Dish> dishes = dishRepository.findAll();
        model.addAttribute("dishes", dishes);
        Order order = orderRepository.findById(id).get();
        model.addAttribute("order", order);
        return "order-detail";
    }

    @RequestMapping("/orderitem-process")
    public String orderItemProcess(Model model, HttpServletRequest request) {
        Long dishId = Long.parseLong(request.getParameter("dish"));
        Long orderId = Long.parseLong(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String saveOption = request.getParameter("save");

        Order order = orderRepository.findById(orderId).get();
        Dish dish = dishRepository.findById(dishId).get();
        order.addItem(dish, quantity);

        orderRepository.save(order);

        model.addAttribute("order", order);
        if(saveOption.equals("Save and add new")) {
            model.addAttribute("additem", true);
            List<Dish> dishes = dishRepository.findAll();
            model.addAttribute("dishes", dishes);
        } else {
            model.addAttribute("additem", false);
        }

        return "order-detail";
    }
}
