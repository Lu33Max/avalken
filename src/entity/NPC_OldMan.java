package entity;

import main.GamePanel;

import java.awt.Rectangle;
import java.util.Random;

public class NPC_OldMan extends Entity {

    int speakCount = 0;

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        speed = 1;

        solidArea = new Rectangle(gp.getTILE_SIZE() / 6, gp.getTILE_SIZE() / 3, (gp.getTILE_SIZE() * 2) / 3, (gp.getTILE_SIZE() * 2) / 3);
        solidAreaDefX = solidArea.x;
        solidAreaDefY = solidArea.y;

        getImage();
        setDialogue();
    }

    // LOAD NPC SPRITES
    public void getImage() {
        up1 = setup("/npcs/oldman_up_1", 1, 1);
        up2 = setup("/npcs/oldman_up_2", 1, 1);
        down1 = setup("/npcs/oldman_down_1", 1, 1);
        down2 = setup("/npcs/oldman_down_2", 1, 1);
        left1 = setup("/npcs/oldman_left_1", 1, 1);
        left2 = setup("/npcs/oldman_left_2", 1, 1);
        right1 = setup("/npcs/oldman_right_1", 1, 1);
        right2 = setup("/npcs/oldman_right_2", 1, 1);
    }

    public void setDialogue(){
        dialogues[0] = "Du bist also endlich aufgewacht.\nWir werden gerade von Monstern angegriffen. Oeffne\nmit SHIFT dein Inventar und rueste deine Waffe\nund deinen Schild mit ENTER aus.";
        dialogues[1] = "Mit E kannst du angreifen. Nun besiege diese Schleime.";
        dialogues[2] = "Gute Arbeit. Jetzt folge mir. Ich bringe dich\nwieder in unser Lager.";
        dialogues[3] = "Oh nein! Es gab eine Erdrutsch und nun ist der\nWeg ins Lager versperrt.\nHier in der Gegend sollte ein hilfreicher Gegenstand sein.\nFinde ihn und komm wieder zu mir!";
        dialogues[4] = "Sehr gut! Nun gehe wieder in dein Inventar und rueste\ndie Bomben mit F aus.\nMit ihnen kannst du den Weg freiraeumen.";
        dialogues[5] = "Jetzt koennen wir endlich ins Lager. Folge mir.";
        dialogues[6] = "In diesem Haus ist ein alter Freund von mir.\nEr wird dich auf deiner Reise mit nuetzlichen Items\nunterstuetzen.\nAllerdings sind seine Waren nicht umsonst.";
        dialogues[7] = "Nun weisst du alles wichtige, um die Insel selbst\nzu erkunden. Solltest du dir einmal unsicher sein\nfindest im ESCAPE Menue sicherlich Antworten.";
        dialogues[8] = "Viel Spass.";
    }

    public void setAction() {
        if(speakCount == 3) {

            //// GO TO SPECIFIC GOAL ////
            int endCol = 37, endRow = 28;
            searchPath(endCol, endRow);

        } else if(speakCount == 6) {

            int endCol = 20, endRow = 37;
            searchPath(endCol, endRow);

        } else {
            actionLock++;

            if (actionLock == 120) {
                Random random = new Random();
                int i = random.nextInt(4);

                switch (i) {
                    case 0 -> direction = "up";
                    case 1 -> direction = "down";
                    case 2 -> direction = "left";
                    case 3 -> direction = "right";
                }

                actionLock = 0;
            }
        }

    }

    public void speak(Entity player) {
        if(dialogueIndex != 8) {
            if (dialogues[dialogueIndex] == null) dialogueIndex = 0;

            if (speakCount != 4 || gp.getPlayer1().isBombObtained()) {
                speakCount++;
            }

            if (speakCount == 5 && gp.getPlayer1().isBombObtained()) {
                dialogueIndex++;
            }

            // Setzt den aktuellen Dialog auf den Dialog des NPCs
            gp.getUi().currentDialogue = dialogues[dialogueIndex];

            if (speakCount != 4) {
                dialogueIndex++;
            }
        } else {
            gp.getUi().currentDialogue = dialogues[8];
        }

        // Dreht NPC zum Spieler hin
        switch (player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }

        System.out.println(speakCount);
    }
}
