package com.visma.of.solvertemplate.solver;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import com.visma.of.api.model.Bin;
import com.visma.of.api.model.BinPackingSolution;
import com.visma.of.api.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MipSolver {

    public static BinPackingSolution runMipSolver(MPSolver solver, int numItems, int numBins, Double[] weights, int binCapacity){
        MPVariable[][] x = new MPVariable[numItems][numBins];
        for (int i = 0; i < numItems; ++i) {
            for (int j = 0; j < numBins; ++j) {
                x[i][j] = solver.makeIntVar(0, 1, "");
            }
        }
        MPVariable[] y = new MPVariable[numBins];
        for (int j = 0; j < numBins; ++j) {
            y[j] = solver.makeIntVar(0, 1, "");
        }

        double infinity = java.lang.Double.POSITIVE_INFINITY;
        for (int i = 0; i < numItems; ++i) {
            MPConstraint constraint = solver.makeConstraint(1, 1, "");
            for (int j = 0; j < numBins; ++j) {
                constraint.setCoefficient(x[i][j], 1);
            }
        }
        // The bin capacity constraint for bin j is
        //   sum_i w_i x_ij <= C*y_j
        // To define this constraint, first subtract the left side from the right to get
        //   0 <= C*y_j - sum_i w_i x_ij
        //
        // Note: Since sum_i w_i x_ij is positive (and y_j is 0 or 1), the right side must
        // be less than or equal to C. But it's not necessary to add this constraint
        // because it is forced by the other constraints.

        for (int j = 0; j < numBins; ++j) {
            MPConstraint constraint = solver.makeConstraint(0, infinity, "");
            constraint.setCoefficient(y[j], binCapacity);
            for (int i = 0; i < numItems; ++i) {
                constraint.setCoefficient(x[i][j], -weights[i]);
            }
        }

        MPObjective objective = solver.objective();
        for (int j = 0; j < numBins; ++j) {
            objective.setCoefficient(y[j], 1);
        }
        objective.setMinimization();

        final MPSolver.ResultStatus resultStatus = solver.solve();
        BinPackingSolution solution = new BinPackingSolution();

        // Check that the problem has an optimal solution.
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Number of bins used: " + objective.value());
            solution.setNumberOfBins((int)Math.floor(objective.value()));
            List<Bin> bins = new ArrayList<>();
            double totalWeight = 0;
            for (int j = 0; j < numBins; ++j) {
                if (y[j].solutionValue() == 1) {
                    System.out.println("\nBin " + j + "\n");
                    Bin bin = new Bin();
                    bin.setBinNumber(j);
                    List<Item> items = new ArrayList<>();
                    double binWeight = 0;
                    for (int i = 0; i < numItems; ++i) {
                        if (x[i][j].solutionValue() == 1) {
                            System.out.println("Item " + i + " - weight: " + weights[i]);
                            Item item = new Item();
                            item.setItemNumber(i);
                            item.setWeight(weights[i]);
                            binWeight += weights[i];
                            items.add(item);
                        }
                    }
                    System.out.println("Packed bin weight: " + binWeight);
                    bin.setItems(items);
                    bin.setWeight(binWeight);
                    totalWeight += binWeight;
                    bins.add(bin);
                }
            }
            System.out.println("\nTotal packed weight: " + totalWeight);
            solution.setNumberOfBins(bins.size());
            solution.setBins(bins);
            solution.setTotalPacked(totalWeight);
        } else {
            System.err.println("The problem does not have an optimal solution.");
        }
        return solution;
    }
}
