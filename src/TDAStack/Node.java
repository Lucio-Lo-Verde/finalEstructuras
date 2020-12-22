package TDAStack;

public class Node<E> {
   protected Node<E> siguiente;
   protected E elemento;
public Node(Node<E> siguiente, E elemento) {
	this.siguiente = siguiente;
	this.elemento = elemento;
}
public Node(E element) {
	this(null,element);
}
public Node() {
	this(null,null);
}

public Node<E> getSiguiente() {
	return siguiente;
}
public void setSiguiente(Node<E> siguiente) {
	this.siguiente = siguiente;
}
public E getElemento() {
	return elemento;
}
public void setElemento(E elemento) {
	this.elemento = elemento;
}
   

}
