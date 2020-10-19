package com.visma.of.solvertemplate.solver.solution;

public class Item {

    private double weight;
    private int itemNumber;

    public Item(double weight, int itemNumber) {
        this.weight = weight;
        this.itemNumber = itemNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
}
