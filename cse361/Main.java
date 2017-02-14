package cse361;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        //-----Test Cases ---
        Vector<String>  treeCollection = new Vector<String>();
        treeCollection.addElement( "(A (B (C ) (D) ) (E) (F))");
        treeCollection.addElement( "(Any Thing Goes (Big Child (Level 21 (Level 211) (Level 212)) (Level 22)(Level 23 (Level 234 )))(Cxihild Node)( Dooble Words))");
        treeCollection.addElement("(Hi)(This)(Is)(Not)(A(Tree))");

        // Running Tests
        Iterator<String>   tcIter = treeCollection.iterator();
        while (tcIter.hasNext()){
            System.out.println ("!!!!!! ----- Starting New Tree Build and Traveral ----- !!!!!!");
            String treeSpec = tcIter.next();
            System.out.println("Tree Specification String = " + treeSpec);

            // Build Tree
            Tree<String> tree = new Tree<String> (treeSpec);
            System.out.println("Pre-order Traversal");

            // Iterate through Nodes -- PreOrder
            Iterator<String> itr = tree.iterator();
            while (itr.hasNext())
                System.out.println (itr.next());
        }
    }
}