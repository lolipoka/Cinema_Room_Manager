import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int partSize = scanner.nextInt();
        int[][] sudokuTable = getSudokuTable(partSize, scanner);

        System.out.println(isSolved(sudokuTable, partSize) ? "YES" : "NO");

        scanner.close();
    }

    private static int[][] getSudokuTable(int partSize, Scanner scanner) {

        int sudokuSize = partSize * partSize;

        int[][] sudokuTable = new int[sudokuSize][sudokuSize];

        for (int rowIndex = 0; rowIndex < sudokuSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < sudokuSize; columnIndex++) {
                sudokuTable[rowIndex][columnIndex] = scanner.nextInt();
            }
        }
        return sudokuTable;
    }

    private static boolean isSolved(int[][] sudokuTable, int partSize) {

        return isSolvedInRows(sudokuTable, partSize)
                && isSolvedInColumns(sudokuTable, partSize)
                && isSolvedInSquares(sudokuTable, partSize);
    }

    private static boolean isSolvedInRows(int[][] sudokuTable, int partSize) {

        boolean isSolvedInRows = true;

        for (int[] row : sudokuTable) {

            AllNumbers allNumbers = new AllNumbers(partSize * partSize);

            for (int number : row) {
                allNumbers.delete(number);
            }

            if (allNumbers.isNotEmpty()) {
                isSolvedInRows = false;
                break;
            }
        }
        return isSolvedInRows;
    }

    private static boolean isSolvedInColumns(int[][] sudokuTable, int partSize) {

        boolean isSolvedInColumns = true;
        int sudokuSize = partSize * partSize;

        for (int columnIndex = 0; columnIndex < sudokuSize; columnIndex++) {

            AllNumbers allNumbers = new AllNumbers(sudokuSize);

            for (int rowIndex = 0; rowIndex < sudokuSize; rowIndex++) {
                int number = sudokuTable[rowIndex][columnIndex];
                allNumbers.delete(number);
            }

            if (allNumbers.isNotEmpty()) {
                isSolvedInColumns = false;
                break;
            }
        }
        return isSolvedInColumns;
    }

    private static boolean isSolvedInSquares(int[][] sudokuTable, int partSize) {

        boolean isSolvedInSquares = true;
        int sudokuSize = partSize * partSize;

        outer:
        for (int rowIndex = 0; rowIndex < sudokuSize; rowIndex += partSize) {
            for (int columnIndex = 0; columnIndex < sudokuSize; columnIndex += partSize) {

                int[][] sudokuPart = getSudokuPart(sudokuTable, rowIndex, columnIndex, partSize);

                AllNumbers allNumbers = new AllNumbers(sudokuSize);
                for (int[] row : sudokuPart) {
                    for (int number : row) {
                        allNumbers.delete(number);
                    }
                }
                if (allNumbers.isNotEmpty()) {
                    isSolvedInSquares = false;
                    break outer;
                }
            }
        }

        return isSolvedInSquares;
    }

    private static int[][] getSudokuPart(int[][] sudokuTable, int rowIndex, int columnIndex, int partSize) {

        int[][] sudokuPart = new int[partSize][partSize];

        for (int i = 0; i < partSize; i++) {
            System.arraycopy(sudokuTable[rowIndex + i], columnIndex, sudokuPart[i], 0, partSize);
        }
        return sudokuPart;
    }

    private static class AllNumbers {

        private final int[] numbers;

        public AllNumbers(int size) {
            numbers = new int[size];
            fillNumbers();
        }

        private void fillNumbers() {
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = i + 1;
            }
        }

        public boolean isNotEmpty() {

            boolean isNotEmpty = false;

            for (int number : numbers) {
                if (number != 0) {
                    isNotEmpty = true;
                    break;
                }
            }
            return isNotEmpty;
        }

        public void delete(int number) {
            try {
                numbers[number - 1] = 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                // Do nothing. May be thrown if numbers are random (not consequent from 1 to N).
            }
        }

    }
}