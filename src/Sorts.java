import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Made with a lot of love by Bruno 🧡

        LabTest test = new LabTest(10);
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            test.add(rand.nextInt(0, 20));
        }

        System.out.println(test.getCollection());
//        test.selectionSort();
//        test.insertionSort();
//        test.quickSort();
        test.mergeSort();
        System.out.println(test.getCollection());

        System.out.print("[BinSeach (12)]: ");
        System.out.print(test.binSearch(12));
    }
}

class LabTest {
    // Made with a lot of love by Bruno 🧡

    public int[] collection;
    public int max;
    public int current;

    public LabTest(int _max) {
        collection = new int[_max];
        max = _max;
        current = 0;
    }

    public void add(int _item) {
        collection[current++] = _item;
    }

    public String getCollection() {
        return Arrays.toString(collection);
    }

    public void selectionSort() {
        for (int i = 0; i < current; i++) {
            int min = i;
            for (int k = i; k < current; k++) {
                if (collection[min] > collection[k]) { // for desc or asc order, just invert this condition
                    min = k;
                }
            }

            int tmp = collection[min];
            collection[min] = collection[i];
            collection[i] = tmp;
        }
    }

    public void insertionSort() {
        for (int i = 0; i < current; i++) {
            for (int k = current - 1; k >= i; k--) {
                if (collection[i] > collection[k]) {
                    int tmp = collection[i];
                    collection[i] = collection[k];
                    collection[k] = tmp;
                }
            }
        }
    }

    public int binSearch(int _key) {
        int lo = 0, hi = current - 1, mid;
        while (lo <= hi) {
            mid = (lo + hi) / 2;
            if (collection[mid] > _key) hi = mid - 1; // we are on the left
            if (collection[mid] < _key) lo = mid + 1; // we are on the right
            if (collection[mid] == _key) return mid;
        }
        return -1;
    }

    public void quickSort() {
        int lo = 0, hi = current - 1;
        quickSort(lo, hi);
    }

    private void quickSort(int _lo, int _hi) {
        if (_lo >= _hi)
            return;

        int mid = partition(_lo, _hi);
        quickSort(mid + 1, _hi);
        quickSort(_lo, mid - 1);
    }

    private int partition(int _lo, int _hi) {
        int ordered = _lo - 1;
        for (int i = _lo; i < _hi; i++) {
            if (collection[i] < collection[_hi]) {
                ordered++;
                int tmp = collection[i];
                collection[i] = collection[ordered];
                collection[ordered] = tmp;
            }
        }

        ordered++;
        int tmp = collection[ordered];
        collection[ordered] = collection[_hi];
        collection[_hi] = tmp;
        return ordered;
    }

    public void mergeSort() {
        int lo = 0, hi = current - 1;
        mergeSort(lo, hi);
    }

    private void mergeSort(int _lo, int _hi) {
        if (_lo >= _hi)
            return;

        int mid = (_lo + _hi) / 2;
        mergeSort(_lo, mid);
        mergeSort(mid + 1, _hi);
        merge(_lo, mid, _hi);
    }

    private void merge(int _lo, int _mid, int _hi) {
        int i = _lo, j = _mid + 1, k = 0;
        int[] temp = new int[_hi - _lo + 1];

        while (i <= _mid && j <= _hi) {
            if (collection[i] < collection[j]) {
                temp[k] = collection[i];
                k++;
                i++;
            } else {
                temp[k] = collection[j];
                k++;
                j++;
            }
        }

        while (i <= _mid) {
            temp[k] = collection[i];
            k++;
            i++;
        }

        while (j <= _hi) {
            temp[k] = collection[j];
            k++;
            j++;
        }

        for (i = _lo; i <= _hi; i++) {
            collection[i] = temp[i - _lo];
        }
    }

    // Made with a lot of love by Bruno ❤️
}