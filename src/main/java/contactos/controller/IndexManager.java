package contactos.controller;

import contactos.model.Contact;
import contactos.structure.AVLTree;
import contactos.structure.BST;
import contactos.structure.TreeNode;
import java.util.stream.Collectors;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class IndexManager {

    public enum TipoIndice {
        BST,
        AVL
    }

    // Crear un índice por campo (nombre, apellido, etc.) con estructura BST o AVL
    public static BST<String> crearIndice(List<Contact> contactos, String campo, TipoIndice tipo) {
        BST<String> arbol = (tipo == TipoIndice.AVL) ? new AVLTree<>() : new BST<>();

        for (Contact c : contactos) {
            String clave = obtenerValorCampo(c, campo);
            if (clave != null && !clave.isEmpty()) {
                arbol.insert(clave.toLowerCase(), c.getId());
            }
        }
        return arbol;
    }

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
    public static void guardarIndice(BST<String> arbol, String campo, TipoIndice tipo) {
        String nombreArchivo = "src/main/resources/" + campo.toLowerCase() + "-" + tipo.name().toLowerCase() + ".txt";
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            List<Integer> ids = arbol.levelOrderTraversal();
            String linea = ids.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            writer.write(linea);

            System.out.println("Índice guardado en: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println(" Error al guardar el índice: " + e.getMessage());
        }
    }


    public static BST<String> cargarIndiceDesdeArchivo(String nombreArchivo, List<Contact> contactos, String campo, TipoIndice tipo) {
        BST<String> arbol = (tipo == TipoIndice.AVL) ? new AVLTree<>() : new BST<>();

        try (Scanner scanner = new Scanner(new java.io.File("src/main/resources/" + nombreArchivo))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir por comas si es una línea con múltiples valores
                String[] tokens = linea.split(",");

                for (String token : tokens) {
                    token = token.trim();
                    if (token.equalsIgnoreCase("null") || token.isEmpty()) continue;

                    try {
                        int id = Integer.parseInt(token);
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

            System.out.println(" Índice reconstruido desde archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println(" Error al leer el archivo: " + e.getMessage());
        }

        return arbol;
    }
}

