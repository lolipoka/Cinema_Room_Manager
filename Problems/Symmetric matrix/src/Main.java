import java.util.Scanner;

class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int size = scanner.nextInt();
        int[][] array = new int[size][size];
        fillArray(array);
        System.out.println(isSymmetric(array) ? "YES" : "NO");
        scanner.close();
    }

    private static void fillArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = scanner.nextInt();
            }
        }
    }

    private static boolean isSymmetric(int[][] array) {
        boolean isSymmetric = true;
        outer:
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != array[j][i]) {
                    isSymmetric = false;
                    break outer;
                }
            }
        }
        return isSymmetric;
    }
}