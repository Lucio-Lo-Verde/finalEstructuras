package TDAStack;

public class LinkedStack<E> implements Stack<E> {
    protected int size;
    protected Node<E> head;
    
    public LinkedStack() {
    	this.size=0;
    	this.head=null;
    }
    
	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public E top() throws EmptyStackException {
        if(size==0)
        	throw new EmptyStackException("Pila vacia; top");
		return head.getElemento();
	}

	@Override
	public void push(E element) {
		head = new Node<E>(head,element);
		size++;

	}

	@Override
	public E pop() throws EmptyStackException {
		if(size==0)
			throw new EmptyStackException("Pila vacia; pop");
		
		E aRetornar = head.getElemento();
		head = head.getSiguiente();
		size--;
		
		return aRetornar;
	}

}
