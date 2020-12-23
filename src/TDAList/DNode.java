package TDAList;

public class DNode<E> implements Position<E> {
	
	protected DNode<E> anterior;
	protected DNode<E> siguiente;
	protected E elemento;
	
	
	public DNode(DNode<E> anterior, E elemento, DNode<E> siguiente) {
		this.anterior = anterior;
		this.siguiente = siguiente;
		this.elemento = elemento;
	}
	
	public DNode(E elemento) {
		this(null, elemento, null);
	}
	
	public DNode() {
		this(null, null, null);
	}
	
	public DNode<E> getAnterior() {
		return anterior;
	}
	public void setAnterior(DNode<E> anterior) {
		this.anterior = anterior;
	}
	public DNode<E> getSiguiente() {
		return siguiente;
	}
	public void setSiguiente(DNode<E> siguiente) {
		this.siguiente = siguiente;
	}
	public E getElemento() {
		return elemento;
	}
	public void setElemento(E elemento) {
		this.elemento = elemento;
	}
	@Override
	public E element() {
		return elemento;
	}
	

}
