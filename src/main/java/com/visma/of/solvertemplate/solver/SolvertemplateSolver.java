package com.visma.of.solvertemplate.solver;

import com.google.ortools.linearsolver.MPSolver;
import com.visma.of.api.model.BinPackingDataProvider;
import com.visma.of.api.model.BinPackingSolution;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.SolverListener;
import com.visma.of.solverapi.SolverProvider;
import org.json.simple.JSONObject;

public class SolvertemplateSolver extends Solver {


    static {
        SolverProvider.registerSolver(new SolvertemplateSolver());
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
        BinPackingSolution solution = MipSolver.runMipSolver(solver, model.getNumItems(), model.getNumBins(), model.getWeights(), model.getBinCapacity());
        JSONObject jsonSolution = Solver.objectToJsonObject(solution);
        for(SolverListener listener : getListeners()){
            listener.newBestSolutionFound(jsonSolution);
        }
    }
    


}
