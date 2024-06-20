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

    public void agregarAtras(T elem) {
        Nodo nuevo_nodo = new Nodo(elem);
        if(tamaño==0){
            this.primer=nuevo_nodo;
            this.ultimo=nuevo_nodo;
        }
        else{
            this.ultimo.sig=nuevo_nodo;
            nuevo_nodo.prev=this.ultimo;
            this.ultimo=nuevo_nodo;
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
    public T obtener(int i) {
        T res;
        if (i==0){
           res= this.primer.valor;	
        }
        else{
            Nodo actual=this.primer;
            for (int j = 0; j < i; j++) {
                actual=actual.sig;   
            }
            
            res= actual.valor;
        }
        return res;
    }

    public void eliminar(int i) {
        
        if (i==0){
            if(tamaño ==1){
                this.primer=null;
                this.ultimo=null;
                this.tamaño-=1;
            }
            else{this.primer=this.primer.sig;
            this.primer.prev=null;
            this.tamaño-=1;}
        }
        else{
            Nodo actual=this.primer;
        Nodo prev_actual=this.primer;
       
        for(int j  = 0; j<i; j++){
            prev_actual=actual;
            actual=actual.sig;
        }
       
        prev_actual.sig=actual.sig;
        actual.prev=null;
        this.tamaño-=1;
        }
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual =this.primer;
        for (int j= 0; j<indice;j++){
            actual=actual.sig;
        }
        actual.valor=elem;
    }

    public Lista_enlazada<T> copiar() {
        Lista_enlazada<T> list = new Lista_enlazada<>();
        if (this.tamaño!=0){
            list.agregarAtras(this.primer.valor);
            Nodo actual = this.primer.sig;
            for(int i = 1;i<this.tamaño;i++){
                list.agregarAtras(actual.valor);
                actual=actual.sig;
                
            }
        }

        return list;
    }

    public Lista_enlazada(Lista_enlazada<T> lista) {
        Lista_enlazada<T> copia = lista.copiar();
        this.primer=copia.primer;
        this.ultimo=copia.ultimo;
        this.tamaño=copia.tamaño;
    }
    
    @Override
    public String toString() {
        StringBuffer sbuffer= new StringBuffer();
        if (this.tamaño==0) {
            sbuffer.append("[]"); 
        }
        else{
            sbuffer.append("["+this.primer.valor);
            if (this. tamaño !=1){
                    Nodo actual=this.primer.sig;
                for (int i =1; i<tamaño;i++){
                    
                    sbuffer.append(", "+actual.valor);
                    actual=actual.sig;
                }
            }
        }
        sbuffer.append("]");            
        
        return sbuffer.toString();
    }
}
