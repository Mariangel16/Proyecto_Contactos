package contactos.structure;

import java.util.*;

public class BST<T extends Comparable<T>> {
    protected TreeNode<T> root;

    public BST() {
        this.root = null;
    }

    public void insert(T key, int id) {
        root = insertRec(root, key, id);
    }

    private TreeNode<T> insertRec(TreeNode<T> node, T key, int id) {
        if (node == null) return new TreeNode<>(key, id);

        if (key.compareTo(node.key) < 0) {
            node.left = insertRec(node.left, key, id);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insertRec(node.right, key, id);
        }
        return node; // duplicados no se insertan
    }

    public boolean contains(T key) {
        return containsRec(root, key);
    }

    private boolean containsRec(TreeNode<T> node, T key) {
        if (node == null) return false;
        if (key.equals(node.key)) return true;
        return key.compareTo(node.key) < 0
                ? containsRec(node.left, key)
                : containsRec(node.right, key);
    }

    public List<Integer> levelOrderTraversal() {
        List<Integer> ids = new ArrayList<>();
        if (root == null) return ids;

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            ids.add(current.id);

            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }

        return ids;
    }
}
