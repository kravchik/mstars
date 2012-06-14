package fp;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public IntBox box = new IntBox();

    public List<int[]> all = new ArrayList<int[]>();

    public List<Node> children = new ArrayList<Node>();

    public Node(int size, int x, int y, int z) {
        box.size = size;
        box.coor = new int[]{x, y, z};
    }

    public void finer() {
        if (!children.isEmpty()) return;
        children = new ArrayList<Node>();

        int x = box.coor[0];
        int y = box.coor[1];
        int z = box.coor[2];
        int ns = box.size / 2;
        children.add(new Node(ns, x, y, z));
        children.add(new Node(ns, x + ns, y, z));
        children.add(new Node(ns, x, y + ns, z));
        children.add(new Node(ns, x + ns, y + ns, z));

        children.add(new Node(ns, x, y, z + ns));
        children.add(new Node(ns, x + ns, y, z + ns));
        children.add(new Node(ns, x, y + ns, z + ns));
        children.add(new Node(ns, x + ns, y + ns, z + ns));

        for (Node child : children) {
            child.takeYours(all);
        }
    }

    public boolean toForget = false;
    public void forget(int x, int y, int z, int size) {
        forget(new IntBox(x, y, z, size));
    }

    public void forget(IntBox box) {
        if (!this.box.isCrossed(box)) return;
        if (children.isEmpty()) {
            toForget = true;
        }
        boolean allForget = true;
        for (Node child : children) {
            child.forget(box);
            allForget &= child.toForget;
        }
        if (allForget) {
            children.clear();
            toForget = true;
        }
    }

    public List<int[]> getAll(int x, int y, int z, int size) {
        List<int[]> result = new ArrayList<int[]>();
        getAll(new IntBox(x, y, z, size), result);
        return result;
    }


    public void getAll(IntBox box, List<int[]> result) {
        if (this.box.isCrossed(box)) {
            if (this.box.size > 10000) finer();
            for (Node child : children) {
                child.getAll(box, result);
            }
            if (children.isEmpty()) {
                result.addAll(all);
            }
        }
    }

    private void takeYours(List<int[]> all) {
        for (int[] ints : all) if (isMy(ints)) this.all.add(ints);
    }


    private boolean isMy(int[] ints) {
        return ints[0] >= box.coor[0] && ints[0] < box.coor[0] + box.size
                && ints[1] >= box.coor[1] && ints[1] < box.coor[1] + box.size
                && ints[2] >= box.coor[2] && ints[2] < box.coor[2] + box.size;
    }

}
