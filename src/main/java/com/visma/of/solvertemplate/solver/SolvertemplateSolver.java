package com.visma.of.solvertemplate.solver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.visma.of.api.model.BinPackingResult;
import com.visma.of.api.model.Request;
import com.visma.of.api.model.SolverStatus;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.SolverListener;
import com.visma.of.solverapi.SolverProvider;
import com.visma.of.solvertemplate.constants.Constants;
import com.visma.of.solvertemplate.solver.model.BinPackingModel;
import com.visma.of.solvertemplate.solver.model.ModelFactory;
import com.visma.of.solvertemplate.solver.solution.BinPackingSolution;
import com.visma.of.solvertemplate.solver.solvers.HeuristicSolver;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class SolvertemplateSolver extends Solver {

    private BinPackingModel model;
    private boolean hasLikelyConverged;

    static {
        SolverProvider.registerSolver(new SolvertemplateSolver());
    }

    @Override
    public void initializeSolver() throws Exception {
        JSONObject jsonObject = getJsonPayload();
        Request dataProvider = Solver.readFromJsonObjectMapper(Request.class, jsonObject.toJSONString());
        model = ModelFactory.generateModelFromDataProvider(dataProvider);
        hasLikelyConverged = false;
    }

    @Override
    public void solve() throws Exception {
        BinPackingSolution solution = HeuristicSolver.generateBestFitSolution(model);
        BinPackingResult result = BinPackingSolution.generateResult(solution);
        JSONObject jsonSolution = Solver.objectToJsonObject(result);
        hasLikelyConverged = true;

        if (getFeatureFlags() != null && getFeatureFlags().get(Constants.OF_TEST_FLAG)) {
            System.out.println("Feature flag in solver: SUCCESS");
        }

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
    public JSONObject getSolverStatus() throws JsonProcessingException, ParseException {
        SolverStatus solverStatus = new SolverStatus().hasLikelyConverged(hasLikelyConverged);
        JSONObject solverStatusJson = Solver.objectToJsonObject(solverStatus);
        return solverStatusJson;
    }

    @Override
    public Map<String, Boolean> getSolverFeatureFlagDefaultValues() {
        Map<String, Boolean> featureFlags = new HashMap<>();
        featureFlags.put(Constants.OF_TEST_FLAG, false);
        return featureFlags;
    }
}
