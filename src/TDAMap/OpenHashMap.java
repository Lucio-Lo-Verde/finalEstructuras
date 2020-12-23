package TDAMap;

import java.util.Iterator;

import TDAList.DoubleLinkedList;
import TDAList.InvalidPositionException;
import TDAList.Position;
import TDAList.PositionList;
import TDAList.DoubleLinkedList;

public class OpenHashMap<K,V> implements Map<K,V>{

	protected PositionList<Entry<K,V>> [] contenedor;
	protected int size;
	protected int cantBuckets;
	protected final float FACTOR = 0.5f; //creo que era 0.5

	public OpenHashMap() {
		this.cantBuckets = 1001;
		contenedor = new PositionList[this.cantBuckets];
		for(int i=0 ; i<this.cantBuckets ; i++) {
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
		//Creo un contenedor de buckets mas grande y lo lleno de listas vacias
		int tamanio = nextPrimo(cantBuckets*2);
		PositionList<Entry<K,V>> [] nuevoContenedor = new PositionList[tamanio];
		for(int i= 0;i<tamanio;i++) {
			nuevoContenedor[i]= new DoubleLinkedList<Entry<K,V>>();
		}
		//-----------------------------------

		//cambio el tamaño del arreglo anterior para poder hacer un nuevo hashcode
		cantBuckets = tamanio;

		//recorro el arreglo del primer contenedor
		for(int i= 0;i<contenedor.length;i++) {
			PositionList<Entry<K,V>> bucket = contenedor[i];
			for(Entry<K,V> entrada : bucket) {
				int funcion = hashValue(entrada.getKey());
				//reubico las entraas en el nuevobucket
				PositionList<Entry<K,V>> nuevoBucket = nuevoContenedor[funcion];
				nuevoBucket.addLast(entrada);
			}
		}
		contenedor = nuevoContenedor;
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
	private boolean esFactor() {
		return size/cantBuckets>=FACTOR;
	}
	@Override
	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		V aRetornar= null;
		boolean esta= false;
		Entrada<K,V> entrada = null;
		Iterator<Entry<K,V>> itBucket = contenedor[hashValue(key)].iterator();
		while(!esta&&itBucket.hasNext()) {
			entrada = (Entrada<K,V>)itBucket.next();
			esta = entrada.getKey().equals(key);
		}
		if(esta) {
			aRetornar = entrada.getValue();
			entrada.setValue(value);
		}else {
			if(esFactor())
				resize();

			contenedor[hashValue(key)].addLast(new Entrada<K,V>(key,value));
			size++;
		}
		return aRetornar;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		V aRetornar = null;

		Position<Entry<K,V>> entrada = null;
		boolean esta= false;
		Iterable<Position<Entry<K,V>>> iterador = contenedor[hashValue(key)].positions();
		Iterator<Position<Entry<K,V>>> itBucket = iterador.iterator();

		while(!esta&&itBucket.hasNext()) {
			entrada = itBucket.next();
			esta = entrada.element().getKey().equals(key);
		}

		if(esta) {
			aRetornar = entrada.element().getValue();
			try {
				contenedor[hashValue(key)].remove(entrada);
			} catch (InvalidPositionException e) {
				e.printStackTrace();
			}
			size--;
		}
		return aRetornar;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> aRetornar = new DoubleLinkedList<K>();
		for(int i= 0; i<cantBuckets;i++) {
			for(Entry<K,V> entrada : contenedor[i]) {
				aRetornar.addLast(entrada.getKey());
			}
		}
		return aRetornar;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> aRetornar = new DoubleLinkedList<V>();
		for(int i= 0; i<cantBuckets;i++) {
			for(Entry<K,V> entrada : contenedor[i]) {
				aRetornar.addLast(entrada.getValue());
			}
		}
		return aRetornar;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> aRetornar = new DoubleLinkedList<Entry<K,V>>();
		for(int i= 0; i<cantBuckets;i++) {
			for(Entry<K,V> entrada : contenedor[i]) {
				aRetornar.addLast(entrada);
			}
		}
		return aRetornar;
	}

}
