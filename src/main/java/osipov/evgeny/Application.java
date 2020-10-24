package osipov.evgeny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import osipov.evgeny.util.HibernateUtil;

/**
 * Класс для запуска программы.
 *
 * @author Evgeny Osipov
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        // Ленивая инициализация Hibernate не создает таблицы вместе с запуском программы. Теперь создает.
        HibernateUtil.getSession().close();

        SpringApplication.run(Application.class, args);

    }

}
