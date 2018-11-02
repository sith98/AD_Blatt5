#include <stdlib.h>
#include <stdio.h>


int * countSort(int a[], int n, int k) {
    int i;
    int * help = malloc(sizeof(int) * k + 1);
    int * a2 = malloc(sizeof(int) * n);

    for (i = 0; i < k + 1; i++) {
        // k
        help[i] = 0;
    }

    for (i = 0; i < n; i++) {
        // n
        help[a[i]] += 1;
    }

    for (i = 1; i < k + 1; i++) {
        // k
        help[i] += help[i - 1];
    }

    for (i = 0; i < n; i++) {
        // n
        a2[help[a[i]] - 1] = a[i];
        // n
        help[a[i]] -= 1;
    }

    free(help);
    return a2;
}

int main() {
    int a[] = {1, 5, 2, 7, 3, 5, 8, 9, 3, 4, 3, 3, 3, 9, 0, 3};
    int n = sizeof(a) / sizeof(a[0]);

    int * a_sorted = countSort(a, n, 10);

    for (int i = 0; i < n; i++) {
        printf("%d, ", a_sorted[i]);
    }

    free(a_sorted);
    return 0;
}