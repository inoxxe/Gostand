package com.example.ino.gostand;


public class Config {
    //Data URL
    public static final String DATA_URL = "http://dinusheroes.xyz/ayam/tampilSemua.php";
    public static final String URL_GET_CART = "http://dinusheroes.xyz/ayam/tampilkeranjang.php";
    public static final String URL_GET_EMP = "http://dinusheroes.xyz/ayam/tampil.php?id=";
    public static final String URL_UPDATE_AYAM= "http://dinusheroes.xyz/ayam/update.php?";
    public static final String URL_PENJUALAN = "http://dinusheroes.xyz/ayam/penjualan.php";
    public static final String TAG_JSON_ARRAY="result";
    private static final String ROOT_URL = "http://dinusheroes.xyz/ayam/Api.php?apicall=";

    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String URL_KERANJANG_AYAM= ROOT_URL + "keranjang";
    public static final String TAG_DESKRIPSI = "desc";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAMABARANG = "nm_brg";
    public static final String KEY_HARGABARANG = "harga";
    public static final String KEY_NAMAGERAI="nm_gerai";
    public static final String KEY_NOTA="no_nota";
    public static final String EMP_ID = "emp_id";


    //JSON TAGS
    public static final String TAG_CART_NAME = "nm_brg";
    public static final String TAG_CART_JUMLAH= "jumlah";
    public static final String TAG_CART_PRICE = "harga";
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "name";
    public static final String TAG_PRICE = "price";
}
