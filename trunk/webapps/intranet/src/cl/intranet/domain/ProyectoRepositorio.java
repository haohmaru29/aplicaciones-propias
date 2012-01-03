package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PROYECTO_REPOSITORIO database table.
 * 
 */
@Entity
@Table(name="PROYECTO_REPOSITORIO")
public class ProyectoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDPROYECTO_REPOSITORIO", unique=true, nullable=false, precision=22)
	private long idproyectoRepositorio;

	//bi-directional many-to-one association to Proyecto
    @ManyToOne
	@JoinColumn(name="PROYECTO_IDPROYECTO")
	private Proyecto proyecto;

	//bi-directional many-to-one association to Repositorio
    @ManyToOne
	@JoinColumn(name="REPOSITORIO_IDREPOSITORIO")
	private Repositorio repositorio;

    public ProyectoRepositorio() {
    }

	public long getIdproyectoRepositorio() {
		return this.idproyectoRepositorio;
	}

	public void setIdproyectoRepositorio(long idproyectoRepositorio) {
		this.idproyectoRepositorio = idproyectoRepositorio;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public Repositorio getRepositorio() {
		return this.repositorio;
	}

	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
	}
	
}