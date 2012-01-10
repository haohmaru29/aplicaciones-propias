package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the USUARIO_CALENDARIO database table.
 * 
 */
@Entity
@Table(name="USUARIO_CALENDARIO")
public class UsuarioCalendario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDUSUARIO_CALENDARIO", unique=true, nullable=false, precision=22)
	private long idusuarioCalendario;

	@Column(precision=22)
	private long idusuario;

	//bi-directional many-to-one association to Calendario
    @ManyToOne
	@JoinColumn(name="IDCALENDARIO")
	private Calendario calendario;

    public UsuarioCalendario() {
    }

	public long getIdusuarioCalendario() {
		return this.idusuarioCalendario;
	}

	public void setIdusuarioCalendario(long idusuarioCalendario) {
		this.idusuarioCalendario = idusuarioCalendario;
	}

	public long getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(long idusuario) {
		this.idusuario = idusuario;
	}

	public Calendario getCalendario() {
		return this.calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}
	
}