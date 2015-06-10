/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mounia.tasks;

import com.mounia.tasks.dao.TaskDao;
import com.mounia.tasks.model.Account;
import com.mounia.tasks.model.Task;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import sun.security.provider.Sun;

/**
 *
 * @author jelle
 */
@WebServlet(name = "Feed", urlPatterns = {"/feed"})
public class Feed extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("externaltoken") != null) {
            String type = request.getParameter("type");
            Account account = (Account)session.getAttribute("account");
            
            JSONObject obj = new JSONObject();
            Map<String, String> map = new LinkedHashMap<String, String>();
            List<Task> tasks = TaskDao.INSTANCE.listTasksByAccount(account);
            
            if(type.equals("json")) {
                if (tasks != null) {
                    for (Task t : tasks) {
                        map.put(t.getTaskId().toString(), t.getName() + " t:" + t.getEndTime() + " owner:" + t.getAccount().getName() + "  " + t.getSummary() + " status: " + t.getStatus());
                    }
                }
                obj.putAll(map);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                StringWriter out = new StringWriter();
                obj.writeJSONString(out);
                String jsonText = out.toString();
                response.getWriter().write(jsonText);
            }else if(type.equals("rss")) {
                response.setContentType("application/rss+xml");
                String content = "";
                content = content + "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                +"<rss version=\"2.0\">"
                +"<channel>"
                +"+<title>Task manager</title>"
                +"<description>Alle openstaande taken</description>"
                +"+<link>http://cloudcomputinghu.appspot.com/mytasksmanager</link>"
                +"<lastBuildDate>"+ new Date() +"</lastBuildDate>"
                +"<pubDate>"+ new Date() +"</pubDate>"
                +"<ttl>1800</ttl>";
                if (tasks != null) {
                    for (Task t : tasks) {
                        content = content + "<item>"
                        +"<title>"+t.getName()+"</title>"
                        +"<description>"+t.getSummary()+"</description>"
                        +"<link>http://cloudcomputinghu.appspot.com/mytasksmanager</link>"
                        +"<id isPermaLink=\"true\">"+t.getTaskId().toString()+"</id>"
                        +"<pubDate>"+ new Date() +"</pubDate>"
                        +"</item>";
                    }
                }
                content = content + "</channel>"
                +"</rss>";
                response.getWriter().println(content);
            }
        }else{
            response.sendRedirect("/external");
        }
    }

}
