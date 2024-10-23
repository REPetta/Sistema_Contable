//Clase encargada del comportamiento de la vista mostrar cuentas//
package Controller;

import ConnectionsBD.ChartAccountsConnection;
import Controller.DetailsAccountController;
import Controller.EditAccountController;
import Model.Account;
import Model.AccountNode;
import Model.User;
import View.DetailsAccount;
import View.ShowChartAccounts;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ShowAccountsController implements ActionListener{
    //Atributos//
    private ShowChartAccounts showAccountsView= new ShowChartAccounts();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    private AddAccountController addAccountView;
    private DeleteAccountController delAccountView;
    private EditAccountController editAccountView;
    private DetailsAccountController detailsAccountView;
    private ChartAccountsConnection chartAccounts=new ChartAccountsConnection();
    private ChartAccountsController chartAccountsController =new ChartAccountsController();
   

    //Constructor//
    public ShowAccountsController() throws SQLException, ClassNotFoundException, IOException{
          this.showAccountsView.setTitle("Mostrar Cuentas"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
          this.showAccountsView.btnAddAccount.addActionListener(this);
          this.showAccountsView.btnDelAccount.addActionListener(this);
          this.showAccountsView.btnEditAccount.addActionListener(this);
          this.showAccountsView.btnExit.addActionListener(this);
          this.showAccountsView.btnDetalis.addActionListener(this);
          chartAccountsController=chartAccounts.createChartAccounts();
          if(currentUser.getTasks().contains("agregar_usuario")==false){
            this.showAccountsView.btnAddAccount.setVisible(false);
            this.showAccountsView.btnDelAccount.setVisible(false);
            this.showAccountsView.btnEditAccount.setVisible(false);
          }
           poblarJTree();
    }
    
    //Metodos//
    private void poblarJTree(){
         // Obtener la raíz del Plan de Cuentas desde el controlador
        AccountNode root = chartAccountsController.getRoot();
        
        // Crear la raíz visual del JTree
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root.getAccount().getAccountName());
        agregarNodos(rootNode, root);
        
        // Crear el modelo del árbol y asignarlo al JTree en la vista
        DefaultTreeModel model = new DefaultTreeModel(rootNode);
        showAccountsView.treeAccounts.setModel(model);
        
        // Configurar el listener de selección de nodos en el JTree
        showAccountsView.treeAccounts.addTreeSelectionListener(e -> manejarSeleccionNodo());
    
    }
    // Método recursivo para agregar nodos al JTree //
    private void agregarNodos(DefaultMutableTreeNode nodoPadre, AccountNode nodoActual) {
        if (nodoActual == null) return;

        // Crear un nuevo nodo para el JTree usando el nombre de la cuenta
        DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(nodoActual);
        nodoPadre.add(nuevoNodo);

        // Recorrer las subcuentas (hijos) y añadirlas recursivamente
        for (AccountNode subCuenta : nodoActual.getSubAccounts()) {
            agregarNodos(nuevoNodo, subCuenta);
        }
    }
    // Método para manejar la selección de un nodo en el JTree //
     private void manejarSeleccionNodo() {
         // Obtener el nodo seleccionado
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) showAccountsView.getTreeAccounts().getLastSelectedPathComponent();

        if (selectedNode != null && selectedNode.getUserObject() instanceof AccountNode) {
            AccountNode nodoSeleccionado = (AccountNode) selectedNode.getUserObject();
        
        // Aquí puedes realizar operaciones sobre el nodo seleccionado (mostrar detalles, editar, eliminar)
        // Ejemplo: mostrarDetallesCuenta(nodoSeleccionado);
    }
        }
    
    // Método recursivo para buscar un nodo en el árbol por el nombre de la cuenta //
    private AccountNode buscarNodoPorNombre(AccountNode nodoActual, String nombreCuenta) {
        if (nodoActual.getAccount().getAccountName().equals(nombreCuenta)) {
            return nodoActual;
        }
       for (AccountNode subCuenta : nodoActual.getSubAccounts()) {
            AccountNode resultado = buscarNodoPorNombre(subCuenta, nombreCuenta);
            if (resultado != null) {
                return resultado;
            }
        }
        return null;
    }
      // Método auxiliar para obtener el nodo seleccionado en el JTree //
    private AccountNode obtenerNodoSeleccionado() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) showAccountsView.treeAccounts.getLastSelectedPathComponent();
        if (selectedNode != null) {
            String nombreCuentaSeleccionada = selectedNode.toString();
            return buscarNodoPorNombre(chartAccountsController.getRoot(), nombreCuentaSeleccionada);
        }
        return null;
    }
    public void openShowAccountsView(){//Metodo para hacer visible la ventana del MainMenu//
        showAccountsView.setVisible(true);
    }
    public void closeShowAccountsView(){//Metodo para cerrar el menu principal
        showAccountsView.dispose();
    }
    
    public void buttonAddAccount(ActionEvent e){//Metodo que le da al boton agregar cuenta funcionalidad//
        if(e.getSource()==showAccountsView.btnAddAccount){
            closeShowAccountsView();
            addAccountView=new AddAccountController();
            addAccountView.openAddAccountView();
        }
    }
    public void buttonDeleteAccount(ActionEvent e){//Metodo que le da al boton eliminar cuenta funcionalidad//
        if (e.getSource() == showAccountsView.btnDelAccount) {
        // Obtener la cuenta seleccionada en el JTree
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) showAccountsView.treeAccounts.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getUserObject() instanceof AccountNode) {
            AccountNode selectedAccountNode = (AccountNode) selectedNode.getUserObject();
            Account selectedAccount = selectedAccountNode.getAccount(); // Obtiene la cuenta

            if (selectedAccount != null) {
                // Abrir la vista de eliminación con la cuenta seleccionada
                closeShowAccountsView();
                delAccountView = new DeleteAccountController(selectedAccount);
                delAccountView.openDeleteAccountView();
            } else {
                JOptionPane.showMessageDialog(null, "La cuenta seleccionada es nula.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una cuenta válida del árbol.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    }
    // Método auxiliar para encontrar el nodo AccountNode por nombre de cuenta
private AccountNode findAccountNodeByName(AccountNode currentNode, String accountName) {
    if (currentNode.getAccount().getAccountName().equals(accountName)) {
        return currentNode;
    }
    for (AccountNode child : currentNode.getSubAccounts()) {
        AccountNode found = findAccountNodeByName(child, accountName);
        if (found != null) {
            return found;
        }
    }
    return null;
}
    
    public void buttonEditAccount(ActionEvent e){ //Metodo que le da al boton modificar cuenta la funcionalidad//
         if (e.getSource() == showAccountsView.btnEditAccount) {
        // Obtener la cuenta seleccionada en el JTree
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) showAccountsView.treeAccounts.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getUserObject() instanceof AccountNode) {
            AccountNode selectedAccountNode = (AccountNode) selectedNode.getUserObject();
            Account selectedAccount = selectedAccountNode.getAccount(); // Obtiene la cuenta

            if (selectedAccount != null) {
                // Abrir la vista de eliminación con la cuenta seleccionada
                showAccountsView.dispose();
                editAccountView = new EditAccountController(selectedAccount);
                editAccountView.openEditAccountView();
            } else {
                JOptionPane.showMessageDialog(null, "La cuenta seleccionada es nula.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una cuenta válida del árbol.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
        }
    
    public void  buttonDetailsAccount(ActionEvent e){
          if (e.getSource() == showAccountsView.btnDetalis) {
            // Obtener la cuenta seleccionada en el JTree
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) showAccountsView.treeAccounts.getLastSelectedPathComponent();
           if (selectedNode != null && selectedNode.getUserObject() instanceof AccountNode) {
                AccountNode selectedAccountNode = (AccountNode) selectedNode.getUserObject();
                Account selectedAccount = selectedAccountNode.getAccount(); // Obtiene la cuenta

            if (selectedAccount != null) {
                // Abrir la vista de eliminación con la cuenta seleccionada
                detailsAccountView = new DetailsAccountController(selectedAccount);
                detailsAccountView.openDetailsAccountView();
            } else {
                JOptionPane.showMessageDialog(null, "La cuenta seleccionada es nula.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una cuenta válida del árbol.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    }
    public void buttonExit(ActionEvent e){//Metodo que le da al boton Salir la funcion de cerrar el Menu Principal y volver al Login//
        if(e.getSource()==showAccountsView.btnExit){
            closeShowAccountsView();
            mainMenuController=new MainMenuController();
            mainMenuController.openMainMenuView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        buttonAddAccount(e);
        buttonDeleteAccount(e);
        buttonEditAccount(e);
        buttonDetailsAccount(e);
        
    }
}
