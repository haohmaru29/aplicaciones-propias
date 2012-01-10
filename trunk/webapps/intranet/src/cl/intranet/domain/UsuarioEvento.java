package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the USUARIO_EVENTO database table.
 * 
 */
@Entity
@Table(name="USUARIO_EVENTO")
public class UsuarioEvento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDUSUARIO_EVENTO", unique=true, nullable=false, precision=22)
	private long idusuarioEvento;

	@Column(precision=22)
	private long idusuario;

	//bi-directional many-to-one association to Evento
    @ManyToOne
	@JoinColumn(name="IDEVENTO")
	private Evento evento;

    public UsuarioEvento() {
    }

	public long getIdusuarioEvento() {
		return this.idusuarioEvento;
	}

	public void setIdusuarioEvento(long idusuarioEvento) {
		this.idusuarioEvento = idusuarioEvento;
	}

	public long getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(long idusuario) {
		this.idusuario = idusuario;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
}