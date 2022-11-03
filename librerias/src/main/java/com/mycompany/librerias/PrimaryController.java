package com.mycompany.librerias;

import CRUD.CRUDLibreria;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {
    
    
    @FXML
    Label error;
    
    @FXML
    TextField name;
 
    @FXML
    PasswordField pass;
    
    
    @FXML
    private void initialize() {
        this.error.setVisible(false);
    }
    
    @FXML
    private void switchToSecondary() throws IOException {
        int rol  = CRUDLibreria.logIn(name.getText(), pass.getText());
        switch (rol){
            case 1:
                App.setRoot("secondary");
            break;
            case 2:
                App.setRoot("admin");
            break;
            default:
                error.setVisible(true);
            break;  
        }
        
    }
}
