package com.users;

import com.data.app.Log;
import com.data.db.DatabaseTables;
import com.error.InValidUserDataException;
import com.manage.Text;
import com.manage.Validation;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Achmad Baihaqi
 */
public class Karyawan extends Users{
    
    private final Text text = new Text();
    
    public String createID(){
        return super.createID(UserLevels.KARYAWAN, UserData.ID_KARYAWAN);
    }
    
    public boolean isExistPetugas(String idKaryawan){
        return super.isExistID(idKaryawan, UserLevels.KARYAWAN, UserData.ID_KARYAWAN);
    }
    
    public final boolean addKaryawan(String namaKaryawan, String noTelp, String alamat, String pass, UserLevels level,String username){
        boolean isAdd;
        PreparedStatement pst;
        String idKaryawan = this.createID();
        try {
            // menambahkan data user ke tabel user
            isAdd = super.addUser(username, pass, level,idKaryawan);
            // mengecek apakah username sudah ditambahkan ke tabel user
            if(isAdd){
                // validasi data sebelum ditambahkan
                if(this.validateAddKaryawan(idKaryawan, namaKaryawan, noTelp, alamat)){
                    Log.addLog("Menambahkan data karyawan dengan nama '" + namaKaryawan + "'");
                    // menambahkan data kedalam Database
                    pst = this.conn.prepareStatement("INSERT INTO karyawan VALUES (?, ?, ?, ?)");
                    pst.setString(1, idKaryawan);
                    pst.setString(2, text.toCapitalize(namaKaryawan));
                    pst.setString(3, noTelp);
                    pst.setString(4, text.toCapitalize(alamat));

                    // mengekusi query
                    return pst.executeUpdate() > 0;
                }
            }
        } catch (SQLException | InValidUserDataException ex) {
            this.deleteUser(idKaryawan);
            System.out.println("Error Message : " + ex.getMessage());
        }
        return false;
    }
    
    public boolean validateAddKaryawan(String idKaryawan, String namaKaryawan, String noTelp, String alamat){
        
        boolean vIdPetugas, vNama, vNoTelp, vAlamat;
        
        // mengecek id petugas valid atau tidak
        if(Validation.isIdKaryawan(idKaryawan)){
            vIdPetugas = true;
        }else{
            throw new InValidUserDataException("'" + idKaryawan + "' ID Karyawan tersebut tidak valid.");
        }
        
        // menecek nama valid atau tidak
        if(Validation.isNamaOrang(namaKaryawan)){
            vNama = true;
        }else{
            throw new InValidUserDataException("'" + namaKaryawan + "' Nama Karyawan tersebut tidak valid.");
        }
                
        // mengecek apakah no hp valid atau tidak
        if(Validation.isNoHp(noTelp)){
            vNoTelp = true;
        }else{
            throw new InValidUserDataException("'" + noTelp + "' No Telephone tersebut tidak valid.");
        }
                
        // mengecek apakah alamat valid atau tidak
        if(Validation.isNamaTempat(alamat)){
            vAlamat = true;
        }else{
            throw new InValidUserDataException("'" + alamat + "' Alamat tersebut tidak valid.");
        }
        
        // mengecek apakah password valid atau tidak
//        if(Validation.isPassword(pass)){
//            vPass = true;
//        }else{
//            throw new InValidUserDataException("'" + pass + "' Password tersebut tidak valid.");
//        }
        
        // mengecek apakah level valid atau tidak
//        if(Validation.isLevel(level)){
//            vLevel = true;
//        }else{
//            throw new InValidUserDataException("'" + level + "' Level tersebut tidak valid.");
//        }
                
        return vIdPetugas && vNama && vNoTelp && vAlamat;
    }
    public boolean validateDataKaryawan(String idKaryawan, String namaKaryawan, String noTelp, String alamat, String pass, UserLevels level){
        
        boolean vIdPetugas, vNama, vNoTelp, vAlamat, vPass, vLevel;
        
        // mengecek id petugas valid atau tidak
        if(Validation.isIdKaryawan(idKaryawan)){
            vIdPetugas = true;
        }else{
            throw new InValidUserDataException("'" + idKaryawan + "' ID Karyawan tersebut tidak valid.");
        }
        
        // menecek nama valid atau tidak
        if(Validation.isNamaOrang(namaKaryawan)){
            vNama = true;
        }else{
            throw new InValidUserDataException("'" + namaKaryawan + "' Nama Karyawan tersebut tidak valid.");
        }
                
        // mengecek apakah no hp valid atau tidak
        if(Validation.isNoHp(noTelp)){
            vNoTelp = true;
        }else{
            throw new InValidUserDataException("'" + noTelp + "' No Telephone tersebut tidak valid.");
        }
                
        // mengecek apakah alamat valid atau tidak
        if(Validation.isNamaTempat(alamat)){
            vAlamat = true;
        }else{
            throw new InValidUserDataException("'" + alamat + "' Alamat tersebut tidak valid.");
        }
        
//         mengecek apakah password valid atau tidak
        if(Validation.isPassword(pass)){
            vPass = true;
        }else{
            throw new InValidUserDataException("'" + pass + "' Password tersebut tidak valid.");
        }
        
//         mengecek apakah level valid atau tidak
        if(Validation.isLevel(level)){
            vLevel = true;
        }else{
            throw new InValidUserDataException("'" + level + "' Level tersebut tidak valid.");
        }
                
        return vIdPetugas && vNama && vNoTelp && vAlamat && vPass && vLevel;
    }
    public String getIdKaryawan(String username){
        return super.getData(DatabaseTables.USERS.name(), "id_karyawan", "WHERE username = '" + username+"'");
    }
    public boolean deletePetugas(String idKaryawan){
        return super.deleteUser(idKaryawan);
    }
    
    private String getDataPetugas(String idKaryawan, UserData data){
        return super.getUserData1(idKaryawan, UserLevels.KARYAWAN, data, UserData.ID_KARYAWAN);
    }
    
    public String getNama(String idKaryawan){
        return this.getDataPetugas(idKaryawan, UserData.NAMA_KARYAWAN);
    }
    
    public String getNoTelp(String idKaryawan){
        return this.getDataPetugas(idKaryawan, UserData.NO_TELP);
    }
    
    public String getAlamat(String idKaryawan){
        return this.getDataPetugas(idKaryawan, UserData.ALAMAT);
    }
    
    private boolean setDataPetugas(String idKaryawan, UserData data, String newValue){
        return super.setUserData(idKaryawan, UserLevels.KARYAWAN, data, UserData.ID_KARYAWAN, newValue);
    }
    
    public boolean setNama(String idKaryawan, String newNama){
        return this.setDataPetugas(idKaryawan, UserData.NAMA_KARYAWAN, newNama);
    }
    
    public boolean setNoTelp(String idKaryawan, String newNoTelp){
        return this.setDataPetugas(idKaryawan, UserData.NO_TELP, newNoTelp);
    }
    
    public boolean setAlamat(String idKaryawan, String newAlamat){
        return this.setDataPetugas(idKaryawan, UserData.ALAMAT, newAlamat);
    }
    

    
    public static void main(String[] args) {
        
        Log.createLog();
        Karyawan petugas = new Karyawan();
//        System.out.println(petugas.getNama("PG002"));
//        System.out.println(petugas.getNoTelp("PG002"));
//        System.out.println(petugas.getAlamat("PG002"));
//        System.out.println(petugas.getNoTelp("PG002"));
//        System.out.println("");
//        System.out.println(petugas.deletePetugas("PG005"));
   
        
    }
}
