package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USUARIOS database table.
 * 
 */
@Entity
@Table(name="USUARIOS")
@NamedQueries({
	@NamedQuery(name = "Usuarios.login", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.clave=:clave")
})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idusuario;

	private String clave;

	private String correo;

	private String nombre;

	//bi-directional many-to-one association to Empresa
    @ManyToOne
	private Empresa empresa;

	//bi-directional many-to-one association to UsuarioGrupo
	@OneToMany(mappedBy="usuario")
	private List<UsuarioGrupo> usuarioGrupos;

	//bi-directional many-to-one association to UsuarioPerfil
	@OneToMany(mappedBy="usuario")
	private List<UsuarioPerfil> usuarioPerfils;

	//bi-directional many-to-one association to UsuarioProyecto
	@OneToMany(mappedBy="usuario")
	private List<UsuarioProyecto> usuarioProyectos;

	//bi-directional many-to-one association to Cargo
    @ManyToOne
	private Cargo cargo;

    public Usuario() {
    }

	public long getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(long idusuario) {
		this.idusuario = idusuario;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public List<UsuarioGrupo> getUsuarioGrupos() {
		return this.usuarioGrupos;
	}

	public void setUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}
	
	public List<UsuarioPerfil> getUsuarioPerfils() {
		return this.usuarioPerfils;
	}

	public void setUsuarioPerfils(List<UsuarioPerfil> usuarioPerfils) {
		this.usuarioPerfils = usuarioPerfils;
	}
	
	public List<UsuarioProyecto> getUsuarioProyectos() {
		return this.usuarioProyectos;
	}

	public void setUsuarioProyectos(List<UsuarioProyecto> usuarioProyectos) {
		this.usuarioProyectos = usuarioProyectos;
	}
	
	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
}