package ar.edu.iua.iw3.web;

import ar.edu.iua.iw3.modelo.HistoricoDTO;
import ar.edu.iua.iw3.util.MensajeRespuesta;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import ar.edu.iua.iw3.negocio.service.IHistoricoBusiness;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;
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
	//---------Listar por ID------------------


	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Historico>> listAll() {
		try {
			return new ResponseEntity<List<Historico>>(historicoBusiness.list(), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Historico>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/all-page", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Historico>> listAllPage(Pageable pageable) {
		try {
			return new ResponseEntity<List<Historico>>(historicoBusiness.listPage(pageable).getContent(), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Historico>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	//---------Guardar Historico en BD------------------

	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MensajeRespuesta> add(@RequestBody Historico historico) {
		try {
			MensajeRespuesta r =   historicoBusiness.add(historico).getMensaje();;
			return new ResponseEntity<MensajeRespuesta>(r, HttpStatus.CREATED);
		} catch (NegocioException e) {
			log.error(e.getMessage(), e);
			MensajeRespuesta r=new MensajeRespuesta(-1,e.getMessage());
			return new ResponseEntity<MensajeRespuesta>(r,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//---------Ultimo valor ------------------

	@GetMapping(value = "/last/{identificador}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HistoricoDTO> loadLasted(@PathVariable("identificador") String identificador) {

			try {
				return new ResponseEntity<HistoricoDTO>(historicoBusiness.loadLastHistory(identificador), HttpStatus.OK);
			} catch (NegocioException e) {
				return new ResponseEntity<HistoricoDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (NoEncontradoException e) {
				return new ResponseEntity<HistoricoDTO>(HttpStatus.NOT_FOUND);
			} catch (JsonProcessingException e) {
				return new ResponseEntity<HistoricoDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}

	//---------Borrar Historico ------------------


	@DeleteMapping(value="/{id}")
	public ResponseEntity<MensajeRespuesta> eliminar(@PathVariable("id") Long id) {
		try {
			MensajeRespuesta r =   historicoBusiness.delete(id).getMensaje();
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
