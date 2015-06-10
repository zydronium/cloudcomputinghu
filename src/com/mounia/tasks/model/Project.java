package com.mounia.tasks.model;

import com.mounia.tasks.dao.AccountDao;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String summary;
    private String name;
    private String projectManager;

    public Project(String name, String summary, Account projectManager) {
        this.name = name;
        this.summary = summary;
        this.projectManager = projectManager.getAccountId();
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long taskId) {
        this.projectId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Account getProjectManager() {
        return AccountDao.INSTANCE.get(this.projectManager);
    }

    public void setProjectManager(Account projectManager) {
        this.projectManager = projectManager.getAccountId();
    }
}
