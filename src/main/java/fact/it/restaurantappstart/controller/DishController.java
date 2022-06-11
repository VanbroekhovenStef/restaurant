package fact.it.restaurantappstart.controller;

import fact.it.restaurantappstart.model.Dish;
import fact.it.restaurantappstart.repository.DishRepository;
import org.springframework.stereotype.Controller;

@Controller
public class DishController {
    private DishRepository dishRepository;

    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public void fillDishes() {
        Dish dish1 = new Dish();
        dish1.setCurrentPrice(10);
        dish1.setName("pizza");
        dishRepository.save(dish1);
        Dish dish2 = new Dish();
        dish1.setCurrentPrice(15);
        dish1.setName("lasagne");
        dishRepository.save(dish1);
        Dish dish3 = new Dish();
        dish1.setCurrentPrice(20);
        dish1.setName("steak");
        dishRepository.save(dish1);
        Dish dish4 = new Dish();
        dish1.setCurrentPrice(25);
        dish1.setName("pasta scampi");
        dishRepository.save(dish1);
        Dish dish5 = new Dish();
        dish1.setCurrentPrice(30);
        dish1.setName("filet pur");
        dishRepository.save(dish1);
    }
}
