package ar.edu.iua.iw3.web.RestTemplate;

import ar.edu.iua.iw3.modelo.DTORestTemplate.Historico;
import ar.edu.iua.iw3.modelo.DTORestTemplate.UltimoHistorico;
import ar.edu.iua.iw3.util.MensajeRespuesta;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;


@RestController
@Component
public class RestTemplate {

    private String direccionConexionB;
    private String direccionConexionC;


    RestTemplate(@Value("${conexion.direccionB}") String direccionB, @Value("${conexion.direccionC}") String direccionC){
        this.direccionConexionB = direccionB;
        this.direccionConexionC = direccionC;
    }

    private Logger log = LoggerFactory.getLogger(RestTemplate.class);


    @Autowired
    org.springframework.web.client.RestTemplate restTemplate;


    public UltimoHistorico getHistoricoUltimo(String identificador) {
        String url = "http://"+direccionConexionC+"/historico/last/".concat(String.valueOf(identificador));
        UltimoHistorico forObject = restTemplate.getForObject(url,UltimoHistorico.class);
        log.info("Result" + forObject);
        System.out.println(forObject.toString());
        return forObject;
    }
    public List<Historico> getByCategory(String categoria,String order,String pageSize,String page) {
        String url = "http://"+direccionConexionC+"/historico/order-by-category/".concat(String.valueOf(categoria)).concat("/"+(order)+"/"+pageSize+"/"+page);
        Historico[] forObject = restTemplate.getForObject(url,Historico[].class);
        log.info("Result" + forObject);
        System.out.println(forObject.toString());
        return  Arrays.asList(forObject);
    }
    public List<Historico> getBySubcategory(String categoria,String subcategoria,String order,String pageSize,String page) {
        String url = "http://"+direccionConexionC+"/historico/order-by-subcategory/".concat(String.valueOf(categoria)).concat("/"+(subcategoria)).concat("/"+(order)+"/"+pageSize+"/"+page);
        Historico[] forObject = restTemplate.getForObject(url,Historico[].class);
        log.info("Result" + forObject);
        System.out.println(forObject.toString());
        return  Arrays.asList(forObject);
    }
    

    public MensajeRespuesta addHistorico(Historico historico) throws RestClientException{
      MensajeRespuesta mensaje =  new MensajeRespuesta();
      return restTemplate.postForObject("http://"+direccionConexionB+"/historico/add",historico,mensaje.getClass());
    }

    public void deleteHistorico(Long id) throws RestClientException{
        restTemplate.delete("http://"+direccionConexionB+"/historico/".concat(String.valueOf(id)));
    }

    public Historico getHistorico(Long id) {
        String url = "http://"+direccionConexionB+"/historico/".concat(String.valueOf(id));
        Historico forObject = restTemplate.getForObject(url, Historico.class);
        log.info("Result" + forObject);
        System.out.println(forObject.toString());
        return forObject;
    }
}
