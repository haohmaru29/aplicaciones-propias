package cl.intranet.service;

import java.util.List;

import cl.intranet.domain.UsuarioCalendario;
import cl.intranet.repository.UsuarioCalendarioController;
import cl.tidev.commons.mvc.service.jpa.AbstractServiceManager;

public class UsuarioCalendarioManager extends AbstractServiceManager<UsuarioCalendario>{

	public List<UsuarioCalendario> findCalendarByUser(Long idUser) {
		return ( ( UsuarioCalendarioController )  jpaController).findCalendarByUser(idUser);
	}
}
