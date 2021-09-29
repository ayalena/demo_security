package com.example.demo_security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "authorities")
public class Authority implements Serializable {

    //standaard indeling///////////////////////
    @Id
    @Column(nullable = false)
    private String username;

    @Id
    @Column(nullable = false)
    private String authority;
    //gecombineerde ID, daarom implement hij serializable. Afhankelijk van twee kolommen. Normaal gesproken zoals bij user class, is er maar 1 ID.
    //de combinatie van username EN authority moet uniek zijn
    ///////////////////////////////////////////

    //getters en setters toevoegen//////////////
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    ////////////////////////////////////////////
}
