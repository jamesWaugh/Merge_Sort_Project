package merge_sort_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import static java.lang.Integer.min;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MergeSortProject {

    public static void generate(int n, String fileName, String directory) {
        Random rand = new Random();
        File newOutput = new File(directory + "\\" + fileName);
        ArrayList<Double> genList = new ArrayList();
        try {
            FileOutputStream fileOutput = new FileOutputStream(directory + "\\" + fileName);

            for (int i = 0; i < n; i++) {
                genList.add(rand.nextDouble());
            }
            try (PrintWriter printer = new PrintWriter(fileOutput)) {
                for (int i = 0; i < n; i++) {
                    printer.println(genList.get(i) + "\n");
                }
                printer.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MergeSortProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Double> readFile(String fileName, String directory) {
        System.out.println("Reading file...\n");
        File file = new File(directory + "\\" + fileName);
        ArrayList<Double> doubleList = new ArrayList();
        try {
            System.out.println("Reading doubles from file...\n");
            Scanner scan = new Scanner(file);
            while (scan.hasNextDouble()) {
                doubleList.add(scan.nextDouble());
            }
            return doubleList;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MergeSortProject.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void printFile(String n, ArrayList<Double> f, String directory) {
        String fileName = n;
        File newOutput = new File(directory + "\\" + fileName);
        ArrayList<Double> printList = new ArrayList();
        try {
            FileOutputStream fileOutput = new FileOutputStream(directory + "\\" + fileName);

            f.stream().forEach((f1) -> {
                printList.add(f1);
            });
            try (PrintWriter printer = new PrintWriter(fileOutput)) {
                for (int i = 0; i < f.size(); i++) {
                    printer.println(printList.get(i) + "\n");
                }
                printer.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MergeSortProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cloneList(ArrayList<Double> inputList, ArrayList<Double> outputList) {
        for (int i = 0; i < inputList.size(); i++) {
            outputList.add(inputList.get(i));
        }
    }

    public static void merge(ArrayList<Double> a, int left, int mid, int right) {
        int midPlus = mid + 1;
        while (left <= mid && midPlus <= right) {
            if (a.get(left) < a.get(midPlus)) {
                left++;
            } else {
                double temp = a.get(midPlus);
                for (int j = midPlus - 1; j >= left; j--) {
                    a.set(j + 1, a.get(j));
                }
                a.set(left, temp);
                left++;
                mid++;
                midPlus++;
            }
        }
    }

    public static void insertionSort(ArrayList<Double> a, int left, int right) {
        if (right - left < 25) {
            double temp;
            for (int i = left; i < right; i++) {
                temp = a.get(i);
                int j;
                for (j = i - 1; j >= 0 && temp < a.get(j); j--) {
                    a.set(j + 1, a.get(j));
                }
                a.set(j + 1, temp);
            }
        }
    }

    public static void mergeSortA(ArrayList<Double> a, int left, int right) {
        int mid;
        if (left < right) {
            mid = (left + right) / 2;
            mergeSortA(a, left, mid);
            mergeSortA(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    public static void mergeSortB(ArrayList<Double> a) {
        int left;
        for (int i = 1; i <= a.size() - 1; i = 2 * i) {
            for (left = 0; left < a.size() - 1; left += 2 * i) {
                int right = min(left + 2 * i - 1, a.size() - 1);
                int mid = left + i - 1;
                merge(a, left, mid, right);
            }
        }
    }

    public static void mergeSortC(ArrayList<Double> a, int left, int right) {
        int mid;
        if (right - left < 25) {
            insertionSort(a, left, right);
        } else {
            mid = (left + right) / 2;
            mergeSortA(a, left, mid);
            mergeSortA(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    public static void mergeSortD(ArrayList<Double> a) {
        int left;
        int mid = 0;
        int right = 0;
        for (int i = 1; i <= a.size() - 1; i = 2 * i) {
            for (left = 0; left < a.size() - 1; left += 2 * i) {
                right = min(left + 2 * i - 1, a.size() - 1);
                mid = left + i - 1;
                merge(a, left, mid, right);
            }
            insertionSort(a, left, right);
        }
    }

    public static void main(String[] args) {
        Scanner scanMain = new Scanner(System.in);
        Scanner scanInt = new Scanner(System.in);
        System.out.println("Input File Directory: \nWARNING: The output files will be printed to this directory.\nC:\\\\Users\\\\James\\\\Desktop\\\\Towson\\\\COSC336\\\\HW02_03_Waugh_James");
        String dir = scanMain.nextLine();

        System.out.println("\nInput the corresponding option number:\n1. Read file\n2. Generate file");
        int option = scanInt.nextInt();

        String fileName = "";
        String fileNameNoTxt = "";
        String sentinel = "";
        if (option == 1) {
            System.out.println("\nInput file name to be read without .txt extension:");
            fileNameNoTxt += scanMain.nextLine();
            fileName = fileNameNoTxt + ".txt";
        } else if (option == 2) {
            System.out.println("\nInput generating file name without .txt extension:");
            fileNameNoTxt += scanMain.nextLine();
            fileName = fileNameNoTxt + ".txt";
        } else {
            sentinel = "N";
            System.out.println("\nIncorrect option selected, ending program.");
        }

        while (!sentinel.equals("N")) {

            if (option == 2) {
                System.out.println("\nInput number of doubles to be generated:");
                int n = scanMain.nextInt();
                System.out.println("\nGenerating double list for test purposes.");
                generate(n, fileName, dir);
            }

            System.out.println("\nCreating list of doubles for merge methods.\n");
            ArrayList<Double> testList = readFile(fileName, dir);

            long startTime;
            long stopTime;
            long elapsedTimeA;
            long elapsedTimeB;
            long elapsedTimeC;
            long elapsedTimeD;

            System.out.println("Running mergeSortA(): ");
            ArrayList<Double> listA = new ArrayList();
            cloneList(testList, listA);
            startTime = System.currentTimeMillis();
            mergeSortA(listA, 0, listA.size() - 1);
            stopTime = System.currentTimeMillis();
            elapsedTimeA = stopTime - startTime;
            System.out.println("Elapsed time for mergeSortA() in milliseconds: " + elapsedTimeA + "\n");
            printFile(fileNameNoTxt + "_postSort_A.txt", listA, dir);

            System.out.println("Running mergeSortB(): ");
            ArrayList<Double> listB = new ArrayList();
            cloneList(testList, listB);
            startTime = System.currentTimeMillis();
            mergeSortB(listB);
            stopTime = System.currentTimeMillis();
            elapsedTimeB = stopTime - startTime;
            System.out.println("Elapsed time for mergeSortB() in milliseconds: " + elapsedTimeB + "\n");
            printFile(fileNameNoTxt + "_postSort_B.txt", listB, dir);

            System.out.println("Running mergeSortC(): ");
            ArrayList<Double> listC = new ArrayList();
            cloneList(testList, listC);
            startTime = System.currentTimeMillis();
            mergeSortC(listC, 0, listC.size() - 1);
            stopTime = System.currentTimeMillis();
            elapsedTimeC = stopTime - startTime;
            System.out.println("Elapsed time for mergeSortC() in milliseconds: " + elapsedTimeC + "\n");
            printFile(fileNameNoTxt + "_postSort_C.txt", listC, dir);

            System.out.println("Running mergeSortD(): ");
            ArrayList<Double> listD = new ArrayList();
            cloneList(testList, listD);
            startTime = System.currentTimeMillis();
            mergeSortD(listD);
            stopTime = System.currentTimeMillis();
            elapsedTimeD = stopTime - startTime;
            System.out.println("Elapsed time for mergeSortD() in milliseconds: " + elapsedTimeD);
            printFile(fileNameNoTxt + "_postSort_D.txt", listD, dir);

            if (option == 2) {
                System.out.println("\nDo this again with different number of doubles? Y or N");
                sentinel = scanMain.next();
            } else {
                sentinel = "N";
            }
        }

        System.out.println("\nEnd.");
    }

}
