package cse361;
import java.util.*;
import java.util.function.*;
public class Tree<E extends String> { //implements Iterable<E> {

	private Node root;

	public Tree(E treeSpec){
		processTreeSpec(treeSpec);
	}

	private void processTreeSpec(E strSpec){
		int index = 0;
		char[] spec = strSpec.toCharArray();
		while(index < spec.length){
			System.out.print(spec[index++]);
		}
	}

	public static void main(String[] args){
		System.out.println("Hey this is a working class!");

	}

	public Iterator<E> iterator() {
		return new TreeIterator<E>();
	}

	/*
	public void forEach(Consumer<? super E> action) {
		System.out.println("Not implemented, sorry guy.");
	}
	
	public Spliterator<E> spliterator() {
		
	}
	*/
	
	private class Node<E> {

		private E contents;
		private Node parent;
		private LinkedList<Node> children;

		public Node(E element){
			this.contents = element;
			this.parent = null;
			this.children = new LinkedList<Node>();
		}

		public Node(E element, Node parent){
			this.contents = element;
			this.parent = parent;
			this.children = new LinkedList<Node>();
		}

		public void addChild(Node c){
			this.children.add(c);
		}

		//visit nodes?

	}
	
	private class TreeIterator<T extends String> implements Iterator<T> {

		//immuteable queue of nodes within tree
		//will store all nodes upon generation
		private Queue<Node> q;

		public TreeIterator(){
			super();
		}

		public boolean hasNext(){
			return false;
		}

		public T next(){
			//unimplemented
			return (T)"Hello there!";
		}

		public void remove(){
			//unimplemented
		}
	}
}
