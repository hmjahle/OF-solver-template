package com.visma.of.solvertemplate.solver.solution;

public class Item {

    private final double weight;
    private final int itemNumber;

    public Item(double weight, int itemNumber) {
        this.weight = weight;
        this.itemNumber = itemNumber;
    }

    public double getWeight() {
        return weight;
    }


    public int getItemNumber() {
        return itemNumber;
    }

}
