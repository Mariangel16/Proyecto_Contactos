package contactos;

import contactos.controller.ContactManager;
import contactos.controller.IndexManager;
import contactos.controller.IndexManager.TipoIndice;
import contactos.model.Contact;
import contactos.structure.BST;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContactManager gestor = new ContactManager();
        Scanner sc = new Scanner(System.in);
        int opcion;

        mostrarBienvenida();

        do {
            imprimirMenu();
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> gestor.mostrarContactos();
                case 2 -> {
                    System.out.println("\n NUEVO CONTACTO");
                    System.out.print("Nombre: "); String nombre = sc.nextLine();
                    System.out.print("Apellido: "); String apellido = sc.nextLine();
                    System.out.print("Apodo: "); String apodo = sc.nextLine();
                    System.out.print("Teléfono: "); String telefono = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("Dirección: "); String direccion = sc.nextLine();
                    System.out.print("Fecha de nacimiento (YYYY-MM-DD): "); String fecha = sc.nextLine();

                    gestor.agregarContacto(nombre, apellido, apodo, telefono, email, direccion, fecha);
                }
                case 3 -> {
                    System.out.println("\n BÚSQUEDA DE CONTACTO");
                    System.out.print("Campo a buscar (nombre, apellido, apodo, telefono, email, direccion, fecha): ");
                    String campo = sc.nextLine();
                    System.out.print("Valor a buscar: ");
                    String valor = sc.nextLine();

                    List<Contact> resultados = gestor.buscarPorCampo(campo, valor);
                    if (resultados.isEmpty()) {
                        System.out.println(" ¡¡NO SE ENCONTRARON RESULTADOS!!");
                    } else {
                        System.out.println("\n📄 RESULTADOS:");
                        for (Contact c : resultados) {
                            System.out.println(c);
                            System.out.println("════════════════════════════════════════════");
                        }
                    }
                }
                case 4 -> {
                    System.out.print("\n Ingrese el ID del contacto a eliminar: ");
                    int idEliminar = sc.nextInt();
                    sc.nextLine();
                    gestor.eliminarContacto(idEliminar);
                }
                case 5 -> {
                    System.out.print("\n Ingrese el ID del contacto a actualizar: ");
                    int idActualizar = sc.nextInt();
                    sc.nextLine();
                    Contact existente = gestor.buscarPorId(idActualizar);
                    if (existente == null) {
                        System.out.println(" ¡¡CONTACTO NO ENCONTRADO!!");
                    } else {
                        System.out.println("Deje vacío cualquier campo para conservar el valor actual.");
                        System.out.print("Nuevo Nombre [" + existente.getNombre() + "]: ");
                        String nuevoNombre = sc.nextLine();
                        System.out.print("Nuevo Apellido [" + existente.getApellido() + "]: ");
                        String nuevoApellido = sc.nextLine();
                        System.out.print("Nuevo Apodo [" + existente.getApodo() + "]: ");
                        String nuevoApodo = sc.nextLine();
                        System.out.print("Nuevo Teléfono [" + existente.getTelefono() + "]: ");
                        String nuevoTelefono = sc.nextLine();
                        System.out.print("Nuevo Email [" + existente.getEmail() + "]: ");
                        String nuevoEmail = sc.nextLine();
                        System.out.print("Nueva Dirección [" + existente.getDireccion() + "]: ");
                        String nuevaDireccion = sc.nextLine();
                        System.out.print("Nueva Fecha de Nacimiento [" + existente.getFechaNacimiento() + "]: ");
                        String nuevaFecha = sc.nextLine();

                        Contact actualizado = new Contact(
                                idActualizar,
                                nuevoNombre.isEmpty() ? existente.getNombre() : nuevoNombre,
                                nuevoApellido.isEmpty() ? existente.getApellido() : nuevoApellido,
                                nuevoApodo.isEmpty() ? existente.getApodo() : nuevoApodo,
                                nuevoTelefono.isEmpty() ? existente.getTelefono() : nuevoTelefono,
                                nuevoEmail.isEmpty() ? existente.getEmail() : nuevoEmail,
                                nuevaDireccion.isEmpty() ? existente.getDireccion() : nuevaDireccion,
                                nuevaFecha.isEmpty() ? existente.getFechaNacimiento() : nuevaFecha
                        );

                        gestor.actualizarContacto(idActualizar, actualizado);
                    }
                }
                case 6 -> {
                    System.out.print("\n Campo para indexar (nombre, apellido, etc.): ");
                    String campo = sc.nextLine();
                    System.out.print("Tipo de índice (BST o AVL): ");
                    String tipoTexto = sc.nextLine();

                    try {
                        TipoIndice tipo = TipoIndice.valueOf(tipoTexto.toUpperCase());
                        BST<String> arbol = IndexManager.crearIndice(gestor.getContactos(), campo, tipo);
                        IndexManager.guardarIndice(arbol, campo, tipo);
                    } catch (IllegalArgumentException e) {
                        System.out.println(" TIPO DE ÍNDICE NO VÁLIDO. USE BST o AVL.");
                    }
                }
                case 7 -> {
                    System.out.print("\n Nombre del archivo del índice (.txt): ");
                    String nombreArchivo = sc.nextLine();
                    System.out.print("Campo del índice (nombre, apellido, etc.): ");
                    String campo = sc.nextLine();
                    System.out.print("Tipo de índice (BST o AVL): ");
                    String tipoTexto = sc.nextLine();

                    try {
                        TipoIndice tipo = TipoIndice.valueOf(tipoTexto.toUpperCase());
                        BST<String> arbol = IndexManager.cargarIndiceDesdeArchivo(nombreArchivo, gestor.getContactos(), campo, tipo);
                        System.out.println("IDs en el índice cargado: ");
                        for (int id : arbol.levelOrderTraversal()) {
                            System.out.print(id + " ");
                        }
                        System.out.println();
                    } catch (IllegalArgumentException e) {
                        System.out.println(" TIPO DE ÍNDICE NO VÁLIDO. USE BST o AVL.");
                    }
                }
                case 0 -> mostrarDespedida();
                default -> System.out.println("OPCIÓN INVÁLIDA. INTENTA NUEVAMENTE.");
            }

        } while (opcion != 0);

        sc.close();
    }

    private static void imprimirMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       LIBRETA DE CONTACTOS - MENÚ    ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ 1. Ver todos los contactos           ║");
        System.out.println("║ 2. Agregar contacto                  ║");
        System.out.println("║ 3. Buscar contacto por campo         ║");
        System.out.println("║ 4. Eliminar contacto por ID          ║");
        System.out.println("║ 5. Actualizar contacto por ID        ║");
        System.out.println("║ 6. Crear y guardar índice            ║");
        System.out.println("║ 7. Cargar índice desde archivo       ║");
        System.out.println("║ 0. Salir                             ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    private static void mostrarBienvenida() {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║                                                    ║");
        System.out.println("║       ¡BIENVENIDO A LA LIBRETA DE CONTACTOS!       ║");
        System.out.println("║                                                    ║");
        System.out.println("║        Administra fácilmente tus contactos         ║");
        System.out.println("║        con búsquedas rápidas, actualización        ║");
        System.out.println("║         dinámica y respaldo en archivo CSV.        ║");
        System.out.println("║                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void mostrarDespedida() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║ ¡Gracias por usar la Libreta de Contactos! ║");
        System.out.println("║          ¡Esperamos verte pronto! :D       ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}