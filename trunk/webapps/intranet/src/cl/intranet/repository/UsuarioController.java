/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.intranet.repository;

import cl.tidev.commons.mvc.repository.jpa.AbstractJpaController;
import cl.intranet.domain.Usuario;
import javax.persistence.Query;

/**
 *
 * @author Administrador
 */
public class UsuarioController extends AbstractJpaController<Usuario> {

    public Usuario login(String correo, String pass) {
        Usuario usuario=null;
        Query q = jpaConnection.getEntityManager().createNamedQuery("Usuario.login");
        q.setParameter("nombre", correo);
        q.setParameter("clave", pass);
        try {
            usuario = (Usuario) q.getSingleResult();
        } catch(Exception e) {}
        
        return usuario;
    }
}
