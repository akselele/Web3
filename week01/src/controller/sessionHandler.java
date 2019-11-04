package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public class sessionHandler extends RequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("time") == null){
            Map<LocalTime,String> time = new TreeMap<>();
            time.put(LocalTime.now(), new Throwable().getStackTrace()[1].getMethodName());
            request.getSession().setAttribute("time", time);
        }
        else{
            Map<LocalTime,String> time = (Map<LocalTime, String>) session.getAttribute("time");
            time.put(LocalTime.now(), new Throwable().getStackTrace()[1].getMethodName());
            request.getSession().setAttribute("time", time);
        }
    }
}
