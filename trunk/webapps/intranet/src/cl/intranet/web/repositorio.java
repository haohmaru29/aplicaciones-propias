package cl.intranet.web;

import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;
import cl.intranet.domain.Usuario;
import cl.intranet.service.RepositorioManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class repositorio {
    
    RepositorioManager repositorioManager = 
            (RepositorioManager) ServiceManager.factory("RepositorioManager");
    
    public void all(HttpServletRequest request, HttpServletResponse response ) {
        JsonView jsonView = new JsonView();
        jsonView.prepareResponse(repositorioManager.findAll());
        jsonView.render(response);
    }
    
    public void myRepo(HttpServletRequest request, HttpServletResponse response ) {
    	Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario");
    	JsonView jsonView = new JsonView();
    	jsonView.prepareResponse(repositorioManager.findByIdUser(usuario.getIdusuario()));
    	jsonView.render(response);
    }
}
