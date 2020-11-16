package com.visma.of.solvertemplate.solver.solvers;

import com.visma.of.solvertemplate.solver.model.BinPackingModel;
import com.visma.of.solvertemplate.solver.solution.Bin;
import com.visma.of.solvertemplate.solver.solution.BinPackingSolution;
import com.visma.of.solvertemplate.solver.solution.Item;


public class HeuristicSolver {

    private HeuristicSolver(){
    }

    public static BinPackingSolution generateBestFitSolution(BinPackingModel model){
        BinPackingSolution solution = new BinPackingSolution(model);
        int res = 0;
        for (Item item : solution.getItems()) {
            double min = model.getBinCapacity() + 1.0 ;
            int bi = 0;

            for (int j = 0; j < res; j++)
            {
                if (solution.getBins().get(j).getRemainingCapacity() >= item.getWeight() && solution.getBins().get(j).getRemainingCapacity() - item.getWeight() < min) {
                    bi = j;
                    min = solution.getBins().get(j).getRemainingCapacity() - item.getWeight();
                }
            }
            if (min == model.getBinCapacity() + 1) {
                solution.addBin(new Bin(model.getBinCapacity(), res));
                solution.addItem(item.getItemNumber(), res);
                res++;
            } else
                solution.addItem(item.getItemNumber(), bi);
        }
        return solution;
    }
}
