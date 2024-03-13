package main;

import javax.swing.JFrame;

public class Main {

    static JFrame window;

    public static void main(String[] args){

        // Erstellen des Spielfensters
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Avalken");

        // GamePanel wird dem Fenster hinzugefügt
        GamePanel gp = new GamePanel();
        window.add(gp);

        // Spieleinstellungen werden geladen und das Fenster (falls aktiviert) auf Fullscreen gesetzt
        gp.config.loadConfig();
        if(gp.fullScreenOn) {
            window.setUndecorated(true);
        }

        // Fenster Größe wird auf Größe des GamePanels gesetzt
        window.pack();

        // Fenster wird sichtbar gemacht und erscheint in der Bildschirmmitte
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // GamePanel führt das Setup durch und startet den Thread
        gp.setupGame();
        gp.startGameThread();
    }
}
