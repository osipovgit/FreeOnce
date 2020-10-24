package osipov.evgeny.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Класс-контроллер для для отображения страниц.
 */
@Controller
public class WebController {
    /**
     * Поле объявления переменной для логгирования.
     */
    private static final Logger log = Logger.getLogger(WebController.class.getName());

    /**
     * Отображение страницы авторизации.
     *
     * @param model инкапсулирует данные приложения
     * @return authorization.html
     */
    @RequestMapping("/")
    public String auth(Model model) {
        return "authorization";
    }

    /**
     * Отображение домашней страницы.
     *
     * @param model инкапсулирует данные приложения
     * @return home.html
     */
    @RequestMapping("/home")
    public String home(Model model) {
        return "home";
    }

    /**
     * Отображение страницы регистрации.
     *
     * @param model инкапсулирует данные приложения
     * @return registration.html
     */
    @RequestMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    /**
     * Отображение страницы завершенных и/или скрытых заказов.
     *
     * @param model инкапсулирует данные приложения
     * @return orders_history.html
     */
    @RequestMapping("/order/history")
    public String closedOrders(Model model) {
        return "orders_history";
    }

    /**
     * Отображение страницы создания заказа.
     *
     * @param model инкапсулирует данные приложения
     * @return create_order.html
     */
    @RequestMapping("/order/create")
    public String createOrder(Model model) {
        return "create_order";
    }

    /**
     * Отображение страницы выбранного заказа.
     *
     * @param model    инкапсулирует данные приложения
     * @param order_id идентификатор задачи
     * @return show_order.html
     */
    @RequestMapping("/show_order/{order_id}")
    public String showOrder(Model model, @PathVariable String order_id) {
        return "show_order";
    }

    /**
     * Отображение страницы активных заказов.
     *
     * @param model инкапсулирует данные приложения
     * @return active_order.html
     */
    @RequestMapping("/order/active")
    public String activeOrder(Model model) {
        return "active_order";
    }

    /**
     * Отображение страницы доступных для исполнителя заказов.
     *
     * @param model инкапсулирует данные приложения
     * @return active_order.html
     */
    @RequestMapping("/order/free")
    public String allOrders(Model model) {
        return "active_order";
    }

    /**
     * Отображение профиля выбранного пользователя.
     *
     * @param model инкапсулирует данные приложения
     * @param id    идентификатор пользователя
     * @return profile.html
     */
    @RequestMapping("/profile/{id}")
    public String showProfile(Model model, @PathVariable String id) {
        return "profile";
    }
}
