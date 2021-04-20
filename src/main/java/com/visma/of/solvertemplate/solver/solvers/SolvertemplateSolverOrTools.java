package com.visma.of.solvertemplate.solver.solvers;

import com.google.ortools.linearsolver.MPSolver;
import com.visma.of.api.model.Request;
import com.visma.of.api.model.BinPackingResult;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.SolverListener;
import com.visma.of.solverapi.SolverProvider;
import com.visma.of.solvertemplate.constants.Constants;
import com.visma.of.solvertemplate.solver.model.BinPackingModel;
import com.visma.of.solvertemplate.solver.model.ModelFactory;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SolvertemplateSolverOrTools extends Solver {

    private BinPackingModel model;

    static {
        //SolverProvider.registerSolver(new SolvertemplateSolverOrTools());
        System.out.println("Path is: " + System.getProperty("java.library.path"));
        System.loadLibrary("jniortools");
    }

    @Override
    public void initializeSolver() throws Exception {
        JSONObject jsonObject = getJsonPayload();
        Request request = Solver.readFromJsonObjectMapper(Request.class, jsonObject.toJSONString());
        model = ModelFactory.generateModelFromDataProvider(request);
    }

    @Override
    public void solve() throws Exception {
        // Create the linear solver with the SCIP backend.
        MPSolver solver = new MPSolver("CBC", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);
        BinPackingResult solution = MipSolver.runMipSolver(solver, model.getNumItems(), model.getNumBins(), model.getWeights(), model.getBinCapacity());
        JSONObject jsonSolution = Solver.objectToJsonObject(solution);
        for (SolverListener listener : getListeners()) {
            listener.newBestSolutionFound(jsonSolution);
        }
    }

    @Override
    public Map<String, Double> getPayloadStatisticsAsNumbers() {
        Map<String, Double> orpStats = new HashMap<>();
        orpStats.put(Constants.PAYLOAD_STATISTICS_NUMBER_OF_BINS, (double) model.getNumBins());
        return orpStats;
    }

    @Override
    public JSONObject getSolverStatus() {
        return new JSONObject();
    }

    @Override
    public Map<String, Boolean> getSolverFeatureFlagDefaultValues() {
        return new HashMap<>();
    }


}
