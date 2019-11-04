package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Map;

public class historyHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler handler = new cookieHandler();
        handler.handleRequest(request,response);
        HttpSession session = request.getSession();
        if(session.getAttribute("time") != null){
            Map<LocalTime,String> time = (Map<LocalTime, String>) session.getAttribute("time");
            request.setAttribute("history", time);
        }
        request.getRequestDispatcher("activity.jsp").forward(request,response);
    }
}
