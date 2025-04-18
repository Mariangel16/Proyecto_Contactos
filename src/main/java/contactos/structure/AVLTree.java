package contactos.structure;

public class AVLTree<T extends Comparable<T>> extends BST<T> {

    @Override
    public void insert(T key, int id) {
        root = insertRec(root, key, id);
    }

    private TreeNode<T> insertRec(TreeNode<T> node, T key, int id) {
        if (node == null) return new TreeNode<>(key, id);

        if (key.compareTo(node.key) < 0) {
            node.left = insertRec(node.left, key, id);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insertRec(node.right, key, id);
        } else {
            return node; // no insertar duplicados
        }

        // Actualizar altura
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // Obtener factor de balance
        int balance = getBalance(node);

        // Casos de rotación
        if (balance > 1 && key.compareTo(node.left.key) < 0)
            return rotateRight(node);

        if (balance < -1 && key.compareTo(node.right.key) > 0)
            return rotateLeft(node);

        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private int getHeight(TreeNode<T> node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(TreeNode<T> node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private TreeNode<T> rotateRight(TreeNode<T> y) {
        TreeNode<T> x = y.left;
        TreeNode<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        // Actualizar alturas
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    private TreeNode<T> rotateLeft(TreeNode<T> x) {
        TreeNode<T> y = x.right;
        TreeNode<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        // Actualizar alturas
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }
}
