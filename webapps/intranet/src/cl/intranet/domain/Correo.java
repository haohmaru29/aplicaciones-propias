package cl.intranet.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CORREOS database table.
 * 
 */
@Entity
@Table(name="CORREOS")
public class Correo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, precision=22)
	private long idcorreos;

	@Column(name="\"BODY\"", length=2000)
	private String body;

	@Column(name="\"FROM\"", length=200)
	private String from;

    @Temporal( TemporalType.DATE)
	@Column(name="MESSAGE_DATE")
	private Date messageDate;

	@Column(name="MESSAGE_NUMBER", precision=22)
	private BigDecimal messageNumber;

	@Column(length=2000)
	private String subject;

	//bi-directional many-to-one association to UsuarioServidorCorreo
    @ManyToOne
	@JoinColumn(name="USER_SERVIDOR_CORREO")
	private UsuarioServidorCorreo usuarioServidorCorreo;

    public Correo() {
    }

	public long getIdcorreos() {
		return this.idcorreos;
	}

	public void setIdcorreos(long idcorreos) {
		this.idcorreos = idcorreos;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getMessageDate() {
		return this.messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public BigDecimal getMessageNumber() {
		return this.messageNumber;
	}

	public void setMessageNumber(BigDecimal messageNumber) {
		this.messageNumber = messageNumber;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public UsuarioServidorCorreo getUsuarioServidorCorreo() {
		return this.usuarioServidorCorreo;
	}

	public void setUsuarioServidorCorreo(UsuarioServidorCorreo usuarioServidorCorreo) {
		this.usuarioServidorCorreo = usuarioServidorCorreo;
	}
	
}