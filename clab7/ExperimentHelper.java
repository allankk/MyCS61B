/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {

        double optimalAverageDepth = optimalAverageDepth(N);

        return (int) (optimalAverageDepth * N);
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {

        int numberOfNodesRemaining = N;
        int sumOfDepths = 0;
        int currentDepth = 0;
        int i = 0;

        while (numberOfNodesRemaining > 0) {
            if (numberOfNodesRemaining < Math.pow(2, i)) {
                double counter = i * numberOfNodesRemaining;
                sumOfDepths += (int) counter;
                break;
            }
            double currentDepthCounter = i * Math.pow(2, i);
            numberOfNodesRemaining -= Math.pow(2, i);
            sumOfDepths += currentDepthCounter;
            i++;
        }

        return sumOfDepths / (double) N;
    }



}
