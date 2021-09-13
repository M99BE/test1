package ru.avid.test.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// указывает Spring контейнеру, чтобы находил файл конфигурации в классе.
// debug = true - для просмотра лога какие бины были созданы, в production нужно ставить false
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(
        prePostEnabled = true // true означает, что можно будет использовать аннотации pre/post в компонентах Spring (например, @PreAuthorize для доступа к методам или контроллеру только для нужных прав)
)
@EnableAsync
public class SpringConfig extends WebSecurityConfigurerAdapter {
    @Value("${client.url}")
    private String clientURL;
    @Value("${client.url1}")
    private String clientURL1;
    @Value("${client.url2}")
    private String clientURL2;

    //Создаются глобальные CORS правила для всех контроллеров
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**"). // все URL
                        allowedOrigins(clientURL, clientURL1, clientURL2). // с каких адресов разрешать запросы (можно указывать через зяпятую) // Todo для отладки frontend
                        allowCredentials(true). // разрешить отправлять куки для межсайтового запроса
                        allowedHeaders("*"). // разрешить все заголовки
                        allowedMethods("*"); // разрешить все HTTP методы
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                /* отключение хранения сессии на сервере.
          Клиент будет вызывать RESTfull Api сервера и передавать токен с информацией пользователя
          http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
          Allways - кук JSESSIONID есть, будет создаваться сессия,
          Newer - кук JSESSIONID есть, будет создаваться сессия, кука нет, сессия тоже нет,
          STATELESS - без сохранения сессии, кук JSESSIONID не создается.
        */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.formLogin().disable();
        http.httpBasic().disable();
    }
}
