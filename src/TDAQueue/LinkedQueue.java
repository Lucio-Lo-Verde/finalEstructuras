package TDAQueue;

import TDAStack.Node;

public class LinkedQueue<E> implements Queue<E>{
	
	protected int size;
	protected Node<E> head;
	protected Node<E> tail;
	
	public LinkedQueue() {
		size = 0;
		head = tail = null;
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
	public E front() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("La cola esta vacia; front");
		
		return head.getElemento();
	}

	@Override
	public void enqueue(E element) {
		Node<E> nuevo = new Node<E>(element);
		if(isEmpty()) {
			head = nuevo;
			tail = nuevo;
		}
		else {
			tail.setSiguiente(nuevo);
			tail = nuevo;
		}
		size++;
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("La cola esta vacia; dequeue");
		
		E aRetornar = head.getElemento();
		head = head.getSiguiente();
		if(size==1)
			tail = null;
		size--;
		
		return aRetornar;
	}

}
