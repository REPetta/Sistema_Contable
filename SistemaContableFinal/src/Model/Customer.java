
package Model;

/**
 *
 * @author Rodrigo
 */
public class Customer {
    
    private int idClient;
    private String clientName;
    private String clientSurname;
    private String socialReason;
    private int dni;
    private String ivaCondition;
    private String clientType;
    private String email;

    public Customer() {
    }

    public Customer(int idClient, String clientName, String clientSurname, String socialReason, int dni, String ivaCondition, String clientType, String email) {
        this.idClient = idClient;
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.socialReason = socialReason;
        this.dni = dni;
        this.ivaCondition = ivaCondition;
        this.clientType = clientType;
        this.email = email;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getIvaCondition() {
        return ivaCondition;
    }

    public void setIvaCondition(String ivaCondition) {
        this.ivaCondition = ivaCondition;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
