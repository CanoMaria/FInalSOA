package ar.edu.iua.iw3.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Ubicacion implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ciudad;
    private String region;
    private String pais;
    private String coordenadas;
    private String codigoPostal;
    private String zonaHoraria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(String zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "id=" + id +
                ", ciudad='" + ciudad + '\'' +
                ", region='" + region + '\'' +
                ", pais='" + pais + '\'' +
                ", coordenadas='" + coordenadas + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", zonaHoraria='" + zonaHoraria + '\'' +
                '}';
    }
}
