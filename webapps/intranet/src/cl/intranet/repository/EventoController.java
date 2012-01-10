package cl.intranet.repository;

import java.util.List;

import javax.persistence.Query;

import cl.intranet.domain.Evento;
import cl.tidev.commons.mvc.repository.jpa.AbstractJpaController;

public class EventoController extends AbstractJpaController<Evento> {
	
	public List<?> findNextEventsByUser(Long idUser) {
		Query q = jpaConnection.getEntityManager().createQuery("SELECT e FROM UsuarioEvento e WHERE e.idusuario=:idUser ORDER BY e.evento.fechaInicio ASC");
		q.setParameter("idUser", idUser);
		
		return q.getResultList();
	}

}
