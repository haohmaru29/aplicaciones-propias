/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.tidev.commons.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrador
 */
public class security {
 
    public void closeSession(HttpServletRequest request, HttpServletResponse response ) {
        request.getSession(false).invalidate();
    }
}
