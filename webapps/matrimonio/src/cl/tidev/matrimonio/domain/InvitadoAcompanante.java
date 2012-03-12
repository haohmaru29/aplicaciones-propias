package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the invitado_acompanante database table.
 * 
 */
@Entity
@Table(name="invitado_acompanante")
public class InvitadoAcompanante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idinvitado_acompanante", unique=true, nullable=false)
	private Long idinvitadoAcompanante;

	//bi-directional many-to-one association to Acompanate
    @ManyToOne
	@JoinColumn(name="idacompanante", nullable=false)
	private Acompanate acompanate;

	//bi-directional many-to-one association to Invitado
    @ManyToOne
	@JoinColumn(name="idinvitados", nullable=false)
	private Invitado invitado;

    public InvitadoAcompanante() {
    }

	public Long getIdinvitadoAcompanante() {
		return this.idinvitadoAcompanante;
	}

	public void setIdinvitadoAcompanante(Long idinvitadoAcompanante) {
		this.idinvitadoAcompanante = idinvitadoAcompanante;
	}

	public Acompanate getAcompanate() {
		return this.acompanate;
	}

	public void setAcompanate(Acompanate acompanate) {
		this.acompanate = acompanate;
	}
	
	public Invitado getInvitado() {
		return this.invitado;
	}

	public void setInvitado(Invitado invitado) {
		this.invitado = invitado;
	}
	
}