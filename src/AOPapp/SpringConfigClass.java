package AOPapp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//Это файлик конфигурации спринга(это проходили). здесь просто подключаем АОП аннотацией @EnableAspectJAutoProxy и включаем сканирование спрингов папки приложения AOPapp(все подпапки понятное дело тоже сканируються)
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("AOPapp")
public class SpringConfigClass {

}
