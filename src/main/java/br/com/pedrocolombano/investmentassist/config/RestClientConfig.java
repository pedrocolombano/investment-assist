package br.com.pedrocolombano.investmentassist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class RestClientConfig {

    private static final int LAST_MONTH = 1;
    private static final int LAST_YEAR = 12;

    private final String bancoCentralUrl;

    public RestClientConfig(@Value("${api.bcb.url}") String bancoCentralUrl) {
        this.bancoCentralUrl = bancoCentralUrl;
    }

    @Bean
    public RestClient cdiRestClient(@Value("${api.bcb.cdi-code}") int cdiCode) {
        return RestClient.builder()
                         .baseUrl(bancoCentralUrl.formatted(cdiCode, LAST_MONTH))
                         .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                         .configureMessageConverters(
                                 converters ->
                                 converters.addCustomConverter(jacksonHtmlConverter()))
                         .build();
    }

    @Bean
    public RestClient selicRestClient(@Value("${api.bcb.selic-code}") int selicCode) {
        return RestClient.builder()
                         .baseUrl(bancoCentralUrl.formatted(selicCode, LAST_MONTH))
                         .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                         .configureMessageConverters(
                                 converters ->
                                         converters.addCustomConverter(jacksonHtmlConverter()))
                         .build();
    }

    @Bean
    public RestClient ipcaRestClient(@Value("${api.bcb.ipca-code}") int ipcaCode) {
        return RestClient.builder()
                         .baseUrl(bancoCentralUrl.formatted(ipcaCode, LAST_YEAR))
                         .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                         .configureMessageConverters(
                                 converters ->
                                         converters.addCustomConverter(jacksonHtmlConverter()))
                         .build();
    }

    private HttpMessageConverter<?> jacksonHtmlConverter() {
        final JacksonJsonHttpMessageConverter converter = new JacksonJsonHttpMessageConverter();

        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON,
                                                 MediaType.TEXT_HTML,
                                                 MediaType.TEXT_PLAIN));
        return converter;
    }
}
