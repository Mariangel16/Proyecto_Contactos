package contactos.controller;

import contactos.model.Contact;
import contactos.structure.AVLTree;
import contactos.structure.BST;
import contactos.structure.TreeNode;
import java.util.stream.Collectors;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class IndexManager {

     //Enumeración que define los tipos de árbol disponibles para los índices.
     //BST: Árbol binario de búsqueda simple.
     //AVL: Árbol AVL (balanceado).
    public enum TipoIndice {
        BST,
        AVL
    }

    /**
     * Crea un índice (BST o AVL) para un campo específico de los contactos.
     * Cada nodo del árbol almacena como clave un valor del campo (ej. nombre, apellido) y como valor el ID del contacto.
     */

    // Crear un índice por campo (nombre, apellido, etc.) con estructura BST o AVL
    public static BST<String> crearIndice(List<Contact> contactos, String campo, TipoIndice tipo) {
        BST<String> arbol = (tipo == TipoIndice.AVL) ? new AVLTree<>() : new BST<>();
        // Recorre la lista de contactos, extrae el valor del campo, y lo inserta en el árbol.
        for (Contact c : contactos) {
            String clave = obtenerValorCampo(c, campo);
            if (clave != null && !clave.isEmpty()) {
                arbol.insert(clave.toLowerCase(), c.getId());
            }
        }
        return arbol;
    }


  //Obtiene dinámicamente el valor de un campo de un contacto.
  //Utiliza una estructura `switch` para manejar múltiples opciones posibles.
    // Extraer el valor del campo dinámicamente
    private static String obtenerValorCampo(Contact c, String campo) {
        return switch (campo.toLowerCase()) {
            case "nombre" -> c.getNombre();
            case "apellido" -> c.getApellido();
            case "apodo" -> c.getApodo();
            case "telefono" -> c.getTelefono();
            case "email" -> c.getEmail();
            case "direccion" -> c.getDireccion();
            case "fecha" -> c.getFechaNacimiento();
            default -> null;
        };
    }

    // Guardar recorrido por niveles del árbol en un archivo
    public static void guardarIndice(BST<String> arbol, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            List<Integer> ids = arbol.levelOrderTraversal();
            String linea = ids.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            writer.write(linea);

            System.out.println("Índice guardado en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el índice: " + e.getMessage());
        }
    }
    //Carga un índice previamente guardado en archivo y lo reconstruye.
    public static BST<String> cargarIndiceDesdeArchivo(String nombreArchivo, List<Contact> contactos, String campo, TipoIndice tipo) {
        BST<String> arbol = (tipo == TipoIndice.AVL) ? new AVLTree<>() : new BST<>();

        try {
            File archivo = new File(nombreArchivo);
            // Verifica si el archivo existe antes de intentar abrirlo
            if (!archivo.exists()) {
                System.out.println(" El archivo no existe: " + nombreArchivo);
                return arbol;
            }
            // Lee el archivo línea por línea
            try (Scanner scanner = new Scanner(archivo)) {
                while (scanner.hasNextLine()) {
                    String linea = scanner.nextLine();
                    // Dividir por comas si es una línea con múltiples valores
                    String[] tokens = linea.split(",");

                    for (String token : tokens) {
                        token = token.trim();
                        if (token.equalsIgnoreCase("null") || token.isEmpty()) continue;

                        try {
                            int id = Integer.parseInt(token);
                            // Busca el contacto con ese ID y reconstruye la clave
                            for (Contact c : contactos) {
                                if (c.getId() == id) {
                                    String clave = obtenerValorCampo(c, campo);
                                    if (clave != null && !clave.isEmpty()) {
                                        arbol.insert(clave.toLowerCase(), id);
                                    }
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.err.println(" Valor no válido en archivo: " + token);
                        }
                    }
                }

                System.out.println(" Índice reconstruido desde archivo: " + archivo.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println(" Error al leer el archivo: " + e.getMessage());
        }

        return arbol;
    }
}

