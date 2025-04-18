package contactos.utils;

import contactos.model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    // Cargar contactos desde un archivo CSV
    public static List<Contact> cargarContactos(String ruta) {
        List<Contact> contactos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    // Saltar la cabecera del archivo CSV
                    primeraLinea = false;
                    continue;
                }

                Contact contacto = Contact.fromCSV(linea);
                contactos.add(contacto);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return contactos;
    }

    // Guardar lista de contactos en un archivo CSV
    public static void guardarContactos(List<Contact> contactos, String ruta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            // Cabecera
            pw.println("id,nombre,apellido,apodo,telefono,email,direccion,fecha_nacimiento");

            for (Contact c : contactos) {
                pw.println(c.toCSV());
            }

        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}
