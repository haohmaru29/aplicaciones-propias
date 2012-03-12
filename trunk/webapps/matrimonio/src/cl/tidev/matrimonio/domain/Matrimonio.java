package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the matrimonio database table.
 * 
 */
@Entity
@Table(name="matrimonio")
public class Matrimonio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long idmatrimonio;

    @Temporal( TemporalType.DATE)
	@Column(name="fecha_matrimonio", nullable=false)
	private Date fechaMatrimonio;

	@Column(name="lugar_matrimonio", length=500)
	private String lugarMatrimonio;

	//bi-directional many-to-one association to InvitadosMatrimonio
	@OneToMany(mappedBy="matrimonio")
	private List<InvitadosMatrimonio> invitadosMatrimonios;

	//bi-directional many-to-one association to MatrimonioRegalo
	@OneToMany(mappedBy="matrimonio")
	private List<MatrimonioRegalo> matrimonioRegalos;

	//bi-directional many-to-one association to UsuarioMatrimonio
	@OneToMany(mappedBy="matrimonio")
	private List<UsuarioMatrimonio> usuarioMatrimonios;

    public Matrimonio() {
    }

	public Long getIdmatrimonio() {
		return this.idmatrimonio;
	}

	public void setIdmatrimonio(Long idmatrimonio) {
		this.idmatrimonio = idmatrimonio;
	}

	public Date getFechaMatrimonio() {
		return this.fechaMatrimonio;
	}

	public void setFechaMatrimonio(Date fechaMatrimonio) {
		this.fechaMatrimonio = fechaMatrimonio;
	}

	public String getLugarMatrimonio() {
		return this.lugarMatrimonio;
	}

	public void setLugarMatrimonio(String lugarMatrimonio) {
		this.lugarMatrimonio = lugarMatrimonio;
	}

	public List<InvitadosMatrimonio> getInvitadosMatrimonios() {
		return this.invitadosMatrimonios;
	}

	public void setInvitadosMatrimonios(List<InvitadosMatrimonio> invitadosMatrimonios) {
		this.invitadosMatrimonios = invitadosMatrimonios;
	}
	
	public List<MatrimonioRegalo> getMatrimonioRegalos() {
		return this.matrimonioRegalos;
	}

	public void setMatrimonioRegalos(List<MatrimonioRegalo> matrimonioRegalos) {
		this.matrimonioRegalos = matrimonioRegalos;
	}
	
	public List<UsuarioMatrimonio> getUsuarioMatrimonios() {
		return this.usuarioMatrimonios;
	}

	public void setUsuarioMatrimonios(List<UsuarioMatrimonio> usuarioMatrimonios) {
		this.usuarioMatrimonios = usuarioMatrimonios;
	}
	
}