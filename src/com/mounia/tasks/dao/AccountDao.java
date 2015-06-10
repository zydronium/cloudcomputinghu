package com.mounia.tasks.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mounia.tasks.model.*;

public enum AccountDao {
	INSTANCE;

	public List<Account> listAccounts() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select t from Account t");
		List<Account> tasks = q.getResultList();
		return tasks;
	}

	public void add(String accountId, String email, String name, String given_name, String family_name, String link, String picture, String gender, String locale) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Account task = new Account(accountId, email, name, given_name, family_name, link, picture, gender, locale);
			em.persist(task);
			em.close();
		}
	}

	public List<Account> getAccounts() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Account t");
		List<Account> tasks = q.getResultList();
		return tasks;
	}

	public Account get(String id) {
		EntityManager em = EMFService.get().createEntityManager();
                Account task = null;
		try {
			task = em.find(Account.class, id);
		} finally {
			em.close();
		}
                return task;
	}

	public void remove(String id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Account task = em.find(Account.class, id);
			em.remove(task);
		} finally {
			em.close();
		}
	}
}