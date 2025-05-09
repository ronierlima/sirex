package br.gov.pe.ses.starter.dto;

import java.sql.Date;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ContagemAtendimentoDTO {
	
    private LocalDate data;
    private Long total;
    
	public ContagemAtendimentoDTO(Date data, Long total) {
		this.data = data.toLocalDate();
		this.total = total;
	}
	
}
