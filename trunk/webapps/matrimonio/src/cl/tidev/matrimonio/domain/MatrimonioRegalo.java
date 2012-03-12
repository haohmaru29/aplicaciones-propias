package cl.tidev.matrimonio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the matrimonio_regalo database table.
 * 
 */
@Entity
@Table(name="matrimonio_regalo")
public class MatrimonioRegalo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idmatrimonio_regalo", unique=true, nullable=false)
	private Long idmatrimonioRegalo;

	//bi-directional many-to-one association to Matrimonio
    @ManyToOne
	@JoinColumn(name="idmatrimonio", nullable=false)
	private Matrimonio matrimonio;

	//bi-directional many-to-one association to Regalo
    @ManyToOne
	@JoinColumn(name="idregalos", nullable=false)
	private Regalo regalo;

    public MatrimonioRegalo() {
    }

	public Long getIdmatrimonioRegalo() {
		return this.idmatrimonioRegalo;
	}

	public void setIdmatrimonioRegalo(Long idmatrimonioRegalo) {
		this.idmatrimonioRegalo = idmatrimonioRegalo;
	}

	public Matrimonio getMatrimonio() {
		return this.matrimonio;
	}

	public void setMatrimonio(Matrimonio matrimonio) {
		this.matrimonio = matrimonio;
	}
	
	public Regalo getRegalo() {
		return this.regalo;
	}

	public void setRegalo(Regalo regalo) {
		this.regalo = regalo;
	}
	
}