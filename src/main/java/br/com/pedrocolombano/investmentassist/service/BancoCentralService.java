package br.com.pedrocolombano.investmentassist.service;

import br.com.pedrocolombano.investmentassist.dto.response.BancoCentralDto;
import br.com.pedrocolombano.investmentassist.model.TaxaInvestimento;
import br.com.pedrocolombano.investmentassist.proxy.BancoCentralProxy;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BancoCentralService {

    //TODO: evoluir para REDIS com TTL de 1 dia
    private final Map<TaxaInvestimento, BancoCentralDto> taxas = new HashMap<>();

    private final BancoCentralProxy cdiProxy;
    private final BancoCentralProxy selicProxy;

    public BancoCentralService(BancoCentralProxy cdiProxy,
                               BancoCentralProxy selicProxy) {
        this.cdiProxy = cdiProxy;
        this.selicProxy = selicProxy;
    }

    @PostConstruct
    public void init() {
        final BancoCentralDto taxaCdi = cdiProxy.getTaxaTotal()
                                                .getFirst();

        final BancoCentralDto taxaSelic = selicProxy.getTaxaTotal()
                                                    .getFirst();


        log.info("Valor taxa CDI em {}: {}", taxaCdi.data(), taxaCdi.valor());
        log.info("Valor taxa SELIC {}: {}", taxaCdi.data(), taxaCdi.valor());

        taxas.put(TaxaInvestimento.CDI, taxaCdi);
        taxas.put(TaxaInvestimento.SELIC, taxaSelic);
    }
}
