package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USUARIO_SERVIDOR_CORREO database table.
 * 
 */
@Entity
@Table(name="USUARIO_SERVIDOR_CORREO")
@SequenceGenerator(name="USUARIO_SERVIDOR_CORREO_SEQ", sequenceName="USUARIO_SERVIDOR_CORREO_SEQ")
public class UsuarioServidorCorreo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_SERVIDOR_CORREO_SEQ")
	@Column(name="IDUSUARIO_SERVIDOR_CORREO", unique=true, nullable=false, precision=22)
	private long idusuarioServidorCorreo;

	@Column(name="CLAVE_CORREO", length=300)
	private String claveCorreo;

	@Column(name="USUARIO_CORREO", length=300)
	private String usuarioCorreo;

	//bi-directional many-to-one association to Correo
	@OneToMany(mappedBy="usuarioServidorCorreo")
	private List<Correo> correos;

	//bi-directional many-to-one association to ServidorCorreo
    @ManyToOne
	@JoinColumn(name="ID_SERVIDOR_CORREO")
	private ServidorCorreo servidorCorreo;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="USUARIO_IDUSUARIO")
	private Usuario usuario;

    public UsuarioServidorCorreo() {
    }

	public long getIdusuarioServidorCorreo() {
		return this.idusuarioServidorCorreo;
	}

	public void setIdusuarioServidorCorreo(long idusuarioServidorCorreo) {
		this.idusuarioServidorCorreo = idusuarioServidorCorreo;
	}

	public String getClaveCorreo() {
		return this.claveCorreo;
	}

	public void setClaveCorreo(String claveCorreo) {
		this.claveCorreo = claveCorreo;
	}

	public String getUsuarioCorreo() {
		return this.usuarioCorreo;
	}

	public void setUsuarioCorreo(String usuarioCorreo) {
		this.usuarioCorreo = usuarioCorreo;
	}

	public List<Correo> getCorreos() {
		return this.correos;
	}

	public void setCorreos(List<Correo> correos) {
		this.correos = correos;
	}
	
	public ServidorCorreo getServidorCorreo() {
		return this.servidorCorreo;
	}

	public void setServidorCorreo(ServidorCorreo servidorCorreo) {
		this.servidorCorreo = servidorCorreo;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}