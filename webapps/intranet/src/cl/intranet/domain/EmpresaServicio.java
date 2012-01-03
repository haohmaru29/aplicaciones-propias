package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the EMPRESA_SERVICIO database table.
 * 
 */
@Entity
@Table(name="EMPRESA_SERVICIO")
public class EmpresaServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDEMPRESA_SERVICIO", unique=true, nullable=false, precision=22)
	private long idempresaServicio;

	@Column(length=500)
	private String direccion;

	@Column(length=500)
	private String nombre;

	//bi-directional many-to-one association to DatosEmpresaServicio
	@OneToMany(mappedBy="empresaServicio")
	private List<DatosEmpresaServicio> datosEmpresaServicios;

	//bi-directional many-to-one association to Proyecto
	@OneToMany(mappedBy="empresaServicio")
	private List<Proyecto> proyectos;

    public EmpresaServicio() {
    }

	public long getIdempresaServicio() {
		return this.idempresaServicio;
	}

	public void setIdempresaServicio(long idempresaServicio) {
		this.idempresaServicio = idempresaServicio;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DatosEmpresaServicio> getDatosEmpresaServicios() {
		return this.datosEmpresaServicios;
	}

	public void setDatosEmpresaServicios(List<DatosEmpresaServicio> datosEmpresaServicios) {
		this.datosEmpresaServicios = datosEmpresaServicios;
	}
	
	public List<Proyecto> getProyectos() {
		return this.proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}
	
}