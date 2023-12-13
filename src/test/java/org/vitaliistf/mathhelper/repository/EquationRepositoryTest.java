package org.vitaliistf.mathhelper.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.vitaliistf.mathhelper.entity.Equation;
import org.vitaliistf.mathhelper.repository.EquationRepository;

import java.util.List;

public class EquationRepositoryTest {

    private SessionFactory sessionFactory;
    private EquationRepository equationRepository;

    @BeforeEach
    public void setUp() {
        // Setting up database
        sessionFactory = new Configuration()
                .addAnnotatedClass(org.vitaliistf.mathhelper.entity.Equation.class)
                .addAnnotatedClass(org.vitaliistf.mathhelper.entity.Root.class)
                .buildSessionFactory();
        equationRepository = new EquationRepository(sessionFactory);
    }

    @AfterEach
    public void tearDown() {
        // Closing session factory after every test
        sessionFactory.close();
    }

    @Test
    public void testFindEquationByExpression() {
        // Test searching equation by the expression
        Equation equation = new Equation("x^2 - 4 = 0");
        equationRepository.save(equation);

        Equation foundEquation = equationRepository.findEquationByExpression("x^2 - 4 = 0");

        assertNotNull(foundEquation);
        assertEquals(equation.getExpression(), foundEquation.getExpression());
    }

    @Test
    public void testFindAll() {
        // Test getting all the equations
        Equation equation1 = new Equation("x^2 - 4 = 0");
        equationRepository.save(equation1);

        Equation equation2 = new Equation("2x + 1 = 0");
        equationRepository.save(equation2);

        List<Equation> equations = equationRepository.findAll();

        assertEquals(2, equations.size());
    }

    @Test
    public void testSave() {
        // Test saving the equation
        Equation equation = new Equation("3 * x - 7 = 0");
        equationRepository.save(equation);

        Equation savedEquation = equationRepository.findEquationById(equation.getId());

        assertNotNull(savedEquation);
        assertEquals(equation.getExpression(), savedEquation.getExpression());
    }

    @Test
    public void testFindEquationsByRoots() {
        // Test searching equation by roots
        Equation equation1 = new Equation("x - 4 = 0");
        equation1.addRoot(4.0);
        equationRepository.save(equation1);

        Equation equation2 = new Equation("x - 9 = 0");
        equation2.addRoot(9.0);
        equationRepository.save(equation2);

        List<Equation> equations = equationRepository.findEquationsByRoots(List.of(4.0));

        assertEquals(1, equations.size());
    }

    @Test
    public void testFindEquationsWithSingleRoot() {
        // Test searching equations with single root
        Equation equation1 = new Equation("x - 1 = 0");
        equation1.addRoot(1.0);
        equationRepository.save(equation1);

        Equation equation2 = new Equation("x + 2 = 0");
        equation2.addRoot(-2.0);
        equationRepository.save(equation2);

        List<Equation> equations = equationRepository.findEquationsWithSingleRoot();

        assertEquals(2, equations.size());
    }

    @Test
    public void testDeleteEquationById() {
        // Test deleting the equation
        Equation equation = new Equation("x^2 - 4 = 0");
        equationRepository.save(equation);

        equationRepository.deleteEquationById(equation.getId());

        Equation deletedEquation = equationRepository.findEquationById(equation.getId());

        assertNull(deletedEquation);
    }
}
