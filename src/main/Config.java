package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    //// SAVE AND LOAD OPTIONS ////
    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // Fullscreen
            if(gp.fullScreenOn) {
                bw.write("On");
            } else {
                bw.write("Off");
            }
            bw.newLine();

            // Lautstärke Musik
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // Lautstärke SFX
            bw.write(String.valueOf(gp.soundEffect.volumeScale));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadConfig(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();

            // Fullscreen
            if(s.equals("On")) {
                gp.fullScreenOn = true;
            } else if(s.equals("Off")) {
                gp.fullScreenOn = false;
            }

            // Lautstärke Musik
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            // Lautstärke SFX
            s = br.readLine();
            gp.soundEffect.volumeScale = Integer.parseInt(s);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
