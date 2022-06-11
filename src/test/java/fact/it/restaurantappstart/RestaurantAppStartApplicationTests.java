package fact.it.restaurantappstart;

import fact.it.restaurantappstart.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RestaurantAppStartApplicationTests {

    @Test
    public void a_testSingleton_1() {
        EntranceCounter entranceCounter = EntranceCounter.getInstance();
        EntranceCounter entranceCounter1 = EntranceCounter.getInstance();
        assertTrue(entranceCounter == entranceCounter1);
    }
//
    @Test
    public void b_testObserverPattern_attach_1() {
        EntranceCounter entranceCounter = EntranceCounter.getInstance();
        entranceCounter.getObservers().clear();
        //een paar personeelsleden
        Waiter jan = new Waiter();
        jan.setName("Jan");
        Waiter piet = new Waiter();
        piet.setName("Piet");
        KitchenStaff serge = new KitchenStaff();
        serge.setName("Serge");
        KitchenStaff jeroen = new KitchenStaff();
        jeroen.setName("Jeroen");

        //we koppelen het zaalpersoneel en het keukenpersoneel als observer aan de entranceCounter
        entranceCounter.attachObserver(jan);
        entranceCounter.attachObserver(piet);
        entranceCounter.attachObserver(serge);
        entranceCounter.attachObserver(jeroen);
        PrintStream defaultSO = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String result;
        System.setOut(new PrintStream(baos));
        try {
            //5 klanten komen binnen
            entranceCounter.setNumber(5);
            BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
            result = br.readLine();
            assertEquals("I am Jan and I start preparing the table for 5 customers.", result);
            result = br.readLine();
            assertEquals("I am Piet and I start preparing the table for 5 customers.", result);
            result = br.readLine();
            assertEquals("I am Serge and I start now with preparing 5 appetizers!", result);
            result = br.readLine();
            assertEquals("I am Jeroen and I start now with preparing 5 appetizers!", result);
            br.close();
        } catch (Exception e) {
            System.setOut(defaultSO);
            System.out.println("Error while redirection System.out");
        }
        System.setOut(defaultSO);

    }

    @Test
    public void c_testObserverPattern_detach_2() {
        EntranceCounter entranceCounter = EntranceCounter.getInstance();
        entranceCounter.getObservers().clear();
        //een paar personeelsleden
        Waiter jan = new Waiter();
        jan.setName("Jan");
        Waiter piet = new Waiter();
        piet.setName("Piet");
        KitchenStaff serge = new KitchenStaff();
        serge.setName("Serge");
        KitchenStaff jeroen = new KitchenStaff();
        jeroen.setName("Jeroen");
        //we koppelen het zaalpersoneel en het keukenpersoneel als observer aan de entranceCounter
        entranceCounter.attachObserver(jan);
        entranceCounter.attachObserver(piet);
        entranceCounter.attachObserver(serge);
        entranceCounter.attachObserver(jeroen);
        //we ontkoppelen piet en serge
        entranceCounter.detachObserver(piet);
        entranceCounter.detachObserver(serge);

        PrintStream defaultSO = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String result;
        System.setOut(new PrintStream(baos));
        try {
            //3 klanten komen binnen
            entranceCounter.setNumber(3);
            BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
            result = br.readLine();
            assertEquals("I am Jan and I start preparing the table for 3 customers.", result);
            result = br.readLine();
            assertEquals("I am Jeroen and I start now with preparing 3 appetizers!", result);
            result = br.readLine();
            br.close();
        } catch (Exception e) {
            System.setOut(defaultSO);
            System.out.println("Error while redirection System.out");
        }
        System.setOut(defaultSO);

    }
//
    @Test
    public void d_testStrategyPatternZonderBetaalStrategie_1(){
        //gerechten aanmaken
        Dish videe = new Dish();
        videe.setName("Vidée met frietjes");
        videe.setCurrentPrice(15.0);
        Dish croque = new Dish();
        croque.setName("Croque Monsieur");
        croque.setCurrentPrice(10.0);

        //maak order met bestelitems
        Order order = new Order();
        //NORMAAL
        order.setDate(LocalDate.now());
        order.addItem(videe, 2);
        order.addItem(croque, 3);
        assertEquals(60.0, order.getTotal(),0.1);


    }
    @Test
    public void e_testStrategyPatternMetBetaalStrategie_2(){
        HappyHourPayment happyHourPayment = new HappyHourPayment();
        NormalPayment normalPayment = new NormalPayment();
        //gerechten aanmaken
        Dish videe = new Dish();
        videe.setName("Vidée met frietjes");
        videe.setCurrentPrice(15.0);
        Dish croque = new Dish();
        croque.setName("Croque Monsieur");
        croque.setCurrentPrice(10.0);

        //maak order met bestelitems
        Order order = new Order();
        //NORMAAL
        order.setPaymentStrategy(normalPayment);
        order.setDate(LocalDate.now());
        order.addItem(videe, 2);
        order.addItem(croque, 3);

        //HAPPYHOUR
        order.setPaymentStrategy(happyHourPayment);
        order.addItem(videe, 5);
        order.addItem(croque, 1);

        //NORMAAL
        order.setPaymentStrategy(normalPayment);
        order.addItem(videe, 2);
        order.addItem(croque, 2);

        assertEquals(178.0, order.getTotal(),0.1);


    }
    @Test
    public void f_testStrategyPatternMetBetaalStrategie_3(){
        HappyHourPayment happyHourPayment = new HappyHourPayment();
        NormalPayment normalPayment = new NormalPayment();
        //gerechten aanmaken
        Dish videe = new Dish();
        videe.setName("Vidée met frietjes");
        videe.setCurrentPrice(15.0);
        Dish croque = new Dish();
        croque.setName("Croque Monsieur");
        croque.setCurrentPrice(10.0);
        //maak order met bestelitems
        Order order = new Order();
        //HAPPYHOUR
        order.setPaymentStrategy(happyHourPayment);
        order.addItem(videe, 5);
        order.addItem(croque, 6);
        //NORMAAL
        order.setPaymentStrategy(normalPayment);
        order.addItem(videe, 2);
        order.addItem(croque, 3);

        assertEquals(168.0, order.getTotal(),0.1);
    }
//
    @Test
    public void g_testDecoratorPattern_Zaal_Admin_1(){
        EntranceCounter entranceCounter = EntranceCounter.getInstance();
        entranceCounter.getObservers().clear();
        // een nieuw zaalpersoneelslid toevoegen
        Waiter manu = new Waiter();
        manu.setName("ZaalManu");
        //we geven hem extra admin-verantwoordelijkheid
        Administrator administrator = new Administrator();
        administrator.setStaff(manu);
        entranceCounter.attachObserver(administrator);

        PrintStream defaultSO = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String result;
        System.setOut(new PrintStream(baos));
        try {
            //5 klanten komen binnen
            entranceCounter.setNumber(7);
            BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
            result = br.readLine();
            assertEquals("I am ZaalManu and I start preparing the table for 7 customers.", result);
            result = br.readLine();
            assertEquals("Next, I register the 7 customers in the customer file.", result);
            br.close();
        } catch (Exception e) {
            System.setOut(defaultSO);
            System.out.println("Error while redirection System.out");
        }
        System.setOut(defaultSO);

    }

    @Test
    public void h_testDecoratorPattern_Keuken_Admin_2(){
        EntranceCounter entranceCounter = EntranceCounter.getInstance();
        entranceCounter.getObservers().clear();
        // een nieuw keukenpersoneelslid toevoegen
        KitchenStaff rob = new KitchenStaff();
        rob.setName("KeukenRob");
        //we geven hem extra admin-verantwoordelijkheid
        Administrator administrator = new Administrator();
        administrator.setStaff(rob);
        entranceCounter.attachObserver(administrator);

        PrintStream defaultSO = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String result;
        System.setOut(new PrintStream(baos));
        try {
            //5 klanten komen binnen
            entranceCounter.setNumber(6);
            BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
            result = br.readLine();
            assertEquals("I am KeukenRob and I start now with preparing 6 appetizers!", result);
            result = br.readLine();
            assertEquals("Next, I register the 6 customers in the customer file.", result);
            br.close();
        } catch (Exception e) {
            System.setOut(defaultSO);
            System.out.println("Error while redirection System.out");
        }
        System.setOut(defaultSO);

    }

    @Test
    public void i_testDecoratorPattern_Zaal_Poets_3(){
        EntranceCounter entranceCounter = EntranceCounter.getInstance();
        entranceCounter.getObservers().clear();
        // een nieuw zaalpersoneelslid toevoegen
        Waiter jozef = new Waiter();
        jozef.setName("ZaalJozef");
        //we geven hem een extra taak
        CleaningStaff cleaningStaff = new CleaningStaff();
        cleaningStaff.setStaff(jozef);
        entranceCounter.attachObserver(cleaningStaff);

        PrintStream defaultSO = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String result;
        System.setOut(new PrintStream(baos));
        try {
            //7 klanten komen binnen
            entranceCounter.setNumber(7);
            cleaningStaff.cleanMake();
            BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
            result = br.readLine();
            assertEquals("I am ZaalJozef and I start preparing the table for 7 customers.", result);
            result = br.readLine();
            assertEquals("I am ZaalJozef and now I start also with cleaning.", result);
            br.close();
        } catch (Exception e) {
            System.setOut(defaultSO);
            System.out.println("Error while redirection System.out");
        }
        System.setOut(defaultSO);

    }

    @Test
    public void j_testDecoratorPattern_Keuken_Poets_4(){
        EntranceCounter entranceCounter = EntranceCounter.getInstance();
        entranceCounter.getObservers().clear();
        // een nieuw zaalpersoneelslid toevoegen
        KitchenStaff bram = new KitchenStaff();
        bram.setName("KeukenBram");
        //we geven hem een extra taak
        CleaningStaff cleaningStaff = new CleaningStaff();
        cleaningStaff.setStaff(bram);
        entranceCounter.attachObserver(cleaningStaff);

        PrintStream defaultSO = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String result;
        System.setOut(new PrintStream(baos));
        try {
            //7 klanten komen binnen
            entranceCounter.setNumber(8);
            cleaningStaff.cleanMake();
            BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
            result = br.readLine();
            assertEquals("I am KeukenBram and I start now with preparing 8 appetizers!", result);
            result = br.readLine();
            assertEquals("I am KeukenBram and now I start also with cleaning.", result);
            br.close();
        } catch (Exception e) {
            System.setOut(defaultSO);
            System.out.println("Error while redirection System.out");
        }
        System.setOut(defaultSO);

    }


}
