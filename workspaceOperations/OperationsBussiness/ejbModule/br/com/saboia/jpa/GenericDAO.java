package br.com.saboia.jpa;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDAO<T> {
    private final static String UNIT_NAME = "OperationsBussiness";
 
    @PersistenceContext(unitName = UNIT_NAME)
    private EntityManager em;
 
    private Class<T> entityClass;
 
    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
 
    public void save(T entity) {
        em.persist(entity);        
    }
    
    public void saveNew(T entity) {
        em.persist(entity);
        em.clear();
    }
    
    public T saveReturn(T entity) {
        em.persist(entity);
        return em.merge(entity);
    }
 
    protected void delete(T entity) {    	
    	em.remove(entity);    	
	}
    
    protected void delete(Object id, Class<T> classe) {
    	
    	T entityToBeRemoved = em.getReference(classe, id);
 
        em.remove(entityToBeRemoved);
    }
 
    public T update(T entity) {
        return em.merge(entity);
    }
 
    public T find(long entityID) {
        return em.find(entityClass, entityID);
    }
    
       
    // Using the unchecked because JPA does not have a
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> findAll() {
    	em.flush();    	
    	CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }
 
    // Using the unchecked because JPA does not have a
    // ery.getSingleResult()<T> method
    @SuppressWarnings("unchecked")
    protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
        T result = null;
 
        try {
            Query query = em.createQuery(namedQuery);
 
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
 
            result = (T) query.getSingleResult();
 
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }
 
        return result;
    }
 
    
    @SuppressWarnings("unchecked")
    protected List<T> findListResult(String namedQuery, Map<String, Object> parameters) {
    	List<T> result = null;
 
        try {
            Query query = em.createQuery(namedQuery);
 
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
 
            result = (List<T>) query.getResultList();
 
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }
 
        return result;
    }
    
    
    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
 
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
    
   
    @SuppressWarnings("unchecked")
	protected T selectQueryString(String query){		
    	
    	T result = null;
    	
    	try{
    		
    		Query q = em.createQuery(query);
    		
    		result = (T) q.getSingleResult();
    		
    	}catch(Exception e){
    		System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
    	}
    	
    	return result;    
    }
    
    
    @SuppressWarnings("unchecked")
   	protected T selectFirstQueryStringDesc(String query){		
       	
       	T result = null;
       	
       	try{
       		
       		Query q = em.createQuery(query);
       		q.setMaxResults(1);
       		
       		result = (T) q.getSingleResult();
       		
       	}catch(Exception e){
       		System.out.println("Error while running query: " + e.getMessage());
               e.printStackTrace();
       	}
       	
       	return result;    
       }
    
    @SuppressWarnings("unchecked")
   	protected List<T> selectListQueryString(String query, Date... param){		
       	
       	List<T> result = null;
       	
       	try{
       		
       		Query q = em.createQuery(query);
       		q.setParameter(1, param[0]);
       		q.setParameter(2, param[1]);
       		
       		result = (List<T>) q.getResultList();
       		
       	}catch(Exception e){
       		System.out.println("Error while running query: " + e.getMessage());
               e.printStackTrace();
       	}
       	
       	return result;       
    }
    
    @SuppressWarnings("unchecked")
   	protected List<T> selectListSimpleQueryString(String query){		
       	
       	List<T> result = null;
       	
       	try{
       		
       		Query q = em.createQuery(query);
       		       		
       		result = (List<T>) q.getResultList();
       		
       	}catch(Exception e){
       		System.out.println("Error while running query: " + e.getMessage());
               e.printStackTrace();
       	}
       	
       	return result;       
    }
    
    @SuppressWarnings("unchecked")
   	protected List<T> selectListSimpleNativeQueryString(String query){		
       	
       	List<T> result = null;
       	
       	try{
       		
       		Query q = em.createNativeQuery(query);
       		
       		result = (List<T>) q.getResultList();
       		
       	}catch(Exception e){
       		System.out.println("Error while running query: " + e.getMessage());
               e.printStackTrace();
       	}
       	
       	return result;       
    }
    
    
    @SuppressWarnings("unchecked")
   	protected List<Object[]> selectListSimpleNativeQueryStringObject(String query){		
       	
       	List<Object[]> result = null;
       	
       	try{
       		
       		Query q = em.createNativeQuery(query);
       		
       		result = q.getResultList();
       		
       	}catch(Exception e){
       		System.out.println("Error while running query: " + e.getMessage());
               e.printStackTrace();
       	}
       	
       	return result;       
    }
    
    
    @SuppressWarnings("unchecked")
   	protected List<String> selectListStringSimpleQueryString(String query){		
       	
       	List<String> result = null;
       	
       	try{
       		
       		Query q = em.createQuery(query);
       		       		
       		result = q.getResultList();
       		
       	}catch(Exception e){
       		System.out.println("Error while running query: " + e.getMessage());
               e.printStackTrace();
       	}
       	
       	return result;       
    }
    
    /**
     * Também serve para update utilizando JPQL
     * @param sqlQuery
     */
    protected void deleteAll(String sqlQuery){
    	
    	Query query = em.createQuery(sqlQuery);
    	
    	query.executeUpdate();
    	
    }
    
    protected void deleteNativeQuery(String sqlQuery){
    	
    	Query query = em.createNativeQuery(sqlQuery);
    	
    	query.executeUpdate();
    	
    }
    
    protected void executeNativeQuery(String sqlQuery){
    	
    	Query query = em.createNativeQuery(sqlQuery);
    	
    	query.executeUpdate();
    	
    }
    
    protected List<T> selectListEntityDataTableLazy(String sqlQuery, int startingAt, int maxPerPage) {
		
    	Query query = em.createQuery(sqlQuery);
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
 
        return query.getResultList();
    	
	}
    
    protected List<T> selectListEntityDataTableLazyParams(String sqlQuery, int startingAt, int maxPerPage, Map<String, Object> params) {
		
    	Query query = em.createQuery(sqlQuery);
    	
    	if (params != null && !params.isEmpty()) {
            populateQueryParameters(query, params);
        }    	
    	
        query.setFirstResult(startingAt);
        query.setMaxResults(maxPerPage);
 
        return query.getResultList();
    	
	}
    
    protected int selectTotalNumberRegistry(String sqlQuery){
    	
    	Query query = em.createQuery(sqlQuery);
    	
    	Number result = (Number) query.getSingleResult();
    	
    	return result.intValue();		
	}
    
    protected int selectTotalNumberRegistryParams(String sqlQuery, Map<String, Object> params){
    	
    	Query query = em.createQuery(sqlQuery);
    	
    	if (params != null && !params.isEmpty()) {
            populateQueryParameters(query, params);
        }  
    	
    	Number result = (Number) query.getSingleResult();
    	
    	return result.intValue();		
	}
    
}
