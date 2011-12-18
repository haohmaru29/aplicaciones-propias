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
	private long idusuariorepo;

	//bi-directional many-to-one association to Proyecto
    @ManyToOne
	private Proyecto proyecto;

	//bi-directional many-to-one association to Repositorio
    @ManyToOne
	private Repositorio repositorio;

    public ProyectoRepositorio() {
    }

	public long getIdusuariorepo() {
		return this.idusuariorepo;
	}

	public void setIdusuariorepo(long idusuariorepo) {
		this.idusuariorepo = idusuariorepo;
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