package org.vitaliistf.mathhelper.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.vitaliistf.mathhelper.entity.Equation;

import java.util.List;

/**
 * Repository class for performing database operations related to Equation entities.
 */
public class EquationRepository {

    private final SessionFactory sessionFactory;

    /**
     * Constructs an EquationRepository with the specified SessionFactory.
     *
     * @param sessionFactory The Hibernate SessionFactory.
     */
    public EquationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Finds an equation by its unique identifier.
     *
     * @param equationId The identifier of the equation to find.
     * @return The Equation entity if found, or null if not found.
     */
    public Equation findEquationById(Long equationId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Equation.class, equationId);
        }
    }

    /**
     * Finds an equation by its mathematical expression.
     *
     * @param expression The mathematical expression of the equation to find.
     * @return The Equation entity if found, or null if not found.
     */
    public Equation findEquationByExpression(String expression) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Equation e WHERE e.expression = :expression";
            Query<Equation> query = session.createQuery(hql, Equation.class);
            query.setParameter("expression", expression);
            return query.uniqueResult();
        }
    }

    /**
     * Retrieves all equations from the database.
     *
     * @return List of all Equation entities.
     */
    public List<Equation> findAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Equation";
            Query<Equation> query = session.createQuery(hql, Equation.class);
            return query.list();
        }
    }

    /**
     * Saves an equation to the database.
     *
     * @param equation The Equation entity to be saved.
     */
    public void save(Equation equation) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(equation);
            transaction.commit();
        }
    }

    /**
     * Finds equations based on a list of root values.
     *
     * @param roots List of root values.
     * @return List of Equation entities that match the root values.
     */
    public List<Equation> findEquationsByRoots(List<Double> roots) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT e FROM Equation e JOIN e.roots r WHERE r.value IN :roots " +
                    "GROUP BY e HAVING COUNT(DISTINCT r.value) = :rootsCount";
            Query<Equation> query = session.createQuery(hql, Equation.class);
            query.setParameterList("roots", roots);
            query.setParameter("rootsCount", roots.size());
            return query.list();
        }
    }

    /**
     * Finds equations with a single root.
     *
     * @return List of Equation entities with a single root.
     */
    public List<Equation> findEquationsWithSingleRoot() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT e FROM Equation e WHERE size(e.roots) = 1";
            Query<Equation> query = session.createQuery(hql, Equation.class);
            return query.list();
        }
    }

    /**
     * Deletes an equation by its unique identifier.
     *
     * @param equationId The identifier of the equation to delete.
     */
    public void deleteEquationById(Long equationId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Equation equation = session.get(Equation.class, equationId);
            if (equation != null) {
                session.remove(equation);
            }
            transaction.commit();
        }
    }

}
