package creatures;
import static org.junit.Assert.*;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

public class TestClorus {

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2);
        c.replicate();
        assertEquals(1, c.energy(), 0.01);

        c = new Clorus(4);
        c.replicate();
        assertEquals(2, c.energy(), 0.01);
    }

    @Test
    public void testAttack() {
        Clorus c = new Clorus(2);
        Plip p = new Plip(2);
        c.attack(p);
        assertEquals(4, c.energy(), 0.01);

        Clorus c2 = new Clorus(2);
        Plip p2 = new Plip(0);
        c2.attack(p2);
        assertEquals(2, c2.energy(), 0.01);
    }

    @Test
    public void actionChoose() {
        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Plip nearby, attack
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> plipNear = new HashMap<Direction, Occupant>();
        plipNear.put(Direction.TOP, new Empty());
        plipNear.put(Direction.BOTTOM, new Plip(1.0));
        plipNear.put(Direction.LEFT, new Impassible());
        plipNear.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(plipNear);
        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);


        // Energy < 1; move to a random empty space
        c = new Clorus(.96);

        HashMap<Direction, Occupant> oneEmpty = new HashMap<Direction, Occupant>();
        oneEmpty.put(Direction.TOP, new Empty());
        oneEmpty.put(Direction.BOTTOM, new Impassible());
        oneEmpty.put(Direction.LEFT, new Impassible());
        oneEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(oneEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.TOP);

        assertEquals(expected, actual);
    }



    @Test
    public void color() {
        Clorus c = new Clorus();
        Color a = new Color(34, 0 ,231);
        assertEquals(a, c.color());
    }


}
