package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the invitados_matrimonio database table.
 * 
 */
@Entity
@Table(name="invitados_matrimonio")
public class InvitadosMatrimonio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idinvitados_matrimonio", unique=true, nullable=false)
	private Long idinvitadosMatrimonio;

	//bi-directional many-to-one association to Invitado
    @ManyToOne
	@JoinColumn(name="idinvitados", nullable=false)
	private Invitado invitado;

	//bi-directional many-to-one association to Matrimonio
    @ManyToOne
	@JoinColumn(name="idmatrimonio", nullable=false)
	private Matrimonio matrimonio;

    public InvitadosMatrimonio() {
    }

	public Long getIdinvitadosMatrimonio() {
		return this.idinvitadosMatrimonio;
	}

	public void setIdinvitadosMatrimonio(Long idinvitadosMatrimonio) {
		this.idinvitadosMatrimonio = idinvitadosMatrimonio;
	}

	public Invitado getInvitado() {
		return this.invitado;
	}

	public void setInvitado(Invitado invitado) {
		this.invitado = invitado;
	}
	
	public Matrimonio getMatrimonio() {
		return this.matrimonio;
	}

	public void setMatrimonio(Matrimonio matrimonio) {
		this.matrimonio = matrimonio;
	}
	
}