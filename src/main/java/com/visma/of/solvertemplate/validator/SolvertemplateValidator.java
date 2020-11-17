package com.visma.of.solvertemplate.validator;

import com.visma.of.api.model.BinPackingDataProvider;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.Validator;
import com.visma.of.solverapi.ValidatorProvider;
import com.visma.of.solvertemplate.solver.model.BinPackingModel;

import java.util.ArrayList;
import java.util.List;

public class SolvertemplateValidator extends Validator {

    private final List<String> errorMessages;

    static {
        ValidatorProvider.registerValidator(new SolvertemplateValidator());
    }

    public SolvertemplateValidator() {
        super();
        errorMessages = new ArrayList<>();
    }

    @Override
    public boolean validate() {
        return validateBinPackingData();
    }

    @Override
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    private boolean validateBinPackingData() {
        try {
            BinPackingDataProvider dataProvider = Solver.readFromJsonObjectMapper(BinPackingDataProvider.class, super.getJsonPayload().toJSONString());
            BinPackingModel model = BinPackingModel.generateModelFromDataProvider(dataProvider);
            return crossValidate(model);
        } catch (Exception e) {
            errorMessages.add("Could not load data into model. " + "Error: " + e.getLocalizedMessage());
            return false;
        }
    }

    private boolean crossValidate(BinPackingModel model) {
        for (Double weight : model.getWeights()) {
            if (weight > model.getBinCapacity()) {
                errorMessages.add("One of the weights was larger than the bin capacity");
                return false;
            }
        }
        return true;
    }


}
