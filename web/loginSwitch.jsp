<%@ page session="false" %>
<%  
    if (request.getSession(false) == null) {
        //response.sendRedirect("index.html");
         request.getRequestDispatcher("/index.html").forward(request, response);
    } else {
         //response.sendRedirect("views/Main.jsp");
         request.getRequestDispatcher("http://localhost:8084/stop_bad_file/").forward(request, response);
    }
%>