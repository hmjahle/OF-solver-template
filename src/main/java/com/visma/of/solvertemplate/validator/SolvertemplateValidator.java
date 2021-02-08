package com.visma.of.solvertemplate.validator;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.visma.of.api.model.Request;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.Validator;
import com.visma.of.solverapi.ValidatorProvider;
import com.visma.of.solvertemplate.solver.model.BinPackingModel;
import com.visma.of.solvertemplate.solver.model.ModelFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class SolvertemplateValidator extends Validator {

    private static final String openapiPath = "build/resources/main/openapi.json";
    private static final String requestPayloadPath = "build/resources/main/spec/schemas/request/Request.json";
    private static final String requestValidationSchemaPath = "build/resources/main/requestValidationSchema.json";


    static {
        ValidatorProvider.registerValidator(new SolvertemplateValidator());
    }

    public SolvertemplateValidator() {
        super();
    }

    @Override
    public boolean validate() throws ParseException, IOException, ProcessingException {
        return validatePayload(openapiPath, requestPayloadPath, requestValidationSchemaPath) && validateBinPackingData();
    }

    private boolean validateBinPackingData() {
        try {
            Request dataProvider = Solver.readFromJsonObjectMapper(Request.class, super.getJsonPayload().toJSONString());
            BinPackingModel model = ModelFactory.generateModelFromDataProvider(dataProvider);
            return crossValidate(model);
        } catch (Exception e) {
            super.addErrorMessages("Could not load data into model. " + "Error: " + e.getLocalizedMessage());
            return false;
        }
    }

    private boolean crossValidate(BinPackingModel model) {
        for (Double weight : model.getWeights()) {
            if (weight > model.getBinCapacity()) {
                super.addErrorMessages("One of the weights was larger than the bin capacity");
                return false;
            }
        }
        return true;
    }


}
