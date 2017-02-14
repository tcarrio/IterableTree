package cse361;
import java.util.*;
import java.util.function.Consumer;

public class Tree<E extends String> implements Iterable<E> {

	private Node root;

	public Tree(E treeSpec){
		root = processTreeSpec((E) cleanTreeSpec(treeSpec.trim()));
	}

	private Node processTreeSpec(E strSpec){
	    // character array for while loop iteration
		char[] spec = strSpec.trim().toCharArray();
		System.out.println(strSpec);

		// screw stacks we're going full tree here
        int firstOpen = strSpec.indexOf('(',1);
        int firstClose= strSpec.indexOf(')',1);
        int index = (firstOpen!=-1 && firstOpen<firstClose)?firstOpen:firstClose;
		int nodeIndex = index;
		Node root = new Node(strSpec.substring(1,index));
        Node currNode = new Node();
		root.makeParentOf(currNode);


		while(index < spec.length - 1 ){
			//System.out.printf("%d : %c\n",index,spec[index]);
            System.out.printf("Node content:%s\n",currNode.getContent());
            if(spec[index] == '(') {
                /* First: Construct node from current string as parent */
                // set stack top node content to substring(start,end)
                String tmpContent = strSpec.substring(nodeIndex, index);
                // pop node from stack to tempNode
                if(index-nodeIndex > 1){
                    currNode.setContent(tmpContent);
                }
                System.out.printf("%s\n", tmpContent);
                /* Next: Prepare new child node */
                currNode.makeParentOf(new Node());
                currNode = (Node) currNode.getChildren().getLast();
                // add new node to stack
                // set current index + 1 to read string
                nodeIndex = index + 1;
                // ie. get prepared to read characters for setting content

            } else if(spec[index] == ')' && spec[index-1] == ')') {
                currNode = currNode.getParent();

            } else if(spec[index] == ')'){
                // set stack top node content to substring(start,end)
                String tmpContent = strSpec.substring(nodeIndex,index);
                System.out.printf("%s\n",tmpContent);
                currNode.setContent(tmpContent);
                currNode = currNode.getParent();
                nodeIndex = index+1;

            } else {
                //nodeIndex = index+1;
            }

            index++;
		}
		return root;
	}

	private String cleanTreeSpec(String s){
	    String tmpS = s.replace("( ","(").replace(") ",")").replace(" (","(").replace(" )",")");
	    if(tmpS.equals(s))
	        return tmpS;
	    else
	        return cleanTreeSpec(tmpS);

    }

	private boolean isEmptyRange(char[] w, int s, int e){
	    boolean isEmpty = false;
	    for(char c : w){
	        if(c != ' ')
	            isEmpty = true;
        }
        return isEmpty;
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

    public void setRoot(Node root) {
        this.root = root;
    }

	public void forEach(Consumer<? super E> action) {
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
		    System.out.printf("Current node: (%s)\n",(T)n.getContent());
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

        //visit nodes?

    }
}
