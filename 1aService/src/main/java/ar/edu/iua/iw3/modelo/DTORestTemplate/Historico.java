package ar.edu.iua.iw3.modelo.DTORestTemplate;



import java.io.Serializable;
import java.util.Date;

public class Historico implements Serializable{

	@Override
	public String toString() {
		return "Historico{" +
				"id_historico=" + id_historico +
				", fechaHoraRecepcion=" + fechaHoraRecepcion +
				", rawData=" + rawData +
				", ubicacion=" + ubicacion +
				", categoria='" + categoria + '\'' +
				", subCategoria='" + subCategoria + '\'' +
				", identificador='" + identificador + '\'' +
				", altitud='" + altitud + '\'' +
				", puntoRocio='" + puntoRocio + '\'' +
				'}';
	}


	public Historico(){}


	private long id_historico;

	private Date fechaHoraRecepcion;

	private RawData rawData;

	private Ubicacion ubicacion;

	private String categoria;

	private String subCategoria;

	private String identificador;

	private String altitud;

	private String puntoRocio;


	public long getId_historico() {
		return id_historico;
	}

	public void setId_historico(long id_historico) {
		this.id_historico = id_historico;
	}

	public Date getFechaHoraRecepcion() {
		return fechaHoraRecepcion;
	}

	public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
		this.fechaHoraRecepcion = fechaHoraRecepcion;
	}

	public RawData getRawData() {
		return rawData;
	}

	public void setRawData(RawData rawData) {
		this.rawData = rawData;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(String subCategoria) {
		this.subCategoria = subCategoria;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getAltitud() {
		return altitud;
	}

	public void setAltitud(String altitud) {
		this.altitud = altitud;
	}

	public String getPuntoRocio() {
		return puntoRocio;
	}

	public void setPuntoRocio(String puntoRocio) {
		this.puntoRocio = puntoRocio;
	}
}
