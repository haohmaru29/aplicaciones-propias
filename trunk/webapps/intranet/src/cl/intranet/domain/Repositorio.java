package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the REPOSITORIOS database table.
 * 
 */
@Entity
@Table(name="REPOSITORIOS")
public class Repositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idrepositorio;

	@Column(name="NOMBRE_REPOSITORIO")
	private String nombreRepositorio;

	//bi-directional many-to-one association to ProyectoRepositorio
	@OneToMany(mappedBy="repositorio")
	private List<ProyectoRepositorio> proyectoRepositorios;

	//bi-directional many-to-one association to UsuarioRepositorio
	@OneToMany(mappedBy="repositorio")
	private List<UsuarioRepositorio> usuarioRepositorios;

    public Repositorio() {
    }

	public long getIdrepositorio() {
		return this.idrepositorio;
	}

	public void setIdrepositorio(long idrepositorio) {
		this.idrepositorio = idrepositorio;
	}

	public String getNombreRepositorio() {
		return this.nombreRepositorio;
	}

	public void setNombreRepositorio(String nombreRepositorio) {
		this.nombreRepositorio = nombreRepositorio;
	}

	public List<ProyectoRepositorio> getProyectoRepositorios() {
		return this.proyectoRepositorios;
	}

	public void setProyectoRepositorios(List<ProyectoRepositorio> proyectoRepositorios) {
		this.proyectoRepositorios = proyectoRepositorios;
	}
	
	public List<UsuarioRepositorio> getUsuarioRepositorios() {
		return this.usuarioRepositorios;
	}

	public void setUsuarioRepositorios(List<UsuarioRepositorio> usuarioRepositorios) {
		this.usuarioRepositorios = usuarioRepositorios;
	}
	
}