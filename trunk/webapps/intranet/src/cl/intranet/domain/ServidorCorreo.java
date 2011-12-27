package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the SERVIDOR_CORREO database table.
 * 
 */
@Entity
@Table(name="SERVIDOR_CORREO")
public class ServidorCorreo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_SERVIDOR_CORREO")
	private long idServidorCorreo;

	private String ip;

	@Column(name="NOMBRE_SERVICIO")
	private String nombreServicio;

	private BigDecimal puerto;

	//bi-directional many-to-one association to UsuarioServidorCorreo
	@OneToMany(mappedBy="servidorCorreo")
	private List<UsuarioServidorCorreo> usuarioServidorCorreos;

    public ServidorCorreo() {
    }

	public long getIdServidorCorreo() {
		return this.idServidorCorreo;
	}

	public void setIdServidorCorreo(long idServidorCorreo) {
		this.idServidorCorreo = idServidorCorreo;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNombreServicio() {
		return this.nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public BigDecimal getPuerto() {
		return this.puerto;
	}

	public void setPuerto(BigDecimal puerto) {
		this.puerto = puerto;
	}

	public List<UsuarioServidorCorreo> getUsuarioServidorCorreos() {
		return this.usuarioServidorCorreos;
	}

	public void setUsuarioServidorCorreos(List<UsuarioServidorCorreo> usuarioServidorCorreos) {
		this.usuarioServidorCorreos = usuarioServidorCorreos;
	}
	
}