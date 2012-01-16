package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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

	@Column(name="NOMBRE_CALENDARIO", length=200)
	private String nombreCalendario;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="calendario")
	private List<Evento> eventos;

    public Calendario() {
    }

	public long getIdcalendario() {
		return this.idcalendario;
	}

	public void setIdcalendario(long idcalendario) {
		this.idcalendario = idcalendario;
	}

	public String getNombreCalendario() {
		return this.nombreCalendario;
	}

	public void setNombreCalendario(String nombreCalendario) {
		this.nombreCalendario = nombreCalendario;
	}

	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	
}