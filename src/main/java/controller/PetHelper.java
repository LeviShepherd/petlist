package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Pet;

public class PetHelper {

	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("petlist");
	
	public void insertPet(Pet p) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<Pet> showAllPets(){
		EntityManager em = emfactory.createEntityManager();
		List<Pet> allPets = em.createQuery("SELECT i FROM Pet i").getResultList();
		return allPets;
	}
	
	public void deletePet(Pet toDelete) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Pet> typedQuery = em.createQuery("select p from Pet p where p.name = :selectedName and p.type = :selectedType", Pet.class);
		
		//Substitute parameter with actual data from the toDelete item
		typedQuery.setParameter("selectedName", toDelete.getName());
		typedQuery.setParameter("selectedType", toDelete.getType());
		
		//we only want one result
		typedQuery.setMaxResults(1);
		
		//get the result and save it into a new list item
		Pet result = typedQuery.getSingleResult();
		
		//remove it
		em.remove(result);
		em.getTransaction().commit();
		em.close();
	}
	
	public Pet searchForPetById(int idToEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		Pet found = em.find(Pet.class, idToEdit);
		em.close();
		return found;
	}
	
	public void updatePet(Pet toEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(toEdit);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<Pet> searchForPetByName(String petName) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Pet> typedQuery = em.createQuery("select p from Pet p where p.name = :selectedName", Pet.class);
		typedQuery.setParameter("selectedName", petName);
		
		List<Pet> foundPets = typedQuery.getResultList();
		em.close();
		return foundPets;
	}
	
	public List<Pet> searchForPetByType(String petType) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Pet> typedQuery = em.createQuery("select	p	from " +
		"Pet p where p.type =:selectedType", Pet.class);
		typedQuery.setParameter("selectedType",	petType);
		List<Pet>	foundPets	=	typedQuery.getResultList();
		em.close();
		return foundPets;
	}
	
	public void cleanUp(){
		emfactory.close();
	}
}
