package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the GRUPOS database table.
 * 
 */
@Entity
@Table(name="GRUPOS")
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idgrupo;

	private String nombre;

	//bi-directional many-to-one association to UsuarioGrupo
	@OneToMany(mappedBy="grupo")
	private List<UsuarioGrupo> usuarioGrupos;

    public Grupo() {
    }

	public long getIdgrupo() {
		return this.idgrupo;
	}

	public void setIdgrupo(long idgrupo) {
		this.idgrupo = idgrupo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<UsuarioGrupo> getUsuarioGrupos() {
		return this.usuarioGrupos;
	}

	public void setUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}
	
}