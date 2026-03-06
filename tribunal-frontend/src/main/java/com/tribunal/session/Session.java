package com.tribunal.session;
/** Clasa pentru gestionarea sesiunii utilizatorului.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class Session {

    private static String rol;

    public static void setRol(String r) {
        rol = r;
    }

    public static String getRol() {
        return rol;
    }

    public static boolean isReadOnly() {
        return !"ADMIN".equalsIgnoreCase(rol);
    }

    public static void clear() {
        rol = null;
    }
}
