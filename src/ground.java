import java.util.Arrays;

public class ground extends GUI{


    public ground()
    {
        matrix = new int[200][224];
        for(int r=0; r<200; r++)
        {
            Arrays.fill(matrix[r], 1);
        }
    }
}
