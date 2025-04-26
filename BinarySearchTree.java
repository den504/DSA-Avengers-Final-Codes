import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {
    public TreeNode EMPTY_TREE = null;
    public TreeNode NO_PARENT = null;
    public TreeNode NODE_NOT_FOUND = null;

    protected TreeNode root = EMPTY_TREE;
    protected int nodeCounter = 0;

    // BinarySearchTree( ) - a constructor that creates an empty binary search tree,
    // that stores the postcodes (strings).
    public BinarySearchTree() {

    }

    // int Count( ) - a method to return the number of postcodes in the BST, if the
    // BST is empty 0
    // is returned, otherwise the number of postcodes is returned.
    public int Count() {
        return nodeCounter;
    }

    // bool Search( string ) - a method to search for the postcode in the BST, if
    // the postcode is in
    // the BST it returns true, if not it returns false.
    public boolean Search(String searchValue) {
        TreeNode currentNode = root; // used to traverse tree

        while (currentNode != EMPTY_TREE) {
            if (currentNode.getData().compareTo(searchValue) == 0) {
                return true;
            } else {
                if (currentNode.getData().compareTo(searchValue) < 0) // currentNode.getData() < searchValue
                {
                    currentNode = currentNode.getRightChild();
                } else {
                    currentNode = currentNode.getLeftChild();
                }
            }
        }

        return false; // value not found in tree
    }

    // void Insert( string ) - a method to insert the postcodes into the BST, if the
    // postcode is already
    // in the BST it is not added.
    public void Insert(String newValue) {
        if (root == EMPTY_TREE) // BST is an empty tree
        {
            root = new TreeNode(newValue); // replace root with the new value node, finished
            nodeCounter++; // changed place because originally count is increasing for other nodes(not
                           // first node) even if it's a duplicate value and not added to the tree
        } else {
            Insert(root, newValue);
        }

    }

    // Overloaded method for nodes other than the first node
    public void Insert(TreeNode root, String newValue) {
        if (root.getData().compareTo(newValue) == 0) {
            return; // Duplicate - do nothing
        }

        if (newValue.compareTo(root.getData()) < 0) {
            if (root.getLeftChild() == EMPTY_TREE) {
                TreeNode newNode = new TreeNode(newValue, root);
                root.setLeftChild(newNode);
                nodeCounter++; // Increment ONLY when adding node
            } else {
                Insert(root.getLeftChild(), newValue);
            }
        } else {
            if (root.getRightChild() == EMPTY_TREE) {
                TreeNode newNode = new TreeNode(newValue, root);
                root.setRightChild(newNode);
                nodeCounter++; // Increment ONLY when adding node
            } else {
                Insert(root.getRightChild(), newValue);
            }
        }
    }

    // bool Delete( string ) - a method to delete the postcode from the BST, if the
    // postcode is in
    // the BST it is deleted & true is returned, if the postcode is not in the BST
    // false is returned.
    public boolean Delete(String value) {
        TreeNode currentNode = root; // used to look for the value to delete

        while (currentNode != EMPTY_TREE && currentNode.getData().compareTo(value) != 0) {
            if (currentNode.getData().compareTo(value) < 0) // currentNode.getData() < value
                currentNode = currentNode.getRightChild();
            else
                currentNode = currentNode.getLeftChild();
        }

        if (currentNode != EMPTY_TREE) // value found, remove it
        {
            TreeNode delValueNode = currentNode; // current node is the node to delete

            if (delValueNode.getLeftChild() == EMPTY_TREE) {
                replaceNode(delValueNode, delValueNode.getRightChild());
            } else {
                if (delValueNode.getRightChild() == EMPTY_TREE) {
                    replaceNode(delValueNode, delValueNode.getLeftChild());
                } else {
                    TreeNode maxLeftNode = delValueNode.getLeftChild(); // Search for the maximum value in the
                                                                        // delValueNode's left sub-tree

                    while (maxLeftNode.getRightChild() != EMPTY_TREE) // max value is on the far right of left sub-tree
                    {
                        maxLeftNode = maxLeftNode.getRightChild();
                    }

                    delValueNode.setData(maxLeftNode.getData()); // repalce delte value with the max value

                    replaceNode(maxLeftNode, maxLeftNode.getLeftChild());
                }
            }
            nodeCounter--;
            return true;

        } else {
            // System.out.println("Deletion of node: {0} -- Failed, Not in TREE");
            return false;
        }
    }

    /*
     * Auxiliary function: replaces a node
     * If node is the root, replacement becomes the new root
     * Otherwise, node is the left/right child of some parent, and replacement takes
     * that place
     */
    private void replaceNode(TreeNode node, TreeNode replacement) {
        TreeNode parent = node.getParent();

        if (parent == NO_PARENT) {
            // node is the root
            root = replacement;

            if (root != EMPTY_TREE) {
                root.setParent(NO_PARENT);
            }
        } else {
            if (node == parent.getLeftChild()) {
                parent.setLeftChild(replacement);
            } else {
                parent.setRightChild(replacement);
            }
        }
    }

    // string[] InOrder( ) - returns an array containing all the postcodes in the
    // BST in ascending
    // alphabetical order. This should use In-Order tree traversal and not alter the
    // BST. All the
    // above BST methods, e.g. Insert & Delete, must maintain the BST property
    public String[] InOrder() {
        List<String> result = new ArrayList<>();
        inOrderRecord(root, result);
        return result.toArray(new String[result.size()]);
    }

    private void inOrderRecord(TreeNode node, List<String> result) {
        if (node != null) {
            inOrderRecord(node.getLeftChild(), result);
            result.add(node.getData());
            inOrderRecord(node.getRightChild(), result);
        }
    }

}