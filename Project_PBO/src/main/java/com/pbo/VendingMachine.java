package com.pbo;

import javafx.beans.property.*;

public class VendingMachine {
    private StringProperty namaMinuman;
    private StringProperty idProduct;
    private IntegerProperty qty;
    private FloatProperty hargaMinuman;

    // Constructor tanpa parameter
    public VendingMachine(){

    }

    public String getNamaMinuman() {
        return namaMinuman.get();
    }

    public StringProperty namaMinumanProperty() {
        return namaMinuman;
    }

    public void setNamaMinuman(String namaMinuman) {
        this.namaMinuman.set(namaMinuman);
    }

    public String getIdProduct() {
        return idProduct.get();
    }

    public StringProperty idProductProperty() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct.set(idProduct);
    }

    public int getQty() {
        return qty.get();
    }

    public IntegerProperty qtyProperty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty.set(qty);
    }

    public float getHargaMinuman() {
        return hargaMinuman.get();
    }

    public FloatProperty hargaMinumanProperty() {
        return hargaMinuman;
    }

    public void setHargaMinuman(float hargaMinuman) {
        this.hargaMinuman.set(hargaMinuman);
    }


    // Constructor tabelMinuman di InputController
    public VendingMachine( String idProduct, String namaMinuman, int qty, float hargaMinuman){
        this.idProduct = new SimpleStringProperty(idProduct);
        this.namaMinuman = new SimpleStringProperty(namaMinuman);
        this.qty = new SimpleIntegerProperty(qty);
        this.hargaMinuman = new SimpleFloatProperty(hargaMinuman);
    }

    // Constructor tbMinumanUser di UserMenuController
    public VendingMachine(String idProduct, String namaMinuman, float hargaMinuman){
        this.idProduct = new SimpleStringProperty(idProduct);
        this.namaMinuman = new SimpleStringProperty(namaMinuman);
        this.hargaMinuman = new SimpleFloatProperty(hargaMinuman);
    }
}
