package br.com.pedrocolombano.investmentassist.schedule;

import br.com.pedrocolombano.investmentassist.service.BancoCentralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BancoCentralSchedule {

    private final BancoCentralService bancoCentralService;

    public BancoCentralSchedule(BancoCentralService bancoCentralService) {
        this.bancoCentralService = bancoCentralService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        atualizarTaxas();
    }

    @Scheduled(cron = "0 0 3 * * 1-7")
    public void atualizarTaxas() {
        log.info("Iniciando atualização de taxas do BCB");
        bancoCentralService.atualizarTaxas();
        log.info("Fim da atualização de taxas do BCB");
   }
}
