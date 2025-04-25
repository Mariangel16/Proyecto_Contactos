package contactos.utils;

import contactos.model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


 // Utilidades para trabajar con archivos CSV relacionados a contactos.
 // Permite cargar, guardar e importar datos desde/ hacia archivos .csv.
public class CSVUtils {

     //Carga una lista de contactos desde un archivo CSV.
     //Cada línea representa un contacto en formato CSV.
     // Se omite la primera línea (encabezado).

    public static List<Contact> cargarContactos(String ruta) {
        List<Contact> contactos = new ArrayList<>();

        // Se usa BufferedReader para leer el archivo línea por línea
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean primeraLinea = true; // bandera para omitir encabezado

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; // saltar encabezado
                }

                // Convertir la línea en un objeto Contact
                Contact contacto = Contact.fromCSV(linea);
                contactos.add(contacto);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return contactos;
    }


     // Guarda una lista de contactos en un archivo CSV.
     // Escribe un encabezado y luego cada contacto en una línea separada.

    public static void guardarContactos(List<Contact> contactos, String rutaArchivo) {
        try (PrintWriter writer = new PrintWriter(rutaArchivo)) {
            // Escribir encabezado del archivo
            writer.println("id,nombre,apellido,apodo,telefono,email,direccion,fechaNacimiento");

            // Escribir cada contacto en formato CSV
            for (Contact c : contactos) {
                writer.println(c.getId() + "," +
                        c.getNombre() + "," +
                        c.getApellido() + "," +
                        c.getApodo() + "," +
                        c.getTelefono() + "," +
                        c.getEmail() + "," +
                        c.getDireccion() + "," +
                        c.getFechaNacimiento());
            }

            System.out.println(" Contactos exportados en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println(" Error al exportar contactos: " + e.getMessage());
        }
    }

     // Importa contactos desde un archivo CSV, similar a cargarContactos pero usando Scanner.
     //@param rutaArchivo Ruta del archivo CSV.
     // return Lista de contactos obtenida del archivo
    public static List<Contact> importarContactos(String rutaArchivo) {
        List<Contact> lista = new ArrayList<>();

        // Usamos Scanner para leer el archivo línea por línea
        try (Scanner scanner = new Scanner(new File(rutaArchivo))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // Saltar encabezado si existe

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(",", -1); // -1 incluye campos vacíos

                // Validar que la línea tenga al menos 8 partes (campos)
                if (partes.length >= 8) {
                    int id = Integer.parseInt(partes[0].trim()); // Convertir el ID
                    Contact c = new Contact(
                            id,
                            partes[1].trim(), // nombre
                            partes[2].trim(), // apellido
                            partes[3].trim(), // apodo
                            partes[4].trim(), // telefono
                            partes[5].trim(), // email
                            partes[6].trim(), // direccion
                            partes[7].trim()  // fechaNacimiento
                    );
                    lista.add(c);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(" Error al importar CSV: " + e.getMessage());
        }

        return lista;
    }
}
