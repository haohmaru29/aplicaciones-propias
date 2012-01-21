package cl.intranet.service;

import java.util.List;

import cl.intranet.domain.Evento;
import cl.intranet.repository.EventoController;
import cl.tidev.commons.mvc.service.jpa.AbstractServiceManager;

public class EventoManager extends AbstractServiceManager<Evento> {

	public List<Evento> findNextEventsByUser(Long idUser) {
		return ((EventoController) jpaController).findNextEventsByUser(idUser);
	}
}
