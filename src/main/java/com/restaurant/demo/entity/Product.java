package com.restaurant.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "produse")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produs")
    private int idProdus;

    @Column(name = "nume_produs")
    private String numeProdus;
    @Column(name = "pret_produs")
    private Integer pretProdus;

    public Product() {
    }

    public Product(String numeProdus, Integer pretProdus) {
        this.numeProdus = numeProdus;
        this.pretProdus = pretProdus;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public Integer getPretProdus() {
        return pretProdus;
    }

    public void setPretProdus(Integer pretProdus) {
        this.pretProdus = pretProdus;
    }

    @Override
    public String toString() {
        return "produs{" +
                "idProdus=" + idProdus +
                ", numeProdus='" + numeProdus + '\'' +
                ", pretProdus=" + pretProdus +
                '}';
    }
}
