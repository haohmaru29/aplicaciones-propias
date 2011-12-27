package cl.intranet.repository;

import javax.persistence.Query;

import cl.intranet.domain.UsuarioServidorCorreo;
import cl.tidev.commons.mvc.repository.jpa.AbstractJpaController;

public class UsuarioServidorCorreoController extends AbstractJpaController<UsuarioServidorCorreo>{

	public UsuarioServidorCorreo findByUser(Long idUsuario) {
		UsuarioServidorCorreo usuarioServidor = null;
		Query q = jpaConnection.getEntityManager().createQuery("SELECT c FROM UsuarioServidorCorreo c WHERE c.usuario.id=?");
		q.setParameter(1, idUsuario);
		usuarioServidor = (UsuarioServidorCorreo) q.getSingleResult();
		
		return usuarioServidor;
	}
}
