package ar.edu.iua.iw3.web;

import ar.edu.iua.iw3.modelo.UltimoHistorico;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import ar.edu.iua.iw3.negocio.service.IHistoricoBusiness;
import ar.edu.iua.iw3.util.excepciones.NegocioException;
import ar.edu.iua.iw3.util.excepciones.NoEncontradoException;
import ar.edu.iua.iw3.modelo.Historico;


@RestController
@RequestMapping(value = "/historico")
public class HistoricoRestController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IHistoricoBusiness historicoBusiness;
	
	//---------Listar por ID------------------

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Historico> load(@PathVariable("id") Long id) {
		try {

			return new ResponseEntity<Historico>(historicoBusiness.load(id), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Historico>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Historico>(HttpStatus.NOT_FOUND);
		}
	}
	
	//---------Ultimo valor ------------------

	@GetMapping(value = "/last/{identificador}")
	public ResponseEntity<UltimoHistorico> loadLast(@PathVariable("identificador") String identificador) throws NegocioException, NoEncontradoException, JsonProcessingException {
		try {
			Historico h = new Historico();
			return new ResponseEntity<UltimoHistorico>(historicoBusiness.loadLastHistory(identificador), HttpStatus.OK);
		} catch (NegocioException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<UltimoHistorico>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<UltimoHistorico>(HttpStatus.NOT_FOUND);
		}
	}
	
	//---------Lista de Valores------------------
/*
		@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Historico>> loadAll() {

				try {
					return new ResponseEntity<List<Historico>>(historicoBusiness.list(), HttpStatus.OK);
				} catch (NegocioException e) {
					return new ResponseEntity<List<Historico>>(HttpStatus.INTERNAL_SERVER_ERROR);
				} catch (NoEncontradoException e) {
					return new ResponseEntity<List<Historico>>(HttpStatus.NOT_FOUND);
				}
		}
		*/
		//---------Lista de Valores------------------

		@GetMapping(value = "/order-by-category/{category}/{order}/{size}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Historico>> loadByCategory(@PathVariable("category") String category,
															  @PathVariable("order") String order,
															  @PathVariable("size") String size,
															  @PathVariable("page") String page) {

				try {
					return new ResponseEntity<List<Historico>>(historicoBusiness.listByCategory(category,order,size,page), HttpStatus.OK);
				} catch (NegocioException e) {
					return new ResponseEntity<List<Historico>>(HttpStatus.INTERNAL_SERVER_ERROR);
				} catch (NoEncontradoException e) {
					return new ResponseEntity<List<Historico>>(HttpStatus.NOT_FOUND);
				}
		}
		@GetMapping(value = "/order-by-subcategory/{category}/{subcategory}/{order}/{size}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Historico>> loadByCategory(@PathVariable("category") String category,
															  @PathVariable("subcategory") String subcategory,
															  @PathVariable("order") String order,
															  @PathVariable("size") String size,
															  @PathVariable("page") String page) {

			try {
					return new ResponseEntity<List<Historico>>(historicoBusiness.listBySubCategory(category,subcategory,order,size,page), HttpStatus.OK);
				} catch (NegocioException e) {
					return new ResponseEntity<List<Historico>>(HttpStatus.INTERNAL_SERVER_ERROR);
				} catch (NoEncontradoException e) {
					return new ResponseEntity<List<Historico>>(HttpStatus.NOT_FOUND);
				}
		}

	
}

