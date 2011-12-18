/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.intranet.service;

import java.util.List;

import cl.tidev.commons.mvc.service.jpa.AbstractServiceManager;
import cl.intranet.domain.Repositorio;
import cl.intranet.repository.RepositorioController;

/**
 *
 * @author Administrador
 */
public class RepositorioManager extends AbstractServiceManager<Repositorio> {
	
	public List<?> findByIdUser(Long idUser) {
		return ((RepositorioController) jpaController).findByIdUser(idUser);
	}
    
}
