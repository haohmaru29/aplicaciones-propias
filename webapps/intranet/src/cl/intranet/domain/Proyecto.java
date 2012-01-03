package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PROYECTO database table.
 * 
 */
@Entity
@Table(name="PROYECTO")
public class Proyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, precision=22)
	private long idproyecto;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_TERMINO")
	private Date fechaTermino;

	@Column(precision=22)
	private BigDecimal margen;

	@Column(name="NOMBRE_PROYECTO", length=200)
	private String nombreProyecto;

	//bi-directional many-to-one association to EmpresaServicio
    @ManyToOne
	@JoinColumn(name="EMPRESA_SERVICIO_IDEMPRESA_SER")
	private EmpresaServicio empresaServicio;

	//bi-directional many-to-one association to ProyectoRepositorio
	@OneToMany(mappedBy="proyecto")
	private List<ProyectoRepositorio> proyectoRepositorios;

	//bi-directional many-to-one association to UsuarioProyecto
	@OneToMany(mappedBy="proyecto")
	private List<UsuarioProyecto> usuarioProyectos;

    public Proyecto() {
    }

	public long getIdproyecto() {
		return this.idproyecto;
	}

	public void setIdproyecto(long idproyecto) {
		this.idproyecto = idproyecto;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public BigDecimal getMargen() {
		return this.margen;
	}

	public void setMargen(BigDecimal margen) {
		this.margen = margen;
	}

	public String getNombreProyecto() {
		return this.nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public EmpresaServicio getEmpresaServicio() {
		return this.empresaServicio;
	}

	public void setEmpresaServicio(EmpresaServicio empresaServicio) {
		this.empresaServicio = empresaServicio;
	}
	
	public List<ProyectoRepositorio> getProyectoRepositorios() {
		return this.proyectoRepositorios;
	}

	public void setProyectoRepositorios(List<ProyectoRepositorio> proyectoRepositorios) {
		this.proyectoRepositorios = proyectoRepositorios;
	}
	
	public List<UsuarioProyecto> getUsuarioProyectos() {
		return this.usuarioProyectos;
	}

	public void setUsuarioProyectos(List<UsuarioProyecto> usuarioProyectos) {
		this.usuarioProyectos = usuarioProyectos;
	}
	
}