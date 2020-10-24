package osipov.evgeny.controller;

import org.apache.log4j.Logger;
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

/**
 * Класс - REST контроллер - основной класс программы. Реализует в себе логическую составляющую приложения:
 * Регистрация и авторизация пользователей, создание, удаление и изменение задач, контроль отображаемых кнопок.
 * Обращение к методам происходит через ajax-запросы.
 */
@RestController
public class UserAndOrderController {
    /**
     * Поле объявления переменной для логгирования.
     */
    private static final Logger log = Logger.getLogger(UserAndOrderController.class.getName());

    /**
     * Метод возвращает идентификатор пользователя, хранящийся в cookie.
     *
     * @param request содержит запрос, поступивший от клиента
     * @return идентификатор пользователя или -1
     */
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

    /**
     * Метод возвращает роль пользователя, хранящийся в cookie.
     *
     * @param request содержит запрос, поступивший от клиента
     * @return роль пользователя или пустую строку ("")
     */
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

    /**
     * Метод регистрации для пользователя. Получает в запросе параметры, которые передаются в конструктор классов
     * Freelancer и Customer.
     *
     * @param model       инкапсулирует данные приложения
     * @param response    определяет ответ клиенту
     * @param fio         ФИО пользователя
     * @param username    имя пользователя (должно быть уникальным)
     * @param password    пароль
     * @param description описание пользователя
     * @param phone       телефон пользователя
     * @param email       e-mail пользователя
     * @param role        роль пользователя (freelancer или customer)
     * @return страницу авторизации, при удачной попытке регистрации, иначе снова отправляет на страницу регистрации
     */
    @GetMapping("/registration/check/")
    @ResponseBody
    public String registration(Model model, HttpServletResponse response, @RequestParam String fio,
                               @RequestParam String username, @RequestParam String password,
                               @RequestParam String description, @RequestParam String phone,
                               @RequestParam String email, @RequestParam String role) {
        if (role.equals("freelancer"))
            if (FreelanceRepo.getFreelancerByUsernameOrEmptyEntity(username).getId() == null) {
                FreelanceRepo.setFreelancer(new Freelancer(fio, username, password, 0L, description, phone, email));
                log.info("Freelancer " + username + " is registered!");
            } else return "registration";
        else if (role.equals("customer"))
            if (CustomerRepo.getCustomerByCustomerNameOrEmptyEntity(username).getId() == null) {
                CustomerRepo.setCustomer(new Customer(fio, username, password, 0L, description, phone, email));
                log.info("Customer " + username + " is registered!");
            } else return "registration";
        return "/";
    }

    /**
     * Метод авторизации для пользователя. Получает в теле запроса параметры, которые проверяются на валидность.
     * Передает в cookie идентификатор пользователя и роль.
     *
     * @param model    инкапсулирует данные приложения
     * @param response определяет ответ клиенту
     * @param request  содержит запрос, поступивший от клиента
     * @param username имя пользователя (должно быть уникальным)
     * @param password пароль
     * @param role     роль пользователя (freelancer или customer)
     * @return домашнюю страницу (home), при удачной попытке авторизации, иначе снова отправляет на страницу авторизации
     */
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

    /**
     * Метод для создания новой задачи.
     *
     * @param model       инкапсулирует данные приложения
     * @param response    определяет ответ клиенту
     * @param category    категория задачи
     * @param name        низвание задачи
     * @param description описание задачи
     * @param time        время исполнения задачи
     * @param price       стоимость выполнения задачи
     * @param customer_id идентификатор заказчика
     * @return страница активных задач (/order/active), в случае ошибки возвращает пользователя на страницу авторизации.
     */
    @GetMapping("/order/create/creating")
    @ResponseBody
    public String createOrder(Model model, HttpServletResponse response, @RequestParam String category,
                              @RequestParam String name, @RequestParam String description,
                              @RequestParam String time, @RequestParam String price,
                              @RequestParam Long customer_id) {
        if (CustomerRepo.getCustomerByIdOrEmptyEntity(customer_id) == null | customer_id == -1) {
            log.error("User " + CustomerRepo.getCustomerByIdOrEmptyEntity(customer_id).toString()
                    + "equals null / can't find or customer_id == -1 [" + customer_id.toString() + "].");
            return "/";
        }
        OrderRepo.setOrder(new Order(customer_id, "published", category, name, description, time, price));
        log.info("Customer " + CustomerRepo.getCustomerByIdOrEmptyEntity(customer_id).getCustomer_name() + " is registered.");
        return "/order/active";
    }

    /**
     * Метод возвращает роль пользователя.
     *
     * @param model   инкапсулирует данные приложения
     * @param request содержит запрос, поступивший от клиента
     * @return роль пользователя
     */
    @PostMapping("/get_role")
    public String getRole(Model model, HttpServletRequest request) {
        return getRoleFromCookieOrReturnEmptyString(request);
    }

    /**
     * Метод возвращает идентификатор пользователя.
     *
     * @param model   инкапсулирует данные приложения
     * @param request содержит запрос, поступивший от клиента
     * @return идентификатор пользователя
     */
    @PostMapping("/get_id")
    public Long getId(Model model, HttpServletRequest request) {
        return getIdFromCookieOrReturnMinusOne(request);
    }

    /**
     * Метод возвращает данные о пользователе в виде объекта JSON.
     *
     * @param model    инкапсулирует данные приложения
     * @param response определяет ответ клиенту
     * @param id       идентификатор просматриваемого пользователя
     * @return
     */
    @GetMapping("/profile/{id}/get_profile_info")
    public String getProfileInfoByIdOrEmptyString(Model model, HttpServletResponse response, @PathVariable Long id) {
        if (FreelanceRepo.getFreelancerByIdOrEmptyEntity(id) != null) {
            Freelancer freelancer = FreelanceRepo.getFreelancerByIdOrEmptyEntity(id);
            return freelancer.toJSON();
        } else if (CustomerRepo.getCustomerByIdOrEmptyEntity(id) != null) {
            Customer customer = CustomerRepo.getCustomerByIdOrEmptyEntity(id);
            return customer.toJSON();
        }
        log.error("User " + id.toString() + " can't find.");
        return "";
    }

    /**
     * Метод возвращает активные задачи заказчика (не done или hidden).
     *
     * @param model    инкапсулирует данные приложения
     * @param request  содержит запрос, поступивший от клиента
     * @param response определяет ответ клиенту
     * @return JSON список активных задач
     */
    @GetMapping("/order/active/get_active_orders")
    public String getActiveOrdersOrEmptyString(Model model, HttpServletRequest request, HttpServletResponse response) {
        String role = getRoleFromCookieOrReturnEmptyString(request);
        List<Order> orders = new ArrayList<>();
        if (role.equals("freelancer")) {
            orders = OrderRepo.getOrdersByFreelancerIdOrEmptyList(getIdFromCookieOrReturnMinusOne(request));
        } else if (role.equals("customer")) {
            orders = OrderRepo.getOrdersByCustomerIdOrEmptyList(getIdFromCookieOrReturnMinusOne(request));
        } else {
            log.error("User " + getIdFromCookieOrReturnMinusOne(request).toString() + " can't find.");
            return "/";
        }
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

    /**
     * Метод возвращает свободные задачи заказчиков (published).
     *
     * @param model    инкапсулирует данные приложения
     * @param request  содержит запрос, поступивший от клиента
     * @param response определяет ответ клиенту
     * @return JSON список активных задач
     */
    @GetMapping("/order/free/get_active_orders")
    public String getAllOrdersOrEmptyString(Model model, HttpServletRequest request, HttpServletResponse response) {
        String role = getRoleFromCookieOrReturnEmptyString(request);
        if (role.equals("freelancer")) {
            List<Order> orders = OrderRepo.getAllOrdersOrEmptyList();
            String JSONKeyIdValueOther = "{";
            boolean skipFirstСomma = true;
            for (Order order : orders) {
                if (order.getStatus().equals("published")) {
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
        } else {
            log.error("User " + getIdFromCookieOrReturnMinusOne(request).toString() + " can't find.");
            return "/";
        }

    }

    /**
     * Метод возвращает завершенные и/или архивные задачи пользователя (done и hidden).
     *
     * @param model    инкапсулирует данные приложения
     * @param request  содержит запрос, поступивший от клиента
     * @param response определяет ответ клиенту
     * @return JSON список завершенных и/или архивных задач
     */
    @GetMapping("/order/history/get_finished_orders")
    public String getFinishedOrHiddenOrdersOrEmptyString(Model model, HttpServletRequest request, HttpServletResponse response) {
        String role = getRoleFromCookieOrReturnEmptyString(request);
        List<Order> orders = new ArrayList<>();
        if (role.equals("freelancer")) {
            orders = OrderRepo.getOrdersByFreelancerIdOrEmptyList(getIdFromCookieOrReturnMinusOne(request));
        } else if (role.equals("customer")) {
            orders = OrderRepo.getOrdersByCustomerIdOrEmptyList(getIdFromCookieOrReturnMinusOne(request));
        } else {
            log.error("User " + getIdFromCookieOrReturnMinusOne(request).toString() + " can't find.");
            return "/";
        }
        String JSONKeyIdValueOther = "{";
        boolean skipFirstСomma = true;
        for (Order order : orders) {
            if (order.getStatus().equals("done") | order.getStatus().equals("hidden")) {
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

    /**
     * Метод для определения текущей ситуации (как следствие - определенный набор возможных действий (кнопок)),
     * в зависимости от пользователя и его роли в задаче (хост/исполнитель/отрекающийся от исполнения/изгнанный заказчиком)
     *
     * @param model    инкапсулирует данные приложения
     * @param request  содержит запрос, поступивший от клиента
     * @param response определяет ответ клиенту
     * @param order_id идентификатор выбранной задачи
     * @return freelancer_accept/freelancer_refuse/done/published/hidden/in_progress
     */
    @GetMapping("/show_order/{order_id}/situation")
    public String deleteButtonThatShouldNotBeVisibleToTheUser(Model model, HttpServletRequest request, HttpServletResponse response,
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
            } else {
                log.error("Order [ id: " + order_id + " ] or User [ id: " + user_id.toString() + " ] can't find.");
                return "/";
            }
        } else if (role.equals("customer")) {
            Order order = OrderRepo.getOrderByIdOrEmptyEntity(order_id);
            if (order != null)
                if (order.getCustomer_id().equals(user_id))
                    return order.getStatus();

        }
        log.error("Order [ id: " + order_id + " ] or User [ id: " + user_id.toString() + " ] can't find.");
        return "/";
    }

    /**
     * Метод для отправки объекта задачи в виде JSON.
     *
     * @param model    инкапсулирует данные приложения
     * @param response определяет ответ клиенту
     * @param order_id идентификатор выбранной задачи
     * @return объект выбранной задачи в виде JSON
     */
    @GetMapping("/show_order/{order_id}/get_order_info")
    public String getOrderJSONByOrderId(Model model, HttpServletResponse response, @PathVariable Long order_id) {
        return OrderRepo.getOrderByIdOrEmptyEntity(order_id).toJSON();
    }

    /**
     * Метод для взаимодействия заказчиков и исполнителей со списком задач:
     * freelancer_accept - исполнитель начинает выполнение задачи;
     * freelancer_refuse - исполнитель отказывается от выполнения задачи;
     * customer_delete - заказчик удаляет задачу;
     * make_visible - заказчик делает задачу снова видимой;
     * customer_dismiss - заказчик отстраняет исполнителя отт выполнения задачи;
     * customer_done - заказчик подтверждает окончание работы над задачей.
     *
     * @param model    инкапсулирует данные приложения
     * @param request  содержит запрос, поступивший от клиента
     * @param response определяет ответ клиенту
     * @param command  команда, возвращаемая по нажатию кнопки
     * @param order_id идентификатор выбранной задачи
     * @return домашняя страница (/home)
     */
    @GetMapping("/show_order/{order_id}/button_click")
    @ResponseBody
    public String selectionOfActionsAccordingToTheUsersChoice(Model model, HttpServletRequest request, HttpServletResponse response,
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





