package TDAList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ElementIterator<E> implements Iterator<E> {

	protected PositionList<E> lista;
	protected Position<E> cursor;

	public ElementIterator(PositionList<E> lista) throws EmptyListException {
		this.lista = lista;
		if(this.lista.isEmpty())
			cursor = null;
		else
			cursor = this.lista.first();
	}

	@Override
	public boolean hasNext() {
		return cursor!=null;
	}

	@Override
	public E next() throws NoSuchElementException {
		if(cursor==null)
			throw new NoSuchElementException("No tiene siguiente");
		E aRetornar = cursor.element();
		
		try {
			cursor = (cursor==lista.last()) ? null : lista.next(cursor);
		} catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			System.out.println(e.getMessage());
		}
		
		return aRetornar;
	}

}
