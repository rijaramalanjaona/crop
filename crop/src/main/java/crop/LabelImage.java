package crop;

import java.util.Stack;

public class LabelImage {
    int[][] label;
    Stack stack;

    /** Creates a new instance of LabelImage */
    public LabelImage() {
    }

    public int[][] labelImage(int[][] img, int stackSize) {
	int nrow = img.length;
	int ncol = img[0].length;
	int lab = 1;
	int[] pos;
	stack = new Stack();
	stack.setSize(stackSize);
	label = new int[nrow][ncol];
	for (int r = 1; r < nrow - 1; r++)
	    for (int c = 1; c < ncol - 1; c++) {
		if (img[r][c] == 0)
		    continue;
		if (label[r][c] > 0)
		    continue;
		/* encountered unlabeled foreground pixel at position r, c */
		/* push the position on the stack and assign label */
		stack.push(new int[] { r, c });
		label[r][c] = lab;

		/* start the float fill */
		while (!stack.isEmpty()) {
		    pos = (int[]) stack.pop();
		    int i = pos[0];
		    int j = pos[1];
		    if (img[i - 1][j - 1] == 1 && label[i - 1][j - 1] == 0) {
			stack.push(new int[] { i - 1, j - 1 });
			label[i - 1][j - 1] = lab;
		    }
		    if (img[i - 1][j] == 1 && label[i - 1][j] == 0) {
			stack.push(new int[] { i - 1, j });
			label[i - 1][j] = lab;
		    }
		    if (img[i - 1][j + 1] == 1 && label[i - 1][j + 1] == 0) {
			stack.push(new int[] { i - 1, j + 1 });
			label[i - 1][j + 1] = lab;
		    }
		    if (img[i][j - 1] == 1 && label[i][j - 1] == 0) {
			stack.push(new int[] { i, j - 1 });
			label[i][j - 1] = lab;
		    }
		    if (img[i][j + 1] == 1 && label[i][j + 1] == 0) {
			stack.push(new int[] { i, j + 1 });
			label[i][j + 1] = lab;
		    }
		    if (img[i + 1][j - 1] == 1 && label[i + 1][j - 1] == 0) {
			stack.push(new int[] { i + 1, j - 1 });
			label[i + 1][j - 1] = lab;
		    }
		    if (img[i + 1][j] == 1 && label[i + 1][j] == 0) {
			stack.push(new int[] { i + 1, j });
			label[i + 1][j] = lab;
		    }
		    if (img[i + 1][j + 1] == 1 && label[i + 1][j + 1] == 0) {
			stack.push(new int[] { i + 1, j + 1 });
			label[i + 1][j + 1] = lab;
		    }
		} /* end while */
		lab++;
	    }
	return label;
    }
}
