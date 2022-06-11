package fact.it.restaurantappstart.controller;

import fact.it.restaurantappstart.model.KitchenStaff;
import fact.it.restaurantappstart.model.Staff;
import fact.it.restaurantappstart.model.Waiter;
import fact.it.restaurantappstart.repository.StaffRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class StaffController {
    private StaffRepository staffRepository;

    public StaffController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @RequestMapping("/staff")
    public String staff(Model model) {
        List<Staff> staff = staffRepository.findAll();
        model.addAttribute("staff", staff);

        return "staff";
    }

    @RequestMapping("/add-staff")
    public String addStaff(Model model) {
        return "add-staff";
    }

    @RequestMapping("/process-staff")
    public String processStaff(Model model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        if (type.equals("kitchen")) {
            KitchenStaff kitchenStaff = new KitchenStaff();
            kitchenStaff.setName(name);
            staffRepository.save(kitchenStaff);
        } else {
            Waiter waiter = new Waiter();
            waiter.setName(name);
            staffRepository.save(waiter);
        }

        List<Staff> staff = staffRepository.findAll();
        model.addAttribute("staff", staff);
        return "staff";
    }
}
