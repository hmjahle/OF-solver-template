package com.visma.of.solvertemplate.solver.model;

import com.visma.of.api.model.BinPackingDataProvider;

public class ModelFactory {

    private ModelFactory(){

    }

    public static BinPackingModel generateModelFromDataProvider(BinPackingDataProvider dataProvider){
        Double[] weights = dataProvider.getWeights().toArray(new Double[0]);
        int numItems = weights.length;
        int numBins = weights.length;
        int binCapacity = dataProvider.getBinCapacity();
        return new BinPackingModel(weights, numItems, numBins, binCapacity);
    }
}
