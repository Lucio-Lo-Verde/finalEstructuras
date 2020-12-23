package TDAList;

import java.util.Iterator;

public class SimpleLinkedList<E> implements PositionList<E> {

	protected int size;
	protected Node<E> head;

	public SimpleLinkedList() {
		size = 0;
		head = null;
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

		return head;
	}

	@Override
	public Position<E> last() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista esta vacia; last");

		Node<E> nodo = head;
		while(nodo.getSiguiente()!=null) {
			nodo = nodo.getSiguiente();
		}

		return nodo;
	}

	private Node<E> checkPosition(Position<E> pos) throws InvalidPositionException {
		if(pos==null)
			throw new InvalidPositionException("La posicion es nula");
		if(isEmpty())
			throw new InvalidPositionException("La lista esta vacia");
		Node<E> aRetornar = null;
		try {
			aRetornar = (Node<E>) pos;
		} catch(ClassCastException e) {
			throw new InvalidPositionException("Error de casteo; no correponde a la lista");
		}

		return aRetornar;
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Node<E> nodo = checkPosition(p);

		if(nodo.getSiguiente() == null)
			throw new BoundaryViolationException("No tengo siguiente al ultimo");

		return nodo.getSiguiente();
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Node<E> nodo = checkPosition(p);

		if(nodo == head)
			throw new BoundaryViolationException("No tengo anterior al primero");

		Node<E> anterior = buscarAnterior(nodo);

		if(anterior.getSiguiente()==null)
			throw new InvalidPositionException("La posicion no pertenece a esta lista");

		return anterior;
	}

	@Override
	public void addFirst(E element) {
		head = new Node<E>(element, head);
		size++;
	}

	@Override
	public void addLast(E element) {
		if(isEmpty())
			head = new Node<E>(element);
		else {
			Node<E> ultimo = buscarAnterior(null);
			ultimo.setSiguiente(new Node<E>(element));
		}
		size++;

	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		Node<E> nodo = checkPosition(p);
		Node<E> nuevo = new Node<E>(element, nodo.getSiguiente());
		nodo.setSiguiente(nuevo);
		size++;
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		Node<E> nodo = checkPosition(p);
		if(nodo==head)
			head = new Node<E>(element, head);//existe el arrayList?
		else {
			Node<E> anterior = buscarAnterior(nodo);
			if(anterior.getSiguiente()!=nodo)
				throw new InvalidPositionException("La posicion no pertenece a la lista");
			anterior.setSiguiente(new Node<E>(element, nodo));
		}
		size++;
	}

	private Node<E> buscarAnterior(Node<E> nodo) {
		Node<E> anterior = head;
		if(!isEmpty()) {
			while(anterior.getSiguiente()!=nodo && anterior.getSiguiente()!=null) {
				anterior = anterior.getSiguiente();
			}
			if(anterior.getSiguiente()!=nodo)
				anterior = null;
		}
		return anterior;
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		Node<E> nodo = checkPosition(p);
		E aRetornar = nodo.element();
		if(nodo==head)
			head = head.getSiguiente();
		else {
			Node<E> anterior = buscarAnterior(nodo);
			anterior.setSiguiente(nodo.getSiguiente());
			nodo.setSiguiente(null);
		}
		size--;

		return aRetornar;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		Node<E> nodo = checkPosition(p);
		E aRetornar = nodo.element();
		nodo.setElemento(element);

		return aRetornar;
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> iterator = null;
		try {
			iterator = new ElementIterator<E>(this);
		} catch(EmptyListException e) {
			System.out.println(e.getMessage());
		}
		return iterator;
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> lista = new SimpleLinkedList<Position<E>>();

		if(!isEmpty()) {
			Node<E> nodo = head;
			while(nodo!=null) {
				lista.addLast(nodo);
				nodo = nodo.getSiguiente();
			}
		}

		return lista;
	}

}
