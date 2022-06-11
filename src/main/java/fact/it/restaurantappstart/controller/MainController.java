package fact.it.restaurantappstart.controller;

import fact.it.restaurantappstart.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import fact.it.restaurantappstart.model.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class MainController {
    private DishRepository dishRepository;
    private OrderRepository orderRepository;
    private StaffRepository staffRepository;
    private TableRepository tableRepository;

    public MainController(DishRepository dishRepository, OrderRepository orderRepository, StaffRepository staffRepository, TableRepository tableRepository) {
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
        this.staffRepository = staffRepository;
        this.tableRepository = tableRepository;
    }

    @RequestMapping("/start")
    public String starten(Model model, HttpServletRequest request) {
        String feedbacktekst ="";

        if (request.getParameter("singletontest") != null) {
            System.out.println("####################################################################");
            EntranceCounter it1 = EntranceCounter.getInstance();
            EntranceCounter it2;
            it2 = EntranceCounter.getInstance();
            if (it1 == it2) {
                System.out.println("The 2 singleton variables refer to the same object.");
            } else {
                System.out.println("The Singleton-pattern is not correct implemented");
            }
            System.out.println("####################################################################");
            feedbacktekst = "The singletontest has been executed. Check the output window of IntelliJ to see the result";

        }
        if (request.getParameter("observertest") != null) {
            EntranceCounter klantTeller = EntranceCounter.getInstance();

            //een paar personeelsleden
            Waiter jan = new Waiter("Jan");
            Waiter piet = new Waiter("Piet");
            KitchenStaff serge = new KitchenStaff("Serge");
            KitchenStaff jeroen = new KitchenStaff("Jeroen");

            //we koppelen de spelers en scheidsrechter als observer aan de bal
            klantTeller.attachObserver(jan);
            klantTeller.attachObserver(piet);
            klantTeller.attachObserver(serge);
            klantTeller.attachObserver(jeroen);


            System.out.println("####################################################################");
            System.out.println("After attaching the observers, 5 customers enter the restaurant (the number of entrance counter is set at 5)");
            //bal van positie veranderen
            klantTeller.setNumber(5);
            //lege lijn
            System.out.println();
            //we doen enkele observers weg
            klantTeller.detachObserver(piet);
            klantTeller.detachObserver(serge);

            System.out.println("After detaching Piet and Serge, 3 customers enter the restaurant (the number of the entrance counter is set tot 3)");
            //we veranderen de bal weer van positie
            klantTeller.setNumber(3);
            System.out.println("####################################################################");
            feedbacktekst = "The observer test has been executed. Check the output window of IntelliJ to see the result";

        }
        if (request.getParameter("strategytest") != null) {
            System.out.println("####################################################################");
            //Betalingstrategieën aanmaken
            HappyHourPayment happyHourPayment = new HappyHourPayment();
            NormalPayment normalPayment = new NormalPayment();
            //gerechten aanmaken
            Dish videe = new Dish();
            videe.setName("Fish and Chips");
            videe.setCurrentPrice(15.0);
            Dish croque = new Dish();
            croque.setName("Cottage Pie");
            croque.setCurrentPrice(10.0);


            //maak order met bestelitems
            Order order = new Order();
            //NORMAAL
            order.setPaymentStrategy(normalPayment);
            order.setDate(LocalDate.now());
            order.addItem(videe, 2);
            order.addItem(croque, 2);
            System.out.println("The normal payment strategy is implemented: ");
            for( OrderItem i: order.getOrderItemsList()){
                System.out.println(i.getQuantity() + " " + i.getDish().getName() + " price " + i.getAppliedPrice() );
            }


            //HAPPYHOUR
            order.setPaymentStrategy(happyHourPayment);
            order.addItem(videe, 2);
            order.addItem(croque, 2);
            System.out.println("The happy-hour payment strategy is implemented: ");
            for (int i=2;i<4;i++){
                System.out.println(order.getOrderItemsList().get(i).getQuantity() + " " + order.getOrderItemsList().get(i).getDish().getName() + " price " + order.getOrderItemsList().get(i).getAppliedPrice() );
            }

            //NORMAAL
            order.setPaymentStrategy(normalPayment);
            order.addItem(videe, 2);
            order.addItem(croque, 2);
            System.out.println("The normal payment strategy is implemented again: ");
            for (int i=4;i<6;i++){
                System.out.println(order.getOrderItemsList().get(i).getQuantity() + " " + order.getOrderItemsList().get(i).getDish().getName() + " price " + order.getOrderItemsList().get(i).getAppliedPrice() );
            }
            System.out.println("Order balance " + order.getTotal() + " euro");
            System.out.println("####################################################################");
            feedbacktekst = "strategytest has been executed. Check the output window of IntelliJ to see the result";
        }
        if (request.getParameter("decoratortest") != null) {
            EntranceCounter entranceCounter = EntranceCounter.getInstance();
            entranceCounter.getObservers().clear();
            // een nieuw zaalpersoneelslid toevoegen
            System.out.println("####################################################################");
            Waiter manu = new Waiter("Manu");
            entranceCounter.attachObserver(manu);
            entranceCounter.setNumber(7);
            // we gaan manu detachen en hem als poetspersoon attachen zodat hij nog altijd kan reageren op de klantenteller maar daarbij ook kan schoonmaken
            System.out.println("####################################################################");
            entranceCounter.detachObserver(manu);
            entranceCounter.setNumber(10);
            CleaningStaff poetsPersoon = new CleaningStaff();
            poetsPersoon.setStaff(manu);
            poetsPersoon.cleanMake();
            // Manu moet nu ook nog de administratie erbij nemen als iemand binnenkomt
            System.out.println("####################################################################");
            Administrator administrator = new Administrator();
            administrator.setStaff(manu);
            entranceCounter.attachObserver(administrator);
            entranceCounter.setNumber(5);
            System.out.println("####################################################################");
            feedbacktekst = "decoratortest has been executed. Check the output window of IntelliJ to see the result";

        }
        model.addAttribute("tekst", feedbacktekst);
        return "tests";
    }



    @RequestMapping("/fill")
    public String fill(Model model) {
        Dish dish1 = new Dish();
        dish1.setCurrentPrice(10);
        dish1.setName("pizza");
        dishRepository.save(dish1);
        Dish dish2 = new Dish();
        dish2.setCurrentPrice(15);
        dish2.setName("lasagne");
        dishRepository.save(dish2);
        Dish dish3 = new Dish();
        dish3.setCurrentPrice(20);
        dish3.setName("steak");
        dishRepository.save(dish3);
        Dish dish4 = new Dish();
        dish4.setCurrentPrice(25);
        dish4.setName("pasta scampi");
        dishRepository.save(dish4);
        Dish dish5 = new Dish();
        dish5.setCurrentPrice(30);
        dish5.setName("filet pur");
        dishRepository.save(dish5);

        Table table1 = new Table();
        table1.setCode("T1");
        tableRepository.save(table1);
        Table table2 = new Table();
        table2.setCode("T2");
        tableRepository.save(table2);
        Table table3 = new Table();
        table3.setCode("T3");
        tableRepository.save(table3);
        Table table4 = new Table();
        table4.setCode("T4");
        tableRepository.save(table4);
        Table table5 = new Table();
        table5.setCode("T5");
        tableRepository.save(table5);

        KitchenStaff kitchen1 = new KitchenStaff();
        kitchen1.setName("Stef");
        staffRepository.save(kitchen1);
        KitchenStaff kitchen2 = new KitchenStaff();
        kitchen2.setName("Robin");
        staffRepository.save(kitchen2);
        KitchenStaff kitchen3 = new KitchenStaff();
        kitchen3.setName("Sander");
        staffRepository.save(kitchen3);
        Waiter waiter1 = new Waiter();
        waiter1.setName("Dennis");
        staffRepository.save(waiter1);
        Waiter waiter2 = new Waiter();
        waiter2.setName("Lisa");
        staffRepository.save(waiter2);

        Order order1 = new Order();
        order1.setDate(LocalDate.now());
        order1.setTable(table1);
        order1.setWaiter(waiter1);
        order1.setPayed(false);
        order1.addItem(dish1, 2);
        orderRepository.save(order1);
        Order order2 = new Order();
        order2.setDate(LocalDate.now());
        order2.setTable(table2);
        order2.setWaiter(waiter1);
        order2.setPayed(false);
        order2.addItem(dish2, 1);
        orderRepository.save(order2);
        Order order3 = new Order();
        order3.setDate(LocalDate.now());
        order3.setTable(table3);
        order3.setWaiter(waiter1);
        order3.setPayed(false);
        order3.addItem(dish3, 4);
        orderRepository.save(order3);
        Order order4 = new Order();
        order4.setDate(LocalDate.now());
        order4.setTable(table4);
        order4.setWaiter(waiter2);
        order4.setPayed(false);
        order4.addItem(dish4, 3);
        orderRepository.save(order4);
        Order order5 = new Order();
        order5.setDate(LocalDate.now());
        order5.setTable(table5);
        order5.setWaiter(waiter2);
        order5.setPayed(false);
        order5.addItem(dish5, 1);
        orderRepository.save(order5);

        model.addAttribute("text", "Table filling was successful");
        return "index";
    }
}
