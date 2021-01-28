package com.visma.of.solvertemplate.solver.model;

import com.visma.of.api.model.Request;

public class ModelFactory {

    private ModelFactory(){

    }

    public static BinPackingModel generateModelFromDataProvider(Request request){
        Double[] weights = request.getWeights().toArray(new Double[0]);
        int numItems = weights.length;
        int numBins = weights.length;
        int binCapacity = request.getBinCapacity();
        return new BinPackingModel(weights, numItems, numBins, binCapacity);
    }
}
