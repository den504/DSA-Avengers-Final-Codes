import java.util.ArrayList;
import java.util.List;

public class AVLTree {
    public TreeNode EMPTY_TREE = null;
    public TreeNode NO_PARENT = null;
    public TreeNode NODE_NOT_FOUND = null;
    protected TreeNode root = EMPTY_TREE;
    protected int nodeCounter = 0;

    // AVLTree( ) - a constructor that creates an empty AVL tree, that stores
    // postcodes (strings).
    public AVLTree() {
    }

    // int Count( ) - a method to return the number of postcodes in the AVL tree,
    // if the AVL tree is empty, 0 is returned, otherwise the number of postcodes is
    // returned.
    public int Count() {
        return nodeCounter;
    }

    // Helper method to get the height of a node
    private int height(TreeNode node) {
        if (node == EMPTY_TREE) {
            return 0;
        }
        return Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1;
    }

    // Helper method to get the balance factor of a node
    private int getBalance(TreeNode node) {
        if (node == EMPTY_TREE) {
            return 0;
        }
        return height(node.getLeftChild()) - height(node.getRightChild());
    }

    // Right rotation helper method
    private TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.getLeftChild();
        TreeNode T2 = x.getRightChild();

        // Perform rotation
        x.setRightChild(y);
        y.setLeftChild(T2);

        // Update parent pointers
        x.setParent(y.getParent());
        y.setParent(x);
        if (T2 != EMPTY_TREE) {
            T2.setParent(y);
        }

        // Return new root
        return x;
    }

    // Left rotation helper method
    private TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.getRightChild();
        TreeNode T2 = y.getLeftChild();

        // Perform rotation
        y.setLeftChild(x);
        x.setRightChild(T2);

        // Update parent pointers
        y.setParent(x.getParent());
        x.setParent(y);
        if (T2 != EMPTY_TREE) {
            T2.setParent(x);
        }

        // Return new root
        return y;
    }

    // bool Search( string ) - a method to search for the postcode in the AVL tree,
    // if the postcode is in the AVL tree it returns true, if not it returns false.
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

    // void Insert( string ) - a method to insert the postcode into the AVL tree,
    // if the postcode is already in the AVL tree, it is not added.
    public void Insert(String newValue) {
        // Check if value already exists
        if (Search(newValue)) {
            return; // Skip duplicate insertion
        }

        // Insert new value and rebalance
        root = insertRecursive(root, newValue, NO_PARENT);
        nodeCounter++;
    }

    // Recursive helper method for insertion
    private TreeNode insertRecursive(TreeNode node, String value, TreeNode parent) {
        // 1. Perform standard BST insertion
        if (node == EMPTY_TREE) {
            return new TreeNode(value, parent);
        }

        if (value.compareTo(node.getData()) < 0) {
            node.setLeftChild(insertRecursive(node.getLeftChild(), value, node));
        } else if (value.compareTo(node.getData()) > 0) {
            node.setRightChild(insertRecursive(node.getRightChild(), value, node));
        } else {
            // Duplicate value, return unchanged
            return node;
        }

        // 2. Get the balance factor of this node to check if it became unbalanced
        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && value.compareTo(node.getLeftChild().getData()) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && value.compareTo(node.getRightChild().getData()) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && value.compareTo(node.getLeftChild().getData()) > 0) {
            node.setLeftChild(leftRotate(node.getLeftChild()));
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && value.compareTo(node.getRightChild().getData()) < 0) {
            node.setRightChild(rightRotate(node.getRightChild()));
            return leftRotate(node);
        }

        // Return the unchanged node
        return node;
    }

    // bool Delete( string ) - a method to delete the postcode from the AVL tree, if
    // the postcode is in the AVL tree it is deleted & true is returned, if the
    // postcode is not in the AVL tree false is returned.
    public boolean Delete(String value) {
        // Check if the value exists
        if (!Search(value)) {
            return false;
        }

        // Delete the value and rebalance
        root = deleteRecursive(root, value);
        nodeCounter--;
        return true;
    }

    // Recursive helper method for deletion
    private TreeNode deleteRecursive(TreeNode root, String value) {
        // Standard BST delete
        if (root == EMPTY_TREE) {
            return root;
        }

        // Find the node to be deleted
        if (value.compareTo(root.getData()) < 0) {
            root.setLeftChild(deleteRecursive(root.getLeftChild(), value));
        } else if (value.compareTo(root.getData()) > 0) {
            root.setRightChild(deleteRecursive(root.getRightChild(), value));
        } else {
            // Node with one child or no child
            if (root.getLeftChild() == EMPTY_TREE || root.getRightChild() == EMPTY_TREE) {
                TreeNode temp = (root.getLeftChild() != EMPTY_TREE) ? root.getLeftChild() : root.getRightChild();

                // No child case
                if (temp == EMPTY_TREE) {
                    temp = root;
                    root = EMPTY_TREE;
                } else {
                    // One child case
                    temp.setParent(root.getParent());
                    root = temp; // Copy the contents of the non-empty child
                }
            } else {
                // Node with two children: Get the inorder predecessor (largest in the left
                // subtree)
                TreeNode temp = findMaxValueNode(root.getLeftChild());

                // Copy the inorder predecessor's data to this node
                root.setData(temp.getData());

                // Delete the inorder predecessor
                root.setLeftChild(deleteRecursive(root.getLeftChild(), temp.getData()));
            }
        }

        // If the tree had only one node, return
        if (root == EMPTY_TREE) {
            return root;
        }

        // Get the balance factor
        int balance = getBalance(root);

        // Left Left Case
        if (balance > 1 && getBalance(root.getLeftChild()) >= 0) {
            return rightRotate(root);
        }

        // Left Right Case
        if (balance > 1 && getBalance(root.getLeftChild()) < 0) {
            root.setLeftChild(leftRotate(root.getLeftChild()));
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.getRightChild()) <= 0) {
            return leftRotate(root);
        }

        // Right Left Case
        if (balance < -1 && getBalance(root.getRightChild()) > 0) {
            root.setRightChild(rightRotate(root.getRightChild()));
            return leftRotate(root);
        }

        return root;
    }

    // Helper method to find the node with maximum value
    private TreeNode findMaxValueNode(TreeNode node) {
        TreeNode current = node;

        // Find the rightmost leaf
        while (current.getRightChild() != EMPTY_TREE) {
            current = current.getRightChild();
        }

        return current;
    }

    // Helper method to find the node with minimum value
    private TreeNode findMinValueNode(TreeNode node) {
        TreeNode current = node;

        // Find the leftmost leaf
        while (current.getLeftChild() != EMPTY_TREE) {
            current = current.getLeftChild();
        }

        return current;
    }

    // string[] InOrder( ) - returns an array containing all the postcodes in the
    // AVL tree
    // in ascending alphabetical order. This should use In-Order tree traversal and
    // not alter the AVL tree.
    public String[] InOrder() {
        List<String> result = new ArrayList<>();
        inOrderRecord(root, result);
        return result.toArray(new String[result.size()]);
    }

    // Helper method for inorder traversal
    private void inOrderRecord(TreeNode node, List<String> result) {
        if (node != EMPTY_TREE) {
            inOrderRecord(node.getLeftChild(), result);
            result.add(node.getData());
            inOrderRecord(node.getRightChild(), result);
        }
    }
}