package ar.edu.iua.iw3.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class RawData  implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private double  temperatura;
  private double humedad;
  private double presion;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public double getTemperatura() {
    return temperatura;
  }

  public void setTemperatura(double temperatura) {
    this.temperatura = temperatura;
  }

  public double getHumedad() {
    return humedad;
  }

  public void setHumedad(double humedad) {
    this.humedad = humedad;
  }

  public double getPresion() {
    return presion;
  }

  public void setPresion(double presion) {
    this.presion = presion;
  }

}
