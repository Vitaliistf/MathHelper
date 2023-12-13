package org.vitaliistf.mathhelper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.vitaliistf.mathhelper.entity.Equation;
import org.vitaliistf.mathhelper.entity.Root;
import org.vitaliistf.mathhelper.evaluation.EquationEvaluator;
import org.vitaliistf.mathhelper.repository.EquationRepository;
import org.vitaliistf.mathhelper.validation.EquationValidator;
import org.vitaliistf.mathhelper.validation.RootConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Main class for the MathHelper application, providing a console-based user interface.
 */
public class MathHelperApplication {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final EquationRepository equationRepository = new EquationRepository(sessionFactory);

    /**
     * The main entry point for the MathHelper application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        while (true) {
            displayMenu();

            String choice = scanner.next().trim();
            scanner.nextLine();

            switch (choice) {
                case "1" -> enterEquation();
                case "2" -> searchEquationsByRoot();
                case "3" -> getAllEquations();
                case "4" -> getAllEquationsWithSingleRoot();
                case "5" -> deleteEquation();
                case "6" -> {
                    exitApplication();
                    System.exit(0);
                }
                default -> System.out.println("\nPlease, enter the correct option.");
            }
        }
    }

    private static SessionFactory buildSessionFactory() {
        return new Configuration()
                .addAnnotatedClass(Equation.class)
                .addAnnotatedClass(Root.class)
                .buildSessionFactory();
    }

    private static void displayMenu() {
        System.out.println("\n\n\n----------------------------------");
        System.out.println("1. Enter equation");
        System.out.println("2. Find equations by roots");
        System.out.println("3. Find all equations");
        System.out.println("4. Find all equations with single root");
        System.out.println("5. Delete equation");
        System.out.println("6. Exit");
        System.out.print("\nChoose an option: ");
    }

    private static void enterEquation() {
        System.out.print("\nEnter equation: ");
        String equationStr = removeSpaces(scanner.nextLine());

        if (EquationValidator.isValid(equationStr)) {
            Equation equation = new Equation(equationStr);
            enterRoots(equation);
            equationRepository.save(equation);
            System.out.println("Equation is saved successfully.");
        } else {
            System.out.println("The equation is not correct. Please, check the input.");
        }
    }

    private static void enterRoots(Equation equation) {
        System.out.print("Enter roots of the equation with spaces between (or press Enter, to skip): ");
        String input = scanner.nextLine();

        String[] roots = input.split("\\s+");

        for (String root : roots) {
            root = root.trim();
            Optional<Double> n = RootConverter.convert(root);

            while (n.isEmpty() && !input.isBlank()) {
                System.out.println("Root " + root +
                        " - is not a number, enter again (or press Enter, to skip): ");
                input = scanner.nextLine();
                n = RootConverter.convert(input);
            }

            if (n.isPresent() && EquationEvaluator.evaluate(equation.getExpression(), n.get())) {
                saveValidRoot(equation, root, n.get());
            } else if (!root.equals("")) {
                System.out.println("Number " + root + " - is not a root of equation, it will not be saved.");
            }
        }
    }

    private static void saveValidRoot(Equation equation, String root, Double rootValue) {
        Root rootToSave = new Root(rootValue, equation);
        equation.getRoots().add(rootToSave);
        System.out.println("Number " + root + " - is a root of equation, it will be saved.");
    }

    private static void searchEquationsByRoot() {
        System.out.print("Enter roots for searching with spaces between: ");
        String rootsString = scanner.nextLine();
        String[] rootsStringArr = rootsString.split(" ");

        List<Double> roots = new ArrayList<>();
        for (String s : rootsStringArr) {
            try {
                roots.add(Double.parseDouble(s));
            } catch (NumberFormatException e) {
                System.out.println("Root " + s + " - is not a number, searching without it.");
            }
        }

        displayEquationsByRoots(roots);
    }

    private static void displayEquationsByRoots(List<Double> roots) {
        System.out.println("Equations with entered roots:");
        equationRepository.findEquationsByRoots(roots).forEach(
                equation -> System.out.println("ID: " + equation.getId() + ", Equation: " + equation.getExpression())
        );
    }

    private static void getAllEquations() {
        System.out.println("All equations: ");
        equationRepository.findAll().forEach(MathHelperApplication::displayEquationDetails);
    }

    private static void getAllEquationsWithSingleRoot() {
        System.out.println("All equations with a single root: ");
        equationRepository.findEquationsWithSingleRoot().forEach(MathHelperApplication::displayEquationDetails);
    }

    private static void deleteEquation() {
        System.out.print("\nEnter ID of equation, or its expression to delete: ");
        String equationStr = scanner.nextLine();
        if (equationStr.contains("x")) {
            Equation equation = equationRepository.findEquationByExpression(removeSpaces(equationStr));
            if (equation != null) {
                equationRepository.deleteEquationById(equation.getId());
                System.out.println("Equation is deleted successfully.");
            } else {
                System.out.println("The entered equation is not in the database. Please, check the input.");
            }
        } else {
            try {
                Long id = Long.parseLong(equationStr);
                Equation equation = equationRepository.findEquationById(id);
                if (equation != null) {
                    equationRepository.deleteEquationById(id);
                    System.out.println("Equation is deleted successfully.");
                } else {
                    System.out.println("Equation with such ID is not present in the database. Please, check the input.");
                }
            } catch (NumberFormatException e) {
                System.out.println("String is not an equation or a number. Please, check the input.");
            }
        }
    }

    private static void displayEquationDetails(Equation equation) {
        System.out.println("\nID: " + equation.getId() + ", Equation: " + equation.getExpression());
        System.out.println("    Roots: ");
        equation.getRoots().forEach(
                root -> System.out.println("    ID: " + root.getId() + ", Root: " + root.getValue())
        );
    }

    private static void exitApplication() {
        System.out.println("\nThank you for using the program. Goodbye!");
        sessionFactory.close();
    }

    private static String removeSpaces(String input) {
        return input.replaceAll(" ", "");
    }

}
