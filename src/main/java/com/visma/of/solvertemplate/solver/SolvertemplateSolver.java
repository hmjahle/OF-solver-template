package com.visma.of.solvertemplate.solver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.visma.of.api.model.BinPackingDataProvider;
import com.visma.of.api.model.BinPackingResult;
import com.visma.of.solverapi.Solver;
import com.visma.of.solverapi.SolverListener;
import com.visma.of.solvertemplate.solver.model.BinPackingModel;
import com.visma.of.solvertemplate.solver.solution.BinPackingSolution;
import com.visma.of.solvertemplate.solver.solvers.RandomSolution;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class SolvertemplateSolver extends Solver {


    @Override
    public void solve() throws JsonProcessingException, ParseException, Exception {
        JSONObject jsonObject = getJsonPayload();
        BinPackingDataProvider dataProvider = Solver.readFromJsonObjectMapper(BinPackingDataProvider.class, jsonObject.toJSONString());
        BinPackingModel model = BinPackingModel.generateModelFromDataProvider(dataProvider);

        BinPackingSolution solution = RandomSolution.generateBestFitSolution(model);
        BinPackingResult result = BinPackingSolution.generateResult(solution);
        JSONObject jsonSolution = Solver.objectToJsonObject(result);
        for(SolverListener listener : getListeners()){
            listener.newBestSolutionFound(jsonSolution);
        }
        System.out.println(jsonSolution);
    }
}
