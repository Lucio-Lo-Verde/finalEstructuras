package TDAMap;

import java.util.Iterator;

import TDAList.DoubleLinkedList;
import TDAList.PositionList;
import TDAList.DoubleLinkedList;

public class OpenHashMap<K,V> implements Map<K,V>{

	protected PositionList<Entry<K,V>> [] contenedor;
	protected int size;
	protected int cantBuckets;
	protected final float FACTOR = 0.5f; //creo que era 0.5

	public OpenHashMap() {
		cantBuckets = 1001;
		contenedor = new PositionList[cantBuckets];
		for(int i=0 ; i<cantBuckets ; i++) {
			contenedor[i] = new DoubleLinkedList<Entry<K,V>>();
		}
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

	private void checkKey(K key) throws InvalidKeyException{
		if(key==null)
			throw new InvalidKeyException("Clave nula");
	}

	private int hashValue(K key) {
		return Math.abs(key.hashCode()%cantBuckets);
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K,V>> bucket = contenedor[hashValue(key)];
		Iterator<Entry<K,V>> itBucket = bucket.iterator();
		boolean esta = false;
		Entry<K,V> entrada = null;
		
		while(!esta && itBucket.hasNext()) {
			entrada = itBucket.next();
			esta = entrada.getKey().equals(key);
		}
		V aRetornar = null;
		if(esta)
			aRetornar = entrada.getValue();
			
		return aRetornar;
	}
	
	private void resize() {
		PositionList<Entry<K,V>> [] nuevoContenedor;
		int nuevoTamaño = nextPrimo(cantBuckets*2);
		nuevoContenedor = new PositionList[nuevoTamaño];
		cantBuckets = nuevoTamaño;
		PositionList<Entry<K,V>> [] contenedorViejo = contenedor;
		contenedor = nuevoContenedor;
		for(int i=0 ; i<cantBuckets ; i++) {
			contenedor[i] = new DoubleLinkedList<Entry<K,V>>();
		}
		for(int i=0 ; i<contenedorViejo.length ; i++) {
			for(Entry<K,V> entrada : contenedorViejo[i]) {
				contenedor.put(entrada.getKey(), entrada.getValue());
			}
		}
	}
	
	private int nextPrimo(int siguientePrimo) {
		while(!esPrimo(siguientePrimo)) {
			siguientePrimo ++;
		}
		
		return siguientePrimo;
	}
	
	private boolean esPrimo(int primo) {
		boolean esPrimo = true;
		for(int i=2 ; i<primo && esPrimo ; i++) {
			esPrimo = primo%i != 0;
		}
		
		return esPrimo;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		
		
		return null;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		// TODO Auto-generated method stub
		return null;
	}

}
