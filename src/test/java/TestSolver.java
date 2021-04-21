import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.visma.of.api.model.SolverStatus;
import com.visma.of.solvertemplate.solver.SolvertemplateSolver;
import com.visma.of.solvertemplate.solver.solvers.SolvertemplateSolverOrTools;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestSolver {

    private static final JSONParser parser = new JSONParser();
    private static final ObjectMapper objectMapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .setSerializationInclusion(JsonInclude.Include.ALWAYS);


    @Test
    public void testSolver() throws Exception {
        SolvertemplateSolverOrTools solvertemplateSolver = new SolvertemplateSolverOrTools();
        File file = new File("src/test/resources/com.visma.of.solvertemplate.solver/binPackingData.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode payload = mapper.readTree(file);
        String jsonString = payload.toString();
        JSONObject model = mapper.readValue(jsonString, JSONObject.class);
        solvertemplateSolver.initialize(model);
        Assert.assertNotNull(solvertemplateSolver.getJsonPayload());
        solvertemplateSolver.solve();
    }

    @Test
    public void testSolver2() throws Exception {
        SolvertemplateSolver solvertemplateSolver = new SolvertemplateSolver();
        File file = new File("src/test/resources/com.visma.of.solvertemplate.solver/binPackingData.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode payload = mapper.readTree(file);
        String jsonString = payload.toString();
        JSONObject model = mapper.readValue(jsonString, JSONObject.class);
        solvertemplateSolver.initialize(model);
        Assert.assertNotNull(solvertemplateSolver.getJsonPayload());
        solvertemplateSolver.solve();
        Assert.assertTrue((boolean) solvertemplateSolver.getSolverStatus(parser, objectMapper).get(SolverStatus.JSON_PROPERTY_HAS_LIKELY_CONVERGED));
    }

}
