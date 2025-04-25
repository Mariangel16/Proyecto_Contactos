package contactos.structure;

/**
 * TreeNode representa un nodo de un árbol binario (BST o AVL).
 * Cada nodo almacena una clave genérica, un ID asociado, referencias a sus hijos y su altura (para AVL).
 */
public class TreeNode<T extends Comparable<T>> {
    // Clave del nodo, usada para ordenarlo dentro del árbol
    public T key;

    // ID asociado al nodo, útil para identificar o recuperar datos relacionados
    public int id;

    // Referencia al hijo izquierdo
    public TreeNode<T> left;

    // Referencia al hijo derecho
    public TreeNode<T> right;

    // Altura del nodo, usada en árboles AVL para mantener el balance
    public int height;

     //Constructor del nodo.
     // key con la que se ordenará el nodo.
     // Identificador único asociado al nodo.
    public TreeNode(T key, int id) {
        this.key = key;       // Se asigna la clave
        this.id = id;         // Se asigna el ID
        this.left = null;     // Inicialmente sin hijo izquierdo
        this.right = null;    // Inicialmente sin hijo derecho
        this.height = 1;      // Altura inicial es 1 (nodo hoja)
    }
}
