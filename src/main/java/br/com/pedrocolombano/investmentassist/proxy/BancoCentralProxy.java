package br.com.pedrocolombano.investmentassist.proxy;

import br.com.pedrocolombano.investmentassist.dto.response.BancoCentralDto;

import java.util.List;

public interface BancoCentralProxy {

    List<BancoCentralDto> getTaxaTotal();

}
