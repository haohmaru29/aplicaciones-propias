package cl.intranet.service;

import java.util.List;

import cl.intranet.domain.UsuarioServidorCorreo;
import cl.intranet.repository.UsuarioServidorCorreoController;
import cl.tidev.commons.mvc.service.jpa.AbstractServiceManager;

public class UsuarioServidorCorreoManager extends AbstractServiceManager<UsuarioServidorCorreo>{

	public UsuarioServidorCorreo findByUser(Long idUsuario) {
		return ((UsuarioServidorCorreoController) jpaController).findByUser(idUsuario);
	}
	
	public List<UsuarioServidorCorreo> findByUserList(Long idUsuario){
		return ((UsuarioServidorCorreoController) jpaController).findByUserList(idUsuario);
	}
	
}
