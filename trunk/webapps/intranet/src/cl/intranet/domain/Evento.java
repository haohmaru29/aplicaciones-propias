package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the EVENTO database table.
 * 
 */
@Entity
@Table(name="EVENTO")
@SequenceGenerator(name="SEQ_EVENTO", sequenceName="SEQ_EVENTO")
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EVENTO")
	@Column(unique=true, nullable=false, precision=22)
	private long idevento;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_TERMINO")
	private Date fechaTermino;

	@Column(name="HORA_INICIO", length=20)
	private String horaInicio;

	@Column(name="HORA_TERMINO", length=20)
	private String horaTermino;

	@Column(length=500)
	private String lugar;

	@Column(length=100)
	private String titulo;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="IDUSUARIO")
	private Usuario usuario;

	//bi-directional many-to-one association to Calendario
    @ManyToOne
	@JoinColumn(name="IDCALENDARIO")
	private Calendario calendario;

    public Evento() {
    }

	public long getIdevento() {
		return this.idevento;
	}

	public void setIdevento(long idevento) {
		this.idevento = idevento;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public String getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraTermino() {
		return this.horaTermino;
	}

	public void setHoraTermino(String horaTermino) {
		this.horaTermino = horaTermino;
	}

	public String getLugar() {
		return this.lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Calendario getCalendario() {
		return this.calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}
	
}