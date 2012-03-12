package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the correo_invitados database table.
 * 
 */
@Entity
@Table(name="correo_invitados")
public class CorreoInvitado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idcorreo_invitados", unique=true, nullable=false)
	private Long idcorreoInvitados;

	//bi-directional many-to-one association to CorreoInvitacion
    @ManyToOne
	@JoinColumn(name="idcorreo_invitacion", nullable=false)
	private CorreoInvitacion correoInvitacion;

	//bi-directional many-to-one association to Invitado
    @ManyToOne
	@JoinColumn(name="idinvitados", nullable=false)
	private Invitado invitado;

    public CorreoInvitado() {
    }

	public Long getIdcorreoInvitados() {
		return this.idcorreoInvitados;
	}

	public void setIdcorreoInvitados(Long idcorreoInvitados) {
		this.idcorreoInvitados = idcorreoInvitados;
	}

	public CorreoInvitacion getCorreoInvitacion() {
		return this.correoInvitacion;
	}

	public void setCorreoInvitacion(CorreoInvitacion correoInvitacion) {
		this.correoInvitacion = correoInvitacion;
	}
	
	public Invitado getInvitado() {
		return this.invitado;
	}

	public void setInvitado(Invitado invitado) {
		this.invitado = invitado;
	}
	
}