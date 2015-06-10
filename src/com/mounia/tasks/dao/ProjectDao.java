package com.mounia.tasks.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mounia.tasks.model.*;

public enum ProjectDao {
	INSTANCE;

	public List<Project> listProjects() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select t from Project t");
		List<Project> tasks = q.getResultList();
		return tasks;
	}

	public void add(String name, String summary, Account projectManager) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Project task = new Project(name, summary, projectManager);
			em.persist(task);
			em.close();
		}
	}

	public List<Project> getProjects() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Project t");
		List<Project> tasks = q.getResultList();
		return tasks;
	}

	public Project get(long id) {
		EntityManager em = EMFService.get().createEntityManager();
                Project task = null;
		try {
			task = em.find(Project.class, id);
		} finally {
			em.close();
		}
                return task;
	}

	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Project task = em.find(Project.class, id);
			em.remove(task);
		} finally {
			em.close();
		}
	}
}