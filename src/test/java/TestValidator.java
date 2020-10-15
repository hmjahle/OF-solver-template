import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visma.of.solvertemplate.validator.SolvertemplateValidator;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestValidator {

    @Test
    public void testValidatorValid() throws Exception {
        SolvertemplateValidator validator = new SolvertemplateValidator();
        File file = new File("src/test/resources/com.visma.of.solvertemplate.solver/binPackingDataInvalid.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode payload = mapper.readTree(file);
        String jsonString = payload.toString();
        JSONObject model = mapper.readValue(jsonString, JSONObject.class);
        validator.initialize(model);
        boolean valid = validator.validate();
        Assert.assertFalse(valid);
    }

    @Test
    public void testValidatorInvalid() throws Exception {
        SolvertemplateValidator validator = new SolvertemplateValidator();
        File file = new File("src/test/resources/com.visma.of.solvertemplate.solver/binPackingData.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode payload = mapper.readTree(file);
        String jsonString = payload.toString();
        JSONObject model = mapper.readValue(jsonString, JSONObject.class);
        validator.initialize(model);
        boolean valid = validator.validate();
        Assert.assertTrue(valid);
    }
}
