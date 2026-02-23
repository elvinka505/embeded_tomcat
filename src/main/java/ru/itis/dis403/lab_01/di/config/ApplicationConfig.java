package ru.itis.dis403.lab_01.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.itis.dis403.lab_01.di.component.StoreService;
import ru.itis.dis403.lab_01.di.model.Basa;

@Configuration
@ComponentScan("ru.itis.dis403.lab_01.di")
public class ApplicationConfig {

    @Bean
    public StoreService storeService() {
        return new StoreService(new Basa()); // как MarketService(new Market()) в lab2_2
    }
}
