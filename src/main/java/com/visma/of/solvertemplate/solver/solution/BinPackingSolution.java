package com.visma.of.solvertemplate.solver.solution;

import com.visma.of.api.model.BinPackingResult;
import com.visma.of.api.model.BinResult;
import com.visma.of.api.model.ItemResult;
import com.visma.of.solvertemplate.solver.model.BinPackingModel;

import java.util.ArrayList;
import java.util.List;

public class BinPackingSolution {

    private List<Bin> bins;
    private double weight;
    private List<Item> items;

    public BinPackingSolution(BinPackingModel model) {
        bins = new ArrayList<>();
        weight = 0;
        setupItems(model);
    }

    public void addBin(Bin bin){
        bins.add(bin);
    }

    public List<Bin> getBins() {
        return bins;
    }

    public void setBins(List<Bin> bins) {
        this.bins = bins;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void addItem(int itemNumber, int binNumber){
        bins.get(binNumber).addItem(items.get(itemNumber));
        weight += items.get(itemNumber).getWeight();
    }

    private void setupItems(BinPackingModel model){
        items = new ArrayList<>();
        for(int i = 0; i<model.getWeights().length; i++){
            items.add(new Item(model.getWeights()[i], i));
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public static BinPackingResult generateResult(BinPackingSolution solution){
        BinPackingResult result = new BinPackingResult();
        List<BinResult> binResults = new ArrayList<>();
        for(Bin bin : solution.getBins()){
            BinResult binResult = new BinResult();
            binResult.setBinNumber(bin.getBinNumber());
            binResult.setWeight(bin.getWeight());
            List<ItemResult> itemResults = new ArrayList<>();
            for(Item item : bin.getItems()){
                ItemResult itemResult = new ItemResult();
                itemResult.setItemNumber(item.getItemNumber());
                itemResult.setWeight(item.getWeight());
                itemResults.add(itemResult);
            }
            binResult.setItems(itemResults);
            binResults.add(binResult);
        }
        result.setBins(binResults);
        result.setNumberOfBins(binResults.size());
        result.setTotalPacked(solution.getWeight());
        return result;
    }
}
