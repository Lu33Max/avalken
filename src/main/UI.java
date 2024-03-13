package main;

import entity.Entity;
import hud.HUD_Hearts;
import hud.HUD_ItemFrames;
import hud.HUD_LifeText;
import hud.SuperHUD;
import object.OBJ_RupeeGreen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font fontBase;
    BufferedImage heart_full, heart_half, heart_empty, bHeart_half, bHeart_full, lifeText, itemFrame1, itemFrame2, itemFrame3, itemFrame4, itemFrame5, rupee;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public int commandNum = 0;

    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;

    int subState = 0;
    int counter = 0;
    public Entity npc;

    //// KONSTRUKTOR ////
    public UI(GamePanel gp) {
        this.gp = gp;

        // Laden der Font
        InputStream inputStream = getClass().getResourceAsStream("/font/MysteryFont.ttf");
        try {
            fontBase = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream)).deriveFont(Font.PLAIN, (int) (gp.getTILE_SIZE() * 0.75));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // Erzeugen der verschiedenen Hud Elemente
        SuperHUD heart = new HUD_Hearts(gp);
        SuperHUD lt = new HUD_LifeText(gp);
        SuperHUD frame = new HUD_ItemFrames(gp);
        heart_empty = heart.getImage()[0];
        heart_half = heart.getImage()[1];
        heart_full = heart.getImage()[2];
        bHeart_half = heart.getImage()[3];
        bHeart_full = heart.getImage()[4];
        lifeText = lt.getImage()[0];
        itemFrame1 = frame.getImage()[0];
        itemFrame2 = frame.getImage()[1];
        itemFrame3 = frame.getImage()[2];
        itemFrame4 = frame.getImage()[3];
        itemFrame5 = frame.getImage()[4];
        Entity greenRupee = new OBJ_RupeeGreen(gp);
        rupee = greenRupee.getDown1();
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        // Zurücksetzen der Font und Farbe auf den Standard
        g2.setFont(fontBase);
        g2.setColor(Color.white);

        // Je nach aktuellem GameState werden unterschiedliche Elemente gezeichnet
        if(gp.gameState == gp.TITLE_STATE) {
            drawTitleScreen();
        }
        if(gp.gameState == gp.PLAY_STATE) {
            drawPlayerLife();
            drawPlayerItems();
            drawMessage();
        }
        if(gp.gameState == gp.DIALOGUE_STATE) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        if(gp.gameState == gp.CHARACTER_STATE) {
            drawCharacterScreen();
            drawInventoryScreen(gp.player1, true);
        }

        if(gp.gameState == gp.OPTION_STATE) {
            drawOptionScreen();
        }

        if(gp.gameState == gp.GAME_OVER_STATE) {
            drawGameOverScreen();
        }

        if(gp.gameState == gp.TRANSITION_STATE) {
            drawTransition();
        }

        if(gp.gameState == gp.TRADE_STATE) {
            drawTradeScreen();
        }
    }

    //// SIDEBOARD Nachrichten ////
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }
    public void drawMessage(){
        // Position und Font des Sideboards
        int messageX = gp.getTILE_SIZE();
        int messageY = gp.getTILE_SIZE() * 8;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, (float) (gp.getTILE_SIZE() / 2)));

        // Durchgehen der message Arraylist
        for(int i = 0; i < message.size(); i++) {
            // Falls der aktuelle Slot gefüllt ist
            if(message.get(i) != null) {
                // Zeichnen der Nachricht
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                // Der Zähler der Nachricht wird erhöht
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                // Der y-Wert wird erhöht, damit die nächste Nachricht unterhalb gezeichnet wird
                messageY += gp.getTILE_SIZE();

                // Nach 3 Sekunden wird die Nachricht aus deer Liste gelöscht und verschwindet vom Sideboard
                if(messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    //// ITEMS UND LEBEN ////
    public void drawPlayerLife(){

        //// EINZELSPIELER ////
        if(gp.getPlayerCount() == 1) {
            // Der Lebenstext wird links gezeichnet
            int x = 3 * gp.getHUD_SIZE();
            int y = gp.getHUD_SIZE() /2;
            g2.drawImage(lifeText, x, y, null);

            // Die Herzen werden gezeichnet
            x = gp.getTILE_SIZE() / 2;
            y += gp.getHUD_SIZE();
            int i = 0;

            // Zeichnen des maximalen Lebens
            while (i < (gp.getPlayer1().getMaxLife() / 2)) {
                g2.drawImage(heart_empty, x, y, null);
                i++;
                x += gp.getHUD_SIZE();
            }

            x = gp.getTILE_SIZE() / 2;
            i = 0;

            // Zeichnen des aktuellen Lebens
            while (i < gp.getPlayer1().getCurrentLife()) {
                g2.drawImage(heart_half, x, y, null);
                i++;
                if (i < gp.getPlayer1().getCurrentLife()) {
                    g2.drawImage(heart_full, x, y, null);
                    i++;
                }
                x += gp.getHUD_SIZE();
            }

        //// ZWEISPIELER MODUS ////
        } else {
            // Lebenstext
            int x = (int) (gp.getSCREEN_WIDTH() /2 - 2.5 * gp.getHUD_SIZE());
            int y = gp.getHUD_SIZE() /2;

            g2.drawImage(lifeText, x, y, null);

            // P1 leere Herzen
            x = gp.getSCREEN_WIDTH() /2 - 5 * gp.getHUD_SIZE();
            y += gp.getHUD_SIZE();
            int i = 0;

            while(i < (gp.getPlayer1().getMaxLife() / 2)) {
                g2.drawImage(heart_empty, x, y, null);
                i++;
                x += gp.getHUD_SIZE();
            }

            // P1 aktuelle Herzen
            x = gp.getSCREEN_WIDTH() /2 - 5 * gp.getHUD_SIZE();
            i = 0;

            while(i < gp.getPlayer1().getCurrentLife()) {
                g2.drawImage(bHeart_half, x, y, null);
                i++;
                if(i < gp.getPlayer1().getCurrentLife()) {
                    g2.drawImage(bHeart_full, x, y, null);
                    i++;
                }
                x += gp.getHUD_SIZE();
            }

            // P2 leere Herzen
            x = gp.getSCREEN_WIDTH() /2 - 5 * gp.getHUD_SIZE();
            y += gp.getHUD_SIZE();
            i = 0;

            while(i < (gp.getPlayer2().getMaxLife() / 2)) {
                g2.drawImage(heart_empty, x, y, null);
                i++;
                x += gp.getHUD_SIZE();
            }

            // P2 aktuelle Herzen
            x = gp.getSCREEN_WIDTH() /2 - 5 * gp.getHUD_SIZE();
            i = 0;

            while(i < gp.getPlayer2().getCurrentLife()) {
                g2.drawImage(heart_half, x, y, null);
                i++;
                if(i < gp.getPlayer2().getCurrentLife()) {
                    g2.drawImage(heart_full, x, y, null);
                    i++;
                }
                x += gp.getHUD_SIZE();
            }
        }
    }
    public void drawPlayerItems() {
        //// EINZELSPIELER MODUS ////
        if(gp.getPlayerCount() == 1) {
            // Leerer Rahmen 1 wird gezeichnet
            int x = gp.getTILE_SIZE() * 23;
            int y = gp.getHUD_SIZE() /2;

            g2.drawImage(itemFrame1, x, y, null);

            // Item1 wird gezeichnet
            x += gp.getHUD_SIZE() - gp.getSCALE() /2;

            if (gp.getPlayer1().getCurrentItem1() != null) {
                y += gp.getHUD_SIZE() * 0.75;
                g2.drawImage(gp.getPlayer1().getCurrentItem1().getDown2(), x, y, null);
                drawAmount(x, y, gp.getPlayer1(), 1);
            }

            // Leerer Rahmen 2 wird gezeichnet
            x = (int) (gp.getTILE_SIZE() * 27.5);
            y = gp.getHUD_SIZE() /2;

            g2.drawImage(itemFrame2, x, y, null);

            // Item 2 wird gezeichnet
            x += gp.getHUD_SIZE() - gp.getSCALE() /2;

            if (gp.getPlayer1().getCurrentItem2() != null) {
                y += gp.getHUD_SIZE() * 0.75;
                g2.drawImage(gp.getPlayer1().getCurrentItem2().getDown2(), x, y, null);
                drawAmount(x, y, gp.getPlayer1(), 2);
            }

        //// MEHRSPIELER ////
        } else {
            // Spieler 1 Items
            int x = gp.getTILE_SIZE() /2;
            int y = gp.getHUD_SIZE() /2;

            g2.drawImage(itemFrame1, x, y, null);

            x += gp.getHUD_SIZE() - gp.getSCALE() /2;

            if (gp.getPlayer1().getCurrentItem1() != null) {
                y += gp.getHUD_SIZE() * 0.75;
                g2.drawImage(gp.getPlayer1().getCurrentItem1().getDown2(), x, y, null);
                drawAmount(x, y, gp.getPlayer1(), 1);
            }

            x = gp.getTILE_SIZE() * 5;
            y = gp.getHUD_SIZE() /2;

            g2.drawImage(itemFrame3, x, y, null);

            x += gp.getHUD_SIZE() - gp.getSCALE() /2;

            if (gp.getPlayer1().getCurrentItem2() != null) {
                y += gp.getHUD_SIZE() * 0.75;
                g2.drawImage(gp.getPlayer1().getCurrentItem2().getDown2(), x, y, null);
                drawAmount(x, y, gp.getPlayer1(), 2);
            }

            // Spieler 2 Items
            x = gp.getTILE_SIZE() * 23;
            y = gp.getHUD_SIZE() /2;

            g2.drawImage(itemFrame4, x, y, null);

            x += gp.getHUD_SIZE() - gp.getSCALE() /2;

            if (gp.getPlayer2().getCurrentItem1() != null) {
                y += gp.getHUD_SIZE() * 0.75;
                g2.drawImage(gp.getPlayer2().getCurrentItem1().getDown2(), x, y, null);
                drawAmount(x, y, gp.getPlayer2(), 1);
            }

            x = (int) (gp.getTILE_SIZE() * 27.5);
            y = gp.getHUD_SIZE() /2;

            g2.drawImage(itemFrame5, x, y, null);

            x += gp.getHUD_SIZE() - gp.getSCALE() /2;

            if (gp.getPlayer2().getCurrentItem2() != null) {
                y += gp.getHUD_SIZE() * 0.75;
                g2.drawImage(gp.getPlayer2().getCurrentItem2().getDown2(), x, y, null);
                drawAmount(x, y, gp.getPlayer2(), 2);
            }
        }
    }
    public void drawAmount(int x, int y, Entity player, int item){
        // Zeichnet für Bomben und Pfeile die aktuelle Anzahl im Besitz über dem Icon
        if(item == 1) {
            if(player.getCurrentItem1().getName().equals("Bow")) {
                g2.setFont(g2.getFont().deriveFont((float)gp.getTILE_SIZE()));

                String s = "" + player.getCurrentArrow();
                x += gp.getTILE_SIZE() * 1.5;
                y += gp.getTILE_SIZE() * 2;

                //SHADOW
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, x, y);
                //NUMBER
                g2.setColor(Color.WHITE);
                g2.drawString(s, x - gp.getTILE_SIZE() / 10, y - gp.getTILE_SIZE() / 10);
            }
            if(player.getCurrentItem1().getName().equals("Bomb")) {
                g2.setFont(g2.getFont().deriveFont((float)gp.getTILE_SIZE()));

                String s = "" + player.getCurrentBomb();
                x += gp.getTILE_SIZE() * 1.5;
                y += gp.getTILE_SIZE() * 2;

                //SHADOW
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, x, y);
                //NUMBER
                g2.setColor(Color.WHITE);
                g2.drawString(s, x - gp.getTILE_SIZE() / 10, y - gp.getTILE_SIZE() / 10);
            }
        }

        if(item == 2) {
            if (player.getCurrentItem2().getName().equals("Bow")) {
                g2.setFont(g2.getFont().deriveFont((float) gp.getTILE_SIZE()));

                String s = "" + player.getCurrentArrow();
                x += gp.getTILE_SIZE() * 1.5;
                y += gp.getTILE_SIZE() * 2;

                //SHADOW
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, x, y);
                //NUMBER
                g2.setColor(Color.WHITE);
                g2.drawString(s, x - gp.getTILE_SIZE() / 10, y - gp.getTILE_SIZE() / 10);
            }
            if (player.getCurrentItem2().getName().equals("Bomb")) {
                g2.setFont(g2.getFont().deriveFont((float) gp.getTILE_SIZE()));

                String s = "" + player.getCurrentBomb();
                x += gp.getTILE_SIZE() * 1.5;
                y += gp.getTILE_SIZE() * 2;

                //SHADOW
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, x, y);
                //NUMBER
                g2.setColor(Color.WHITE);
                g2.drawString(s, x - gp.getTILE_SIZE() / 10, y - gp.getTILE_SIZE() / 10);
            }
        }
    }

    //// VERSCHIEDENE GAMESTATE OVERLAYS ////
    public void drawTitleScreen() {
        // Der Hintergrund wird Grün gezeichnet
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        // Spieltitel mit Schatten
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, (gp.getTILE_SIZE() * 2)));

        String text = "AVALKEN";
        int x = getCenterTextX(text);
        int y = gp.getTILE_SIZE() * 3;

        g2.setColor(Color.black);
        g2.drawString(text, x + (gp.getTILE_SIZE() / 10), y + (gp.getTILE_SIZE() / 10));
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Spieler Bild
        x = (int) ((gp.getSCREEN_WIDTH() / 2) - (gp.getTILE_SIZE() * 1.5));
        y += gp.getTILE_SIZE() * 2.5;

        g2.drawImage(gp.player1.getDown1(), x, y, gp.getTILE_SIZE() * 3, gp.getTILE_SIZE() * 3, null);

        //// Menü ////
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.getTILE_SIZE()));

        // Zeichnen der Schrift mit Schatten
        text = "EINZELSPIELER";
        x = getCenterTextX(text);
        y += 7 * gp.getTILE_SIZE();
        g2.setColor(Color.black);
        g2.drawString(text, x + (gp.getTILE_SIZE() / 20), y + (gp.getTILE_SIZE() / 20));
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        // Zeichnet ein >, falls dieser Punkt aktuell angewählt ist
        if(commandNum == 0) {
            g2.drawString(">", x - gp.getTILE_SIZE(), y);
        }

        // Analog
        text = "MEHRSPIELER";
        x = getCenterTextX(text);
        y += 1.25 * gp.getTILE_SIZE();
        g2.setColor(Color.black);
        g2.drawString(text, x + (gp.getTILE_SIZE() / 20), y + (gp.getTILE_SIZE() / 20));
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x - gp.getTILE_SIZE(), y);
        }

        // Analog
        text = "SPIEL VERLASSEN";
        x = getCenterTextX(text);
        y += 1.25 * gp.getTILE_SIZE();
        g2.setColor(Color.black);
        g2.drawString(text, x + (gp.getTILE_SIZE() / 20), y + (gp.getTILE_SIZE() / 20));
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString(">", x - gp.getTILE_SIZE(), y);
        }
    }
    public void drawDialogueScreen() {
        // Fenster
        int x = gp.getTILE_SIZE() * 4;
        int y = gp.getTILE_SIZE() / 2;
        int width = gp.SCREEN_WIDTH - (gp.getTILE_SIZE() * 8);
        int height = gp.getTILE_SIZE() * 5;

        drawSubWindow(x, y, width, height);

        // Dialog
        g2.setFont(fontBase);
        g2.setColor(Color.white);

        x += gp.getTILE_SIZE();
        y += gp.getTILE_SIZE() * 1.5;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += fontBase.getSize() * 1.3;
        }
    }
    public void drawCharacterScreen() {
        // Fenster
        int frameX = gp.getTILE_SIZE();
        int frameY = gp.getTILE_SIZE();
        int frameWidth = gp.getTILE_SIZE() * 8;
        int frameHeight = gp.getTILE_SIZE() * 14;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Texteinstellungen
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont((float) (gp.getTILE_SIZE() * 0.75)));

        int textX = (int) (1.5 * frameX);
        int textY = (int) (1.5 * frameY);
        int lineHeight = gp.getTILE_SIZE();

        // Wertenamen
        textY += lineHeight;
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Leben", textX, textY);
        textY += lineHeight;
        g2.drawString("Staerke", textX, textY);
        textY += lineHeight;
        g2.drawString("Geschick", textX, textY);
        textY += lineHeight;
        g2.drawString("Angriff", textX, textY);
        textY += lineHeight;
        g2.drawString("Verteidigung", textX, textY);
        textY += lineHeight;
        g2.drawString("Erfahrung", textX, textY);
        textY += lineHeight;
        g2.drawString("Neues Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Rubine", textX, textY);
        textY += lineHeight;
        g2.drawString("Waffe", textX, textY);
        textY += lineHeight;
        g2.drawString("Schild", textX, textY);

        // Werte
        int tailX = (frameX + frameWidth) - (frameX / 2);
        textY = (int) (1.5 * frameY);
        String value;

        textY += lineHeight;
        value = String.valueOf(gp.player1.getLevel());
        g2.drawString(value, getRightTextX(value, tailX), textY);
        textY += lineHeight;
        value = (gp.player1.getCurrentLife() + "/" + gp.player1.getMaxLife());
        g2.drawString(value, getRightTextX(value, tailX), textY);
        textY += lineHeight;
        value = String.valueOf(gp.player1.getStrength());
        g2.drawString(value, getRightTextX(value, tailX), textY);
        textY += lineHeight;
        value = String.valueOf(gp.player1.getDexterity());
        g2.drawString(value, getRightTextX(value, tailX), textY);
        textY += lineHeight;
        value = String.valueOf(gp.player1.getAttack());
        g2.drawString(value, getRightTextX(value, tailX), textY);
        textY += lineHeight;
        value = String.valueOf(gp.player1.getDefense());
        g2.drawString(value, getRightTextX(value, tailX), textY);
        textY += lineHeight;
        value = String.valueOf(gp.player1.getExp());
        g2.drawString(value, getRightTextX(value, tailX), textY);
        textY += lineHeight;
        value = String.valueOf(gp.player1.getNextLevelExp());
        g2.drawString(value, getRightTextX(value, tailX), textY);
        textY += lineHeight;
        value = String.valueOf(gp.player1.getMoney());
        g2.drawString(value, getRightTextX(value, tailX), textY);

        // Zeichnen von Waffe und Schild
        if(gp.getPlayer1().getCurrentWeapon() != null) {
            textY += (int) (0.25 * gp.getTILE_SIZE());
            g2.drawImage(gp.player1.getCurrentWeapon().getDown1(), tailX - gp.getTILE_SIZE(), textY, null);
        }
        if(gp.getPlayer1().getCurrentShield() != null) {
            textY += lineHeight;
            g2.drawImage(gp.player1.getCurrentShield().getDown1(), tailX - gp.getTILE_SIZE(), textY, null);
        }
    }
    public void drawInventoryScreen(Entity entity, boolean cursor) {
        // Fenster
        int frameX;
        int frameY = gp.getTILE_SIZE();
        int frameWidth = gp.getTILE_SIZE() * 15;
        int frameHeight = gp.getTILE_SIZE() * 9;
        // Position im Inventar
        int slotCol;
        int slotRow;

        if(entity == gp.player1) {
            // Spielerinventar
            frameX = gp.getTILE_SIZE() * 16;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            // Händleriventar
            frameX = gp.getTILE_SIZE();
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXstart = frameX + (gp.getTILE_SIZE() / 2);
        final int slotYstart = frameY + (gp.getTILE_SIZE() / 2);
        int slotX = slotXstart;
        int slotY = slotYstart;

        // Zeichne die Items
        for(int i = 0; i < entity.getInventory().size(); i++) {

            // Hintergrund der ausgerüsteten Items
            if(entity.getInventory().get(i) == entity.getCurrentWeapon() || entity.getInventory().get(i) == entity.getCurrentShield()) {
                g2.setColor(new Color(10, 190, 90));
                g2.fillRect(slotX, slotY, gp.getTILE_SIZE() *2, gp.getTILE_SIZE() *2);
            }
            if(entity.getInventory().get(i) == entity.getCurrentItem1()) {
                g2.setColor(new Color(0, 80, 240));
                g2.fillRect(slotX, slotY, gp.getTILE_SIZE() *2, gp.getTILE_SIZE() *2);
            }
            if(entity.getInventory().get(i) == entity.getCurrentItem2()) {
                g2.setColor(new Color(240, 0, 80));
                g2.fillRect(slotX, slotY, gp.getTILE_SIZE() *2, gp.getTILE_SIZE() *2);
            }

            g2.drawImage(entity.getInventory().get(i).getDown2(), slotX, slotY, null);

            //// Zeichnen der Anzahl ////
            // Stapelbare Items
            if(entity == gp.getPlayer1() && entity.getInventory().get(i).getAmount() > 1) {
                g2.setFont(g2.getFont().deriveFont((float)gp.getTILE_SIZE()));

                int amountX;
                int amountY;

                String s = "" + entity.getInventory().get(i).getAmount();
                amountX = getRightTextX(s, slotX + (gp.getTILE_SIZE() * 2));
                amountY = slotY + gp.getTILE_SIZE() *2;

                //SHADOW
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);
                //NUMBER
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - gp.getTILE_SIZE() / 10, amountY - gp.getTILE_SIZE() / 10);
            }
            // Bogen und Bomben
            else if(entity.getInventory().get(i).getName().equals("Bow")) {
                g2.setFont(g2.getFont().deriveFont((float)gp.getTILE_SIZE()));

                int amountX;
                int amountY;

                String s = "" + entity.getCurrentArrow();
                amountX = getRightTextX(s, slotX + (gp.getTILE_SIZE() * 2));
                amountY = slotY + gp.getTILE_SIZE() *2;

                //SHADOW
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);
                //NUMBER
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - gp.getTILE_SIZE() / 10, amountY - gp.getTILE_SIZE() / 10);

            } else if(entity.getInventory().get(i).getName().equals("Bomb")) {
                g2.setFont(g2.getFont().deriveFont((float)gp.getTILE_SIZE()));

                int amountX;
                int amountY;

                String s = "" + entity.getCurrentBomb();
                amountX = getRightTextX(s, slotX + (gp.getTILE_SIZE() * 2));
                amountY = slotY + gp.getTILE_SIZE() *2;

                //SHADOW
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);
                //NUMBER
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - gp.getTILE_SIZE() / 10, amountY - gp.getTILE_SIZE() / 10);
            }

            slotX += gp.getTILE_SIZE() * 2;

            if(i % 7 == 6) {
                slotY += gp.getTILE_SIZE() * 2;
                slotX = slotXstart;
            }
        }

        if(cursor) {
            // Cursorposition und -größe
            int cursorX = slotXstart + (gp.getTILE_SIZE() * 2 * slotCol);
            int cursorY = slotYstart + (gp.getTILE_SIZE() * 2 * slotRow);
            int cursorWidth = gp.getTILE_SIZE() * 2;
            int cursorHeight = gp.getTILE_SIZE() * 2;

            // Zeichne Cursor
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke((float) (gp.getTILE_SIZE() / 10)));
            g2.drawRect(cursorX, cursorY, cursorWidth, cursorHeight);

            // Beschreibungsfenster
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.getTILE_SIZE() * 5;

            // Beschreibungstext
            int textX = slotXstart, textY = dFrameY + (gp.getTILE_SIZE());
            g2.setFont(g2.getFont().deriveFont((float) (gp.getTILE_SIZE() * 0.75)));

            int itemIndex = getItemIndex(slotCol, slotRow);
            if (itemIndex < entity.getInventory().size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for (String line : entity.getInventory().get(itemIndex).getDescription().split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += gp.getTILE_SIZE();
                }
            }
        }
    }

    public void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont((float) gp.getTILE_SIZE()));

        // SUB WINDOW
        int frameX = gp.getTILE_SIZE() * 10;
        int frameY = gp.getTILE_SIZE() * 2;
        int frameWidth = gp.getTILE_SIZE() * 12;
        int frameHeight = gp.getTILE_SIZE() * 14;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0 -> options_top(frameX, frameY);
            case 1 -> options_fullScreenNotification(frameX, frameY);
            case 2 -> options_control(frameX, frameY);
            case 3 -> options_endGameConfirmation(frameX, frameY);
        }

        gp.keyHandler.setEnterPressed(false);
    }
    public void options_top(int frameX, int frameY) {
        // Titel
        int textX = getCenterTextX("Optionen");
        int textY = frameY + (int) (gp.getTILE_SIZE() * 1.5);
        g2.drawString("Optionen", textX, textY);

        g2.setFont(g2.getFont().deriveFont(gp.getTILE_SIZE() / 4F * 3));

        // Fullscreen
        textX = frameX + (int) (gp.getTILE_SIZE() * 1.5);
        textY += gp.getTILE_SIZE() * 2.5;
        g2.drawString("Fullscreen", textX, textY);

        // Setzt Fullscreen auf 'an'
        if(commandNum == 0) {
            g2.drawString(">", textX - (gp.getTILE_SIZE() * 2 / 3), textY);

            if (gp.keyHandler.isEnterPressed()) {
                gp.fullScreenOn = !gp.fullScreenOn;
                subState = 1;
                commandNum = 0;
            }
        }

        // Musik
        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("Musik", textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - (gp.getTILE_SIZE() * 2 / 3), textY);
        }

        // SFX
        textY += (gp.getTILE_SIZE() * 1.5);
        g2.drawString("SFX", textX, textY);
        if(commandNum == 2) {
            g2.drawString(">", textX - (gp.getTILE_SIZE() * 2 / 3), textY);
        }

        // Steuerung
        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("Steuerung", textX, textY);
        if(commandNum == 3) {
            g2.drawString(">", textX - (gp.getTILE_SIZE() * 2 / 3), textY);

            if(gp.keyHandler.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        // Verlasssen
        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("Spiel verlassen", textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX - (gp.getTILE_SIZE() * 2 / 3), textY);

            if(gp.keyHandler.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }

        // Zurück
        textY += gp.getTILE_SIZE() * 2.5;
        g2.drawString("Zurueck", textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX - (gp.getTILE_SIZE() * 2 / 3), textY);
            if(gp.keyHandler.enterPressed) {
                gp.gameState = gp.PLAY_STATE;
                commandNum = 0;
            }
        }

        // Fullscreen Checkbox
        textX = frameX + (gp.getTILE_SIZE() * 10) + (g2.getFont().getSize() / 3);
        textY = frameY + (gp.getTILE_SIZE() * 4) - g2.getFont().getSize();
        g2.setStroke(new BasicStroke(gp.getTILE_SIZE() / 15F));
        if(gp.fullScreenOn) g2.fillRect(textX, textY, g2.getFont().getSize(), g2.getFont().getSize());
        g2.drawRect(textX, textY, g2.getFont().getSize(), g2.getFont().getSize());

        // Musik Lautstärke
        textX = frameX + (gp.getTILE_SIZE() * 8);
        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawRect(textX, textY, gp.getTILE_SIZE() * 3, g2.getFont().getSize());
        int volumeWidth = gp.getTILE_SIZE() * 3 / 5 * gp.music.volumeScale + (gp.getTILE_SIZE() / 15);
        g2.fillRect(textX, textY, volumeWidth, g2.getFont().getSize());

        // SFX Lautstärke
        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawRect(textX, textY, gp.getTILE_SIZE() * 3, g2.getFont().getSize());
        volumeWidth = ((gp.getTILE_SIZE() * 3) / 5) * gp.soundEffect.volumeScale + (gp.getTILE_SIZE() / 15);
        g2.fillRect(textX, textY, volumeWidth, g2.getFont().getSize());

        // Speichern der Einstellungen
        gp.config.saveConfig();
    }
    public void options_fullScreenNotification(int frameX, int frameY) {
        // Nachricht, nachdem man die Fullscreen Einstellung geändert hat
        int textX = frameX + (int) (gp.getTILE_SIZE() * 1.5);
        int textY = frameY + gp.getTILE_SIZE() * 4;

        currentDialogue = "Die Aenderung tritt\nnach einem Neustart\nin Kraft.";
        g2.setFont(g2.getFont().deriveFont(gp.getTILE_SIZE() / 4F * 3));
        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += gp.getTILE_SIZE();
        }

        // Zurück
        textY += gp.getTILE_SIZE() * 5.5;
        g2.drawString("Zurueck", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - (gp.getTILE_SIZE() * 2 / 3), textY);
            if(gp.keyHandler.enterPressed) subState = 0;
        }
    }
    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // Titel
        String text = "Steuerung";
        textX = getCenterTextX(text);
        textY = frameY + (int) (gp.getTILE_SIZE() * 1.5);
        g2.drawString(text, textX, textY);

        // Text
        g2.setFont(g2.getFont().deriveFont(gp.getTILE_SIZE() / 4F * 3));

        textX = frameX + (int) (gp.getTILE_SIZE() * 1.5);
        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("Bewegen", textX, textY);

        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("Interaktion", textX, textY);

        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("Angriff", textX, textY);

        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("Item1/2", textX, textY);

        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("Inventar", textX, textY);

        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("Optionen", textX, textY);

        textY += gp.getTILE_SIZE() * 2;
        g2.drawString("Zurueck", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - (gp.getTILE_SIZE() * 2 / 3), textY);
            if(gp.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }

        // Tasten
        textX = (int) (frameX + (gp.getTILE_SIZE() * 8.5));
        textY = frameY + (gp.getTILE_SIZE() * 3);
        g2.drawString("WASD", textX, textY);

        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("ENTER", textX, textY);

        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("E", textX, textY);

        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("F/G", textX, textY);

        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("SHIFT", textX, textY);

        textY += gp.getTILE_SIZE() * 1.5;
        g2.drawString("ESC", textX, textY);
    }
    public void options_endGameConfirmation(int frameX, int frameY) {
        // Fenster, nachdem man 'Spiel verlassen' ausgewählt hat
        int textX = frameX + (int) (gp.getTILE_SIZE() * 1.5);
        int textY = frameY + gp.getTILE_SIZE() * 4;

        currentDialogue = "Das Spiel verlassen\nund zum Titelbildschirm\nzurueckkehren?";
        g2.setFont(g2.getFont().deriveFont(gp.getTILE_SIZE() / 4F * 3));
        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += gp.getTILE_SIZE();
        }

        // Ja
        String text = "Ja";
        textX = getCenterTextX(text);
        textY += gp.getTILE_SIZE() * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - (gp.getTILE_SIZE() * 2 / 3), textY);
            if(gp.keyHandler.enterPressed) {
                subState = 0;
                gp.gameState = gp.TITLE_STATE;
                gp.stopMusic();
            }
        }

        // Nein
        text = "Nein";
        textX = getCenterTextX(text);
        textY += gp.getTILE_SIZE();
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - (gp.getTILE_SIZE() * 2 / 3), textY);
            if(gp.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 5;
            }
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.getSCREEN_WIDTH(), gp.getSCREEN_HEIGHT());

        //// Text ////
        int x, y;
        String text = "Game Over";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, gp.getTILE_SIZE() * 2));

        // Schatten
        g2.setColor(Color.BLACK);
        x = getCenterTextX(text);
        y = gp.getTILE_SIZE() * 6;
        g2.drawString(text, x + (gp.getTILE_SIZE() / 10), y + (gp.getTILE_SIZE() / 10));

        // Haupttext
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // Erneut
        g2.setFont(g2.getFont().deriveFont((float) gp.getTILE_SIZE()));
        text = "Erneut";
        x = getCenterTextX(text);
        y += 6 * gp.getTILE_SIZE();
        g2.drawString(text, x, y);

        if(commandNum == 0) {
            g2.drawString(">", x - gp.getTILE_SIZE(), y);
        }

        // Verlassen
        text = "Verlassen";
        x = getCenterTextX(text);
        y += 1.5 * gp.getTILE_SIZE();
        g2.drawString(text, x, y);

        if(commandNum == 1) {
            g2.drawString(">", x - gp.getTILE_SIZE(), y);
        }
    }

    //// MAP TRANSITION ////
    public void drawTransition() {
        // Der Bildschirm wird allmählich schwarz
        counter++;
        g2.setColor(new Color(0,0,0, counter*5));
        g2.fillRect(0, 0, gp.getSCREEN_WIDTH(), gp.SCREEN_HEIGHT);

        // Nach 50 Frames wird der Gamestate wieder zurückgesetzt und der Spieler teleportiert
        if(counter == 50) {
            counter = 0;
            gp.gameState = gp.PLAY_STATE;

            gp.currentMap = gp.eventHandler.tempMap;
            gp.player1.setWorldX((int) (gp.eventHandler.tempCol * gp.getTILE_SIZE()));
            gp.player1.setWorldY((int) (gp.eventHandler.tempRow * gp.getTILE_SIZE()));

            // Da die neue Karte eine andere Größe haben könnte, werden die MaxWorld Variablen erneut gesetzt und der Pathfinder setzt neue Nodes
            gp.setMaxWorld(gp.mapManager.getMapList().get(gp.currentMap).getMaxCol(), gp.mapManager.getMapList().get(gp.currentMap).getMaxRow());
            gp.pathFinder.instantiateNode();
        }
    }

    //// TRADE SCREEN ////
    public void drawTradeScreen() {
        switch (subState) {
            case 0 -> trade_select();
            case 1 -> trade_buy();
        }
    }
    public void trade_select() {
        //// Fenster nach dem ersten Ansprechen des Händlers ////
        drawDialogueScreen();

        // Zeichnet Fenster für die auswählbaren Optionen
        int x = gp.getTILE_SIZE() * 4;
        int y = gp.getTILE_SIZE() * 6;
        int width = gp.getTILE_SIZE() * 7;
        int height = gp.getTILE_SIZE() * 3;
        drawSubWindow(x, y, width, height);

        // Kaufen
        x += gp.getTILE_SIZE() * 1.5;
        y += gp.getTILE_SIZE() * 1.25;
        g2.drawString("Kaufen", x, y);
        if(commandNum == 0) {
            g2.drawString(">", x - (gp.getTILE_SIZE() * 2 / 3), y);
            if(gp.keyHandler.enterPressed) {
                subState = 1;
                gp.keyHandler.enterPressed = false;
            }
        }

        // Verlassen
        y += gp.getTILE_SIZE();
        g2.drawString("Verlassen", x, y);
        if(commandNum == 1) {
            g2.drawString(">", x - (gp.getTILE_SIZE() * 2 / 3), y);
            if(gp.keyHandler.enterPressed) {
                commandNum = 0;
                gp.gameState = gp.DIALOGUE_STATE;
                currentDialogue = "Komm wieder wenn du etwas *ehm* reicher bist.";
            }
        }

    }
    public void trade_buy() {

        // Zeichne das Spieler-Inventar
        drawInventoryScreen(gp.player1, false);
        // Zecihne das Händler-Inventar
        drawInventoryScreen(npc, true);

        // Fenster für die Kosten
        int x = gp.getTILE_SIZE() * 16;
        int y = gp.getTILE_SIZE() * 10;
        int width = gp.getTILE_SIZE() * 15;
        int height = (int) (gp.getTILE_SIZE() * 2.5);

        int itemIndex = getItemIndex(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.getInventory().size()) {
            drawSubWindow(x, y, width, height);

            // Zeichnet einen Rubin
            x += gp.getTILE_SIZE() * 0.5;
            y += gp.getTILE_SIZE() * 0.5;
            g2.drawImage(rupee, x , y, (int) (gp.getTILE_SIZE() * 1.25), (int) (gp.getTILE_SIZE() * 1.25), null);

            // Zeichnet den Preis den aktuellen items
            int price = npc.getInventory().get(itemIndex).getPrice();
            String text = "" + price;
            x = (int) (getCenterTextX(text) + 2.5 * gp.getTILE_SIZE()); //CenterTextX unnötig und dumm
            g2.drawString(text, x, y + gp.getTILE_SIZE());

            // Zeichnet das Fenster mit dem Spielergeld
            x = gp.getTILE_SIZE() * 16;
            y = (int) (gp.getTILE_SIZE() * 12.5);
            drawSubWindow(x, y, width, height);

            // Zeichnet Rubinzahl
            x += gp.getTILE_SIZE();
            y += gp.getTILE_SIZE() * 1.5;
            g2.drawString("Deine Rubine: " + gp.player1.getMoney(), x, y);

            // Kaufen von Items
            if(gp.keyHandler.enterPressed) {
                if(npc.getInventory().get(itemIndex).getPrice() > gp.player1.getMoney()) {
                    commandNum = 0;
                    gp.gameState = gp.DIALOGUE_STATE;
                    currentDialogue = "Du brauchst mehr Rubine dafuer.";
                } else {
                    if(gp.getPlayer1().canObtainItem(npc.getInventory().get(itemIndex))) {
                        gp.player1.setMoney(gp.getPlayer1().getMoney() - npc.getInventory().get(itemIndex).getPrice());
                    } else {
                        subState = 0;
                        gp.gameState = gp.DIALOGUE_STATE;
                        currentDialogue = "Du kannst keine Items mehr tragen.";
                    }
                }
                gp.keyHandler.enterPressed = false;
            }
        }
    }

    //// NÜTZLICHES ////
    public int getItemIndex(int slotCol, int slotRow) {
        // Gibt den Index des aktuellen ausgewählten Items
        return slotCol + (slotRow * 7);
    }
    public void drawSubWindow(int x, int y, int width, int height) {
        // Zeichnen der Subwindows mit übergebener Position und Größe
        int tile = gp.getTILE_SIZE();
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, tile / 2, tile /  2);

        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke((float) tile / 10));
        g2.drawRoundRect(x + (tile / 5), y + (tile / 5), width - (2 * tile / 5), height - (2 * tile / 5), (tile / 2) - (2 * tile / 5), (tile / 2) - (2 * tile / 5));
    }
    public int getCenterTextX (String text) {
        // Gibt x-Position aus, mit welchem ein Text mittig platziert werden kann
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.getSCREEN_WIDTH() / 2) - (textLength / 2);
    }
    public int getRightTextX (String text, int tailX) {
        // Gibt x-Position aus, mit welchem ein Text rechtsbündig zum angegebenen tailX platziert werden kann
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - textLength;
    }
}
