package com.visma.of.solvertemplate.validator;

import com.visma.of.api.model.Request;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.Validator;
import com.visma.of.solverapi.ValidatorProvider;
import com.visma.of.solvertemplate.solver.model.BinPackingModel;
import com.visma.of.solvertemplate.solver.model.ModelFactory;
import org.json.simple.JSONObject;

import java.io.IOException;

public class SolvertemplateValidator extends Validator {


    static {
        ValidatorProvider.registerValidator(SolvertemplateValidator.class);
    }

    public SolvertemplateValidator() {
        super();
    }

    @Override
    public boolean validate(JSONObject openApiObject, JSONObject requestObject) throws IOException {
        return validatePayload(openApiObject, requestObject) && validateBinPackingData();
    }

    private boolean validateBinPackingData() {
        try {
            Request dataProvider = Solver.readFromJsonObjectMapper(Request.class, super.getJsonPayload().toJSONString());
            BinPackingModel model = ModelFactory.generateModelFromDataProvider(dataProvider);
            return crossValidate(model);
        } catch (Exception e) {
            super.addErrorMessage("Could not load data into model. " + "Error: " + e.getLocalizedMessage());
            return false;
        }
    }

    private boolean crossValidate(BinPackingModel model) {
        for (Double weight : model.getWeights()) {
            if (weight > model.getBinCapacity()) {
                super.addErrorMessage("One of the weights was larger than the bin capacity");
                return false;
            }
        }
        return true;
    }


}
