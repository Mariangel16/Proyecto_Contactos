package contactos.structure;

public class TreeNode<T extends Comparable<T>> {
    public T key;
    public int id;
    public TreeNode<T> left;
    public TreeNode<T> right;
    public int height;

    public TreeNode(T key, int id) {
        this.key = key;
        this.id = id;
        this.left = null;
        this.right = null;
        this.height = 1; // importante para AVL
    }
}
