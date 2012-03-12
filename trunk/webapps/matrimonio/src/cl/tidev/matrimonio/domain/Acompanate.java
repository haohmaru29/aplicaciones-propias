package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the acompanate database table.
 * 
 */
@Entity
@Table(name="acompanate")
public class Acompanate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long idacompanante;

	@Column(name="nombre_acompanante", length=250)
	private String nombreAcompanante;

	//bi-directional many-to-one association to InvitadoAcompanante
	@OneToMany(mappedBy="acompanate")
	private List<InvitadoAcompanante> invitadoAcompanantes;

    public Acompanate() {
    }

	public Long getIdacompanante() {
		return this.idacompanante;
	}

	public void setIdacompanante(Long idacompanante) {
		this.idacompanante = idacompanante;
	}

	public String getNombreAcompanante() {
		return this.nombreAcompanante;
	}

	public void setNombreAcompanante(String nombreAcompanante) {
		this.nombreAcompanante = nombreAcompanante;
	}

	public List<InvitadoAcompanante> getInvitadoAcompanantes() {
		return this.invitadoAcompanantes;
	}

	public void setInvitadoAcompanantes(List<InvitadoAcompanante> invitadoAcompanantes) {
		this.invitadoAcompanantes = invitadoAcompanantes;
	}
	
}