/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.intranet.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import cl.intranet.domain.Repositorio;
import cl.tidev.commons.mvc.repository.jpa.AbstractJpaController;

/**
 *
 * @author Administrador
 */
public class RepositorioController extends AbstractJpaController<Repositorio> {
	
	public List<?> findByIdUser(String idUser) {
		EntityManager em = jpaConnection.getEntityManager();
		Query q = em.createNamedQuery("", Repositorio.class);
		
		return q.getResultList();
	}
}
