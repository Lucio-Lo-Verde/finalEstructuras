package TDADictionary;

import java.util.Iterator;

import TDAList.DoubleLinkedList;
import TDAList.InvalidPositionException;
import TDAList.Position;
import TDAList.PositionList;
import TDAMap.Entrada;
import TDAMap.Entry;
import TDAMap.InvalidKeyException;

public class OpenHashDictionary<K,V> implements Dictionary<K,V> {

	protected int size;
	protected int cantBuckets;
	protected PositionList<Entry<K,V>> [] contenedor;
	protected final float FACTOR = 0.5f;


	public OpenHashDictionary() {
		this.size = 0;
		this.cantBuckets = 1001;
		this.contenedor = new PositionList[this.cantBuckets];
		for(int i=0 ; i<this.cantBuckets ; i++) {
			this.contenedor[i] = new DoubleLinkedList<Entry<K,V>>();
		}
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
	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K,V>> bucket = contenedor[hashValue(key)];
		Iterator<Entry<K,V>> itBucket = bucket.iterator();
		boolean esta = false;
		Entry<K,V> entrada = null;
		while(!esta && itBucket.hasNext()) {
			entrada = itBucket.next();
			esta = entrada.getKey().equals(key);
		}
		if(!esta)
			entrada = null;

		return entrada;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K,V>> bucket = contenedor[hashValue(key)];
		PositionList<Entry<K,V>> aRetornar = new DoubleLinkedList<Entry<K,V>>();
		for(Entry<K,V> entrada : bucket) {
			if(entrada.getKey().equals(key))
				aRetornar.addLast(entrada);
		}

		return aRetornar;
	}
	
	private void resize() {
		int tamanio = nextPrimo(this.cantBuckets*2);
		PositionList<Entry<K,V>> [] nuevoContenedor = new PositionList[tamanio];
		for(int i=0 ; i<tamanio ; i++)
			nuevoContenedor[i] = new DoubleLinkedList<Entry<K,V>>();
		
		PositionList<Entry<K,V>> bucket;
		PositionList<Entry<K,V>> nuevoBucket;
		
		//cambio el tamaño del arreglo anterior para poder hacer un nuevo hashcode
		cantBuckets = tamanio;

		//recorro el arreglo del primer contenedor
		for(int i=0 ; i<contenedor.length ; i++) {
			bucket = contenedor[i];
			for(Entry<K,V> entrada : bucket) {
				//reubico las entraas en el nuevobucket
				nuevoBucket = nuevoContenedor[hashValue(entrada.getKey())];
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
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		
		if(esFactor())
			resize();
		
		PositionList<Entry<K,V>> bucket = contenedor[hashValue(key)];
		Entry<K,V> entrada = new Entrada<K,V>(key, value);
		bucket.addLast(entrada);
		size++;

		return entrada;
	}

	private void checkEntry(Entry<K,V> entry) throws InvalidEntryException {
		if(entry==null)
			throw new InvalidEntryException("Entrada nula");
		if(entry.getKey()==null)
			throw new InvalidEntryException("Clave nula");
	}

	@Override
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		checkEntry(e);
		PositionList<Entry<K,V>> bucket = contenedor[hashValue(e.getKey())];
		Iterable<Position<Entry<K,V>>> posiciones = bucket.positions();
		Iterator<Position<Entry<K,V>>> itPosiciones = posiciones.iterator();
		Position<Entry<K,V>> pos = null;
		boolean esta = false;
		Entry<K,V> entrada = null;

		while(!esta && itPosiciones.hasNext()) {
			pos = itPosiciones.next();
			esta = pos.element().equals(e);
		}

		if(!esta)
			throw new InvalidEntryException("La entrada no se encuentra en el diccionario; remove");
		
		entrada = pos.element();
		try {
			bucket.remove(pos);
		} catch(InvalidPositionException exc) {
			exc.printStackTrace();
		}
		size--;

		return entrada;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> aRetornar = new DoubleLinkedList<Entry<K,V>>();
		for(int i=0 ; i<this.cantBuckets ; i++) {
			for(Entry<K,V> entrada : contenedor[i]) {
				aRetornar.addLast(entrada);
			}
		}

		return aRetornar;
	}

}
