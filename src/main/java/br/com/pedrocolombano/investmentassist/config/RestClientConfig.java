package br.com.pedrocolombano.investmentassist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private final String bancoCentralUrl;

    public RestClientConfig(@Value("${api.bcb.url}") String bancoCentralUrl) {
        this.bancoCentralUrl = bancoCentralUrl;
    }

    @Bean
    public RestClient cdiRestClient(@Value("${api.bcb.cdi-code}") int cdiCode) {
        return RestClient.builder()
                         .baseUrl(bancoCentralUrl.formatted(cdiCode))
                         .build();
    }

    @Bean
    public RestClient selicRestClient(@Value("${api.bcb.selic-code}") int selicCode) {
        return RestClient.builder()
                         .baseUrl(bancoCentralUrl.formatted(selicCode))
                         .build();
    }
}
