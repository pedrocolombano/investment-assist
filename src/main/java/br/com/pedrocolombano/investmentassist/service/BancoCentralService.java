package br.com.pedrocolombano.investmentassist.service;

import br.com.pedrocolombano.investmentassist.dto.response.BancoCentralDto;
import br.com.pedrocolombano.investmentassist.model.TaxaInvestimento;
import br.com.pedrocolombano.investmentassist.proxy.BancoCentralProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BancoCentralService {

    //TODO: evoluir para REDIS com TTL de 1 dia
    private final Map<TaxaInvestimento, BancoCentralDto> taxas = new HashMap<>();

    private final BancoCentralProxy cdiProxy;
    private final BancoCentralProxy ipcaProxy;
    private final BancoCentralProxy selicProxy;

    public BancoCentralService(BancoCentralProxy cdiProxy,
                               BancoCentralProxy ipcaProxy,
                               BancoCentralProxy selicProxy) {
        this.cdiProxy = cdiProxy;
        this.selicProxy = selicProxy;
        this.ipcaProxy = ipcaProxy;
    }

    public void atualizarTaxas() {
        final LocalDate dataAtual = LocalDate.now();

        log.info("Consultando taxa CDI - {}", dataAtual);
        final BancoCentralDto taxaCdi = cdiProxy.getTaxaTotal().getFirst();
        log.info("Valor retornado taxa CDI | Data: {} - {}%", taxaCdi.data(), taxaCdi.valor());

        log.info("Consultando taxa IPCA - {}", taxaCdi.data());
        final List<BancoCentralDto> taxasIpca = ipcaProxy.getTaxaTotal().reversed();

        final LocalDate ultimaDataIpca = taxasIpca.getFirst().data();
        final BigDecimal valorIpca = taxasIpca.stream()
                                              .map(BancoCentralDto::valor)
                                              .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Valor retornado taxa IPCA calculando últimos 12 meses | Data: {} - {}%", ultimaDataIpca, valorIpca);

        log.info("Consultando taxa SELIC - {}", dataAtual);
        final BancoCentralDto taxaSelic = selicProxy.getTaxaTotal().getFirst();
        log.info("Valor retornado taxa SELIC | Data: {} - {}%", taxaSelic.data(), taxaSelic.valor());

        taxas.put(TaxaInvestimento.CDI, taxaCdi);
        taxas.put(TaxaInvestimento.IPCA, new BancoCentralDto(ultimaDataIpca, valorIpca));
        taxas.put(TaxaInvestimento.SELIC, taxaSelic);
    }
}
