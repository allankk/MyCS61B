import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        Random r = new Random();
        List<Double> yValues = new ArrayList<>();
        List<Double> y2Values = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();
        BST<Integer> chartBST = new BST<Integer>();

        for (int i = 0; i < 1000; i += 1) {
            chartBST.add(RandomGenerator.getRandomInt(300));
            xValues.add(i);
            yValues.add(chartBST.averageDepth());
        }

        for (int i = 0; i < 1000; i++) {
            y2Values.add(ExperimentHelper.optimalAverageDepth(i));
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Node count").yAxisTitle("Average depth").build();
        chart.addSeries("Randomly generated nodes", xValues, yValues);
        chart.addSeries("Optimal tree", xValues, y2Values);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        List<Integer> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();



    }

    public static void experiment3() {
    }

    public static void main(String[] args) {
        experiment1();
    }
}
