package osipov.evgeny.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import osipov.evgeny.entity.Customer;
import osipov.evgeny.entity.Freelancer;
import osipov.evgeny.entity.Order;
import osipov.evgeny.repository.CustomerRepo;
import osipov.evgeny.repository.FreelanceRepo;
import osipov.evgeny.repository.OrderRepo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserAndOrderController {

    public Long getIdFromCookieOrReturnMinusOne(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        long userId = -1L;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = Long.parseLong(cookie.getValue());
            }
        }
        return userId;
    }

    public String getRoleFromCookieOrReturnEmptyString(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String role = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("role")) {
                role = cookie.getValue();
            }
        }
        return role;
    }

    @GetMapping("/registration/check/")
    @ResponseBody
    public String registration(Model model, HttpServletResponse response, @RequestParam String fio,
                               @RequestParam String username, @RequestParam String password,
                               @RequestParam String description, @RequestParam String phone,
                               @RequestParam String email, @RequestParam String role) {
        if (role.equals("freelancer"))
            if (FreelanceRepo.getFreelancerByUsernameOrEmptyEntity(username).getId() == null) {
                FreelanceRepo.setFreelancer(new Freelancer(fio, username, password, 0L, description, phone, email));
            } else return "registration";
        else if (role.equals("customer"))
            if (CustomerRepo.getCustomerByCustomerNameOrEmptyEntity(username).getId() == null) {
                CustomerRepo.setCustomer(new Customer(fio, username, password, 0L, description, phone, email));
            } else return "registration";
        return "/";
    }

    @PostMapping("/auth_check/")
    @ResponseBody
    public String authorization(Model model, HttpServletRequest request, HttpServletResponse response,
                                @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        if (role.equals("freelancer"))
            if (FreelanceRepo.getFreelancerByUsernameOrEmptyEntity(username).getPassword() != null) {
                if (FreelanceRepo.getFreelancerByUsernameOrEmptyEntity(username).getPassword().equals(password)) {
                    Cookie cookieId = new Cookie("userId", FreelanceRepo.getFreelancerByUsernameOrEmptyEntity(username).getId().toString());
                    cookieId.setPath("/");
                    response.addCookie(cookieId);
                    Cookie cookieRole = new Cookie("role", role);
                    cookieRole.setPath("/");
                    response.addCookie(cookieRole);
                } else return "/";
            } else return "/";
        else if (role.equals("customer"))
            if (CustomerRepo.getCustomerByCustomerNameOrEmptyEntity(username).getPassword() != null) {
                if (CustomerRepo.getCustomerByCustomerNameOrEmptyEntity(username).getPassword().equals(password)) {
                    Cookie cookieId = new Cookie("userId", CustomerRepo.getCustomerByCustomerNameOrEmptyEntity(username).getId().toString());
                    cookieId.setPath("/");
                    response.addCookie(cookieId);
                    Cookie cookieRole = new Cookie("role", role);
                    cookieRole.setPath("/");
                    response.addCookie(cookieRole);
                } else return "/";
            } else return "/";
        return "/home";
    }

    @GetMapping("/order/create/creating")
    @ResponseBody
    public String registration(Model model, HttpServletResponse response, @RequestParam String category,
                               @RequestParam String name, @RequestParam String description,
                               @RequestParam String time, @RequestParam String price,
                               @RequestParam Long customer_id) {
        if (CustomerRepo.getCustomerByIdOrEmptyEntity(customer_id) == null | customer_id == -1) {
            return "/";
        }
        OrderRepo.setOrder(new Order(customer_id, "published", category, name, description, time, price));
        return "/order/active";
    }

    @PostMapping("/get_role")
    public String getRole(Model model, HttpServletRequest request) {
        return getRoleFromCookieOrReturnEmptyString(request);
    }

    @PostMapping("/get_id")
    public Long getId(Model model, HttpServletRequest request) {
        return getIdFromCookieOrReturnMinusOne(request);
    }

    @GetMapping("/profile/{id}/get_profile_info")
    public String getProfileInfoByIdOrEmptyString(Model model, HttpServletResponse response, @PathVariable Long id) {
        if (FreelanceRepo.getFreelancerByIdOrEmptyEntity(id) != null) {
            Freelancer freelancer = FreelanceRepo.getFreelancerByIdOrEmptyEntity(id);
            return freelancer.toJSON();
        } else if (CustomerRepo.getCustomerByIdOrEmptyEntity(id) != null) {
            Customer customer = CustomerRepo.getCustomerByIdOrEmptyEntity(id);
            return customer.toJSON();
        }
        return "";
    }

    @GetMapping("/order/active/get_active_orders")
    public String getActiveOrdersOrEmptyString(Model model, HttpServletRequest request, HttpServletResponse response) {
        String role = getRoleFromCookieOrReturnEmptyString(request);
        List<Order> orders = new ArrayList<>();
        if (role.equals("freelancer")) {
            orders = OrderRepo.getOrdersByFreelancerIdOrEmptyList(getIdFromCookieOrReturnMinusOne(request));
        } else if (role.equals("customer")) {
            orders = OrderRepo.getOrdersByCustomerIdOrEmptyList(getIdFromCookieOrReturnMinusOne(request));
        } else return "/";
        String JSONKeyIdValueOther = "{";
        boolean skipFirstСomma = true;
        for (Order order : orders) {
            if (!order.getStatus().equals("hidden") & !order.getStatus().equals("done")) {
                if (skipFirstСomma) {
                    JSONKeyIdValueOther += order.toJSONLineKeyIdValueOtherPersonalView();
                    skipFirstСomma = false;
                } else {
                    JSONKeyIdValueOther += "," + order.toJSONLineKeyIdValueOtherPersonalView();
                }
            }
        }
        JSONKeyIdValueOther += "}";
        return JSONKeyIdValueOther;
    }

    @GetMapping("/order/free/get_active_orders")
    public String getAllOrdersOrEmptyString(Model model, HttpServletRequest request, HttpServletResponse response) {
        String role = getRoleFromCookieOrReturnEmptyString(request);
        List<Order> orders = new ArrayList<>();
        if (role.equals("freelancer")) {
            orders = OrderRepo.getOrdersByFreelancerIdOrEmptyList(getIdFromCookieOrReturnMinusOne(request));
        } else if (role.equals("customer")) {
            orders = OrderRepo.getOrdersByCustomerIdOrEmptyList(getIdFromCookieOrReturnMinusOne(request));
        } else return "/";
        String JSONKeyIdValueOther = "{";
        boolean skipFirstСomma = true;
        for (Order order : orders) {
            if (order.getStatus().equals("hidden") | order.getStatus().equals("done")) {
                if (skipFirstСomma) {
                    JSONKeyIdValueOther += order.toJSONLineKeyIdValueOtherForAllAccess();
                    skipFirstСomma = false;
                } else {
                    JSONKeyIdValueOther += "," + order.toJSONLineKeyIdValueOtherForAllAccess();
                }
            }
        }
        JSONKeyIdValueOther += "}";
        System.out.println(JSONKeyIdValueOther);
        return JSONKeyIdValueOther;
    }

    @GetMapping("/order/history/get_finished_orders")
    public String getFinishedOrHiddenOrdersOrEmptyString(Model model, HttpServletRequest request, HttpServletResponse response) {
        String role = getRoleFromCookieOrReturnEmptyString(request);
        List<Order> orders = new ArrayList<>();
        if (role.equals("freelancer")) {
            orders = OrderRepo.getOrdersByFreelancerIdOrEmptyList(getIdFromCookieOrReturnMinusOne(request));
        } else if (role.equals("customer")) {
            orders = OrderRepo.getOrdersByCustomerIdOrEmptyList(getIdFromCookieOrReturnMinusOne(request));
        } else return "/";
        String JSONKeyIdValueOther = "{";
        boolean skipFirstСomma = true;
        for (Order order : orders) {
            if (order.getStatus().equals("done") || order.getStatus().equals("hidden")) {
                if (skipFirstСomma) {
                    JSONKeyIdValueOther += order.toJSONLineKeyIdValueOtherForAllAccess();
                    skipFirstСomma = false;
                } else {
                    JSONKeyIdValueOther += "," + order.toJSONLineKeyIdValueOtherForAllAccess();
                }
            }
        }
        JSONKeyIdValueOther += "}";
        System.out.println(JSONKeyIdValueOther);
        return JSONKeyIdValueOther;
    }

    @GetMapping("/show_order/{order_id}/situation")
    public String getAllOrdersOrEmptyString(Model model, HttpServletRequest request, HttpServletResponse response,
                                            @PathVariable Long order_id) {
        String role = getRoleFromCookieOrReturnEmptyString(request);
        Long user_id = getIdFromCookieOrReturnMinusOne(request);
        if (role.equals("freelancer")) {
            Order order = OrderRepo.getOrderByIdOrEmptyEntity(order_id);
            if (order != null) {
                if (order.getFreelancer_id() == null) {
                    return "freelancer_accept";
                } else if (order.getFreelancer_id().equals(user_id)) {
                    if (order.getStatus().equals("done")) {
                        return "done";
                    }
                    return "freelancer_refuse";
                }
            } else return "/";
        } else if (role.equals("customer")) {
            Order order = OrderRepo.getOrderByIdOrEmptyEntity(order_id);
            if (order != null)
                if (order.getCustomer_id().equals(user_id))
                    return order.getStatus();

        }
        return "/";
    }

    @GetMapping("/show_order/{order_id}/get_order_info")
    public String getOrderJSONByOrderId(Model model, HttpServletResponse response, @PathVariable Long order_id) {
        return OrderRepo.getOrderByIdOrEmptyEntity(order_id).toJSON();
    }

    @GetMapping("/show_order/{order_id}/button_click")
    @ResponseBody
    public String registration(Model model, HttpServletRequest request, HttpServletResponse response,
                               @RequestParam String command, @PathVariable Long order_id) {
        String role = getRoleFromCookieOrReturnEmptyString(request);
        Long user_id = getIdFromCookieOrReturnMinusOne(request);
        Order order = OrderRepo.getOrderByIdOrEmptyEntity(order_id);
        if (command.equals("freelancer_accept") & role.equals("freelancer")) {
            order.setFreelancer_id(user_id);
            order.setStatus("in_progress");
        } else if (command.equals("customer_delete") & role.equals("customer")) {
            OrderRepo.deleteOrder(order);
            return "/home";
        } else if (command.equals("customer_hide") & role.equals("customer")) {
            order.setStatus("hidden");
        } else if (command.equals("make_visible") & role.equals("customer")) {
            order.setStatus("published");
        } else if (command.equals("freelancer_refuse") & role.equals("freelancer")) {
            order.setFreelancer_id(null);
            order.setStatus("published");
        } else if (command.equals("customer_dismiss") & role.equals("customer")) {
            order.setFreelancer_id(null);
            order.setStatus("published");
        } else if (command.equals("customer_done") & role.equals("customer")) {
            order.setStatus("done");
            Freelancer freelancer = FreelanceRepo.getFreelancerByIdOrEmptyEntity(order.getFreelancer_id());
            freelancer.setCount_deals(freelancer.getCount_deals() + 1);
            FreelanceRepo.updateFreelancer(freelancer);
            Customer customer = CustomerRepo.getCustomerByIdOrEmptyEntity(user_id);
            customer.setCount_orders(customer.getCount_orders() + 1);
            CustomerRepo.updateCustomer(customer);
        }
        OrderRepo.updateOrder(order);
        return "/home";
    }
}





