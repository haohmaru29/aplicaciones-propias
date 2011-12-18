<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Intranet [Developer]</title>
        <jsp:include page="public/js/tpl/css.jsp"></jsp:include>
        <jsp:include page="public/js/tpl/javascript.jsp"></jsp:include>
    </head>
    <body>
        <%
        try {
            if(request.getSession(false).getAttribute("usuario") != null ) {
                %>
                  <jsp:include page="view/index/index.jsp"></jsp:include>
                <%
            } else {
                %><jsp:include page="view/index/login.jsp"></jsp:include><%
            }
        } catch(Exception e ) {
             %><jsp:include page="view/index/login.jsp"></jsp:include><%
        }
        %>  
    </body>
</html>