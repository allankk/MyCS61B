package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.random;
import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {

    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Clorus(){
        super("clorus");
        energy = 1.0;
    }


    public Color color(){
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    @Override
    public void move() {
        energy -= 0.03;
        checkEnergy();
    }


    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Creature replicate() {
        Clorus c = new Clorus(energy/2);
        energy /= 2;

        return c;
    }

    @Override
    public void stay() {
        energy -= 0.01;
        checkEnergy();
    }

    public void checkEnergy(){
        if (energy < 0) {
            energy = 0;
        }
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();

        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("empty")) {
                emptyNeighbors.add(dir);
                continue;
            }
            if (neighbors.get(dir).name().equals("plip")) {
                plipNeighbors.add(dir);
                continue;
            }
        }

        if (emptyNeighbors.isEmpty()) {
            stay();
            return new Action(Action.ActionType.STAY);
        } else if (!plipNeighbors.isEmpty()) {
            Direction dir = randomEntry(plipNeighbors);
            attack((Creature) neighbors.get(dir));
            return new Action(Action.ActionType.ATTACK, dir);
        } else if (energy >= 1) {
            Direction dir = randomEntry(emptyNeighbors);
            replicate();
            return new Action(Action.ActionType.REPLICATE, dir);
        } else {
            Direction dir = randomEntry(emptyNeighbors);
            move();
            return new Action(Action.ActionType.MOVE, dir);
        }

    }
}
