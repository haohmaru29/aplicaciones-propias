package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ICONOS database table.
 * 
 */
@Entity
@Table(name="ICONOS")
public class Icono implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ICONOS", unique=true, nullable=false, precision=22)
	private long idIconos;

	@Column(name="NOMBRE_ICONOS", length=50)
	private String nombreIconos;

	//bi-directional many-to-one association to ServidorCorreo
	@OneToMany(mappedBy="icono")
	private List<ServidorCorreo> servidorCorreos;

    public Icono() {
    }

	public long getIdIconos() {
		return this.idIconos;
	}

	public void setIdIconos(long idIconos) {
		this.idIconos = idIconos;
	}

	public String getNombreIconos() {
		return this.nombreIconos;
	}

	public void setNombreIconos(String nombreIconos) {
		this.nombreIconos = nombreIconos;
	}

	public List<ServidorCorreo> getServidorCorreos() {
		return this.servidorCorreos;
	}

	public void setServidorCorreos(List<ServidorCorreo> servidorCorreos) {
		this.servidorCorreos = servidorCorreos;
	}
	
}