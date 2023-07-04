package br.com.rockeit.agendamentoconsultas.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.rockeit.agendamentoconsultas.dominio.Usuario;

public class FactoryEntityManager<T extends Cloneable> {
	
	private String persistenceUnity;
	
	private EntityManagerFactory factory;
	
	private EntityManager manager ;
	
	public FactoryEntityManager() {
		
	}
	
	public FactoryEntityManager(String persistenceUnity) {
		this.persistenceUnity = persistenceUnity;
		
		factory = Persistence.
	              createEntityManagerFactory("agenda");
		
		manager = factory.createEntityManager();
	}
	
	public String getPersistenceUnity() {
		return this.persistenceUnity;
	}
	
	public void setPersistenceUnity(String persistence) {
		this.persistenceUnity = persistence;
	}
	
	public EntityManagerFactory getFactory() {
		return this.factory;
	}
	
	public void setFactory(EntityManagerFactory factory) {
		this.factory = factory;
	}
	
	public EntityManager getManager() {
		return this.manager;
	}
	
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	
	public <E extends T> E findNamedQuerySingleResult(Class<E> classe, String namedQuery, Object... parameters) {
		
		E result = null;
		Query query = manager.createNamedQuery(namedQuery, classe);
		
		if (parameters != null && parameters.length > 0 && parameters[0] != null) {
			for (int i = 0; i < parameters.length; i++) {
				Object object = parameters[i];
				query.setParameter(i + 1, object);
			}
		}
		
		try {
			result = (E) query.getSingleResult();
		} catch(Exception e) {
			if (e instanceof javax.persistence.NoResultException) {
				AlertFactory.showErrorAlert("Usuário não cadastrado no banco de dados");
			}
		}
		
		manager.close();
		factory.close();
		
		return result;
	}
	
public <E extends T> List<E> findNamedQuery(Class<E> classe, String namedQuery, Object... parameters) {
		
		List<E> result = null;
		Query query = manager.createNamedQuery(namedQuery, classe);
		
		if (parameters != null && parameters.length > 0 && parameters[0] != null) {
			for (int i = 0; i < parameters.length; i++) {
				Object object = parameters[i];
				query.setParameter(i + 1, object);
			}
		}
		
		try {
			return query.getResultList();
		} catch(Exception e) {
			if (e instanceof javax.persistence.NoResultException) {
				AlertFactory.showErrorAlert("Usuário não cadastrado no banco de dados");
			}
		}
		
		manager.close();
		factory.close();
		
		return result;
	}
}
