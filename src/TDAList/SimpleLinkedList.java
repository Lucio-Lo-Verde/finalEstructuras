package TDAList;

import java.util.Iterator;

public class SimpleLinkedList<E> implements PositionList<E> {
    protected int size;
    protected Node<E> head;
    public SimpleLinkedList() {
    	size=0;
    	head=null;
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
		if(size==0)
			throw new EmptyListException("Lista vacia");
		
		return head;
	}
	@Override
	public Position<E> last() throws EmptyListException {
		if(size==0)
			throw new EmptyListException("lista vacia");
		Node<E> anterior= head;
		while(anterior.getSiguiente()!=null) {
			anterior = anterior.getSiguiente();
		}
		
		
		return anterior;
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Node<E> nodo = checkPosition(p);
		
		if(nodo.getSiguiente()==null)
			throw new BoundaryViolationException("No hay siguiente");
				
		return nodo.getSiguiente();
	}

	private Node<E> checkPosition(Position<E> pos)throws InvalidPositionException{
    	Node<E> nodo= null;
    	if(pos==null) {
    		throw new InvalidPositionException("pos nula");
    	}
       if(isEmpty()) {
    		throw new InvalidPositionException("Lista vacia");
    	}else {
    		
    		try {
    			nodo = (Node<E>)pos;
    		}catch(ClassCastException e) {
    			throw new InvalidPositionException("Error de casteo");
    		}
    		
    	}
    	return nodo;
    }
	
	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Node<E> nodo = checkPosition(p);
		if(nodo==head)
			throw new BoundaryViolationException("no hay anterior");
		Node<E> anterior = head;
		
		while(anterior.getSiguiente()!=nodo&&anterior.getSiguiente()!=null) {
			anterior = anterior.getSiguiente();
		}
		
		
		return anterior;
	}

	@Override
	public void addFirst(E element) {
		 head = new Node<E>(element,head);
		 size++;
	} 

	@Override
	public void addLast(E element) {
		if(head==null) {
			head= new Node<E>(element);
		}else {
			Node<E> ultimo = head;		
			while(ultimo.getSiguiente()!=null) {
				ultimo = ultimo.getSiguiente();
			}
			ultimo.setSiguiente(new Node<E>(element));
		}
	    size++;
	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		Node<E> nodo = checkPosition(p);
		nodo.setSiguiente(new Node<E>(element,nodo.getSiguiente()));
		size++;
		
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		Node<E> nodo = checkPosition(p);
		if(nodo==head)

			head = new Node<E>(element, head);//existe el arrayList?

		else {
		Node<E> anterior = head;

		if(!isEmpty()) {
			while(anterior.getSiguiente()!=nodo && anterior.getSiguiente()!=null) {
				anterior = anterior.getSiguiente();
			}
			if(anterior.getSiguiente()!=nodo)
				anterior = null;

		}
		anterior.setSiguiente(new Node<E>(element,anterior.getSiguiente()));
		}
		size++;
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
      Node<E> nodo = checkPosition(p);
      E resultado = p.element();
      if(nodo==head) {
    	  head = head.getSiguiente();
      }else {
    	  Node<E> anterior = head;
    	  while(anterior.getSiguiente()!= nodo) {
    		  anterior = anterior.getSiguiente();
    	  }
    	  anterior.setSiguiente(nodo.getSiguiente());
    	  nodo.setSiguiente(null);
    	  
      }
      size--;
		return resultado;

	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
       Node<E> nodo = checkPosition(p);
       E resultado = p.element();
       nodo.setElemento(element);
		return resultado;
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> resultado= null;
		try {
			resultado = new ElementIterator<E>(this);
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>>lista =new SimpleLinkedList<Position<E>>();
		
		if(!isEmpty()) {
		Node<E> nuevo= head;	
		  while(nuevo!=null) {
			 lista.addLast(nuevo);
			 nuevo=nuevo.getSiguiente();
		  }
			  
		}
		return lista;
	}
  
}
