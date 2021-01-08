import java.util.*;

public class Main {

    public static void main(String[] args) {

        int size = 4;
        char[][] colorPattern = new char[size][size];

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < colorPattern.length; i++) {
            colorPattern[i] = scanner.nextLine().toCharArray();
        }
        scanner.close();

        System.out.println(isPretty(colorPattern) ? "YES" : "NO");
    }

    private static boolean isPretty(char[][] colorPattern) {

        int maxCheckIndex = colorPattern.length - 2;
        boolean isPretty = true;

        outer:
        for (int i = 0; i <= maxCheckIndex; i++) {
            for (int j = 0; j <= maxCheckIndex; j++) {

                char currentCell = colorPattern[i][j];
                char rightCell = colorPattern[i][j + 1];
                char bottomCell = colorPattern[i + 1][j];
                char bottomRightCell = colorPattern[i + 1][j + 1];

                boolean isSameColor2x2 = currentCell == rightCell
                                        && currentCell == bottomCell
                                        && currentCell == bottomRightCell;

                if (isSameColor2x2) {
                    isPretty = false;
                    break outer;
                }
            }
        }
        return isPretty;
    }
}