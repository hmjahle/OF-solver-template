package com.visma.of.solvertemplate.solver.model;

public class BinPackingModel {

    private final Double[] weights;
    private final int numItems;
    private final int numBins;
    private final int binCapacity;

    public BinPackingModel(Double[] weights, int numItems, int numBins, int binCapacity) {
        this.weights = weights;
        this.numItems = numItems;
        this.numBins = numBins;
        this.binCapacity = binCapacity;
    }

    public Double[] getWeights() {
        return weights;
    }

    public int getNumItems() {
        return numItems;
    }

    public int getNumBins() {
        return numBins;
    }

    public int getBinCapacity() {
        return binCapacity;
    }
}
