package Connect4;

/**
 * A collection of utility methods that may or may not come in handy.
 * @author Owen Jow
 */
public class Utilities {
    /**
     * Returns the index of the maximum value in the integer array ARR.
     * @param the array to be considered
     * @return array index of the max value
     */
    public static int maxValueIndex(int[] arr) {
        // Initial values will correspond to the last item in the array
        int maxIndex = arr.length - 1;
        int maxValue = arr[maxIndex];
        
        int value; // in order to cut down on the number of array references
        for (int i = maxIndex - 1; i >= 0; i--) { // descend backward just to be cool
            value = arr[i];
            if (value > maxValue) {
                maxIndex = i;
                maxValue = value;
            }
        }
        
        return maxIndex;
    }
    
    /**
     * Returns the index of the minimum value in the integer array ARR.
     * @param the array to be considered
     * @return array index of the min value
     */
    public static int minValueIndex(int[] arr) {
        int minIndex = arr.length - 1;
        int minValue = arr[minIndex];
        
        int value;
        for (int i = minIndex - 1; i >= 0; i--) {
            value = arr[i];
            if (value < minValue) {
                minIndex = i;
                minValue = value;
            }
        }
        
        return minIndex;
    }
}