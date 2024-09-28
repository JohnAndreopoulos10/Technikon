package gr.ed.technikon.Repositories;

import gr.ed.technikon.exceptions.CustomException;
import gr.ed.technikon.exceptions.OwnerNotFoundException;
import gr.ed.technikon.models.Property;
import gr.ed.technikon.utility.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.text.ParseException;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@Slf4j
public class PropertyRepository implements PropertyRepositoryInterface<Property, Long> {

    private final EntityManager entityManager;

    public PropertyRepository() {
        entityManager = JPAUtil.getEntityManager();
    }

    @Override
    public Optional<Property> findById(Long propertyId) {
        try {
            Property property = entityManager.find(Property.class, propertyId);
            return Optional.ofNullable(property);
        } catch (CustomException ce) {
            log.debug("Could not find Property with ID: " + propertyId);
            entityManager.getTransaction().rollback();
            System.out.println(ce.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Property> findByOwnerId(Long ownerId) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Property> query = entityManager.createQuery(
                    "SELECT p FROM Property p WHERE p.owner.OwnerId = :ownerId", Property.class); //paizei na einai ownerId
            query.setParameter("ownerId", ownerId);
            entityManager.getTransaction().commit();
            return query.getResultList();
        } catch (OwnerNotFoundException oe) {
            log.debug("Could not find Properties for Owner ID: " + ownerId);
            entityManager.getTransaction().rollback();
            System.out.println(oe.getMessage());
        }
        return List.of();
    }

    @Override
    public List<Property> findAll() {
        try {
            TypedQuery<Property> query = entityManager.createQuery(
                    "SELECT p FROM Property p WHERE p.deleted = false", Property.class);
            return query.getResultList();
        } catch (Exception e) {
            log.debug("Could not retrieve all Properties", e);
            return List.of();
        }
    }

    @Override
    public Optional<Property> save(Property property) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(property);
            entityManager.getTransaction().commit();
            return Optional.of(property);
        } catch (Exception e) {
            log.debug("Could not save Property", e);
            entityManager.getTransaction().rollback();
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long propertyId) {
        try {
            Property property = entityManager.find(Property.class, propertyId);
            if (property != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(property);
                entityManager.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            log.debug("Could not delete Property with ID: " + propertyId);
            entityManager.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public boolean safeDeleteById(Long propertyId) {
        try {
            Property property = entityManager.find(Property.class, propertyId);
            if (property != null) {
                entityManager.getTransaction().begin();
                property.setDeletedProperty(true);
                entityManager.merge(property);
                entityManager.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            log.debug("Could not safely delete Property with ID: " + propertyId, e);
            entityManager.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public Optional<Property> update(Property property) {
        try {
            entityManager.getTransaction().begin();
            Property p = entityManager.find(Property.class, property.getPropertyId());
            if (p != null) {

                p.setAddress(property.getAddress());
                p.setYearOfConstruction(property.getYearOfConstruction());
                p.setPropertyType(property.getPropertyType());
                p.setOwner(property.getOwner());

                entityManager.merge(p);
                entityManager.getTransaction().commit();
                return Optional.of(p);
            } else {
                log.debug("Property with ID: " + property.getPropertyId() + " not found for update.");
            }
        } catch (Exception e) {
            log.debug("Could not update Property", e);
            entityManager.getTransaction().rollback();
        }
        return Optional.empty();
    }

//    private Class<Property> getEntityClass() {
//        return Property.class;
//    }
//
//    private String getEntityClassName() {
//        return Property.class.getName();
//    }
}
