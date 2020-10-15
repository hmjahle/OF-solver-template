package com.visma.of.solvertemplate.validator;

import com.visma.of.api.model.BinPackingDataProvider;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.Validator;
import com.visma.of.solvertemplate.solver.BinPackingModel;

public class SolvertemplateValidator extends Validator {

    private BinPackingDataProvider dataProvider;
    private BinPackingModel model;
    private String errorMessage;

    public SolvertemplateValidator() {
        super();
        errorMessage = "";
    }

    @Override
    public boolean validate() {
        return validateBinPackingData();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    private boolean validateBinPackingData(){
        try {
            dataProvider = Solver.readFromJsonObjectMapper(BinPackingDataProvider.class, super.getJsonPayload().toJSONString());
            model = BinPackingModel.generateModelFromDataProvider(dataProvider);
            return crossValidate(model);
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage += "Could not load data into model. ";
            errorMessage += "Error: " + e.getLocalizedMessage();
            return false;
        }
    }

    private boolean crossValidate(BinPackingModel model){
        for(Double weight : model.getWeights()){
            if(weight > model.getBinCapacity()){
                errorMessage += "One of the weights was larger than the bin capacity";
                return false;
            }
        }
        return true;
    }


}
