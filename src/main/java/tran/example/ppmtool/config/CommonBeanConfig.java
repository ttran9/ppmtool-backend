package tran.example.ppmtool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableJpaRepositories("tran.example.ppmtool.repositories")
public class CommonBeanConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncryptor() {
        return new BCryptPasswordEncoder();
    }
}
