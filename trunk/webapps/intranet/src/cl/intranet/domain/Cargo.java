package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CARGO database table.
 * 
 */
@Entity
@Table(name="CARGO")
public class Cargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=22)
	private long idcargo;

	@Column(name="NOMBRE_CARGO", length=250)
	private String nombreCargo;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="cargo")
	private List<Usuario> usuarios;

    public Cargo() {
    }

	public long getIdcargo() {
		return this.idcargo;
	}

	public void setIdcargo(long idcargo) {
		this.idcargo = idcargo;
	}

	public String getNombreCargo() {
		return this.nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}