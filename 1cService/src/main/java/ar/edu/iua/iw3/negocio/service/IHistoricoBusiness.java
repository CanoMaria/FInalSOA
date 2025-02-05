package ar.edu.iua.iw3.negocio.service;

import java.util.List;

import ar.edu.iua.iw3.modelo.UltimoHistorico;
import ar.edu.iua.iw3.util.excepciones.NegocioException;
import ar.edu.iua.iw3.util.excepciones.NoEncontradoException;
import ar.edu.iua.iw3.modelo.Historico;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface IHistoricoBusiness {
	
	public Historico load(Long id) throws NoEncontradoException, NegocioException;
	//public List<Historico> list(String pageSize,String pageIndex) throws NegocioException, NoEncontradoException;
	//public List<Historico> listByCategory(String category,String order,String pageSize, String pageIndex) throws NegocioException, NoEncontradoException;
	//public List<Historico> listBySubCategory(String category,String subcategory,String order,String pageSize, String pageIndex) throws NegocioException, NoEncontradoException;
	public UltimoHistorico loadLastHistory(String identificador) throws NoEncontradoException, NegocioException, JsonProcessingException;
	List<Historico> list(String pageSize, String page, String category, String subcategory, String order)
			throws NegocioException, NoEncontradoException;
	List<Historico> listBySubCategory(String category, String subcategory, String order, String pageSize, String page)
			throws NegocioException, NoEncontradoException;
	List<Historico> listByCategory(String category, String order, String pageSize, String page)
			throws NegocioException, NoEncontradoException;
	
}

