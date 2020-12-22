package TDAList;

public class Node<E> implements Position<E> {
	
	private E elemento;
	private Node<E> siguiente;
	
	public Node(E element, Node<E> siguiente) {
		this.elemento = element;
		this.siguiente = siguiente;
	}
	
	public Node(E element) {
		this(element, null);
	}
	
	public Node() {
		this(null, null);
	}

	
	public void setElemento(E element) {
		this.elemento = element;
	}

	public Node<E> getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Node<E> siguiente) {
		this.siguiente = siguiente;
	}

	@Override
	public E element() {
		return elemento;
	}

}
