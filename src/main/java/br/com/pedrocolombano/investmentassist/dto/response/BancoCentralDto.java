package br.com.pedrocolombano.investmentassist.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BancoCentralDto(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                              LocalDate data,
                              BigDecimal valor) {
}
