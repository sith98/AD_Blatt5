import java.util.Arrays;
import java.util.Random;

public class Aufgabe1 {
    
    // end is inclusive, middle is inclusive end of first half
    static void merge(int[] numbers, int start, int end, int middle, int[] helper) {
        int leftIndex = start;
        int rightIndex = middle + 1;
        for (int i = start; i <= end; i++) {
            if (leftIndex > middle) {
                helper[i] = numbers[rightIndex++];
            } else if (rightIndex > end) {
                helper[i] = numbers[leftIndex++];
            } else {
                if (numbers[leftIndex] <= numbers[rightIndex]) {
                    helper[i] = numbers[leftIndex++];
                } else {
                    helper[i] = numbers[rightIndex++];
                }
            }
        }
        System.arraycopy(helper, start, numbers, start, end + 1 - start);
    }
    
    private static void mergeSort(int[] numbers) {
        var helper = new int[numbers.length];
        
        for (int step = 1; step < numbers.length; step *= 2) {
            for (int i = 0; i < numbers.length - step; i += step * 2) {
                int end = Math.min(i + step * 2, numbers.length);
                int middle = i + step;
                merge(numbers, i, end - 1, middle - 1, helper);
            }
        }
    }
    
    /**
     * T(0) = 1
     * T(n) = T(n/2) + 1
     * => Master-Methode:
     * n^log_2(1) = n^0 = 1 -> Fall 2:
     * T(n) = Θ(log n)
     */
    private static boolean binarySearch(int a[], int num, int start, int end) {
        if (end < start) {
            return false;
        }
        
        int middleIndex = (start + end) / 2;
        int middle = a[middleIndex];
        
        if (middle == num) {
            return true;
        }
        if (num < middle) {
            return binarySearch(a, num, start, middleIndex - 1);
        }
        return binarySearch(a, num, middleIndex + 1, end);
    }
    
    // containsSum terminiert nach Θ(n * log(n)) + n * Θ(n * log(n)) = Θ(n * log(n)) Schritten
    private static boolean containsSum(int[] a, int s) {
        // Sortiert a in Θ(n * log(n))
        mergeSort(a);
        // n Durchläufe
        for (int num : a) {
            int missingNum = s - num;
            // binarySearch hat T(n) = Θ(log n)
            if (binarySearch(a, missingNum, 0, a.length - 1)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * T(n) = Θ(n * log(n)) + Θ(n) = Θ(n * log(n))
     */
    private static boolean containsSum2(int[] a, int s) {
        // T(n) = Θ(n * log(n))
        mergeSort(a);
        
        int i = 0;
        int j = a.length - 1;
        
        // In jedem Durchlauf wird i um 1 erhöht oder j um 1 verkleinert
        // (Es sei denn sum = s)
        // T(n) = Θ(n)
        while (i < j) {
            int left = a[i];
            int right = a[j];
            int sum = left + right;
            
            if (sum == s) {
                return true;
            } else if (sum < s) {
                i += 1;
            } else {
                j -= 1;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        var r = new Random();
        int[] a = new int[77];
    
        for (int i = 0; i < 10_000; i++) {
            for (int j = 0; j < a.length; j++) {
                a[j] = r.nextInt(10_000);
            }
            int s = a[0] + a[1];
            if (!containsSum2(a, s)){
                System.out.println("False");
                System.out.println(Arrays.toString(a));
            }
        }
        System.out.println("---------");
    }
    
}
