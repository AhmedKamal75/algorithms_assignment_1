import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        int[] array = {25, 24, 33, 39, 3, 18, 19, 31, 23, 49, 45, 16, 1, 29, 40, 22, 15, 20, 24, 4, 13,34};
        System.out.println("For an array of size: " + array.length);
        median(array, 11);
        System.out.println("############################");

        for (int size = 10; size < Math.pow(10,7) * 2; size *= 10){
            System.out.println("For an array of size: " + size);
            median(generateRandomArray(size),10);
            System.out.println("############################");

        }

    }

    private static void median(int[] array, int numberOfTimesToGetAverage) {
        long randomSum = 0;
        long momSum = 0;
        long sortingSum = 0;
        int randomMedium = 0;
        int momMedium = 0;
        int sortingMedium = 0;

        for (int i = 0; i < numberOfTimesToGetAverage; i++) {
            int[] array1 = new int[array.length];
            int[] array2 = new int[array.length];
            int[] array3 = new int[array.length];
            System.arraycopy(array, 0, array1, 0, array.length);
            System.arraycopy(array, 0, array2, 0, array.length);
            System.arraycopy(array, 0, array3, 0, array.length);
            FirstPart firstPart = new FirstPart();

            long startingTime = System.nanoTime();
            randomMedium = firstPart.getMedianRandomSelect(array1);
            long endingTime = System.nanoTime();
            randomSum += (endingTime - startingTime);

            startingTime = System.nanoTime();
            momMedium = firstPart.getMedianThroughMedianOfMedians(array2);
            endingTime = System.nanoTime();
            momSum += (endingTime - startingTime);

            startingTime = System.nanoTime();
            sortingMedium = firstPart.getMedianUsingSorting(array3);
            endingTime = System.nanoTime();
            sortingSum += (endingTime - startingTime);
        }

        System.out.println("for Random Select time elapsed  = " + (randomSum / numberOfTimesToGetAverage) + ", median = " + randomMedium);
        System.out.println("for MOM method time elapsed     = " + (momSum / numberOfTimesToGetAverage) + ", median = " + momMedium);
        System.out.println("for sorting method time elapsed = " + (sortingSum / numberOfTimesToGetAverage) + ", median = " + sortingMedium);

    }

    private static int[] generateRandomArray(int size) {
        ArrayList<Integer> tempList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            tempList.add(i);
        }
        Collections.shuffle(tempList);
        return Arrays.stream(tempList.toArray(new Integer[0])).mapToInt(Integer::intValue).toArray();
    }
}
