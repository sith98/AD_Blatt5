import java.util.Arrays;

public class Aufgabe4 {
    static int[] countSort(int[] a, int k) {
        // k
        int[] help = new int[k + 1];
        for (int num : a) {
            // n
            help[num] += 1;
        }
        // k
        for (int i = 1; i < help.length; i++) {
            help[i] += help[i - 1];
        }
        
        int[] a2 = new int[a.length];
        for (int num : a) {
            // n
            a2[help[num] - 1] = num;
            // n
            help[num] -= 1;
        }
        
        return a2;
    }
    
    public static void main(String[] args) {
        int[] a = {1, 5, 2, 7, 3, 5, 8, 9, 3, 4, 3, 3, 3, 9, 0, 3};
        System.out.println(Arrays.toString(countSort(a, 10)));
    }
}
