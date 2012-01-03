package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the USUARIO_GRUPO database table.
 * 
 */
@Entity
@Table(name="USUARIO_GRUPO")
public class UsuarioGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDUSUARIO_GRUPO", unique=true, nullable=false, precision=22)
	private long idusuarioGrupo;

	//bi-directional many-to-one association to Grupo
    @ManyToOne
	@JoinColumn(name="GRUPO_IDGRUPO")
	private Grupo grupo;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="USUARIO_IDUSUARIO")
	private Usuario usuario;

    public UsuarioGrupo() {
    }

	public long getIdusuarioGrupo() {
		return this.idusuarioGrupo;
	}

	public void setIdusuarioGrupo(long idusuarioGrupo) {
		this.idusuarioGrupo = idusuarioGrupo;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}