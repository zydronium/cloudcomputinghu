package com.mounia.tasks.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mounia.tasks.model.*;

public enum TaskDao {
	INSTANCE;

	public List<Task> listTasks() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select t from Task t");
		List<Task> tasks = q.getResultList();
		return tasks;
	}

	public void add(String name, String summary, String endTime, Project project, Account account, String status) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Task task = new Task(name, summary, endTime, project, account, status);
			em.persist(task);
			em.close();
		}
	}

	public List<Task> getTasks() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Task t");
		List<Task> tasks = q.getResultList();
		return tasks;
	}

	public List<Task> listTasksFromProject(long project) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Task t where project = " + project);
		List<Task> tasks = q.getResultList();
		return tasks;
	}

	public List<Task> listTasksByAccount(Account account) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select t from Task t where account = '" + account.getAccountId()+"' and status = 'todo'");
		List<Task> tasks = q.getResultList();
		return tasks;
	}

	public List<Task> searchTasks(String name, long project) {
            String searchString = "select t from Task t where name like '" + name+"%'";
            if(project != 0) {
                //searchString = searchString + " and project = "+ project;
            }
            EntityManager em = EMFService.get().createEntityManager();
            Query q = em.createQuery(searchString);
            List<Task> tasks = q.getResultList();
            return tasks;
	}

	public Task get(long id) {
		EntityManager em = EMFService.get().createEntityManager();
                Task task = null;
		try {
			task = em.find(Task.class, id);
		} finally {
			em.close();
		}
                return task;
	}

	public void update(Task task) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.merge(task);
			em.close();
		}
	}

	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Task task = em.find(Task.class, id);
			em.remove(task);
		} finally {
			em.close();
		}
	}
}