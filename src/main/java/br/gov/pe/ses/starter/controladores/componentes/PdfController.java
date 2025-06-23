package br.gov.pe.ses.starter.controladores.componentes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {

    @Autowired
    private DadosSistemaBean dadosSistemaBean;

    @GetMapping("/manual")
    public ResponseEntity<byte[]> getManual() {
        byte[] pdf = dadosSistemaBean.getDadosSistema().getManual();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("manual.pdf").build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}