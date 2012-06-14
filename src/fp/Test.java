package fp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {


    public static String readLine(InputStreamReader s) throws IOException {
        StringBuilder sb = new StringBuilder();
        int next;
        while ((next = s.read()) != -1) {
            if (next == '\n') break;
            sb.append((char) next);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        int next;
        InputStreamReader isr = new InputStreamReader(new FileInputStream("stars.dat"));
        String cur = "";
        int axis = 0;

        List<int[]> all = new ArrayList<int[]>();
        int[] curCoor = new int[3];
        int progress = 0;
        while ((next = isr.read()) != -1) {

            char c = ((char)next);
            if (c == '\t') {
                curCoor[axis] = Integer.parseInt(cur);
                axis ++;
                cur = "";
            } else if (c == 0xa) {
            } else if (c == 0xd) {
                curCoor[axis] = Integer.parseInt(cur);
                all.add(curCoor);

                curCoor = new int[3];
                axis = 0;
                cur = "";
                progress++;
                if (progress % 1000 == 0) System.out.println(progress);
            } else {
                cur += c;
            }
        }

        float minD = 1000000;
        int[] a = null;
        int[] b = null;

        long startTime = System.currentTimeMillis();

        Node n = new Node(1000000, 0, 0, 0);
        n.all = all;
        int m = 1000000;
        int step = 13000;
        for (int x = 0; x < m - step; x += step) {
            System.out.println("--- x : " + x);
            for (int y = 0; y < m - step; y += step) {
                for (int z = 0; z < m - step; z += step) {
                    List<int[]> list = n.getAll(x, y, z, step + 1);
                    n.forget(x - step * 2, y - step * 2, z - step * 2, step);
                    for (int i = 0; i < list.size() - 1; i++) {
                        for (int j = i + 1; j < list.size(); j++) {
                            float d = distance(list.get(i), list.get(j));
                            if (minD > d) {
                                minD = d;
                                a = list.get(i);
                                b = list.get(j);
                            }

                        }
                    }
                }
            }
        }
        System.out.println("minD = " + Math.sqrt(minD));
        System.out.println("a = " + Arrays.toString(a));
        System.out.println("b = " + Arrays.toString(b));
        long resTime = System.currentTimeMillis() - startTime;
        System.out.println("time: " + resTime / 1000 + "s");
    }

    public static float distance(int[] a, int[] b) {
        return (float) sqr(a[0] - b[0]) + sqr(a[1] - b[1]) + sqr(a[2] - b[2]);
    }
    public static float sqr(float a) {
        return a * a;
    }


}
