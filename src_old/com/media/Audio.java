package com.media;

import javazoom.jl.player.Player;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;


/**
 * Class ini digunakan untuk memutar audio mp3 dan wav. 
 * 
 * @author Achmad Baihaqi
 * @since 2020-11-22
 */
public class Audio {
    
    /**
     * attribute yang digunakan untuk memutar dan menonaktifkan audio/suara
     */
    private static Player players;
    /**
     * nama file dari audio yang akan diputar
     */
    public static final String  SOUND_ERROR = "sound-error.wav", SOUND_WARNING = "sound-warning.wav", SOUND_INFO = "sound-info.wav";
    /**
     * directory penyimpanan file audio
     */
    private static final String directory = "src\\resources\\audio\\";
        
    /**
     * Method ini digunakan untuk memutar audio. Method ini akan memutar audio dengan menggunakan object <code>Players</code>.
     * Sebelum memutar audio method akan mengecek apakah ada audio yang sedang diputar atau tidak. Jika audio sedang diputar maka audio
     * sebelumnya akan di stop. Ini penting dilakukan supaya tidak terjadi error saat beberapa audio dijalankan dalam waktu yang bersamaan. 
     * Kemudian method akan mengambil input audio dari user melalui object <code>FileInputStream</code>. Jika tidak terjadi error saat 
     * pengambilan input audio maka audio akan diputar.
     * 
     * @param filename file audio yang akan diputar
     */
    public static void play(final String filename){
        System.out.println("TES FILE AUDIO");
        // mengecek apakah audio sedang digunakan atau tidak, jika sedang digunakan maka audio akan diclose
        System.out.println("PLAYErS : " + players);
        if(players != null){
            System.out.println("PLAYERS TIDAK NULL");
//            stop();
            System.out.println("OLD PLAYERS DI STOP");
        }
        System.out.println("MENGAMBIL AUDIO");
        // mengambil input audio dari user
        try{
            System.out.println("FILE AUDIO : "+new java.io.File(directory+filename).exists());
            FileInputStream input = new FileInputStream(directory+filename);
            System.out.println("PERSIAPAN EKSEKUSI AUDIO PLAYER");
//            System.out.println(input.);
            players = new Player(input);
            System.out.println("AUDIO PLAYER SUDAH DIEKSEKUSI");
        }catch(IOException | JavaLayerException iex){
            iex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengambil file audio \nError : " + iex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        System.out.println("PERSIAPAN MEMUTAR AUDIO");
        // memutar audio
        new Thread(new Runnable(){
            
            @Override
            public void run(){
                try{
                    System.out.println("PERSIAPAN MEMUTAR AUDIO ke 2");
                    System.out.println("PLAYErs : " + players.toString());
                    players.play();
                    System.out.println("SETELAH AUDIO DIPUTAR");
                }catch(JavaLayerException jlex){
                    jlex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat akan meutar audio! \nError : " + jlex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }).start();

    }

    /**
     * Method ini digunakan untuk menghentikan/menclose file audio yang sedang diputar.
     * Sebelum audio dihentikan method akan mengecek apakah object pada <code>Players</code> kosong atau tidak. 
     * Jika object <code>Players</code> tidak kosong maka audio akan dihentikan. 
     */
    public static void stop(){
        try{
            // mengecek apakah objek players kosong atau tidak
            if(players != null){
                // menghentikan audio
//                players.close();
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        Audio.play("lemon.mp3");
        
    }
    
}
