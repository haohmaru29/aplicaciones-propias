/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.intranet.service;

import cl.tidev.commons.mvc.service.jpa.AbstractServiceManager;
import cl.intranet.domain.Usuario;
import cl.intranet.repository.UsuarioController;

/**
 *
 * @author Administrador
 */
public class UsuarioManager extends AbstractServiceManager<Usuario> {
    
    public Usuario login(String correo, String pass) {
        return ((UsuarioController)jpaController).login(correo, pass);
    }
}
