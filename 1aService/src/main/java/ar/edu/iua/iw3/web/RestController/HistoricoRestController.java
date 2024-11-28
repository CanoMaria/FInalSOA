package ar.edu.iua.iw3.web.RestController;

import ar.edu.iua.iw3.modelo.DTORestTemplate.Historico;
import ar.edu.iua.iw3.modelo.DTORestTemplate.UltimoHistorico;
import ar.edu.iua.iw3.negocio.IHistoricoNegocio;
import ar.edu.iua.iw3.util.MensajeRespuesta;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;


@RestController
@RequestMapping(value = "/historico")
public class HistoricoRestController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IHistoricoNegocio historicoNegocio;
	
	//---------Listar por ID------------------

	@GetMapping(value = "/{id}")
	public ResponseEntity<Historico> load(@PathVariable("id") Long id) throws NegocioException, NoEncontradoException {
		try {
		Historico h = new Historico();
		return new ResponseEntity<Historico>(historicoNegocio.buscarHistorico(id), HttpStatus.OK);
	} catch (NegocioException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<Historico>(HttpStatus.INTERNAL_SERVER_ERROR);
	} catch (NoEncontradoException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<Historico>(HttpStatus.NOT_FOUND);
	}
}


	@GetMapping(value = "/last/{identificador}")
	public ResponseEntity<UltimoHistorico> loadLast(@PathVariable("identificador") String identificador) throws NegocioException, NoEncontradoException {
		try {
			Historico h = new Historico();
			return new ResponseEntity<UltimoHistorico>(historicoNegocio.buscarUltimoHistorico(identificador), HttpStatus.OK);
		} catch (NegocioException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<UltimoHistorico>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<UltimoHistorico>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping(value = "/order-by-category/{categoria}/{order}/{size}/{page}")
	public ResponseEntity<List<Historico>> filtrarPorCategoria(
			@PathVariable("categoria") String categoria,
			@PathVariable("order") String order,
			@PathVariable("size") String size,
			@PathVariable("page") String page) throws NegocioException, NoEncontradoException {
		try {
			Historico h = new Historico();
			return new ResponseEntity<List<Historico>>(historicoNegocio.buscarPorCategoria(categoria,order,size,page), HttpStatus.OK);
		} catch (NegocioException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<List<Historico>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<List<Historico>>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping(value = "/order-by-subcategory/{categoria}/{subcategoria}/{order}/{size}/{page}")
	public ResponseEntity<List<Historico>> filtrarPorSubcategoria(
			@PathVariable("categoria") String categoria,
			@PathVariable("subcategoria") String subcategoria,
			@PathVariable("order") String order,
			@PathVariable("size") String size,
			@PathVariable("page") String page) throws NegocioException, NoEncontradoException {
		try {
			return new ResponseEntity<List<Historico>>(historicoNegocio.buscarPorSubcategoria(categoria, subcategoria, order,size,page), HttpStatus.OK);
		} catch (NegocioException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<List<Historico>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<List<Historico>>(HttpStatus.NOT_FOUND);
		}
	}
	//---------Guardar Historico en BD------------------

	@PostMapping(value="/add")
	public ResponseEntity<MensajeRespuesta> agregar(@RequestBody Historico historico) {
		try {
			MensajeRespuesta r = historicoNegocio.agregar(historico).getMensaje();
			return new ResponseEntity<MensajeRespuesta>(r, HttpStatus.CREATED);
		} catch (NegocioException e) {
			log.error(e.getMessage(), e);
			MensajeRespuesta r = new MensajeRespuesta(-1, e.getMessage());
			return new ResponseEntity<MensajeRespuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}

	@DeleteMapping(value="/{id}")
	public ResponseEntity<MensajeRespuesta> eliminar(@PathVariable("id") Long id) {
		try {
			MensajeRespuesta r =   historicoNegocio.eliminar(id).getMensaje();
			return new ResponseEntity<MensajeRespuesta>(r,HttpStatus.OK);
		} catch (NegocioException e) {
			log.error(e.getMessage(), e);
			MensajeRespuesta r=new MensajeRespuesta(-1,e.getMessage());
			return new ResponseEntity<MensajeRespuesta>(r,HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			log.error(e.getMessage(), e);
			MensajeRespuesta r=new MensajeRespuesta(-1,e.getMessage());
			return new ResponseEntity<MensajeRespuesta>(r,HttpStatus.NOT_FOUND);
		}
	}
	}

