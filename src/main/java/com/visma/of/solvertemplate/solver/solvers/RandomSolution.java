package com.visma.of.solvertemplate.solver;

import com.visma.of.api.model.BinPackingSolution;

public class RandomSolution {

    public static BinPackingSolution generateRandomSolution(BinPackingModel model){
        // Initialize result (Count of bins)
        BinPackingSolution solution = new BinPackingSolution();
        int res = 0;

        // Create an array to store remaining space in bins
        // there can be at most n bins
        double[]bin_rem = new double[model.getNumBins()];

        // Place items one by one
        for (int i = 0; i < model.getNumBins(); i++) {
            // Find the best bin that ca\n accomodate
            // weight[i]

            // Initialize minimum space left and index
            // of best bin
            double min = model.getBinCapacity() + 1;
            int bi = 0;

            for (int j = 0; j < res; j++)
            {
                if (bin_rem[j] >= model.getWeights()[i] && bin_rem[j] - model.getWeights()[i] < min) {
                    bi = j;
                    min = bin_rem[j] - model.getWeights()[i];
                }
            }

            // If no bin could accommodate weight[i],
            // create a new bin
            if (min == model.getBinCapacity() + 1) {
                bin_rem[res] = model.getBinCapacity() - model.getWeights()[i];
                res++;
            } else // Assign the item to best bin
                bin_rem[bi] -= model.getWeights()[i];
        }
        solution.setNumberOfBins(res);
        return solution;
    }
}
