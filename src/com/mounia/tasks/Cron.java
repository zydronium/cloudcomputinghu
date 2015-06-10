/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mounia.tasks;

import com.mounia.tasks.dao.ProjectDao;
import com.mounia.tasks.dao.TaskDao;
import com.mounia.tasks.model.Project;
import com.mounia.tasks.model.Task;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jelle
 */
public class Cron extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Project> projects = ProjectDao.INSTANCE.listProjects();
        if (projects != null) {
            for (Project p : projects) {
                String message = "";
                message = message + "Dear " + p.getProjectManager().getName() + "\n\nHere are the tasks and their statuses of your project:\n\n";
                List<Task> tasks = TaskDao.INSTANCE.listTasksFromProject(p.getProjectId());
                if (tasks != null) {
                    for (Task t : tasks) {
                        message = message + t.getName() + " status: " + t.getStatus() + "\n";
                    }
                }
                String url = "http://fcapi.merenita.com/email.php?to="+p.getProjectManager().getEmail()+"&su=Status mail voor: "+p.getName()+"&ms="+message;
                URLConnection connection = new URL(url).openConnection();
                InputStream responseUrl = connection.getInputStream();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
