import java.util.Arrays;
import java.util.Random;

public class Aufgabe4 {
    private static int[] countSort(int[] a, int k) {
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

    private static void swap(int[] numbers, int first, int second) {
        int temp = numbers[first];
        numbers[first] = numbers[second];
        numbers[second] = temp;
    }

    private static void heapify(int[] numbers, int rootIndex, int endIndex) {
        int leftChildIndex = rootIndex * 2 + 1;
        int rightChildIndex = rootIndex * 2 + 2;

        int root = numbers[rootIndex];

        if (leftChildIndex > endIndex) {
            // no children
            return;
        }

        int leftChild = numbers[leftChildIndex];
        if (rightChildIndex > endIndex) {
            // Only left child exists
            if (root < leftChild) {
                swap(numbers, rootIndex, leftChildIndex);
            }
        } else {
            // Both children exist
            int rightChild = numbers[rightChildIndex];
            if (root < rightChild && leftChild < rightChild) {
                // right child is the largest
                swap(numbers, rootIndex, rightChildIndex);
                heapify(numbers, rightChildIndex, endIndex);
            } else if (root < leftChild) {
                // left child is the largest
                swap(numbers, rootIndex, leftChildIndex);
                heapify(numbers, leftChildIndex, endIndex);
            }
        }
    }

    private static void heapSort(int[] numbers) {
        int endIndex = numbers.length - 1;

        int firstIndexWithChildren = (endIndex - 1) / 2;

        // build heap
        for (int i = firstIndexWithChildren; i >= 0; i--) {
            heapify(numbers, i, endIndex);
        }
        while (endIndex > 0) {
            swap(numbers, 0, endIndex);
            endIndex -= 1;
            heapify(numbers, 0, endIndex);
        }
    }

    private static void mapSort(int[] a, double c) {
        int newn = (int) ((double) a.length * c);
        int i, j = 0;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        int[] bin = new int[newn];

        for (i = 0; i < bin.length; i++) {
            bin[i] = -1;
        }
        for (i = 0; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
            }
            if (a[i] > max) {
                max = a[i];
            }
        }
        double dist = (double) (max - min) / (double) (bin.length - 1);

        for (i = 0; i < a.length; i++) {
            int t = (int) ((double) (a[i] - min) / dist);
            int insert = a[i];
            boolean left = bin[t] != -1 && insert <= bin[t];
            while (bin[t] != -1) {
                if (left) {
                    if (insert > bin[t]) {
                        int temp = insert;
                        insert = bin[t];
                        bin[t] = temp;
                    }
                    if (t > 0) {
                        t -= 1;
                    } else {
                        left = false;
                    }
                } else {
                    if (insert <= bin[t]) {
                        int temp = insert;
                        insert = bin[t];
                        bin[t] = temp;
                    }
                    if (t < bin.length - 1) {
                        t += 1;
                    } else {
                        left = true;
                    }
                }
            }
            bin[t] = insert;
        }
        for (i = 0; i < bin.length; i++) {
            if (bin[i] != -1) {
                a[j] = bin[i];
                j += 1;
            }
        }
    }

    private static Random r = new Random();

    private static int nextInt() {
        return 1_000 + r.nextInt(10_000 - 1_000 + 1);
    }

    private static int[] getRandomArray(int length) {
        int[] a = new int[length];
        for (int i = 0; i < a.length; i++) {
            a[i] = nextInt();
        }
        return a;
    }

    private static long measureMillis(int[] numbers, SortingAlgorithm algo) {
        int[] copy = copyOf(numbers);
        long start = System.currentTimeMillis();
        algo.sort(copy);
        long end =System.currentTimeMillis();
        System.out.println(isSorted(copy));
//        System.out.println(Arrays.toString(copy));
        return end - start;
    }

    private static boolean isSorted(int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < numbers[i - 1]) {
                return  false;
            }
        }
        return true;
    }

    private static int[] copyOf(int[] numbers) {
        return Arrays.copyOf(numbers, numbers.length);
    }

    public static void main(String[] args) {
        long heapMillis = 0, countMillis = 0, mapMillis = 0;



        int[] numbers = getRandomArray(10_000_000);
        heapMillis += measureMillis(numbers, Aufgabe4::heapSort);
        //noinspection ResultOfMethodCallIgnored
        countMillis += measureMillis(numbers, n -> countSort(n, 10_001));
        mapMillis += measureMillis(numbers, n -> mapSort(n, 1.25));

        System.out.println(
            "heap: " + heapMillis +
                ", count: " + countMillis +
                ", map: " + mapMillis
        );
    }
}
