//Clase encargada del comportamiento de la vista mostrar cuentas//
package Controller;

import Connection.AccountConnection;
import Model.Account;
import Model.CustomTreeModel;
import Model.AccountNode;
import Model.SingletonUser;
import View.ShowChartAccountsView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ShowAccounts implements ActionListener{
    //Atributos//
    private final ShowChartAccountsView showAccountsView; 
    private MainMenu mainMenu;
    private final SingletonUser currentUser=SingletonUser.getInstance();

    private final AccountConnection con=new AccountConnection();
    private ChartAccounts chartAccounts =new ChartAccounts();
    private AddAccount addAccount;
    private DetailsAccount detailsAccount;
   

    //Constructor//
    public ShowAccounts() throws SQLException, ClassNotFoundException, IOException{
          showAccountsView=new ShowChartAccountsView();
          this.showAccountsView.setTitle("Mostrar Cuentas"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
          chartAccounts=generatedChartAccounts(con.getAccounts());
          initializeListeners();
          displayBasedRol(currentUser);
          populateJTree();
    }
    
    //Metodos//
    //Metodo para inicializar los listeners//
    public final void initializeListeners(){
        this.showAccountsView.btnAddAccount.addActionListener(this);
        this.showAccountsView.btnExit.addActionListener(this);
    }
    //Metodo para desplagar los botones segun el rol//
    public final void displayBasedRol(SingletonUser currentUser){
         if(currentUser.getTasks().contains("agregar_usuario")==false){
            this.showAccountsView.btnAddAccount.setVisible(false);
          }
    }
    //Metodo para hacer visible la ventana de la vista//
    public void openShowAccountsView(){
        showAccountsView.setVisible(true);
    }
    //Metodo para cerrar la ventana de la vista//
    public void closeShowAccountsView(){
        showAccountsView.dispose();
    }
    //Metodo par generar el plan de cuentas  a partir de una lista con las cuenta de la base de datos//
    public final ChartAccounts generatedChartAccounts(List<Account> accounts){
         ChartAccounts chartAccounts = new ChartAccounts();
         for (Account account : accounts) {
             AccountNode auxNode= new AccountNode();
             auxNode.setAccount(account);
             chartAccounts.addAccount(chartAccounts.getRoot(), auxNode);
         }
         return chartAccounts;
    }
    
    //Metodo para poblar el JTree//
    private void populateJTree(){
         // Obtener la raíz del Plan de Cuentas desde el controlador
        AccountNode root = chartAccounts.getRoot();
        
        // Crear la raíz visual del JTree
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root.getAccount().getAccountName());
        agregarNodos(rootNode, root);
        
        // Crear el modelo del árbol y asignarlo al JTree en la vista
        CustomTreeModel model = new CustomTreeModel(rootNode);
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
        if(nodoActual.getAccount().getReceiveBalance()==0){
            for (AccountNode subCuenta : nodoActual.getSubAccounts()) {
                agregarNodos(nuevoNodo, subCuenta);
            }
        }
    }
    // Método para manejar la selección de un nodo en el JTree //
     private void manejarSeleccionNodo() {
         // Obtener el nodo seleccionado
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) showAccountsView.getTreeAccounts().getLastSelectedPathComponent();

        if (selectedNode != null && selectedNode.getUserObject() instanceof AccountNode ) {
            AccountNode nodoSeleccionado = (AccountNode) selectedNode.getUserObject();
        
        // Verificar si el nodo seleccionado es un hijo (no tiene más subcuentas)
        if (nodoSeleccionado.getAccount().getReceiveBalance() == 1) { // 1 indica que recibe saldo (es un hijo)
            try {
                showDetails(nodoSeleccionado);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                    showAccountsView, // Componente padre
                    "Por favor, seleccione una cuenta válida para ver los detalles.", // Mensaje
                     "Cuenta no válida", // Título del cuadro de diálogo
                    JOptionPane.WARNING_MESSAGE // Tipo de mensaje
    );
            }
        }

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
            return buscarNodoPorNombre(chartAccounts.getRoot(), nombreCuentaSeleccionada);
        }
        return null;
    }
    
    //Metodo para conectar el boton agregar cuenta con su funcionalidad//
    public void buttonAddAccount(ActionEvent e){
        if(e.getSource()==showAccountsView.btnAddAccount){
            closeShowAccountsView();
            addAccount=new AddAccount();
            addAccount.openAddAccountView();
        }
    }
    //Metodo para ver los detalles de la cuenta//
    public void showDetails(AccountNode nodoSeleccionado) throws SQLException{
            this.showAccountsView.setVisible(false);
            detailsAccount=new DetailsAccount(this);
            detailsAccount.loadDetails(nodoSeleccionado.getAccount());
            detailsAccount.openDetailsAccountView();
            
    }
    //Metodo que le da al boton Salir la funcion de cerrar el Menu Principal y volver al Login//
    public void buttonExit(ActionEvent e){
        if(e.getSource()==showAccountsView.btnExit){
            closeShowAccountsView();
            mainMenu=new MainMenu();
            mainMenu.openMainMenuView();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        buttonAddAccount(e);

        }
        
    }

