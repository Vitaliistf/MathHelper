package org.vitaliistf.mathhelper.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a root entity associated with an equation.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Root {

    /**
     * The unique identifier for the root.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The numerical value of the root. Cannot be null.
     */
    @NonNull
    @Column(nullable = false)
    private Double value;

    /**
     * The equation to which this root belongs.
     */
    @ManyToOne
    @NonNull
    @JoinColumn(name = "equation_id", nullable = false)
    private Equation equation;

    /**
     * Constructs a root with the specified value and associated equation.
     *
     * @param value    The numerical value of the root. Cannot be null.
     * @param equation The equation to which this root belongs. Cannot be null.
     */
    public Root(Double value, Equation equation) {
        this.value = value;
        this.equation = equation;
    }
}
