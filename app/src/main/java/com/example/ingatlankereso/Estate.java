package com.example.ingatlankereso;

public class Estate {
    private int imageResource;
    private String estateAddress;
    private String estatePrice;
    private String estateSize;
    private String estateRoomNum;
    private String estateDescription;
    private String sellerContact;

    public Estate() {}

    public Estate(String estateAddress, String estatePrice, String estateSize,
                  String estateRoomNum, String estateDescription, String sellerContact,
                  int imageResource) {
        this.estateAddress = estateAddress;
        this.estatePrice = estatePrice;
        this.estateSize = estateSize;
        this.estateRoomNum = estateRoomNum;
        this.estateDescription = estateDescription;
        this.sellerContact = sellerContact;
        this.imageResource = imageResource;
    }

    public String getEstateAddress() {
        return estateAddress;
    }

    public String getEstatePrice() {
        return estatePrice;
    }

    public String getEstateSize() {
        return estateSize;
    }

    public String getEstateRoomNum() {
        return estateRoomNum;
    }

    public String getEstateDescription() {
        return estateDescription;
    }

    public String getSellerContact() {
        return sellerContact;
    }

    public int getImageResource() {
        return imageResource;
    }


}


