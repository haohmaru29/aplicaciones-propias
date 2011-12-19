package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the EMPRESA database table.
 * 
 */
@Entity
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idempresa;

	private String empresa;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="empresa")
	private List<Usuario> usuarios;

    public Empresa() {
    }

	public long getIdempresa() {
		return this.idempresa;
	}

	public void setIdempresa(long idempresa) {
		this.idempresa = idempresa;
	}

	public String getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}