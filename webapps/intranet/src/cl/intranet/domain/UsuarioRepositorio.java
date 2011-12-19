package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the USUARIO_REPOSITORIO database table.
 * 
 */
@Entity
@Table(name="USUARIO_REPOSITORIO")
public class UsuarioRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDUSUARIO_REPOSITORIO")
	private long idusuarioRepositorio;

	//bi-directional many-to-one association to Repositorio
    @ManyToOne
	private Repositorio repositorio;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	private Usuario usuario;

    public UsuarioRepositorio() {
    }

	public long getIdusuarioRepositorio() {
		return this.idusuarioRepositorio;
	}

	public void setIdusuarioRepositorio(long idusuarioRepositorio) {
		this.idusuarioRepositorio = idusuarioRepositorio;
	}

	public Repositorio getRepositorio() {
		return this.repositorio;
	}

	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}