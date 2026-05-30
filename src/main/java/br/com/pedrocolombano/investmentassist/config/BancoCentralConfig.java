package br.com.pedrocolombano.investmentassist.config;

import br.com.pedrocolombano.investmentassist.proxy.BancoCentralProxy;
import br.com.pedrocolombano.investmentassist.proxy.impl.BancoCentralProxyImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class BancoCentralConfig {

    @Bean
    public BancoCentralProxy cdiProxy(@Qualifier("cdiRestClient") RestClient cdiRestClient) {
        return new BancoCentralProxyImpl(cdiRestClient);
    }

    @Bean
    public BancoCentralProxy selicProxy(@Qualifier("selicRestClient") RestClient selicRestClient) {
        return new BancoCentralProxyImpl(selicRestClient);
    }

}
