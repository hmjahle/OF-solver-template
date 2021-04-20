import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visma.of.api.model.SolverStatus;
import com.visma.of.solvertemplate.solver.SolvertemplateSolver;
import com.visma.of.solvertemplate.solver.solvers.SolvertemplateSolverOrTools;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestSolver {


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
        Assert.assertTrue((boolean) solvertemplateSolver.getSolverStatus().get(SolverStatus.JSON_PROPERTY_HAS_LIKELY_CONVERGED));
    }

}
