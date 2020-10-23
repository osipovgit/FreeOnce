package osipov.evgeny.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    private static final Logger log = Logger.getLogger(WebController.class.getName());

    @RequestMapping("/")
    public String auth(Model model) {
        return "authorization";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @RequestMapping("/order/history")
    public String allOrders(Model model) {
        return "orders_history";
    }

    @RequestMapping("/order/create")
    public String createOrder(Model model) {
        return "create_order";
    }

    @RequestMapping("/show_order")
    public String showOrder(Model model) {
        return "show_order";
    }

    @RequestMapping("/order/active")
    public String activeOrder(Model model) {
        return "active_order";
    }

    @RequestMapping("/profile")
    public String showProfile(Model model) {
        return "show_profile";
    }
}
