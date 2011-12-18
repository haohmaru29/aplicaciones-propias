package cl.intranet.web;

import cl.intranet.domain.Usuario;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;
import cl.intranet.service.UsuarioManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class usuario {
    
    private UsuarioManager usuarioManager = 
            (UsuarioManager) ServiceManager.factory("UsuarioManager");
    
    public void login(HttpServletRequest request, HttpServletResponse response) {
        Usuario usuario = usuarioManager.login(request.getParameter("nombre"), request.getParameter("clave"));
        JsonView jsonView = new JsonView();
        if(usuario!=null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuario);
            jsonView.prepareResponse(true, "Usuario existe");
        } else {
            jsonView.prepareResponse(true, "Usuario No Existe");
        }
        
        jsonView.render(response);
    }
    
    public void all(HttpServletRequest request, HttpServletResponse response ) {
        JsonView jsonView = new JsonView();
        jsonView.prepareResponse(usuarioManager.findAll());
        jsonView.render(response);
    }
    
    public void session(HttpServletRequest request, HttpServletResponse response ) {
        Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario");
        
        JsonView jsonView = new JsonView();
        jsonView.prepareResponse(usuario);
        jsonView.render(response);
    }
}
