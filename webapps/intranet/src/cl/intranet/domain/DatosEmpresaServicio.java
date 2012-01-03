package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the DATOS_EMPRESA_SERVICIO database table.
 * 
 */
@Entity
@Table(name="DATOS_EMPRESA_SERVICIO")
public class DatosEmpresaServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDDATOS_EMPRESA_SERVICIO", unique=true, nullable=false, precision=22)
	private long iddatosEmpresaServicio;

	@Column(precision=22)
	private BigDecimal celular;

	@Column(name="NOMBRE_CONTACTO", length=500)
	private String nombreContacto;

	@Column(precision=22)
	private BigDecimal telefono;

	//bi-directional many-to-one association to EmpresaServicio
    @ManyToOne
	@JoinColumn(name="EMPRESA_SERVICIO_IDEMPRESA_SER")
	private EmpresaServicio empresaServicio;

    public DatosEmpresaServicio() {
    }

	public long getIddatosEmpresaServicio() {
		return this.iddatosEmpresaServicio;
	}

	public void setIddatosEmpresaServicio(long iddatosEmpresaServicio) {
		this.iddatosEmpresaServicio = iddatosEmpresaServicio;
	}

	public BigDecimal getCelular() {
		return this.celular;
	}

	public void setCelular(BigDecimal celular) {
		this.celular = celular;
	}

	public String getNombreContacto() {
		return this.nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public BigDecimal getTelefono() {
		return this.telefono;
	}

	public void setTelefono(BigDecimal telefono) {
		this.telefono = telefono;
	}

	public EmpresaServicio getEmpresaServicio() {
		return this.empresaServicio;
	}

	public void setEmpresaServicio(EmpresaServicio empresaServicio) {
		this.empresaServicio = empresaServicio;
	}
	
}