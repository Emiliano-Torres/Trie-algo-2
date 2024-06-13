package aed;

public class Trie<T>{

    private Nodo_Trie<T> raiz;

    private class Nodo_Trie<T> {

        // Array para los hijos (256 signos del codigo ASCII)
        private Nodo_Trie<T>[] hijos;

        // Significado del nodo
        private T significado;

         // Constructor
         public Nodo_Trie() {
            hijos = new Nodo_Trie[256];
            significado = null;
        }

        // Métodos para obtener y establecer hijos y la marca de fin de palabra

        public Nodo_Trie<T>[] obtenerHijos() {
           return hijos;
        }

        public T obtenerSignificado() {
           return significado;
        }

        public void definirSignificado(T significado) {
           this.significado = significado;
        }

        // Método para verificar si tiene hijos
        public boolean tieneHijos() {
           for (Nodo_Trie<T> hijo : hijos) {
               if (hijo != null) {
                   return true;
               }
           }
           return false;
        }
    }

    // Constructor
    public Trie() {
       raiz = new Nodo_Trie<T>();
    }

    // Método para agregar una palabra
    public void agregar(String palabra, T significado) {
        Nodo_Trie<T> actual = raiz;
        for (char c : palabra.toCharArray()) { 
            // El metodo toCharArray tiene complejidad O(n) debido a que el metodo funciona de la siguiente manera:
            // Primerp accede a cada caracter (acceder a cada carácter de la cadena original para copiarlo en el nuevo array y acceder a un caracter es O(1))
            // Luego, el método recorre todos los caracteres de la cadena en un bucle, copiándolos uno por uno en el nuevo array, ese bucle se repite n veces.
            // Finalmente, crear un nuevo array de caracteres también es una operación O(n) porque la memoria tiene que ser asignada para n elementos.
            // Si juntas todo te queda complejidad de O(n).
            int index = c; // El índice es el valor ASCII del carácter
            if (actual.obtenerHijos()[index] == null) {
                actual.obtenerHijos()[index] = new Nodo_Trie<T>();
            }
            actual = actual.obtenerHijos()[index];
        } 
        actual.definirSignificado(significado);
    }

    // Método para verificar si una palabra pertenece al trie
    public boolean pertenece(String palabra) {
        Nodo_Trie<T> actual = raiz;
        for (char c : palabra.toCharArray()) {
            int index = c; // El índice es el valor ASCII del carácter
            if (actual.obtenerHijos()[index] == null) {
                return false;
            }
            actual = actual.obtenerHijos()[index];
        }
        return true;
    }

    // Método para borrar una palabra
    public void borrar(String palabra) {
        borrarRecursivo(raiz, palabra, 0);
    }

    public void borrarRecursivo(Nodo_Trie<T> actual, String palabra, int index) {
        if (index == palabra.length()) {
            if (actual.obtenerSignificado() != null) {
                actual.definirSignificado(null);
            }
            return;
        }

        char c = palabra.charAt(index);
        int charIndex = c; // El índice es el valor ASCII del carácter
        Nodo_Trie<T> nodo = actual.obtenerHijos()[charIndex];
        if (nodo == null){
            return; // La palabra no está en el Trie
        }

        borrarRecursivo(nodo, palabra, index + 1);

        // Eliminar el nodo hijo si es redundante y no es el final de una palabra
        if (nodo.obtenerSignificado() == null && !nodo.tieneHijos()) {
            actual.obtenerHijos()[index] = null;
        }
    }

}
