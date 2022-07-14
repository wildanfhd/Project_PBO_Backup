package com.pbo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

// Controller untuk switch scene tiap menu
public class MenuController{

    public PasswordField teksPassword;
    public TextField teksUsername, teksUang;
    public Label labelItem, labelHargaItem;

    @FXML private Pane scenePane;
    private Stage stage;
    private Scene scene;
    private Parent root;

    Koneksi koneksiVM = new Koneksi();


    // Method untuk scene main menu ke menu minuman
    public void switchToMinuman(ActionEvent actionEvent) throws Exception{
        this.root = FXMLLoader.load(getClass().getResource("menuminuman.fxml"));
        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    // Method untuk scene menu minuman ke main menu
    public void switchToMenu(ActionEvent actionEvent) throws Exception{
        this.root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    // Method untuk scene login admin ke dalam input minuman
    public void switchToDB(ActionEvent actionEvent) throws Exception{
            String username = teksUsername.getText();
            String pass = teksPassword.getText();

            try{
                String query = "SELECT * FROM admin WHERE username='" + username + "' AND password='" + pass +"'";
                ResultSet hasilSet = koneksiVM.getData(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);

                if(hasilSet.next()){
                    String adminVM = hasilSet.getString("username");
                    String passVM = hasilSet.getString("password");

                    if(username.equals(adminVM) && pass.equals(passVM)){

                        alert.setContentText("Anda berhasil login");
                        alert.show();

                        this.root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admininput.fxml")));
                        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        this.scene = new Scene(this.root);
                        this.stage.setScene(this.scene);
                        this.stage.show();
                    } else if(!username.equals(adminVM) && !pass.equals(passVM)){
                        System.out.println("Cek");
                        teksUsername.setText("");
                        teksPassword.setText("");
                        alert1.setTitle("Username atau password salah");
                        alert1.setContentText("Username atau password salah");
                        alert1.show();
                    } else {
                        System.out.println("Cek");
                        teksUsername.setText("");
                        teksPassword.setText("");
                        alert1.setTitle("Verifikasi");
                        alert1.setContentText("Harap masukkan username dan password anda");
                        alert1.show();
                    }
                }
            } catch(Exception e){
                System.out.println(e);
            }
    }

    // Method untuk scene input minuman ke login admin
    public void switchToLogin(ActionEvent actionEvent) throws Exception{
        this.root = FXMLLoader.load(getClass().getResource("loginadmin.fxml"));
        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    // Method untuk button How To
    public void handleHowToButton(ActionEvent actionEvent) throws Exception{
        this.root = FXMLLoader.load(getClass().getResource("howto.fxml"));
        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    // Method untuk Button Exit pada menu utama
    public void handleExitButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("");
        alert.setContentText("Apakah anda yakin ingin keluar?");

        if(alert.showAndWait().get() == ButtonType.OK){
            this.stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("anda berhasil keluar");
            this.stage.close();
        }
    }

}
