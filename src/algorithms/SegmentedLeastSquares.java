package algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Hikmet Orhun Güley S004428 Department of Industrial Engineering

public class SegmentedLeastSquares {

    public static void main(String[] args) {


       // String path = "C:\\Users\\og4428\\Google Drive\\Orhun\\Terms\\Spring 2018\\CS333 - Algorithm Analysis\\HW2\\Points.txt";
        String path = "";
        path = getCurrentPath();
        path += "\\Points.txt";

        ArrayList<Double> x = new ArrayList();
        ArrayList<Double> y = new ArrayList();

        readTxt(path, x, y);

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter error coefficient c: ");
        double c = reader.nextInt();

        int i = 0;
        double Cost = SegmentedLeastSquares(x, y, c);
        i = 1;

        System.out.println(Cost);






    }

    public static String getCurrentPath() {

        String currentpath = "";

        String current = "";
        try {
            currentpath = new java.io.File(".").getCanonicalPath();
            String currentDir = System.getProperty("user.dir");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return currentpath;
    }

    public static void readTxt(String path, ArrayList<Double> x, ArrayList<Double> y) {

        int i = 0;
        try {

            BufferedReader br = new BufferedReader(new BufferedReader(new FileReader(path)));
            String line = "";
            // reading when each line has x, y
            while ((line = br.readLine()) != null) {
                String[] t = line.split("\\s+");

                x.add(Double.parseDouble(t[1]));
                y.add(Double.parseDouble(t[2]));
                i = i + 1;

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static double CalculateSSE(ArrayList<Double> x, ArrayList<Double> y, int start, int end) {
        // TODO Auto-generated method stub
        double SSE = 0;

        int n = end - start + 1;
        a = 0;
        b = 0;
        double sumxy = 0;
        double sumx = 0;
        double sumy = 0;
        double sumsquarredx = 0;

        for (int i = start; i < end + 1; i++) {

            sumxy += x.get(i) * y.get(i);
            sumx += x.get(i);
            sumy += y.get(i);
            sumsquarredx += Math.pow(x.get(i), 2);

        }

        a = ((n * sumxy) - (sumx * sumy)) / ((n * sumsquarredx) - (Math.pow(sumx, 2)));
        b = (sumy - (a * sumx)) / n;

        for (int i = start; i < end + 1; i++) {

            SSE += Math.pow((y.get(i) - (a * x.get(i)) - b), 2);

        }

        return SSE;
    }

    static double a = 0;
    static double b = 0;

    public static double SegmentedLeastSquares(ArrayList<Double> x, ArrayList<Double> y, double c) {
        // TODO Auto-generated method stub

        double M[] = new double[x.size()];
        double error[][] = new double[x.size()][x.size()];
        int N = M.length;
        double a_array[][] = new double[N][N];
        double b_array[][] = new double[N][N];

        M[0] = 0;

        for (int j = 0; j < N; j++) {

            for (int i = 0; i < j; i++) {

                error[i][j] = CalculateSSE(x, y, i, j);
                a_array[i][j] = a;
                b_array[i][j] = b;

            }

        }

        int startIndex = 0;
        int endIndex = 0;
        int trackSegments[] = new int[N];

        for (int j = 1; j < N; j++) {

            double min = Integer.MAX_VALUE;

            for (int i = 1; i < j; i++) {

                double m_error = error[i][j] + c + M[i - 1];

                if (min > m_error) {
                    min = m_error;
                    M[j] = error[i][j] + c + M[i - 1];
                    startIndex = i;

                }
            }

            trackSegments[j] = startIndex;

        }

        endIndex = N - 1;
        int i = N - 1;
        int j = 1;
        while (i >= 0) {

            System.out.println("Segment " + j + "   --->   start index is : " + trackSegments[i]
                    + "      end index is: " + endIndex + "    a = " + a_array[trackSegments[i]][endIndex] + "   b = "
                    + b_array[trackSegments[i]][endIndex]);

            if (i == 0)
                break;

            endIndex = trackSegments[i];
            i = trackSegments[i - 1];
            j = j + 1;
        }

        return M[N - 1];

    }
}
