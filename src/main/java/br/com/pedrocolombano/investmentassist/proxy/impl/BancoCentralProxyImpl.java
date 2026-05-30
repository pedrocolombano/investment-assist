package br.com.pedrocolombano.investmentassist.proxy.impl;

import br.com.pedrocolombano.investmentassist.dto.response.BancoCentralDto;
import br.com.pedrocolombano.investmentassist.proxy.BancoCentralProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
public class BancoCentralProxyImpl implements BancoCentralProxy {

    private final RestClient restClient;

    public BancoCentralProxyImpl(RestClient restClient) {
        this.restClient = restClient;

    }

    @Override
    public List<BancoCentralDto> getTaxaTotal() {
        return restClient.get()
                         .retrieve()
                         .body(new ParameterizedTypeReference<>() {});
    }
}
