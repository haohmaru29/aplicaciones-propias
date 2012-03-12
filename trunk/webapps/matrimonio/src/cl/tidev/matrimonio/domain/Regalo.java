package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the regalos database table.
 * 
 */
@Entity
@Table(name="regalos")
public class Regalo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long idregalos;

	@Column(name="nombre_regalo", length=250)
	private String nombreRegalo;

	@Column(name="valor_regalo")
	private Long valorRegalo;

	//bi-directional many-to-one association to MatrimonioRegalo
	@OneToMany(mappedBy="regalo")
	private List<MatrimonioRegalo> matrimonioRegalos;

    public Regalo() {
    }

	public Long getIdregalos() {
		return this.idregalos;
	}

	public void setIdregalos(Long idregalos) {
		this.idregalos = idregalos;
	}

	public String getNombreRegalo() {
		return this.nombreRegalo;
	}

	public void setNombreRegalo(String nombreRegalo) {
		this.nombreRegalo = nombreRegalo;
	}

	public Long getValorRegalo() {
		return this.valorRegalo;
	}

	public void setValorRegalo(Long valorRegalo) {
		this.valorRegalo = valorRegalo;
	}

	public List<MatrimonioRegalo> getMatrimonioRegalos() {
		return this.matrimonioRegalos;
	}

	public void setMatrimonioRegalos(List<MatrimonioRegalo> matrimonioRegalos) {
		this.matrimonioRegalos = matrimonioRegalos;
	}
	
}