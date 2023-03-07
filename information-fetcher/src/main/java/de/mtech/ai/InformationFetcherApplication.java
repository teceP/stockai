package de.mtech.ai;

import com.gargoylesoftware.htmlunit.WebClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InformationFetcherApplication {
    public static void main(String[] args) {
        SpringApplication.run(InformationFetcherApplication.class, args);
    }

    @Bean
    public WebClient getWebClient() {
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        return webClient;
    }
}