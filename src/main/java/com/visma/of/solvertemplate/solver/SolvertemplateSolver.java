package com.visma.of.solvertemplate.solver;

import com.visma.of.api.model.BinPackingDataProvider;
import com.visma.of.api.model.BinPackingResult;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.SolverListener;
import com.visma.of.solverapi.SolverProvider;
import com.visma.of.solvertemplate.constants.Constants;
import com.visma.of.solvertemplate.solver.model.BinPackingModel;
import com.visma.of.solvertemplate.solver.solution.BinPackingSolution;
import com.visma.of.solvertemplate.solver.solvers.HeuristicSolver;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SolvertemplateSolver extends Solver {

    static {
        SolverProvider.registerSolver(new SolvertemplateSolver());
    }

    @Override
    public void solve() throws Exception {
        JSONObject jsonObject = getJsonPayload();
        BinPackingDataProvider dataProvider = Solver.readFromJsonObjectMapper(BinPackingDataProvider.class, jsonObject.toJSONString());
        BinPackingModel model = BinPackingModel.generateModelFromDataProvider(dataProvider);

        BinPackingSolution solution = HeuristicSolver.generateBestFitSolution(model);
        BinPackingResult result = BinPackingSolution.generateResult(solution);
        JSONObject jsonSolution = Solver.objectToJsonObject(result);

        if (getFeatureFlags() != null && getFeatureFlags().get(Constants.OF_TEST_FLAG)){
            System.out.println("Feature flag in solver: SUCCESS");
        }
        
        for(SolverListener listener : getListeners()){
            listener.newBestSolutionFound(jsonSolution);
            listener.solverFinished();
        }
    }

    @Override
    public Map<String, Boolean> getSolverFeatureFlagDefaultValues() {
        Map<String, Boolean> featureFlags = new HashMap<>();
        featureFlags.put(Constants.OF_TEST_FLAG, false);
        return featureFlags;
    }
}
