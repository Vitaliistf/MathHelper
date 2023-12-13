# MathHelper Application

> This repository contains the solution for the "Mathematical Assistant" test task. This is a prototype application that 
> will assist mathematics teachers.

## Table of Contents

- [Requirements](#requirements)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Usage](#usage)

## Requirements

The application should provide the following features:

- Enter mathematical equations containing numbers (integers or decimal fractions), as well as mathematical operations 
+, -, *, /, and parentheses. The level of nesting of parentheses is arbitrary. In all equations, the unknown variable 
is denoted by the English letter x. 
- Check the correctness of the placement of parentheses in the entered equation.
- Check the correctness of the entered expression (there should not be two consecutive mathematical operation signs, 
for example, the expression 3+\*4 is not allowed, while the expression 4\*-7 is acceptable).
- Examples of correct equations:
    - 2 * x + 5 = 17
    - -1.3 * 5 / x = 1.2
    - 2 * x * x = 10
    - 2 * (x + 5 + x) + 5 = 10
    - 17 = 2 * x + 5
- If the equation is correct, save it in the database.
- Allow the input of equation roots, check during input whether the specified number is a root, and if so, save it in 
the database.
- Implement functions to search for equations in the database based on their roots. For example, possible queries 
include finding all equations that have one of the specified roots or finding all equations that have exactly one root saved in the database.

## Technologies Used

The application utilizes the following technologies:

- Java 17
- Hibernate
- JUnit
- Maven
- Lombok
- MySQL

## Project Structure

The project contains following classes:

- Entity package
    - Equation - Represents an equation entity with its expression and associated roots.
    - Root - Represents a root entity associated with an equation.
- Evaluation package
    - EquationEvaluator - Utility class for evaluating mathematical equations.
    - StatefulEvaluationHelper - A helper class for stateful mathematical expression parsing and evaluation.
- Repository package
    - EquationRepository - Repository class for performing database operations related to Equation entities.
- Validation package
    - EquationValidator - Utility class for validating mathematical equations.
    - RootConverter - Utility class for converting a string to a Double value.
- MathHelperApplication - Main class for the MathHelper application, providing a console-based user interface.

## Getting Started

1. Clone the repository.
2. Navigate to the resources directory.
3. Set up the database configuration in `hibernate.properties`.
4. Build and run the application.

## Usage

- Follow the on-screen menu to perform various operations:
- Enter a new equation.
- Search for equations based on roots.
- Retrieve all stored equations.
- Find equations with a single root.
- Delete an equation by ID or expression.