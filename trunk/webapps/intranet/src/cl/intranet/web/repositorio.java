package cl.intranet.web;

import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;
import cl.tidev.commons.mvc.view.View;
import cl.intranet.service.RepositorioManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class repositorio {
    
    RepositorioManager repositorioManager = 
            (RepositorioManager) ServiceManager.factory("RepositorioManager");
    
    public void all(HttpServletRequest request, HttpServletResponse response ) {
        View jsonView = new JsonView();
        jsonView.prepareResponse(repositorioManager.findAll());
        jsonView.render(response);
    }
}
