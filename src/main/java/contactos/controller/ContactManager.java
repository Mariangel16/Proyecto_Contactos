package contactos.controller;

import contactos.model.Contact;
import contactos.utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de gestionar una lista de contactos.
 * Permite agregar, eliminar, actualizar, buscar y exportar contactos.
 */
public class ContactManager {
    private List<Contact> contactos; // Lista que almacena los contactos
    private int ultimoId = 0; // Último ID asignado, se incrementa automáticamente

    /**
     * Constructor: inicializa la lista sin cargar contactos automáticamente.
     */
    public ContactManager() {
        contactos = new ArrayList<>(); // Ya no se cargan contactos automáticamente
    }

    /**
     * Recorre los contactos y determina cuál es el ID más alto.
     * Se utiliza para asegurar que no se repitan IDs al agregar nuevos contactos.
     */
    private void calcularUltimoId() {
        for (Contact c : contactos) {
            if (c.getId() > ultimoId) {
                ultimoId = c.getId();
            }
        }
    }

    /**
     * Agrega un nuevo contacto con un ID único autogenerado.
     */
    public void agregarContacto(String nombre, String apellido, String apodo, String telefono,
                                String email, String direccion, String fechaNacimiento) {
        int nuevoId = ++ultimoId; // Genera un nuevo ID incrementado
        Contact nuevo = new Contact(nuevoId, nombre, apellido, apodo, telefono, email, direccion, fechaNacimiento);
        contactos.add(nuevo);
        System.out.println("Contacto agregado con ID: " + nuevoId);
    }

    /**
     * Elimina un contacto según su ID.
     */
    public void eliminarContacto(int id) {
        boolean eliminado = contactos.removeIf(c -> c.getId() == id); // Elimina el contacto con el ID dado
        if (eliminado) {
            System.out.println("Contacto eliminado.");
        } else {
            System.out.println("No se encontró contacto con ID " + id);
        }
    }

    /**
     * Actualiza un contacto existente con nuevos datos, conservando su ID.
     */
    public void actualizarContacto(int id, Contact nuevosDatos) {
        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getId() == id) {
                nuevosDatos.setId(id); // Asegura que el ID no cambie
                contactos.set(i, nuevosDatos); // Reemplaza el contacto viejo con el nuevo
                System.out.println("Contacto actualizado.");
                return;
            }
        }
        System.out.println("No se encontró contacto con ID " + id);
    }

    /**
     * Muestra todos los contactos en consola con separadores visuales.
     */
    public void mostrarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("No hay contactos registrados.");
        } else {
            for (Contact c : contactos) {
                System.out.println(c);
                System.out.println("════════════════════════════════════════════════════════════════════════════");
            }
        }
    }

    /**
     * Busca un contacto por su ID y lo retorna, o null si no existe.
     */
    public Contact buscarPorId(int id) {
        for (Contact c : contactos) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    /**
     * Busca contactos que coincidan con un valor específico en un campo determinado.
     * Los campos válidos son: nombre, apellido, apodo, telefono, email, direccion, fecha.
     */
    public List<Contact> buscarPorCampo(String campo, String valor) {
        List<Contact> resultados = new ArrayList<>();
        for (Contact c : contactos) {
            switch (campo.toLowerCase()) {
                case "nombre" -> {
                    if (c.getNombre().equalsIgnoreCase(valor)) resultados.add(c);
                }
                case "apellido" -> {
                    if (c.getApellido().equalsIgnoreCase(valor)) resultados.add(c);
                }
                case "apodo" -> {
                    if (c.getApodo().equalsIgnoreCase(valor)) resultados.add(c);
                }
                case "telefono" -> {
                    if (c.getTelefono().equalsIgnoreCase(valor)) resultados.add(c);
                }
                case "email" -> {
                    if (c.getEmail().equalsIgnoreCase(valor)) resultados.add(c);
                }
                case "direccion" -> {
                    if (c.getDireccion().equalsIgnoreCase(valor)) resultados.add(c);
                }
                case "fecha" -> {
                    if (c.getFechaNacimiento().equalsIgnoreCase(valor)) resultados.add(c);
                }
            }
        }
        return resultados;
    }

    /**
     * Exporta la lista de contactos a un archivo CSV en la ruta indicada.
     */
    public void guardarEnArchivo(String rutaDestino) {
        CSVUtils.guardarContactos(contactos, rutaDestino);
    }

    /**
     * Retorna la lista completa de contactos.
     */
    public List<Contact> getContactos() {
        return contactos;
    }

    /**
     * Agrega una lista de contactos (por ejemplo, al importar desde un archivo).
     * También recalcula el último ID para evitar conflictos al agregar nuevos.
     */
    public void agregarContactos(List<Contact> nuevos) {
        contactos.addAll(nuevos);
        calcularUltimoId(); // Asegura que el siguiente ID sea correcto
    }
}
