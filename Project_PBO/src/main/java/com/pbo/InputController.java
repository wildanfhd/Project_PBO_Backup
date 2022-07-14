package com.pbo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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


// Controller untuk Admin
public class InputController implements Initializable {

    public TextField teksKodeMinuman, teksNamaMinuman, teksQtyMinuman, teksHargaMinuman, teksDelete;
    public Label labelStatus;

    // Untuk Membuat tabel pada menu input minuman
    public ListView<String> listMinuman;
    public TableView<VendingMachine> tabelMinuman;
    public TableColumn<VendingMachine, SimpleStringProperty> columnId;
    public TableColumn<VendingMachine, SimpleStringProperty> columnNama;
    public TableColumn<VendingMachine, SimpleIntegerProperty> columnQty;
    public TableColumn<VendingMachine, SimpleDoubleProperty> columnHarga;

    // Rumus dan koneksi ditaro di controller
    Koneksi koneksiVM = new Koneksi();

    // Method method buat yang dipencet
    public void initialize(URL url, ResourceBundle resourceBundle){
        showVendingList();
        tableViewMinuman();
    }

    // Method untuk button save ketika di click
    public void handleButtonSave() {
        String namaMinuman = teksNamaMinuman.getText();
        String kodeMinuman = teksKodeMinuman.getText();
        String qty = teksQtyMinuman.getText();
        String hargaMinuman = teksHargaMinuman.getText();

        if(!kodeMinuman.isEmpty()){
            String query = "INSERT INTO product (idProduct, namaMinuman, qty, harga)" +
                    "VALUES ('"+ kodeMinuman +"', '"+ namaMinuman +"', '"+ qty +"', '"+ hargaMinuman +"')";
            int hasil = koneksiVM.manipulasiData(query);
            if(hasil == 1){
                System.out.println("Data berhasil diinput");
                labelStatus.setText("Data berhasil diinput");
                showVendingList();
                tableViewMinuman();
            } else {
                System.out.println("Data gagal diinput, silahkan cek kembali");
                labelStatus.setText("Data gagal diinput, silahkan cek kembali");
            }
        } else{
            labelStatus.setText("Harap masukkan inputan, minimal 1 karakter!");
        }
    }

    public void handleButtonUpdate(){
        String namaMinuman = teksNamaMinuman.getText();
        String kodeMinuman = teksKodeMinuman.getText();
        String qty = teksQtyMinuman.getText();
        String hargaMinuman = teksHargaMinuman.getText();

        if(!kodeMinuman.isEmpty()){
            try{

                String query = "UPDATE product SET namaMinuman='"+ namaMinuman +"', qty='"+ qty +"', harga='"+ hargaMinuman +"' WHERE idProduct='"+ kodeMinuman +"'";
                int hasil = koneksiVM.manipulasiData(query);
                if(hasil == 1){
                    System.out.println("Data berhasil diupdate");
                    labelStatus.setText("Data berhasil diupdate");
                    showVendingList();
                    tableViewMinuman();
                } else {
                    System.out.println("Data gagal diupdate, mohon dicek kembali");
                    labelStatus.setText("Data gagal diupdate, mohon dicek kembali");
                }
            } catch(Exception e){
                System.out.println(e);
            }

        }
    }

    // Method untuk button Reset ketika di click
    public void handleButtonReset() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("");
        alert.setContentText("Apakah anda yakin ingin reset?");

        if(alert.showAndWait().get() == ButtonType.OK){
            teksNamaMinuman.setText("");
            teksKodeMinuman.setText("");
            teksQtyMinuman.setText("");
            teksHargaMinuman.setText("");
            labelStatus.setText("");
        }
    }

    // Method untuk button Delete ketika di click
    public void handleButtonDelete() {
        String kodeMinuman = teksDelete.getText();

        try{
            if(!kodeMinuman.isEmpty()){
                String query = "DELETE FROM product WHERE idProduct = '" + kodeMinuman + "';";
                int hasil = koneksiVM.manipulasiData(query);
                if(hasil == 1){
                    System.out.println("Data berhasil dihapus");
                    labelStatus.setText("Data berhasil dihapus");
                    showVendingList();
                    tableViewMinuman();
                }
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }

    //Method untuk ListView
    public void showVendingList(){
        try{
            String query = "SELECT * FROM product";
            ResultSet hasilSet = koneksiVM.getData(query);
            ObservableList<String> minuman = FXCollections.observableArrayList();
            listMinuman.setItems(minuman);
            while(hasilSet.next()){
                minuman.add(hasilSet.getString(2));
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    // Method untuk TableView
    private void tableViewMinuman(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        columnNama.setCellValueFactory(new PropertyValueFactory<>("namaMinuman"));
        columnQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        columnHarga.setCellValueFactory(new PropertyValueFactory<>("hargaMinuman"));

        try{
            String query = "SELECT * FROM product";
            ResultSet hasilSet = koneksiVM.getData(query);
            ObservableList<VendingMachine> vendMac = FXCollections.observableArrayList();
            tabelMinuman.setItems(vendMac);
            while (hasilSet.next()){
                // mengambil data dari result set berdasarkan nama column pada tabel
                String idProduct = hasilSet.getString("idProduct");
                String namaMinuman = hasilSet.getString("namaMinuman");
                int qty = hasilSet.getInt("qty");
                float hargaMinuman = hasilSet.getFloat("harga");
                vendMac.add(new VendingMachine(idProduct, namaMinuman, qty, hargaMinuman));
            }
        } catch(Exception e){
            System.out.println(e);
        }
        // Mengosongkan masing masing text field
        teksKodeMinuman.setText("");
        teksNamaMinuman.setText("");
        teksQtyMinuman.setText("");
        teksHargaMinuman.setText("");

        tabelMinuman.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showContents(newValue));
    }

    private void showContents(VendingMachine vendMac){
        if(vendMac != null){
            teksKodeMinuman.setText(vendMac.getIdProduct());
            teksNamaMinuman.setText(vendMac.getNamaMinuman());
            teksQtyMinuman.setText(String.valueOf(vendMac.getQty())); // String.valueOf berfunsi untuk mengubah ke String
            teksHargaMinuman.setText(String.valueOf(vendMac.getHargaMinuman()));
        } else {
            teksKodeMinuman.setText("");
            teksNamaMinuman.setText("");
            teksQtyMinuman.setText("");
            teksHargaMinuman.setText("");
        }
    }

    public void handleButtonLogOut(ActionEvent actionEvent){
        Stage stage;
        Scene scene;
        Parent root;

        try{
            // Object alert untuk menampilkan alert dialog
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Log Out");
            alert.setHeaderText("");
            alert.setContentText("Apakah anda yakin ingin logout?");

            if(alert.showAndWait().get() == ButtonType.OK){
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Anda berhasil logout");
                alert.show();

                root = FXMLLoader.load(getClass().getResource("loginadmin.fxml"));
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }
}