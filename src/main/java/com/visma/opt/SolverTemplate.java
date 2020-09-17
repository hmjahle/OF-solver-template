package com.visma.opt;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.SolverProvider;
import org.json.simple.JSONObject;

public class SolverTemplate extends Solver {


    static {
        SolverProvider.registerSolver(new SolverTemplate());
        System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary("jniortools");
    }

    @Override
    public void solve() {
        JSONObject jsonObject = getJsonPayload();

        MPSolver solver = new MPSolver("Mip-Solver", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);
        // Create the variables x and y.
        MPVariable x = solver.makeNumVar(0.0, 1.0, "x");
        MPVariable y = solver.makeNumVar(0.0, 2.0, "y");

        System.out.println("Number of variables = " + solver.numVariables());

        // Create a linear constraint, 0 <= x + y <= 2.
        MPConstraint ct = solver.makeConstraint(0.0, 2.0, "ct");
        ct.setCoefficient(x, 1);
        ct.setCoefficient(y, 1);

        System.out.println("Number of constraints = " + solver.numConstraints());

        // Create the objective function, 3 * x + y.
        MPObjective objective = solver.objective();
        objective.setCoefficient(x, 3);
        objective.setCoefficient(y, 1);
        objective.setMaximization();

        solver.solve();
        System.out.println("Solution:");
        System.out.println("Objective value = " + objective.value());
        System.out.println("x = " + x.solutionValue());
        System.out.println("y = " + y.solutionValue());
    }

}
