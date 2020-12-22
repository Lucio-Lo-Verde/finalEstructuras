package TDAStack;

public class ArrayStack<E> implements Stack<E> {
    protected int size;
    protected E[] pila;
    
    @SuppressWarnings("unchecked")
	public ArrayStack() {
    	
    	this.pila = (E[])new Object[5];
    	this.size=0;
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
			throw new EmptyStackException("La pila esta vacia");
		
		
		return pila[size-1];
	}

	@Override
	public void push(E element) {
		if(size==pila.length)
			resize();
		
		pila[size++]=element;
		
		
	}

	@Override
	public E pop() throws EmptyStackException {
		if(size==0)
			throw new EmptyStackException("pila vacia para retirar");
		size--;
		E resultado = pila[size];
		
		pila[size]=null;
		
		return resultado;
	}

    private void resize() {
    	E[] nuevaPila = (E[])new Object[size*2];
    	for(int i=0;i<size;i++) {
    		nuevaPila[i]= pila[i];
    	}
    	pila= nuevaPila;
    
    }

}
