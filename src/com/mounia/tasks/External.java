/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mounia.tasks;

import com.mounia.tasks.dao.*;
import com.mounia.tasks.model.Account;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author jelle
 */
public class External extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //MySQLConnection DBConnection = new MySQLConnection();
        HttpSession session = request.getSession();
        GoogleAuthHelper helper = new GoogleAuthHelper();
        if (request.getParameter("code") == null
                || request.getParameter("state") == null) {
            response.sendRedirect(helper.buildLoginUrl());
            session.setAttribute("state", helper.getStateToken());
        } else if (request.getParameter("code") != null && request.getParameter("state") != null && request.getParameter("state").equals(session.getAttribute("state"))) {
            session.removeAttribute("state");
            String JSONString = helper.getUserInfoJson(request.getParameter("code"));
            JSONObject rootOfPage = new JSONObject(JSONString);
            Account account = AccountDao.INSTANCE.get(rootOfPage.get("id").toString());
            if(account == null) {
                AccountDao.INSTANCE.add(rootOfPage.get("id").toString(), rootOfPage.get("email").toString(), rootOfPage.get("name").toString(), rootOfPage.get("given_name").toString(), rootOfPage.get("family_name").toString(), rootOfPage.get("link").toString(), rootOfPage.get("picture").toString(), rootOfPage.get("gender").toString(), rootOfPage.get("locale").toString());
                account = AccountDao.INSTANCE.get(rootOfPage.get("id").toString());
            }
            session.setAttribute("account", account);
            session.setAttribute("externaltoken", "1");
            response.sendRedirect("/mytasksmanager");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
