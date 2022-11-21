import java.util.Arrays;

public class MainAPP {
    static final int size = 45_000_000;
    static final int half1 = size / 2;
    static final int half2 = size / 3;

    public static void main(String[] args) {
        OneThread();
        TwoThread();
        ThreeThread();
    }

    public static void OneThread() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1.0f);
        long time = System.currentTimeMillis();



        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("Первая ячейка - " + arr[0] + " последняя ячейка - " + arr[size - 1]);
        System.out.println("Время: " + (System.currentTimeMillis() - time));
    }

    public static void TwoThread() {
        float[] arr = new float[size];
        float[] arr1 = new float[half1];
        final float[] arr2 = new float[half1];
        Arrays.fill(arr, 1.0f);
        System.arraycopy(arr, 0, arr1, 0, half1);
        System.arraycopy(arr, half1, arr2, 0, half1);
        long time = System.currentTimeMillis();

        Thread t = new Thread(() -> {
            for (int i = 0; i < half1; i++) {
                arr2[i] = (float) (arr2[i] * Math.sin(0.2f + (i + half1) / 5) * Math.cos(0.2f + (i + half1) / 5) * Math.cos(0.4f + (i + half1) / 2));
            }
        });

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < half1; i++) {
                arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        t.start();
        t1.start();


        try {
            t.join();
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.arraycopy(arr1, 0, arr, 0, half1);
        System.arraycopy(arr2, 0, arr, half1, half1);

        System.out.println("Первая ячейка - " + arr[0] + " последняя ячейка - " + arr[size - 1]);
        System.out.println("Время: " + (System.currentTimeMillis() - time));
    }

    public static void ThreeThread(){
        float[] arr = new float[size];
        float[] arr1 = new float[half2];
        float[] arr2 = new float[half2];
        final float[] arr3 = new float[half2];
        Arrays.fill(arr, 1.0f);
        System.arraycopy(arr, 0, arr1, 0, half2);
        System.arraycopy(arr, half2, arr2, 0, half2);
        System.arraycopy(arr, half2, arr3, 0, half2);
        long time = System.currentTimeMillis();

        Thread t = new Thread(() -> {
            for (int i = 0; i < half2; i++) {
                arr3[i] = (float) (arr3[i] * Math.sin(0.2f + (i + half2 * 2) / 5) * Math.cos(0.2f + (i + half2 * 2) / 5) * Math.cos(0.4f + (i + half2 * 2) / 2));
            }
        });

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < half2; i++) {
                arr2[i] = (float) (arr2[i] * Math.sin(0.2f + (i + half2) / 5) * Math.cos(0.2f + (i + half2) / 5) * Math.cos(0.4f + (i + half2) / 2));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < half2; i++) {
                arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        t.start();
        t1.start();
        t2.start();


        try {
            t.join();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.arraycopy(arr1, 0, arr, 0, half2);
        System.arraycopy(arr2, 0, arr, half2, half2);
        System.arraycopy(arr3, 0, arr, half2 * 2, half2);

        System.out.println("Первая ячейка - " + arr[0] + " последняя ячейка - " + arr[size - 1]);
        System.out.println("Время: " + (System.currentTimeMillis() - time));
    }
}