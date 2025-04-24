package contactos.model;

public class Contact {
    private int id;
    private String nombre;
    private String apellido;
    private String apodo;
    private String telefono;
    private String email;
    private String direccion;
    private String fechaNacimiento;

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

    // Getters y Setters
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

    @Override
    public String toString() {
        return "Contacto #" + id + " - " + nombre + " " + apellido + " (" + apodo + ")\n" +
                "Teléfono: " + telefono + " | Email: " + email + "\n" +
                "Dirección: " + direccion + " | Fecha Nacimiento: " + fechaNacimiento;
    }

    // Método para convertir a línea CSV
    public String toCSV() {
        return id + "," + nombre + "," + apellido + "," + apodo + "," +
                telefono + "," + email + "," + direccion + "," + fechaNacimiento;
    }

    public static Contact fromCSV(String linea) {
        String[] partes = linea.split(",", -1); // -1 para incluir campos vacíos si hay

        if (partes.length < 8) {
            throw new IllegalArgumentException("Línea CSV inválida: " + linea);
        }

        int id = Integer.parseInt(partes[0].trim());

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
