package com.mounia.tasks;

import com.mounia.tasks.dao.AccountDao;
import com.mounia.tasks.dao.ProjectDao;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.mounia.tasks.dao.TaskDao;
import com.mounia.tasks.model.Account;
import com.mounia.tasks.model.Project;
import com.mounia.tasks.model.Task;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class JsonTaskServlet
 */
public class JsonTaskServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("externaltoken") != null) {
            String action = request.getParameter("action");
            JSONObject obj = new JSONObject();
            Map<String, String> map = new LinkedHashMap<String, String>();
            if (action.equals("project")) {
                List<Project> tasks = ProjectDao.INSTANCE.listProjects();
                if (tasks != null) {
                    for (Project t : tasks) {
                        map.put(t.getProjectId().toString(), t.getName() + " manager:" + t.getProjectManager().getName() + " " + t.getSummary());
                    }
                }
            } else if (action.equals("projectname")) {
                List<Project> tasks = ProjectDao.INSTANCE.listProjects();
                if (tasks != null) {
                    for (Project t : tasks) {
                        map.put(t.getProjectId().toString(), t.getName());
                    }
                }
            } else if (action.equals("account")) {
                List<Account> accounts = AccountDao.INSTANCE.listAccounts();
                if (accounts != null) {
                    for (Account t : accounts) {
                        map.put(t.getAccountId(), t.getName());
                    }
                }
            } else if (action.equals("task")) {
                long project = Long.parseLong(request.getParameter("project"));
                List<Task> tasks = TaskDao.INSTANCE.listTasksFromProject(project);
                if (tasks != null) {
                    for (Task t : tasks) {
                        map.put(t.getTaskId().toString(), t.getName() + " t:" + t.getEndTime() + " owner:" + t.getAccount().getName() + "  " + t.getSummary() + " status: " + t.getStatus());
                    }
                }
            } else if (action.equals("search")) {
                String name = request.getParameter("name");
                long project = Long.parseLong(request.getParameter("project"));
                List<Task> tasks = TaskDao.INSTANCE.searchTasks(name, project);
                if (tasks != null) {
                    for (Task t : tasks) {
                        map.put(t.getTaskId().toString(), t.getName() + " t:" + t.getEndTime() + " owner:" + t.getAccount().getName() + "  " + t.getSummary() + " status: " + t.getStatus());
                    }
                }
            } else if (action.equals("status")) {
                long taskId = Long.parseLong(request.getParameter("task"));
                Task task = TaskDao.INSTANCE.get(taskId);
                if (task != null) {
                    if (task.getStatus().equals("todo")) {
                        task.setStatus("done");
                    } else if (task.getStatus().equals("done")) {
                        task.setStatus("todo");
                    }
                    TaskDao.INSTANCE.update(task);
                    String msgBody = "...";
                    
                    String url = "http://fcapi.merenita.com/email.php?to="+task.getProject().getProjectManager().getEmail()+"&su=Status is aangepast van project: " + task.getProject().getName() + " taak: " + task.getName()+"&ms=...";
                    URLConnection connection = new URL(url).openConnection();
                    InputStream responseUrl = connection.getInputStream();
                    map.put("recordKey", task.getName() + " t:" + task.getEndTime() + " owner:" + task.getAccount().getName() + "  " + task.getSummary() + " status: " + task.getStatus());
                }
            }

            obj.putAll(map);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            StringWriter out = new StringWriter();
            obj.writeJSONString(out);
            String jsonText = out.toString();
            response.getWriter().write(jsonText);
        } else {
            response.sendRedirect("/external");
        }
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("externaltoken") != null) {
            String action = request.getParameter("action");
            if (action.equals("project")) {
                String name = request.getParameter("name");
                String summary = request.getParameter("summary");
                String accountId = request.getParameter("account");

                Account account = AccountDao.INSTANCE.get(accountId);

                ProjectDao.INSTANCE.add(name, summary, account);
            } else if (action.equals("task")) {
                String name = request.getParameter("name");
                String summary = request.getParameter("summary");
                String endtime = request.getParameter("endtime");
                long projectId = Long.parseLong(request.getParameter("project"));
                String accountId = request.getParameter("account");

                Project project = ProjectDao.INSTANCE.get(projectId);
                Account account = AccountDao.INSTANCE.get(accountId);

                TaskDao.INSTANCE.add(name, summary, endtime, project, account, "todo");
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            StringWriter out = new StringWriter();
            response.getWriter().write("{\"status\" : \"ok\"}");
        } else {
            response.sendRedirect("/external");
        }
    }
}
