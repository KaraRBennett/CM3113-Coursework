package edof;

public class Debug {

    public static Boolean debug = true;
    public static Boolean printProcessess = true;

    public static void printReadFileHeader() {
        if (printProcessess) {
            System.out.println("\nReading the following files: -");
        }
    }

    public static void printSaveFileHeader() {
        if (printProcessess) {
            System.out.println("\nSaving the following files: -");
        }
    }

    public static void printString(String i) {
        if (printProcessess) {
            System.out.println(i);
        }
    }

    public static void checkSobelMasks(int[][] gx, int[][]gy) {
        System.out.println("\n\nMask Gx\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(gx[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n\nMask Gy\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(gy[i][j] + " ");
            }
            System.out.println();
        }
    }
}