package ar.edu.iua.iw3.negocio.service;


import ar.edu.iua.iw3.modelo.Historico;
import ar.edu.iua.iw3.modelo.UltimoHistorico;
import ar.edu.iua.iw3.util.excepciones.NegocioException;
import ar.edu.iua.iw3.util.excepciones.NoEncontradoException;
import ar.edu.iua.iw3.web.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.text.Normalizer;
@Service
public class HistoricoBusiness implements IHistoricoBusiness{

	private static String ultimo  = "ULTIMO";
	//private Memcache cache = new Memcache();
	private Logger log = LoggerFactory.getLogger(HistoricoBusiness.class);
	
	@Autowired
    private RestTemplate historicosRestTemplate;


	@Override
	 public Historico load(Long id) throws NegocioException, NoEncontradoException {
        Optional<Historico> o = null;
        try {
                o = Optional.ofNullable(historicosRestTemplate.getHistorico(id));
        } catch (Exception e) {
            if(o == null)
                throw new NoEncontradoException("No se encuentra al historico con id= " + id);
            log.error(e.getMessage(), e);
            throw new NegocioException(e);
        }
        return o.get();
    }

	/*@Override
	public List<Historico> list(String pageSize, String page) throws NegocioException, NoEncontradoException {
		List<Historico> o = null;
	        try {
	           o = historicosRestTemplate.getHistoricosList(pageSize,page);
	        } catch (Exception e) {
	            if(o == null)
	                throw new NoEncontradoException("No se encuentra ninguna lista de historicos");
	            log.error(e.getMessage(), e);
	            throw new NegocioException(e);
	        }
	        return o;
	}*/
	@Override
	public List<Historico> list(String pageSize, String page, String category, String subcategory, String order) 
	        throws NegocioException, NoEncontradoException {
	    List<Historico> o = null;
	    try {
	        // Llamar al método getHistoricosList con los nuevos parámetros
	        o = historicosRestTemplate.getHistoricosList(pageSize, page, category, subcategory, order);
	    } catch (Exception e) {
	        if (o == null)
	            throw new NoEncontradoException("No se encuentra ninguna lista de historicos");
	        log.error(e.getMessage(), e);
	        throw new NegocioException(e);
	    }
	    return o;
	}


	
	@Override
    public UltimoHistorico loadLastHistory(String identificador) throws NegocioException, NoEncontradoException {
        Optional<UltimoHistorico> o = null;
        try {

            o = Optional.ofNullable(historicosRestTemplate.getHistoricoUltimo(identificador));
        } catch (Exception e) {
            if(o == null)
                throw new NoEncontradoException("No hay historicos cargados");
            log.error(e.getMessage(), e);
            throw new NegocioException(e);
        }
        return o.get();
    }


	@Override
	public List<Historico> listByCategory(String category, String order, String pageSize, String page)  
	        throws NegocioException, NoEncontradoException {

	    String subcategory = ""; 
	    List<Historico> orderResults = list(pageSize, page, category, subcategory, order);

	    orderResults = orderResults.stream()
	        .filter(p -> p.getCategoria().equalsIgnoreCase(category))
	        .collect(Collectors.toList());

	    orderResults.sort("desc".equalsIgnoreCase(order) 
	        ? Comparator.comparing(Historico::getFechaHoraRecepcion).reversed()
	        : Comparator.comparing(Historico::getFechaHoraRecepcion));

	    return orderResults;
	}

	
	/*@Override
	public List<Historico> listBySubCategory(String category,String subcategory,String order,String pageSize, String page) throws NegocioException, NoEncontradoException {
		
		List<Historico> orderResults =  list(pageSize,page);
		orderResults  = orderResults.stream().filter(p -> p.getCategoria().toLowerCase().equals(category.toLowerCase())).collect(Collectors.toList());
		orderResults  = orderResults.stream().filter(p -> p.getSubCategoria().toLowerCase().equals(subcategory.toLowerCase())).collect(Collectors.toList());
		
		if(order.equals("desc")) {
			Collections.sort(orderResults, (s1, s2) -> { return s1.getFechaHoraRecepcion().compareTo(s2.getFechaHoraRecepcion()); });
		}else {
			Collections.sort(orderResults, Collections.reverseOrder((s1, s2) -> { return s1.getFechaHoraRecepcion().compareTo(s2.getFechaHoraRecepcion());}));
		}
		
		return orderResults;
	}*/
	


	@Override
	public List<Historico> listBySubCategory(String category, String subcategory, String order, String pageSize, String page) 
	        throws NegocioException, NoEncontradoException {

	    List<Historico> orderResults = list(pageSize, page, category, subcategory, order);

	    // Normalizar y comparar correctamente
	    orderResults = orderResults.stream()
	        .filter(p -> normalizarTexto(p.getCategoria()).equalsIgnoreCase(normalizarTexto(category)))
	        .filter(p -> normalizarTexto(p.getSubCategoria()).equalsIgnoreCase(normalizarTexto(subcategory)))
	        .sorted("desc".equalsIgnoreCase(order) 
	            ? Comparator.comparing(Historico::getFechaHoraRecepcion).reversed()
	            : Comparator.comparing(Historico::getFechaHoraRecepcion))
	        .collect(Collectors.toList());

	    return orderResults;
	}

	// Método para normalizar el texto
	private String normalizarTexto(String texto) {
	    if (texto == null) return "";
	    return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
	            .replaceAll("[^\\p{ASCII}]", ""); // Elimina tildes y caracteres especiales
	}

	
	

}

