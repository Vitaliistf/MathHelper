package org.vitaliistf.mathhelper.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an equation entity with its expression and associated roots.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Equation {

    /**
     * The unique identifier for the equation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The mathematical expression of the equation. Cannot be null.
     */
    @Column(nullable = false)
    @NonNull
    private String expression;

    /**
     * The list of roots associated with this equation.
     */
    @OneToMany(mappedBy = "equation", cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true)
    private List<Root> roots = new ArrayList<>();

    /**
     * Constructs an equation with the specified expression.
     *
     * @param expression The mathematical expression of the equation. Cannot be null.
     */
    public Equation(String expression) {
        this.expression = expression;
    }

    /**
     * Adds a root to the list of roots associated with this equation.
     *
     * @param root The root value to be added.
     */
    public void addRoot(double root) {
        roots.add(new Root(root, this));
    }
}
