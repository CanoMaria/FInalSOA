package ar.edu.iua.iw3.web;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.iua.iw3.modelo.Historico;
import ar.edu.iua.iw3.modelo.UltimoHistorico;


@RestController
@Component
public class RestTemplate {

    private String direccionConexionB;
    private Logger log = LoggerFactory.getLogger(RestTemplate.class);


    RestTemplate(@Value("${conexion.direccionB}") String direccionB, RestTemplateBuilder builder){
        this.direccionConexionB = direccionB;

        this.restTemplate = builder.build();
    }



    private final org.springframework.web.client.RestTemplate restTemplate;

/*
    @Autowired
    public RestTemplate(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }*/
    
    public UltimoHistorico getHistoricoUltimo(String identificador) {
        String url = "http://"+direccionConexionB+"/historico/last/".concat(String.valueOf(identificador));
        UltimoHistorico forObject = restTemplate.getForObject(url,UltimoHistorico.class);
        log.info("Result" + forObject);
        System.out.println(forObject.toString());
        return forObject;
    }
    
    public Historico getHistorico(Long id) {
        String url = "http://"+direccionConexionB+"/historico/".concat(String.valueOf(id));
        Historico forObject = restTemplate.getForObject(url, Historico.class);
        log.info("Result" + forObject);
        System.out.println(forObject.toString());
        return forObject;
    }
    public List<Historico> getHistoricosList(String pageSize,String page) {
        String url = "http://"+direccionConexionB+"/historico/all-page?size=".concat(pageSize)+"&page=".concat(page);
        Historico[] forObject = restTemplate.getForObject(url, Historico[].class);
        log.info("Result" + Arrays.toString(forObject));
        return  Arrays.asList(forObject);
    }   

}
