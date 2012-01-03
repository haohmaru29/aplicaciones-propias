package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


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
	@Column(name="ID_SERVIDOR_CORREO", unique=true, nullable=false, precision=22)
	private long idServidorCorreo;

	@Column(length=200)
	private String ip;

	@Column(name="NOMBRE_SERVICIO", length=200)
	private String nombreServicio;

	@Column(name="PORT_POP", precision=22)
	private BigDecimal portPop;

	@Column(name="PORT_SMTP", length=20)
	private String portSmtp;

	@Column(length=200)
	private String smtp;

	//bi-directional many-to-one association to Icono
    @ManyToOne
	@JoinColumn(name="ICONO_ID_ICONOS")
	private Icono icono;

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

	public BigDecimal getPortPop() {
		return this.portPop;
	}

	public void setPortPop(BigDecimal portPop) {
		this.portPop = portPop;
	}

	public String getPortSmtp() {
		return this.portSmtp;
	}

	public void setPortSmtp(String portSmtp) {
		this.portSmtp = portSmtp;
	}

	public String getSmtp() {
		return this.smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public Icono getIcono() {
		return this.icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}
	
}