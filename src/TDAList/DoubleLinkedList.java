package TDAList;

import java.util.Iterator;

public class DoubleLinkedList<E> implements PositionList<E> {

	protected DNode<E> header;
	protected DNode<E> trailer;
	protected int size;

	public DoubleLinkedList() {
		header = new DNode<E>();
		trailer = new DNode<E>(header, null, null);
		header.setSiguiente(trailer);
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public Position<E> first() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista esta vacia; first");

		return header.getSiguiente();
	}

	@Override
	public Position<E> last() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista esta vacia; last");

		return trailer.getAnterior();
	}

	private DNode<E> checkPosition(Position<E> pos) throws InvalidPositionException {
		if(pos==null)
			throw new InvalidPositionException("La posicion es nula; checkPosition");
		if(isEmpty())
			throw new InvalidPositionException("La lista esta vacia; checkPosition");
		DNode<E> nodo = null;
		try {
			nodo = (DNode<E>) pos;
		} catch(ClassCastException e) {
			throw new InvalidPositionException("Error de casteo; checkPosition");
		}

		return nodo;
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> nodo = checkPosition(p);

		if(nodo.getSiguiente()==trailer)
			throw new BoundaryViolationException("No existe el siguiente al ultimo nodo");

		return nodo.getSiguiente();
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> nodo = checkPosition(p);

		if(nodo.getAnterior()==header)
			throw new BoundaryViolationException("No existe el anterior al primer nodo");

		return nodo.getAnterior();
	}

	@Override
	public void addFirst(E element) {
		DNode<E> siguiente = header.getSiguiente();
		DNode<E> nuevo = new DNode<E>(header, element, siguiente);

		siguiente.setAnterior(nuevo);
		header.setSiguiente(nuevo);

		size++;
	}

	@Override
	public void addLast(E element) {
		DNode<E> anterior = trailer.getAnterior();
		DNode<E> nuevo = new DNode<E>(anterior, element, trailer);

		trailer.setAnterior(nuevo);
		anterior.setSiguiente(nuevo);

		size++;
	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> nodo = checkPosition(p);
		DNode<E> siguiente = nodo.getSiguiente();
		DNode<E> nuevo = new DNode<E>(nodo, element, siguiente);

		siguiente.setAnterior(nuevo);
		nodo.setSiguiente(nuevo);

		size++;
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> nodo = checkPosition(p);
		DNode<E> anterior = nodo.getAnterior();
		DNode<E> nuevo = new DNode<E>(anterior, element, nodo);

		anterior.setSiguiente(nuevo);
		nodo.setAnterior(nuevo);

		size++;		
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		DNode<E> nodo = checkPosition(p);
		E aRetornar = nodo.getElemento();

		DNode<E> anterior = nodo.getAnterior();
		DNode<E> siguiente = nodo.getSiguiente();

		anterior.setSiguiente(siguiente);
		siguiente.setAnterior(anterior);

		nodo.setAnterior(null);
		nodo.setSiguiente(null);

		size--;

		return aRetornar;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> nodo = checkPosition(p);
		E aRetornar = nodo.getElemento();

		nodo.setElemento(element);

		return aRetornar;
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> iterator = null;

		try {
			iterator = new ElementIterator(this);
		} catch(EmptyListException e) {
			System.out.println("La lista esta vacia; iterator");
		}

		return iterator;
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> iterable = new DoubleLinkedList<Position<E>>();
		DNode<E> nodo = header.getSiguiente();
		if(!isEmpty())
			while(nodo!=trailer) {
				iterable.addLast(nodo);
				nodo = nodo.getSiguiente();
			}

		return iterable;
	}

}
