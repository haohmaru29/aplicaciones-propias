package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PERFIL database table.
 * 
 */
@Entity
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idperfil;

	private String perfil;

	//bi-directional many-to-one association to UsuarioPerfil
	@OneToMany(mappedBy="perfil")
	private List<UsuarioPerfil> usuarioPerfils;

    public Perfil() {
    }

	public long getIdperfil() {
		return this.idperfil;
	}

	public void setIdperfil(long idperfil) {
		this.idperfil = idperfil;
	}

	public String getPerfil() {
		return this.perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public List<UsuarioPerfil> getUsuarioPerfils() {
		return this.usuarioPerfils;
	}

	public void setUsuarioPerfils(List<UsuarioPerfil> usuarioPerfils) {
		this.usuarioPerfils = usuarioPerfils;
	}
	
}