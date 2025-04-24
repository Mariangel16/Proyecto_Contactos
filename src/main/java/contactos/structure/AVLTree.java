package contactos.structure;

public class AVLTree<T extends Comparable<T>> extends BST<T> {

    // Método público para insertar una clave y su id correspondiente
    @Override
    public void insert(T key, int id) {
        root = insertRec(root, key, id); // Inserta recursivamente desde la raíz
    }

    // Método recursivo de inserción que también asegura el balance del árbol
    private TreeNode<T> insertRec(TreeNode<T> node, T key, int id) {
        // Caso base: si el nodo es null, se crea uno nuevo
        if (node == null) return new TreeNode<>(key, id);

        // Comparar la clave para decidir si se va a la izquierda o derecha
        if (key.compareTo(node.key) < 0) {
            node.left = insertRec(node.left, key, id);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insertRec(node.right, key, id);
        } else {
            return node; // No se permite insertar claves duplicadas
        }

        // Actualizar la altura del nodo actual
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // Calcular el factor de balance para verificar si está desbalanceado
        int balance = getBalance(node);

        // Casos de rotaciones para balancear el árbol

        // Caso Izquierda-Izquierda (rotación simple a la derecha)
        if (balance > 1 && key.compareTo(node.left.key) < 0)
            return rotateRight(node);

        // Caso Derecha-Derecha (rotación simple a la izquierda)
        if (balance < -1 && key.compareTo(node.right.key) > 0)
            return rotateLeft(node);

        // Caso Izquierda-Derecha (rotación doble: izquierda y luego derecha)
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Caso Derecha-Izquierda (rotación doble: derecha y luego izquierda)
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        // Si no hay desbalance, se retorna el nodo tal como está
        return node;
    }

    // Retorna la altura de un nodo, 0 si es null
    private int getHeight(TreeNode<T> node) {
        return node == null ? 0 : node.height;
    }

    // Calcula el factor de balance: altura izquierda - altura derecha
    private int getBalance(TreeNode<T> node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    // Realiza una rotación a la derecha y retorna la nueva raíz del subárbol
    private TreeNode<T> rotateRight(TreeNode<T> y) {
        TreeNode<T> x = y.left;
        TreeNode<T> T2 = x.right;

        // Realizar la rotación
        x.right = y;
        y.left = T2;

        // Actualizar alturas
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x; // x se convierte en la nueva raíz del subárbol
    }

    // Realiza una rotación a la izquierda y retorna la nueva raíz del subárbol
    private TreeNode<T> rotateLeft(TreeNode<T> x) {
        TreeNode<T> y = x.right;
        TreeNode<T> T2 = y.left;

        // Realizar la rotación
        y.left = x;
        x.right = T2;

        // Actualizar alturas
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y; // y se convierte en la nueva raíz del subárbol
    }
}
