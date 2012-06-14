package fp;

public class IntBox {
    public int[] coor;
    public int size;

    public IntBox() {
    }

    public IntBox(int x, int y, int z, int size) {
        coor = new int[]{x, y, z};
        this.size = size;
    }

    //true if given box intersects with this box
    public boolean isCrossed(IntBox box) {
        return isCrossedi(box.coor, box.size);
    }

    public boolean isCrossedi(int[] coor, int size) {
        for (int i = 0; i < 3; i++) {
            if (coor[i] >= this.coor[i] + this.size) return false;
            if (coor[i] + size < this.coor[i]) return false;
        }
        return true;
    }
}