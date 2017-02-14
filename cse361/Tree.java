package cse361;
import com.sun.media.sound.InvalidFormatException;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tree<E extends String> implements Iterable<E> {

	private Node root;

	public Tree(E treeSpec){
		try {
            root = processTreeSpec((E) cleanTreeSpec(treeSpec.trim()));
        } catch(InvalidFormatException ife){
		    System.out.println("The tree given was an incorrect format!");
        }
	}

	private Node processTreeSpec(E strSpec) throws InvalidFormatException {

        if(strSpec.length() == 0)
            throw new InvalidFormatException("Learn the tree spec!");

        String regexp = "\\([\\w\\s]*[\\w\\s\\(\\)]*\\)";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(strSpec);

	    if (matcher.find()) {
            System.out.println("tree specification: '" + strSpec + "'");
            System.out.println("Found valid tree specification: '" + matcher.group() + "'");
            strSpec = (E)matcher.group();
            System.out.println(matcher.start() + ":" + matcher.end());
            strSpec = (E)strSpec.substring(matcher.start() + 1, matcher.end() - 1);

            // character array for while loop iteration
            char[] spec = strSpec.trim().toCharArray();
            System.out.println(strSpec);

            // screw stacks we're going full tree here
            int firstOpen = strSpec.indexOf('(',1);
            int firstClose= strSpec.indexOf(')',1);
            int index = (firstOpen!=-1 && firstOpen<firstClose)?firstOpen:firstClose;
            int nodeIndex = index;
            Node root = new Node(strSpec.substring(0,index));
            Node currNode = root;

            try {
                while (index < spec.length) {
                    if (spec[index] == '(') {
                    /* First: Construct node from current string as parent */
                        // set stack top node content to substring(start,end)
                        String tmpContent = strSpec.substring(nodeIndex, index);
                        if (index - nodeIndex > 0) {
                            currNode.setContent(tmpContent);
                        }
                    /* Next: Prepare new child node */
                        currNode.makeParentOf(new Node());
                        currNode = (Node) currNode.getChildren().getLast();
                        // add new node to stack
                        // set current index + 1 to read string
                        nodeIndex = index + 1;
                        // ie. get prepared to read characters for setting content

                    } else if (spec[index] == ')') {
                        if (spec[index - 1] != ')') {
                            // set stack top node content to substring(start,end)
                            String tmpContent = strSpec.substring(nodeIndex, index);
                            currNode.setContent(tmpContent);
                        }
                        currNode = currNode.getParent();
                        nodeIndex = index + 1;
                    }
                    index++;
                }
            } catch (NullPointerException nfe){
                // Tried to access a node that doesn't exist
                // Invalid tree architecture
                throw new InvalidFormatException("Learn the tree spec!");
            }
            return root;
        } else
            throw new InvalidFormatException("Learn the tree spec!");
	}

	private String cleanTreeSpec(String s){
	    String tmpS = s.replace("( ","(").replace(") ",")").replace(" (","(").replace(" )",")");
	    if(tmpS.equals(s))
	        return tmpS;
	    else
	        return cleanTreeSpec(tmpS);

    }

	public static void main(String[] args){
		Tree<String> testTree = new Tree<>("(tom(chase)(chase(john)(kyle))");

	}

	public Iterator<E> iterator() {
		return new TreeIterator<E>(this);
	}

    public Node getRoot() {
        return root;
    }

	public void forEach(Consumer<? super E> action) {
	    //unimplemented
		System.out.println("Not implemented, sorry guy.");
	}
	
	public Spliterator<E> spliterator() {
	    //unimplemented
        return (Spliterator<E>)new Object();
	}
	
	private class TreeIterator<T extends String> implements Iterator<T> {

		//immutable queue of nodes within tree
		//will store all nodes upon generation
		private LinkedList<T> nodeList = new LinkedList<T>();
		private int index = 0;

		public TreeIterator(Tree t){
            preOrderTraversal(t.getRoot(), nodeList);
		}

		private void preOrderTraversal(Node n, LinkedList<T> ll){
		    ll.add((T)n.getContent());
		    for(Object child : n.getChildren()){
		        preOrderTraversal((Node)child, ll);
            }
        }

		public boolean hasNext(){
			return (index < nodeList.size());
		}

		public T next(){
			//unimplemented
			return nodeList.get(index++);
		}

		public void remove(){
			//unimplemented
		}
	}

    public class Node<E extends String> {

        private E content;
        private Node parent;
        private LinkedList<Node> children;

        public Node(){
            this.content = (E)"";
            this.parent = null;
            this.children = new LinkedList<Node>();
        }

        public Node(E element){
            this.content = element;
            this.parent = null;
            this.children = new LinkedList<Node>();
        }

        public Node(E element, Node parent){
            this.content = element;
            this.parent = parent;
            this.children = new LinkedList<Node>();
        }

        public void setContent(E c){
            this.content = c;
        }

        public void makeParentOf(Node child){
            this.addChild(child);
            child.setParent(this);
        }

        public void setParent(Node n) {
            this.parent = n;
        }

        public void addChild(Node c){
            this.children.add(c);
        }

        public E getContent(){
            return this.content;
        }

        public Node getParent(){
            return this.parent;
        }

        public LinkedList<Node> getChildren(){
            return this.children;
        }
    }
}
