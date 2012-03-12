package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long idusuario;

	@Column(name="clave_usuario", length=250)
	private String claveUsuario;

	@Column(name="correo_usuario", length=250)
	private String correoUsuario;

	@Column(name="nombre_usuario", nullable=false, length=250)
	private String nombreUsuario;

	//bi-directional many-to-one association to Perfil
    @ManyToOne
	@JoinColumn(name="idperfil", nullable=false)
	private Perfil perfil;

	//bi-directional many-to-one association to UsuarioMatrimonio
	@OneToMany(mappedBy="usuario")
	private List<UsuarioMatrimonio> usuarioMatrimonios;

    public Usuario() {
    }

	public Long getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(Long idusuario) {
		this.idusuario = idusuario;
	}

	public String getClaveUsuario() {
		return this.claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public String getCorreoUsuario() {
		return this.correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public List<UsuarioMatrimonio> getUsuarioMatrimonios() {
		return this.usuarioMatrimonios;
	}

	public void setUsuarioMatrimonios(List<UsuarioMatrimonio> usuarioMatrimonios) {
		this.usuarioMatrimonios = usuarioMatrimonios;
	}
	
}