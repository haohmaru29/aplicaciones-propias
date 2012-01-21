package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CALENDARIO database table.
 * 
 */
@Entity
@Table(name="CALENDARIO")
public class Calendario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, precision=22)
	private long idcalendario;

	@Column(precision=22)
	private BigDecimal color;

	@Column(name="NOMBRE_CALENDARIO", length=200)
	private String nombreCalendario;

    public Calendario() {
    }

	public long getIdcalendario() {
		return this.idcalendario;
	}

	public void setIdcalendario(long idcalendario) {
		this.idcalendario = idcalendario;
	}

	public BigDecimal getColor() {
		return this.color;
	}

	public void setColor(BigDecimal color) {
		this.color = color;
	}

	public String getNombreCalendario() {
		return this.nombreCalendario;
	}

	public void setNombreCalendario(String nombreCalendario) {
		this.nombreCalendario = nombreCalendario;
	}

}