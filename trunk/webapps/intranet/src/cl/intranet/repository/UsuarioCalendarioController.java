package cl.intranet.repository;

import java.util.List;

import javax.persistence.Query;

import cl.intranet.domain.UsuarioCalendario;
import cl.tidev.commons.mvc.repository.jpa.AbstractJpaController;

public class UsuarioCalendarioController extends AbstractJpaController<UsuarioCalendario>{
	
	@SuppressWarnings("unchecked")
	public List<UsuarioCalendario> findCalendarByUser(Long idUser) {
		Query q = jpaConnection.getEntityManager().createQuery("SELECT c FROM UsuarioCalendario c WHERE c.idusuario=:idUser "); 
		q.setParameter("idUser", idUser);
		
		return q.getResultList();
	}
	
}
