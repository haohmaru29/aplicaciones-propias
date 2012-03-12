package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuario_matrimonio database table.
 * 
 */
@Entity
@Table(name="usuario_matrimonio")
public class UsuarioMatrimonio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idusuario_matrimonio", unique=true, nullable=false)
	private Long idusuarioMatrimonio;

	//bi-directional many-to-one association to Matrimonio
    @ManyToOne
	@JoinColumn(name="idmatrimonio", nullable=false)
	private Matrimonio matrimonio;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="idusuario", nullable=false)
	private Usuario usuario;

    public UsuarioMatrimonio() {
    }

	public Long getIdusuarioMatrimonio() {
		return this.idusuarioMatrimonio;
	}

	public void setIdusuarioMatrimonio(Long idusuarioMatrimonio) {
		this.idusuarioMatrimonio = idusuarioMatrimonio;
	}

	public Matrimonio getMatrimonio() {
		return this.matrimonio;
	}

	public void setMatrimonio(Matrimonio matrimonio) {
		this.matrimonio = matrimonio;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}