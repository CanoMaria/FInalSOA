package ar.edu.iua.iw3.negocio.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import ar.edu.iua.iw3.modelo.HistoricoDTO;
import ar.edu.iua.iw3.modelo.cache.Memcache;
import ar.edu.iua.iw3.util.MensajeRespuesta;
import ar.edu.iua.iw3.util.RespuestaGenerica;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;
import ar.edu.iua.iw3.modelo.Historico;
import ar.edu.iua.iw3.modelo.persistencia.HistoricoRepository;


@Service
public class HistoricoBusiness implements IHistoricoBusiness{

	public HistoricoBusiness() throws IOException {
	}
	private static String ultimo  = "ULTIMO";
	@Autowired
	private Memcache cache;

	private Logger log = LoggerFactory.getLogger(HistoricoBusiness.class);

	@Autowired
	private HistoricoRepository historicoDAO;


	@Override
	public Historico load(Long id) throws NoEncontradoException, NegocioException {
		Optional<Historico> op;
		try {
			op =  historicoDAO.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if (!op.isPresent()) {
			throw new NoEncontradoException("El historico no se encuentra en la BD");
		}
		return op.get();
	}

	@Override
	public List<Historico> list() throws NegocioException {
		
		List<Historico> op;
		try {
			op = (List<Historico>) historicoDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		
		return op;
	}


	/*@Override
	public Page listPage(Pageable pageable) throws NegocioException {

		Optional<Page> op;
		try {
			op = Optional.ofNullable(historicoDAO.findAll(pageable));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}

		return op.get();
	}*/
	@Override
	public Page<Historico> listPage(String category, String subcategory, Pageable pageable) throws NegocioException {
	    try {
	        // Verificar si subcategory está vacío o es null
	        if (subcategory == null || subcategory.trim().isEmpty()) {
	            return historicoDAO.findByCategory(category, pageable); // Llamada al método que busca solo por categoría
	        } else {
	            return historicoDAO.findByCategoryAndSubcategory(category, subcategory, pageable); // Llamada al método que busca por categoría y subcategoría
	        }
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        throw new NegocioException(e);
	    }
	}




	/*@Override
	public RespuestaGenerica<Historico> add(Historico historico) throws NegocioException {
		MensajeRespuesta m=new MensajeRespuesta();
		try {
			historico.setFechaHoraRecepcion(new Date());
			historicoDAO.save(historico);
			m.setMensaje(historico.toString());
			
			ultimo=historico.getIdentificador();
			
			cache.agregar(historico,ultimo,3600);
			log.debug(historico + "\nGuardado en el cache");
			return new RespuestaGenerica<Historico>(historico, m);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}*/
	@Override
	public RespuestaGenerica<Historico> add(Historico historico) throws NegocioException {
	    MensajeRespuesta m = new MensajeRespuesta();
	    
	    try {
	    	historico.setFechaHoraRecepcion(new Date());
			historicoDAO.save(historico);
			m.setMensaje(historico.toString());
			
			ultimo=historico.getIdentificador();
			
			
	        // Verificamos si el identificador ya está en el cache
	        String cachedData = cache.buscar(ultimo);

	        // Si está en el cache, lo actualizamos
	        if (cachedData != null) {
	            if (cache.actualizar(historico, 3600)) {
	                log.debug("Histórico con identificador " + ultimo+ " actualizado en el cache");
	            } else {
	                log.warn("No se pudo actualizar el histórico con identificador " + ultimo + " en el cache");
	            }
	        } else {
	            // Si no está en el cache, lo agregamos
	            if (cache.agregar(historico, String.valueOf(ultimo), 3600)) {
	                log.debug("Histórico con identificador " + ultimo+ " guardado en el cache");
	            } else {
	                log.warn("No se pudo agregar el histórico con identificador " + ultimo + " al cache");
	            }
	        }   
	        return new RespuestaGenerica<Historico>(historico, m);
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        throw new NegocioException(e);
	    }
	}


	@Override
	public Historico update(Historico historico) throws NoEncontradoException, NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RespuestaGenerica<Historico> delete(Long id) throws NegocioException, NoEncontradoException {
		MensajeRespuesta m=new MensajeRespuesta();
		Historico h = load(id);
		if(h == null)
			throw new NoEncontradoException("El historico que desea eliminar no se encuentra registrado");
		try {
			historicoDAO.deleteById(id);
			return new RespuestaGenerica<Historico>(h, m);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		
	}

	@Override
	public HistoricoDTO loadLastHistory(String identificador) throws NoEncontradoException, NegocioException{
		Gson gson = new Gson();
		String	ultimoHistorico = null;
		Optional<Historico> o = null;
		ultimo=identificador;
		ultimoHistorico =  cache.buscar(ultimo);
		if(ultimoHistorico == null || ultimoHistorico == "") {
			try {
				o = historicoDAO.findLast(ultimo);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new NegocioException(e);
			}
			if (!o.isPresent()) {
				throw new NoEncontradoException("No hay historicos registrados");
			}else {
				cache.agregar(o.get(), ultimo,3600);
				log.debug(o.get() + "\nGuardado en el cache");
			}
		}else{
			o = Optional.ofNullable(gson.fromJson(ultimoHistorico, Historico.class));
			log.debug(o.get() +"\nSacado del cache");
		}
		return new HistoricoDTO(o.get());
	}

}
