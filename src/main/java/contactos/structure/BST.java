package contactos.structure;

import java.util.*;

public class BST<T extends Comparable<T>> {
    // Nodo raíz del árbol
    protected TreeNode<T> root;

    // Constructor: crea un árbol vacío
    public BST() {
        this.root = null;
    }

    // Método público para insertar una clave y su ID asociado en el árbol
    public void insert(T key, int id) {
        root = insertRec(root, key, id); // llamada recursiva comenzando desde la raíz
    }

    // Método privado recursivo para insertar un nuevo nodo en su posición correcta
    private TreeNode<T> insertRec(TreeNode<T> node, T key, int id) {
        // Si el nodo es null, se crea un nuevo nodo con la clave e ID
        if (node == null) return new TreeNode<>(key, id);

        // Comparar la clave con la clave del nodo actual
        if (key.compareTo(node.key) < 0) {
            node.left = insertRec(node.left, key, id); // insertar en subárbol izquierdo
        } else if (key.compareTo(node.key) > 0) {
            node.right = insertRec(node.right, key, id); // insertar en subárbol derecho
        }
        // Si la clave ya existe, no se inserta (evitar duplicados)
        return node;
    }

    // Verifica si una clave existe en el árbol
    public boolean contains(T key) {
        return containsRec(root, key); // llamada recursiva desde la raíz
    }

    // Método recursivo para verificar si el árbol contiene una clave dada
    private boolean containsRec(TreeNode<T> node, T key) {
        // Si el nodo es null, la clave no está presente
        if (node == null) return false;

        // Si la clave coincide con la del nodo, se encontró
        if (key.equals(node.key)) return true;

        // Continuar búsqueda en subárbol izquierdo o derecho según corresponda
        return key.compareTo(node.key) < 0
                ? containsRec(node.left, key)
                : containsRec(node.right, key);
    }

    // Realiza un recorrido por niveles (Breadth-First Search) y devuelve una lista con los IDs en ese orden
    public List<Integer> levelOrderTraversal() {
        List<Integer> ids = new ArrayList<>();

        // Si el árbol está vacío, retornar lista vacía
        if (root == null) return ids;

        // Usamos una cola para recorrer por niveles
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root); // iniciar desde la raíz

        // Mientras haya nodos en la cola
        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll(); // obtener el primer nodo
            ids.add(current.id); // agregar su ID a la lista de resultado

            // Agregar hijos izquierdo y derecho a la cola si existen
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }

        return ids; // retornar lista de IDs en orden por niveles
    }
}
