package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the correo_invitacion database table.
 * 
 */
@Entity
@Table(name="correo_invitacion")
public class CorreoInvitacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idcorreo_invitacion", unique=true, nullable=false)
	private Long idcorreoInvitacion;

	@Column(name="estado_correoinvitacion")
	private Integer estadoCorreoinvitacion;

    @Temporal( TemporalType.DATE)
	@Column(name="fecha_correo_invitacion")
	private Date fechaCorreoInvitacion;

	//bi-directional many-to-one association to CorreoInvitado
	@OneToMany(mappedBy="correoInvitacion")
	private List<CorreoInvitado> correoInvitados;

    public CorreoInvitacion() {
    }

	public Long getIdcorreoInvitacion() {
		return this.idcorreoInvitacion;
	}

	public void setIdcorreoInvitacion(Long idcorreoInvitacion) {
		this.idcorreoInvitacion = idcorreoInvitacion;
	}

	public Integer getEstadoCorreoinvitacion() {
		return this.estadoCorreoinvitacion;
	}

	public void setEstadoCorreoinvitacion(Integer estadoCorreoinvitacion) {
		this.estadoCorreoinvitacion = estadoCorreoinvitacion;
	}

	public Date getFechaCorreoInvitacion() {
		return this.fechaCorreoInvitacion;
	}

	public void setFechaCorreoInvitacion(Date fechaCorreoInvitacion) {
		this.fechaCorreoInvitacion = fechaCorreoInvitacion;
	}

	public List<CorreoInvitado> getCorreoInvitados() {
		return this.correoInvitados;
	}

	public void setCorreoInvitados(List<CorreoInvitado> correoInvitados) {
		this.correoInvitados = correoInvitados;
	}
	
}