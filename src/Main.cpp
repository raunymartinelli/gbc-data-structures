#include <bits/stdc++.h>

using namespace std;

void merge(int *&_array, int _lo, int _mid, int _hi)
{
    int i = _lo, j = _mid + 1, k = 0, temp[_hi - _lo + 1];
    while (i <= _mid && j <= _hi)
    {
        if (_array[i] < _array[j])
        {
            temp[k] = _array[i];
            k++;
            i++;
        }
        else
        {
            temp[k] = _array[j];
            k++;
            j++;
        }
    }

    while (i <= _mid)
    {
        temp[k] = _array[i];
        k++;
        i++;
    }

    while (j <= _hi)
    {
        temp[k] = _array[j];
        k++;
        j++;
    }

    for (i = _lo; i <= _hi; i++)
    {
        _array[i] = temp[i - _lo];
    }
}

void mergeSort(int *&_array, int _lo, int _hi)
{
    if (_lo >= _hi)
        return;

    int mid = (_lo + _hi) / 2;
    mergeSort(_array, _lo, mid);
    mergeSort(_array, mid + 1, _hi);
    merge(_array, _lo, mid, _hi);
}

int partition(int *&_array, int _lo, int _hi)
{
    int ordered = _lo - 1;

    for (int i = _lo; i < _hi; i++)
    {
        if (_array[i] < _array[_hi])
        {
            ordered++;
            int tmp = _array[i];
            _array[i] = _array[ordered];
            _array[ordered] = tmp;
        }
    }

    ordered++;
    int tmp = _array[ordered];
    _array[ordered] = _array[_hi];
    _array[_hi] = tmp;
    return ordered;
}

void quickSort(int *&_array, int _lo, int _hi)
{
    if (_lo >= _hi)
        return;

    int mid = partition(_array, _lo, _hi);
    quickSort(_array, mid + 1, _hi);
    quickSort(_array, _lo, mid - 1);
}

int binsearch(int *_array, int _size, int _key)
{
    int lo = 0, hi = _size - 1, mid;
    while (lo <= hi)
    {
        mid = (lo + hi) / 2;
        if (_array[mid] > _key)
            hi = mid - 1;
        if (_array[mid] < _key)
            lo = mid + 1;
        if (_array[mid] == _key)
            return mid;
    }
    return -1;
}

void insertionSort(int *&_array, int _size) // inverse min search
{
    for (int i = 0; i < _size; i++)
    {
        for (int k = _size - 1; k <= 0; k--)
        {
            if (_array[i] < _array[k])
            {
                int tmp = _array[i];
                _array[i] = _array[k];
                _array[k] = tmp;
            }
        }
    }
}

void selectionSort(int *&_array, int _size) // min search
{
    for (int i = 0; i < _size; i++)
    {
        int min = i;
        for (int k = i; k < _size; k++)
        {
            if (_array[min] < _array[k])
            {
                min = k;
            }
        }

        int tmp = _array[min];
        _array[min] = _array[i];
        _array[i] = tmp;
    }
}

void printArray(int *_array, int _size)
{
    for (int i = 0; i < _size; i++)
    {
        string info = i == _size - 1 ? "" : ", ";
        cout << _array[i] << info;
    }
    cout << endl;
}

void resetArray(int *&_array, int _size)
{
    int og[] = {20, 11, 19, 14, 16};
    for (int i = 0; i < _size; i++)
        _array[i] = og[i];
}

int main()
{
    int size = 5;
    int array[size] = {0};
    int *parray = array;

    cout << "---------- Selection Sort ----------" << endl;
    resetArray(parray, size);
    printArray(parray, size);
    selectionSort(parray, size);
    printArray(parray, size);

    cout << "---------- Insertion Sort ----------" << endl;
    resetArray(parray, size);
    printArray(parray, size);
    selectionSort(parray, size);
    printArray(parray, size);

    cout << "---------- Quick Sort ----------" << endl;
    resetArray(parray, size);
    printArray(parray, size);
    quickSort(parray, 0, size - 1);
    printArray(parray, size);

    cout << "---------- Merge Sort ----------" << endl;
    resetArray(parray, size);
    printArray(parray, size);
    mergeSort(parray, 0, size - 1);
    printArray(parray, size);

    cout << "---------- Bin Seach ----------" << endl;
    cout << "Search [20]: " << binsearch(parray, size, 20) << endl;
    cout << "Search [19]: " << binsearch(parray, size, 19) << endl;
    cout << "Search [11]: " << binsearch(parray, size, 11) << endl;
    cout << "Search [16]: " << binsearch(parray, size, 16) << endl;
    cout << "Search [-2321]: " << binsearch(parray, size, -2321) << endl;

    return 0;
}