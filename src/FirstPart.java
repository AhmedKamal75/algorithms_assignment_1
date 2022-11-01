import java.util.Arrays;
import java.util.LinkedList;

public class FirstPart {
    private void swap(int[] array, int firstIndex, int secondIndex) {
        if (array[firstIndex] == array[secondIndex]) {
            return;
        }
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    // array --> is an array of size n
    // startIndex --> is the starting index from which we can select
    // endingIndex   --> is the ending index at which we end our selection
    private int randomPartition(int[] array, int startingIndex, int endingIndex) {
//      select random pivot and move it to the end of the given array.
        int pivotIndex = (int) (Math.random() * (endingIndex - startingIndex + 1) + startingIndex);
        this.swap(array, pivotIndex, endingIndex);

//        now that the pivot is at the back of the array, we will start to partition regularly.
        int pivot = array[endingIndex];
        int possiblePivotPosition = startingIndex - 1;
        for (int i = startingIndex; i < endingIndex; i++) {
            if (array[i] <= pivot) {
                possiblePivotPosition += 1;
                swap(array, possiblePivotPosition, i);
            }
        }
        swap(array, endingIndex, possiblePivotPosition + 1);
        return possiblePivotPosition + 1;
    }

    public Integer randomSelect(int[] array, int startingIndex, int endingIndex, int targetIndex) {
        if ((array == null) || (targetIndex < 0) || (targetIndex > array.length) ||
                (endingIndex > array.length) || (startingIndex < 0)) {
            return null;
        }

        if (startingIndex == endingIndex) {
            return array[startingIndex];
        }

        int pivotIndex = this.randomPartition(array, startingIndex, endingIndex);
        int k = pivotIndex - startingIndex + 1;
        if (k == targetIndex) {
            return array[pivotIndex];
        } else if (targetIndex < k) {
            return this.randomSelect(array, startingIndex, pivotIndex - 1, targetIndex);
        } else {
            return this.randomSelect(array, pivotIndex + 1, endingIndex, targetIndex - k);
        }
    }


    // using soring method
    // works
    public int getMedianTrivial(int[] array) {
        Arrays.sort(array);
        int targetIndex = array.length;
//        if (array.length % 2 == 1) {
//            targetIndex = array.length / 2;
//        } else {
//            targetIndex = array.length / 2;
//        }
        return array[targetIndex / 2];
    }

    // works
    public int approximateMedian(int[] array, int startingIndex, int endingIndex, int sizeOfPartition) {
        if (startingIndex == endingIndex) {
            return array[startingIndex];
        }
        int[] medians = new int[(int) Math.ceil((double) ((endingIndex - startingIndex + 1) / ((double) sizeOfPartition)))];
        int currentMedianIndex = 0;
        for (int i = startingIndex; (i < array.length) && (i < endingIndex); i += sizeOfPartition) {
            int[] temp = new int[sizeOfPartition];
            if (i + sizeOfPartition - 1 <= endingIndex) {
                System.arraycopy(array, i, temp, 0, sizeOfPartition);

            } else {
                temp = new int[endingIndex - i + 1];
                System.arraycopy(array, i, temp, 0, temp.length);
            }
//            try {
//            } catch (ArrayIndexOutOfBoundsException ignored) {
//            }
            medians[currentMedianIndex++] = getMedianTrivial(temp);
        }

        // recursively select the median if the size of the current medians is greater than the size of partition
        if (medians.length > 5) {
            approximateMedian(medians, 0, medians.length - 1, sizeOfPartition);
        }
        return getMedianTrivial(medians);
    }

    public int partitionAroundPivot(int[] array, int startingPoint, int endingIndex, int pivot) {
        LinkedList<Integer> tempSmaller = new LinkedList<>();
        LinkedList<Integer> tempBigger = new LinkedList<>();
        int pivotCounter = 0;
        for (int i = startingPoint; i <= endingIndex; i++) {
            if (array[i] < pivot) {
                tempSmaller.add(array[i]);
            } else if (array[i] > pivot) {
                tempBigger.add(array[i]);
            } else {
                pivotCounter++;
            }
        }
        int indexOfThePivot = startingPoint + tempSmaller.size() + pivotCounter / 2;
        int arrayCounter = 0;
        for (Integer item : tempSmaller) {
            array[startingPoint + arrayCounter] = item;
            arrayCounter++;
        }
        for (int i = 0; i < pivotCounter; i++) {
            array[startingPoint + arrayCounter] = pivot;
        }
        arrayCounter += pivotCounter;
        for (Integer item : tempBigger) {
            array[startingPoint + arrayCounter] = item;
            arrayCounter++;
        }
        return indexOfThePivot;

    }


    public Integer medianOfMedianSelect(int[] array, int startingIndex, int endingIndex, int targetIndex, int sizeOfPartition) {
        if (targetIndex < 1 && targetIndex > array.length) {
            return null;
        }
        // get the approximate median of the array.
        int pivot = approximateMedian(array, startingIndex, endingIndex, sizeOfPartition);
        // partition the array around the pivot and get its index.
        int pivotIndex = partitionAroundPivot(array, startingIndex, endingIndex, pivot);
        int rankOfPivot = pivotIndex - startingIndex + 1;
        if (rankOfPivot == targetIndex) {
            return pivot;
        } else if (targetIndex < rankOfPivot) {
            return medianOfMedianSelect(array, startingIndex, pivotIndex - 1, targetIndex, sizeOfPartition);
        } else {
            return medianOfMedianSelect(array, pivotIndex + 1, endingIndex, targetIndex - rankOfPivot, sizeOfPartition);
        }
    }

    public int getMedianRandomSelect(int[] array) {
        int targetIndex = 0;
        if (array.length % 2 == 1) {
            return this.randomSelect(array, 0, array.length - 1, 1 + array.length / 2);
        } else {
            return this.randomSelect(array, 0, array.length - 1, array.length / 2);
        }
    }

    public int getMedianThroughMedianOfMedians(int[] array) {
        int targetIndex = 0;
        if (array.length % 2 == 1) {
            return this.medianOfMedianSelect(array, 0, array.length - 1, array.length / 2 + 1, 5);
        } else {
            return this.medianOfMedianSelect(array, 0, array.length - 1, array.length / 2, 5);
        }
    }

    public int getMedianUsingSorting(int[] array) {
        Arrays.sort(array);
        if (array.length % 2 == 1) {
            return array[array.length / 2];
        } else {
            return array[array.length / 2 - 1];
        }
    }
}
