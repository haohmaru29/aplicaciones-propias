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
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, precision=22)
	private long idusuario;

	@Column(length=200)
	private String clave;

	@Column(length=500)
	private String correo;

	@Column(length=200)
	private String nombre;

	//bi-directional many-to-one association to Cargo
    @ManyToOne
	@JoinColumn(name="CARGO_IDCARGO")
	private Cargo cargo;

	//bi-directional many-to-one association to Empresa
    @ManyToOne
	@JoinColumn(name="EMPRESA_IDEMPRESA")
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

	//bi-directional many-to-one association to UsuarioRepositorio
	@OneToMany(mappedBy="usuario")
	private List<UsuarioRepositorio> usuarioRepositorios;

	//bi-directional many-to-one association to UsuarioServidorCorreo
	@OneToMany(mappedBy="usuario")
	private List<UsuarioServidorCorreo> usuarioServidorCorreos;

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

	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
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
	
	public List<UsuarioRepositorio> getUsuarioRepositorios() {
		return this.usuarioRepositorios;
	}

	public void setUsuarioRepositorios(List<UsuarioRepositorio> usuarioRepositorios) {
		this.usuarioRepositorios = usuarioRepositorios;
	}
	
	public List<UsuarioServidorCorreo> getUsuarioServidorCorreos() {
		return this.usuarioServidorCorreos;
	}

	public void setUsuarioServidorCorreos(List<UsuarioServidorCorreo> usuarioServidorCorreos) {
		this.usuarioServidorCorreos = usuarioServidorCorreos;
	}
	
}