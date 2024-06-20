package aed;

/* INV REP:
 * tamaño >= 0
 * tamaño == #Nodos
 * primer.prev == null
 * ultimo.sig == null
 * (para todo nodo N)(para todo nodo M)(para todo nodo Ñ)(((si N.sig == Ñ y M.sig == Ñ , entonces N == M)))
 * (para todo nodo N)(para todo nodo M)(para todo nodo Ñ)(((si N.prev == Ñ y M.prev == Ñ , entonces N == M)))
 * (para todo nodo N)(existe un entero I)((Si N != "ultimo" , entonces partiendo desde N, .sig I veces alcanza al nodo "ultimo"))
 * (para todo nodo N)(existe un entero I)((Si N != "primer" , entonces partiendo desde N, .prev I veces alcanza al nodo "primer"))
 * (para todo nodo N)(N.valor != null)
*/

public class Lista_enlazada<T> {
    private Nodo primer;
    private Nodo ultimo;
    private int  tamaño;

    private class Nodo {
        public Nodo sig;
        public Nodo prev;
        public T valor;

        public Nodo(T valor){
            this.valor=valor;
            this.sig=null;
            this.prev=null;
        }
    }

    public Lista_enlazada() {
        this.primer=null;
        this.ultimo=null;
        this.tamaño=0;
    }

    public int longitud() {
        return tamaño;
    }
    public void agregarAdelante(T elem) {
        Nodo nuevo_nodo= new Nodo(elem);
        if(tamaño==0){
            this.ultimo=nuevo_nodo;
            this.primer=nuevo_nodo;
        }
        else{
            nuevo_nodo.sig=this.primer;
            this.primer.prev=nuevo_nodo;
            this.primer=nuevo_nodo;

        }
        
        this.tamaño+=1;
    }

    public void eliminar_ultimo(){
        if(ultimo!=primer){
            ultimo=ultimo.prev;
            ultimo.sig=null;
            tamaño--;
        }
        else{
            primer=null;
            ultimo=null;
            tamaño=0;
        }
        

    }
    public T obtenerUltimo(){
        return ultimo.valor;
    }

}
