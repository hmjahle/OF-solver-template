package com.visma.of.solvertemplate.solver.solvers;

import com.google.ortools.linearsolver.MPSolver;
import com.visma.of.api.model.BinPackingDataProvider;
import com.visma.of.api.model.BinPackingResult;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.SolverListener;
import com.visma.of.solverapi.SolverProvider;
import com.visma.of.solvertemplate.solver.model.BinPackingModel;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SolvertemplateSolverOrTools extends Solver {


    static {
        SolverProvider.registerSolver(new SolvertemplateSolverOrTools());
        System.out.println("Path is: " + System.getProperty("java.library.path"));
        System.loadLibrary("jniortools");
    }
    
    @Override
    public void solve() throws Exception {
        JSONObject jsonObject = getJsonPayload();
        BinPackingDataProvider dataProvider = Solver.readFromJsonObjectMapper(BinPackingDataProvider.class, jsonObject.toJSONString());
        BinPackingModel model = BinPackingModel.generateModelFromDataProvider(dataProvider);

        // Create the linear solver with the SCIP backend.
        MPSolver solver = new MPSolver("CBC", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);
        if (solver == null) {
            System.out.println("Could not create solver CBC");
            return;
        }
        BinPackingResult solution = MipSolver.runMipSolver(solver, model.getNumItems(), model.getNumBins(), model.getWeights(), model.getBinCapacity());
        JSONObject jsonSolution = Solver.objectToJsonObject(solution);
        for(SolverListener listener : getListeners()){
            listener.newBestSolutionFound(jsonSolution);
        }
        System.out.println(jsonSolution);
    }

    @Override
    public Map<String, Boolean> getSolverFeatureFlagDefaultValues() {
        return new HashMap<>();
    }


}
