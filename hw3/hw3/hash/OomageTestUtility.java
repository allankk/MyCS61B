package hw3.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */

        Map<Integer, ArrayList> oomageMap = new HashMap<>();
        int N = oomages.size();

        for (int i = 0; i < M; i++) {
            oomageMap.put(i, new ArrayList<Oomage>());
        }

        for (Oomage each : oomages) {
            int bucketNum = (each.hashCode() & 0x7FFFFFFF) % M;
            oomageMap.get(bucketNum).add(each);
        }

        for (int i = 0; i < M; i++) {
            List<Oomage> each = oomageMap.get(i);
            if (each.size() < (N/50) || each.size() > (N/2.5)) {
                return false;
            }
        }
        return true;
    }
}
