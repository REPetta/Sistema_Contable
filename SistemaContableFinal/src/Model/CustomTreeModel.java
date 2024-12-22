//Clase creada para sobreescribir un metodo de la creacion del arbol//
package Model;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class CustomTreeModel extends DefaultTreeModel {
    public CustomTreeModel(TreeNode root) {
        super(root);
    }

    @Override
    public boolean isLeaf(Object node) {
        // Verificar si el nodo es un AccountNode//
        if (node instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
            Object userObject = treeNode.getUserObject();

            // Si el objeto asociado es un AccountNode, usar recibeSaldo//
            if (userObject instanceof AccountNode) {
                AccountNode accountNode = (AccountNode) userObject;
                return accountNode.getAccount().getReceiveBalance()== 1;
            }
        }

        // Comportamiento por defecto//
        return super.isLeaf(node);
    }
}
