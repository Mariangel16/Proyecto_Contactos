package contactos.utils;

import contactos.model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class CSVUtils {

    // Cargar contactos desde un archivo CSV
    public static List<Contact> cargarContactos(String ruta) {
        List<Contact> contactos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false; // saltar encabezado
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
    public static void guardarContactos(List<Contact> contactos, String rutaArchivo) {
        try (PrintWriter writer = new PrintWriter(rutaArchivo)) {
            writer.println("id,nombre,apellido,apodo,telefono,email,direccion,fechaNacimiento");
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

    //Importar contactos
    public static List<Contact> importarContactos(String rutaArchivo) {
        List<Contact> lista = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(rutaArchivo))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // Saltar encabezado
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(",", -1);
                if (partes.length >= 8) {
                    int id = Integer.parseInt(partes[0].trim());
                    Contact c = new Contact(
                            id,
                            partes[1].trim(),
                            partes[2].trim(),
                            partes[3].trim(),
                            partes[4].trim(),
                            partes[5].trim(),
                            partes[6].trim(),
                            partes[7].trim()
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
