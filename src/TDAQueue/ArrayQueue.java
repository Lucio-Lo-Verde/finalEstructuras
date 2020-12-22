package TDAQueue;

public class ArrayQueue<E> implements Queue<E> {
	protected E[] cola;
	protected int inicio;
	protected int fin;
	
	public ArrayQueue() {
		cola = (E[]) new Object[5];
		inicio = fin = 0;
	}

	@Override
	public int size() {
		return (cola.length - inicio + fin) % cola.length;
	}

	@Override
	public boolean isEmpty() {
		return size()==0;
	}

	@Override
	public E front() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("La cola esta vacia; front");
		
		return cola[inicio];
	}

	@Override
	public void enqueue(E element) {
		if(size()==cola.length-1)
			resize();
		
		cola[fin] = element;
		fin = (fin+1)%cola.length;
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("La cola esta vacia; dequeue");
		
		E aRetornar = cola[inicio];
		inicio = (inicio+1)%cola.length;
		
		return aRetornar;
	}
	
	private void resize() {
		E[] nuevaCola = (E[]) new Object[cola.length*2];
		for(int i=0 ; i<cola.length ; i++) {
			nuevaCola[i] = cola[(inicio+i)%cola.length];
		}
		fin = size();
		inicio = 0;
		cola = nuevaCola;
	}

}
