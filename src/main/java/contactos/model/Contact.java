package contactos.model;


 //Clase que representa un contacto dentro del sistema.
 // Contiene información personal como nombre, apodo, teléfono, etc.
public class Contact {
    // Atributos privados del contacto
    private int id;
    private String nombre;
    private String apellido;
    private String apodo;
    private String telefono;
    private String email;
    private String direccion;
    private String fechaNacimiento;

     //Constructor principal del contacto.
     //Inicializa todos los campos del contacto con los valores proporcionados.
    public Contact(int id, String nombre, String apellido, String apodo,
                   String telefono, String email, String direccion, String fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.apodo = apodo;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Métodos Getter y Setter para acceder y modificar los atributos de forma controlada.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

     // Método sobrescrito para mostrar la información del contacto de forma legible.
     //Se utiliza al imprimir el objeto en consola o al mostrarlo al usuario.

    @Override
    public String toString() {
        return "Contacto #" + id + " - " + nombre + " " + apellido + " (" + apodo + ")\n" +
                "Teléfono: " + telefono + " | Email: " + email + "\n" +
                "Dirección: " + direccion + " | Fecha Nacimiento: " + fechaNacimiento;
    }
     //Convierte el objeto Contact a una línea de texto en formato CSV.

    public String toCSV() {
        return id + "," + nombre + "," + apellido + "," + apodo + "," +
                telefono + "," + email + "," + direccion + "," + fechaNacimiento;
    }


     //Método estático para crear un objeto Contact a partir de una línea CSV.
     //Se espera que los valores estén separados por comas.
     // param linea Línea leída del archivo CSV.
     // return Objeto Contact creado a partir de los datos.

    public static Contact fromCSV(String linea) {
        String[] partes = linea.split(",", -1); // -1 asegura que se incluyan campos vacíos

        // Validación básica de longitud
        if (partes.length < 8) {
            throw new IllegalArgumentException("Línea CSV inválida: " + linea);
        }

        int id = Integer.parseInt(partes[0].trim()); // Conversión del ID a entero

        // Retorna el nuevo objeto Contact usando los valores extraídos
        return new Contact(
                id,
                partes[1].trim(), // nombre
                partes[2].trim(), // apellido
                partes[3].trim(), // apodo
                partes[4].trim(), // teléfono
                partes[5].trim(), // email
                partes[6].trim(), // dirección
                partes[7].trim()  // fecha de nacimiento
        );
    }
}
