package contactos.controller;

import contactos.model.Contact;
import contactos.utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private List<Contact> contactos;
    private final String rutaArchivo = "src/main/resources/contacts.csv";
    private int ultimoId = 0;

    public ContactManager() {
        contactos = CSVUtils.cargarContactos(rutaArchivo);
        calcularUltimoId();
    }

    private void calcularUltimoId() {
        for (Contact c : contactos) {
            if (c.getId() > ultimoId) {
                ultimoId = c.getId();
            }
        }
    }

    public void agregarContacto(String nombre, String apellido, String apodo, String telefono,
                                String email, String direccion, String fechaNacimiento) {
        int nuevoId = ++ultimoId;
        Contact nuevo = new Contact(nuevoId, nombre, apellido, apodo, telefono, email, direccion, fechaNacimiento);
        contactos.add(nuevo);
        guardar();
        System.out.println("Contacto agregado con ID: " + nuevoId);
    }

    public void eliminarContacto(int id) {
        boolean eliminado = contactos.removeIf(c -> c.getId() == id);
        if (eliminado) {
            guardar();
            System.out.println("Contacto eliminado.");
        } else {
            System.out.println("No se encontró contacto con ID " + id);
        }
    }

    public void actualizarContacto(int id, Contact nuevosDatos) {
        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getId() == id) {
                nuevosDatos.setId(id); // conservar el mismo ID
                contactos.set(i, nuevosDatos);
                guardar();
                System.out.println("Contacto actualizado.");
                return;
            }
        }
        System.out.println("No se encontró contacto con ID " + id);
    }

    public void mostrarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("No hay contactos registrados.");
        } else {
            for (Contact c : contactos) {
                System.out.println(c);
                System.out.println("---------------------------");
            }
        }
    }

    public Contact buscarPorId(int id) {
        for (Contact c : contactos) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<Contact> buscarPorCampo(String campo, String valor) {
        List<Contact> resultados = new ArrayList<>();
        for (Contact c : contactos) {
            switch (campo.toLowerCase()) {
                case "nombre":
                    if (c.getNombre().equalsIgnoreCase(valor)) resultados.add(c);
                    break;
                case "apellido":
                    if (c.getApellido().equalsIgnoreCase(valor)) resultados.add(c);
                    break;
                case "apodo":
                    if (c.getApodo().equalsIgnoreCase(valor)) resultados.add(c);
                    break;
                case "telefono":
                    if (c.getTelefono().equalsIgnoreCase(valor)) resultados.add(c);
                    break;
                case "email":
                    if (c.getEmail().equalsIgnoreCase(valor)) resultados.add(c);
                    break;
                case "direccion":
                    if (c.getDireccion().equalsIgnoreCase(valor)) resultados.add(c);
                    break;
                case "fecha":
                    if (c.getFechaNacimiento().equalsIgnoreCase(valor)) resultados.add(c);
                    break;
            }
        }
        return resultados;
    }

    private void guardar() {
        CSVUtils.guardarContactos(contactos, rutaArchivo);
    }

    public List<Contact> getContactos() {
        return contactos;
    }

}
