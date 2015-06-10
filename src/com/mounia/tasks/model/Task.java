package com.mounia.tasks.model;

import com.mounia.tasks.dao.*;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String summary;
    private String name;
    private String endTime;
    private long project;
    private String account;
    private String status;

    public Task(String name, String summary, String endTime, Project project, Account account, String status) {
        this.name = name;
        this.summary = summary;
        this.endTime = endTime;
        this.project = project.getProjectId();
        this.account = account.getAccountId();
        this.status = status;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Project getProject() {
        return ProjectDao.INSTANCE.get(this.project);
    }

    public void setProject(Project project) {
        this.project = project.getProjectId();
    }

    public Account getAccount() {
        return AccountDao.INSTANCE.get(this.account);
    }

    public void setAccount(Account account) {
        this.account = account.getAccountId();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
