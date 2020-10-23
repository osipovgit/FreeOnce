package osipov.evgeny.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import osipov.evgeny.entity.Customer;
import osipov.evgeny.entity.Freelancer;
import osipov.evgeny.repository.CustomerRepo;
import osipov.evgeny.repository.FreelanceRepo;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserAndOrderController {

    @GetMapping("/registration/check/")
    @ResponseBody
    public String registration(Model model, HttpServletResponse response,
                                @RequestParam String fio, @RequestParam String username, @RequestParam String password,
                                @RequestParam String description, @RequestParam String phone,
                                @RequestParam String email, @RequestParam String role) {
        if (role.equals("freelancer"))
            if (FreelanceRepo.getFreelancerByUsernameOrEmptyEntity(username).getId() == null)
                FreelanceRepo.setFreelancer(new Freelancer(fio, username, password, 0L, description, phone, email));
            else return "registration";
        else if (role.equals("customer"))
            if (CustomerRepo.getCustomerByCustomerNameOrEmptyEntity(username).getId() == null)
                CustomerRepo.setCustomer(new Customer(fio, username, password, 0L, description, phone, email));
            else return "registration";
        return "authorization";
    }
}
