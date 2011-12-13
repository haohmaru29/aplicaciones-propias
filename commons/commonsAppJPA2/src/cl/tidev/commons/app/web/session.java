package cl.tidev.commons.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.tidev.commons.library.session.Session;

public class session {

    public void put(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sessionId = request.getParameter("sessid") == null ? request.getSession(false).getId() : request.getParameter("sessid");
        Session session = Session.initSession(sessionId);

        response.getWriter().println(("Session ID:").concat(sessionId));
        response.getWriter().println(("Session Var [Set]:").concat(request.getParameter("session_var").concat(" => ") + session.getParameter(request.getParameter("session_value"))));

        session.setParameter(request.getParameter("session_var"), request.getParameter("session_value"));
    }

    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sessionId = request.getParameter("sessid") == null ? request.getSession(false).getId() : request.getParameter("sessid");
        Session session = Session.initSession(sessionId);

        response.getWriter().println(("Session ID:").concat(sessionId));
        response.getWriter().println(("Session Var [Get]:").concat(request.getParameter("session_var").concat(" => ") + session.getParameter(request.getParameter("session_var"))));
    }

    public void close(HttpServletRequest request, HttpServletResponse response) {
        Session.initSession((String) request.getSession(false).getAttribute("sessid")).close();
        request.getSession(false).invalidate();
    }
}
