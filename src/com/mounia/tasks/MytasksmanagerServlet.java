package com.mounia.tasks;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MytasksmanagerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
                HttpSession session = req.getSession(true);
                if(session.getAttribute("externaltoken") != null) {
                    RequestDispatcher rd = null;
                    rd = req.getRequestDispatcher("taskmanager.jsp");
                    rd.forward(req, resp);
                }else{
                    resp.sendRedirect("/external");
                }
	}
}
