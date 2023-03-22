import java.util.Arrays;

public class ground extends GUI {

    public ground() {
        matrix = new int[width][groundHeight];
        for (int[] ints : matrix) {
            Arrays.fill(ints, 1);
        }
    }
}
