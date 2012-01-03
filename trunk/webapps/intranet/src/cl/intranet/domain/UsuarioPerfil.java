package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the USUARIO_PERFIL database table.
 * 
 */
@Entity
@Table(name="USUARIO_PERFIL")
public class UsuarioPerfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDUSUARIO_PERFIL", unique=true, nullable=false, precision=22)
	private long idusuarioPerfil;

	//bi-directional many-to-one association to Perfil
    @ManyToOne
	@JoinColumn(name="PERFIL_IDPERFIL")
	private Perfil perfil;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="USUARIO_IDUSUARIO")
	private Usuario usuario;

    public UsuarioPerfil() {
    }

	public long getIdusuarioPerfil() {
		return this.idusuarioPerfil;
	}

	public void setIdusuarioPerfil(long idusuarioPerfil) {
		this.idusuarioPerfil = idusuarioPerfil;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}