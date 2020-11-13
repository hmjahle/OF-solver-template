package com.visma.of.solvertemplate.solver.solution;

import java.util.ArrayList;
import java.util.List;

public class Bin {

    private final int binNumber;
    private final List<Item> items;
    private double remainingCapacity;
    private double weight;

    public Bin(double binCapacity, int binNumber) {
        items = new ArrayList<>();
        remainingCapacity = binCapacity;
        this.binNumber = binNumber;
        weight = 0;
    }

    public void addItem(Item item) {
        items.add(item);
        remainingCapacity -= item.getWeight();
        weight += item.getWeight();
    }

    public List<Item> getItems() {
        return items;
    }

    public int getBinNumber() {
        return binNumber;
    }

    public double getRemainingCapacity() {
        return remainingCapacity;
    }

    public double getWeight() {
        return weight;
    }
}
