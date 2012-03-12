package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the invitados database table.
 * 
 */
@Entity
@Table(name="invitados")
public class Invitado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long idinvitados;

	@Column(name="correo_invitado", length=250)
	private String correoInvitado;

	@Column(name="nombre_invitado", length=250)
	private String nombreInvitado;

	//bi-directional many-to-one association to CorreoInvitado
	@OneToMany(mappedBy="invitado")
	private List<CorreoInvitado> correoInvitados;

	//bi-directional many-to-one association to InvitadoAcompanante
	@OneToMany(mappedBy="invitado")
	private List<InvitadoAcompanante> invitadoAcompanantes;

	//bi-directional many-to-one association to InvitadosMatrimonio
	@OneToMany(mappedBy="invitado")
	private List<InvitadosMatrimonio> invitadosMatrimonios;

    public Invitado() {
    }

	public Long getIdinvitados() {
		return this.idinvitados;
	}

	public void setIdinvitados(Long idinvitados) {
		this.idinvitados = idinvitados;
	}

	public String getCorreoInvitado() {
		return this.correoInvitado;
	}

	public void setCorreoInvitado(String correoInvitado) {
		this.correoInvitado = correoInvitado;
	}

	public String getNombreInvitado() {
		return this.nombreInvitado;
	}

	public void setNombreInvitado(String nombreInvitado) {
		this.nombreInvitado = nombreInvitado;
	}

	public List<CorreoInvitado> getCorreoInvitados() {
		return this.correoInvitados;
	}

	public void setCorreoInvitados(List<CorreoInvitado> correoInvitados) {
		this.correoInvitados = correoInvitados;
	}
	
	public List<InvitadoAcompanante> getInvitadoAcompanantes() {
		return this.invitadoAcompanantes;
	}

	public void setInvitadoAcompanantes(List<InvitadoAcompanante> invitadoAcompanantes) {
		this.invitadoAcompanantes = invitadoAcompanantes;
	}
	
	public List<InvitadosMatrimonio> getInvitadosMatrimonios() {
		return this.invitadosMatrimonios;
	}

	public void setInvitadosMatrimonios(List<InvitadosMatrimonio> invitadosMatrimonios) {
		this.invitadosMatrimonios = invitadosMatrimonios;
	}
	
}