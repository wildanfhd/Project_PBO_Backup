package com.pbo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class UserMenuController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;


    public Label labelMinuman, labelHarga;
    public TableView<VendingMachine> tbMinumanUser;
    public TableColumn<VendingMachine, SimpleStringProperty> columnId;
    public TableColumn<VendingMachine, SimpleStringProperty> columnNama;
    public TableColumn<VendingMachine, SimpleDoubleProperty> columnHarga;
    public TextField teksDuit;

    Koneksi koneksiVM = new Koneksi();

    public void initialize(URL url, ResourceBundle resourceBundle){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        columnNama.setCellValueFactory(new PropertyValueFactory<>("namaMinuman"));
        columnHarga.setCellValueFactory(new PropertyValueFactory<>("hargaMinuman"));

        // Mengisi table pada saat belum ada minuman yang dipilih
        this.handleTableKosong();
    }

    public void switchToMenu(ActionEvent actionEvent) throws Exception{
        this.root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    private void showContents(VendingMachine vendMac){
        if(vendMac != null){
            vendMac.getIdProduct();
            vendMac.getNamaMinuman();
            // String.valueOf berfunsi untuk mengubah ke String
            String.valueOf(vendMac.getHargaMinuman());
        }
    }

    public void showToTable(String kodeMinuman){
        try{
            String query = "SELECT * FROM product WHERE idProduct='" + kodeMinuman + "'";
            ResultSet hasilSet = koneksiVM.getData(query);
            ObservableList<VendingMachine> vendMac = FXCollections.observableArrayList();
            tbMinumanUser.setItems(vendMac);
            while (hasilSet.next()){
                // mengambil data dari result set berdasarkan nama column pada tabel
                String idProduct = hasilSet.getString("idProduct");
                String namaMinuman = hasilSet.getString("namaMinuman");
                float hargaMinuman = hasilSet.getFloat("harga");
                vendMac.add(new VendingMachine(idProduct, namaMinuman, hargaMinuman));
            }
        } catch(Exception e){
            System.out.println(e);
        }

        tbMinumanUser.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showContents(newValue));
    }

    public void handleTableKosong(){
        labelMinuman.setText("Pilih Minuman");
        this.showToTable("-");
    }

    public void handleBtnAdemSari(){
        labelMinuman.setText("Adem Sari Chingku");
        this.showToTable("ASC");
        setTipe("Kaleng");
    }

    public void handleBtnBear(){
        labelMinuman.setText("Bear Brand");
        this.showToTable("BRB");
        setTipe("Kaleng");
    }

    public void handleBtnMilo(){
        labelMinuman.setText("Milo");
        this.showToTable("SML");
        setTipe("Kaleng");
    }

    public void handleBtnNescafe(){
        labelMinuman.setText("Nescafe Original");
        this.showToTable("NSC");
        setTipe("Kaleng");
    }

    public void handleBtnFloridina(){
        labelMinuman.setText("Floridina");
        this.showToTable("FLO");
        setTipe("Botol");
    }

    public void handleBtnTeh(){
        labelMinuman.setText("Teh Pucuk");
        this.showToTable("THP");
        setTipe("Botol");
    }

    public void handleBtnAqua(){
        labelMinuman.setText("Aqua");
        this.showToTable("AQA");
        setTipe("Botol");
    }

    public void handleBtnFTea(){
        labelMinuman.setText("Fruit Tea");
        this.showToTable("FRT");
        setTipe("Botol");
    }

    // Untuk menentukan tipe minuman
    public void setTipe(String tipe){
        if(tipe.equals("Kaleng")){
            labelHarga.setText("10000");
        } else {
            labelHarga.setText("5000");
        }
    }

    // Untuk menghandle button beli pada menu minuman
    public void handleBtnBeli(ActionEvent actionEvent) {
            try{
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                String harga = labelHarga.getText();
                String uang = teksDuit.getText();

                if(harga.equals(uang)){
                    alert1.setTitle("Konfirmasi");
                    alert1.setContentText("Apa anda yakin ingin membeli?");

                    if(alert1.showAndWait().get() == ButtonType.OK){
                        alert2.setAlertType(Alert.AlertType.INFORMATION);
                        alert2.setContentText("Pembelian berhasil!");
                        this.root = FXMLLoader.load(getClass().getResource("penutup.fxml"));
                        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        this.scene = new Scene(this.root);
                        this.stage.setScene(this.scene);
                        this.stage.show();
                    }
                } else {
                    alert2.setAlertType(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Pembelian gagal");
                    alert2.setContentText("Mohon masukkan nominal yang pas dengan harga minuman!");
                    alert2.show();
                }
                System.out.println("Sukses membeli minuman");
            } catch(Exception e){
                System.out.println(e);
            }
    }
}
