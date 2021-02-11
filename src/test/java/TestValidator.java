import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visma.of.solvertemplate.constants.Constants;
import com.visma.of.solvertemplate.validator.SolvertemplateValidator;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestValidator {

    private static String openApiPath = Constants.class.getResource("/openapi.json").getPath();

    @Test
    public void testValidatorInvalid() throws Exception {
        SolvertemplateValidator validator = setupTestValidator("src/test/resources/com.visma.of.solvertemplate.solver/binPackingDataInvalid.json");
        boolean valid = validator.validate(openApiPath);
        System.out.println(validator.getErrorMessages());
        Assert.assertFalse(valid);
    }

    @Test
    public void testValidatorInvalidMissingField() throws Exception {
        SolvertemplateValidator validator = setupTestValidator("src/test/resources/com.visma.of.solvertemplate.solver/binPackingDataInvalidMissingField.json");
        boolean valid = validator.validate(openApiPath);
        System.out.println(validator.getErrorMessages());
        Assert.assertFalse(valid);
    }

    @Test
    public void testValidatorInvalidWrongField() throws Exception {
        SolvertemplateValidator validator = setupTestValidator("src/test/resources/com.visma.of.solvertemplate.solver/binPackingDataInvalidWrongField.json");
        boolean valid = validator.validate(openApiPath);
        System.out.println(validator.getErrorMessages());
        Assert.assertFalse(valid);
    }

    @Test
    public void testValidatorValid() throws Exception {
        SolvertemplateValidator validator = setupTestValidator("src/test/resources/com.visma.of.solvertemplate.solver/binPackingData.json");
        boolean valid = validator.validate(openApiPath);
        System.out.println(validator.getErrorMessages());
        Assert.assertTrue(valid);
    }

    private SolvertemplateValidator setupTestValidator(String testPayloadPath) throws IOException {
        SolvertemplateValidator validator = new SolvertemplateValidator();
        File file = new File(testPayloadPath);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode payload = mapper.readTree(file);
        String jsonString = payload.toString();
        JSONObject model = mapper.readValue(jsonString, JSONObject.class);
        validator.initialize(model);
        return validator;
    }
}