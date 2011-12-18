package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the USUARIO_PROYECTO database table.
 * 
 */
@Entity
@Table(name="USUARIO_PROYECTO")
public class UsuarioProyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDUSUARIO_PROYECTO")
	private long idusuarioProyecto;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_INCORPORACION_USUARIO")
	private Date fechaIncorporacionUsuario;

	//bi-directional many-to-one association to Proyecto
    @ManyToOne
	private Proyecto proyecto;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	private Usuario usuario;

    public UsuarioProyecto() {
    }

	public long getIdusuarioProyecto() {
		return this.idusuarioProyecto;
	}

	public void setIdusuarioProyecto(long idusuarioProyecto) {
		this.idusuarioProyecto = idusuarioProyecto;
	}

	public Date getFechaIncorporacionUsuario() {
		return this.fechaIncorporacionUsuario;
	}

	public void setFechaIncorporacionUsuario(Date fechaIncorporacionUsuario) {
		this.fechaIncorporacionUsuario = fechaIncorporacionUsuario;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}