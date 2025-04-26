public class TreeNode {
    public TreeNode EMPTY_TREE = null;
    public TreeNode NO_PARENT = null;

    private String data;
    private TreeNode leftChild, rightChild, parent;

    public TreeNode(String data) {
        this.data = data;

        parent = NO_PARENT;

        leftChild = EMPTY_TREE;
        rightChild = EMPTY_TREE;
    }

    public TreeNode(String data, TreeNode parent) {
        this.data = data;

        this.parent = parent;

        leftChild = EMPTY_TREE;
        rightChild = EMPTY_TREE;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setParent(TreeNode newP) {
        parent = newP;
    }

    public void setLeftChild(TreeNode newLC) {
        leftChild = newLC;

        if (newLC != EMPTY_TREE) {
            newLC.parent = this;
        }
    }

    public void setRightChild(TreeNode newRC) {
        rightChild = newRC;

        if (newRC != EMPTY_TREE) {
            newRC.parent = this;
        }
    }

    public String getData() {
        return data;
    }

    public TreeNode getParent() {
        return parent;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void print() {
        // print meaningful message if a variable is "null"

        String parentnode = (parent == NO_PARENT ? "NO_PARENT" : parent.getData());

        String leftchild = (leftChild == EMPTY_TREE ? "EMPTY_TREE" : leftChild.getData());

        String rightchild = (rightChild == EMPTY_TREE ? "EMPTY_TREE" : rightChild.getData());

        System.out.println(
                "TreeNode[ data = {0, -5} parent --> {1, -11} leftChild --> {2, -12} rightChild --> {3, -12}]" +
                        data + "," + parentnode + "," + leftchild + "," + rightchild);

    }
}
