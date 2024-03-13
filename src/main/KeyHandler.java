package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    boolean enterPressed;

    public KeyHandler (GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(gp.gameState == gp.TITLE_STATE) titleState(code);
        else if(gp.gameState == gp.PLAY_STATE) playState(code);
        else if(gp.gameState == gp.DIALOGUE_STATE) dialogueState(code);
        else if(gp.gameState == gp.CHARACTER_STATE) characterState(code);
        else if(gp.gameState == gp.OPTION_STATE) optionState(code);
        else if(gp.gameState == gp.GAME_OVER_STATE) gameOverState(code);
        else if(gp.gameState == gp.TRADE_STATE) tradeState(code);
    }

    // Legt fest, welche Tasten der Spieler in welchem GameState benutzen kann und dient zum Wechsel der Substates in verschiedenen Untermenüs
    //// GAME STATES ////
    public void titleState(int code) {
        if(code == KeyEvent.VK_W) {
            if(gp.ui.commandNum > 0) gp.ui.commandNum--;
            else gp.ui.commandNum = 2;
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.commandNum < 2) gp.ui.commandNum++;
            else gp.ui.commandNum = 0;
        }
        if(code == KeyEvent.VK_ENTER) {
            switch (gp.ui.commandNum) {
                case 0 -> {
                    gp.setPlayerCount(1);
                    gp.getPlayer1().setDefaultValues1P();
                    gp.gameState = gp.PLAY_STATE;
                    gp.playMusic(0);
                }
                case 1 -> {
                    gp.setPlayerCount(2);
                    gp.getPlayer1().setDefaultValues2P();
                    gp.getPlayer2().setDefaultValues2P();
                    gp.setCurrentMap(gp.getDungeon2Maps());
                    gp.gameState = gp.PLAY_STATE;
                    gp.playMusic(0);
                }
                case 2 -> System.exit(0);
            }
        }
    }
    public void playState(int code) {
        if(code == KeyEvent.VK_W) gp.getPlayer1().setUpPressed(true);
        if(code == KeyEvent.VK_S) gp.getPlayer1().setDownPressed(true);
        if(code == KeyEvent.VK_A) gp.getPlayer1().setLeftPressed(true);
        if(code == KeyEvent.VK_D) gp.getPlayer1().setRightPressed(true);
        if(code == KeyEvent.VK_E) gp.getPlayer1().setAttackPressed(true);
        if(code == KeyEvent.VK_F) gp.getPlayer1().setItem1KeyPressed(true);
        if(code == KeyEvent.VK_G) gp.getPlayer1().setItem2KeyPressed(true);
        if(code == KeyEvent.VK_ENTER) enterPressed = true;

        if(gp.getPlayerCount() == 1) {
            if(code == KeyEvent.VK_SHIFT) gp.gameState = gp.CHARACTER_STATE;
        }
        if(gp.getPlayerCount() == 2) {
            if(code == KeyEvent.VK_UP) gp.getPlayer2().setUpPressed(true);
            if(code == KeyEvent.VK_DOWN) gp.getPlayer2().setDownPressed(true);
            if(code == KeyEvent.VK_LEFT) gp.getPlayer2().setLeftPressed(true);
            if(code == KeyEvent.VK_RIGHT) gp.getPlayer2().setRightPressed(true);
            if(code == KeyEvent.VK_NUMPAD0) gp.getPlayer2().setAttackPressed(true);
            if(code == KeyEvent.VK_NUMPAD1) gp.getPlayer2().setItem1KeyPressed(true);
            if(code == KeyEvent.VK_NUMPAD2) gp.getPlayer2().setItem2KeyPressed(true);
        }

        if(code == KeyEvent.VK_ESCAPE) gp.gameState = gp.OPTION_STATE;

        //DEBUG
        if(code == KeyEvent.VK_Q) {
            System.out.println("x:" + gp.getPlayer1().getWorldX() / gp.getTILE_SIZE() + " y:" + gp.getPlayer1().getWorldY() / gp.getTILE_SIZE());
        }
    }
    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.PLAY_STATE;
        }
    }
    public void characterState(int code) {
        if(code == KeyEvent.VK_SHIFT) gp.gameState = gp.PLAY_STATE;
        if(code == KeyEvent.VK_ENTER) gp.player1.selectItem();
        if(code == KeyEvent.VK_F) gp.player1.selectEquippable1();
        if(code == KeyEvent.VK_G) gp.player1.selectEquippable2();
        playerInventory(code);
    }
    public void optionState(int code) {
        if(code == KeyEvent.VK_ESCAPE) gp.gameState = gp.PLAY_STATE;
        if(code == KeyEvent.VK_ENTER) enterPressed = true;

        int maxCommandNum = switch (gp.ui.subState) {
            case 0 -> 5;
            case 3, 4 -> 1;
            default -> 0;
        };

        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSoundEffect(9);
            if (gp.ui.commandNum < 0) gp.ui.commandNum = maxCommandNum;
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSoundEffect(9);
            if(gp.ui.commandNum > maxCommandNum) gp.ui.commandNum = 0;
        }
        if(code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSoundEffect(9);
                }
                if(gp.ui.commandNum == 2 && gp.soundEffect.volumeScale > 0) {
                    gp.soundEffect.volumeScale--;
                    gp.soundEffect.checkVolume();
                    gp.playSoundEffect(9);
                }
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSoundEffect(9);
                }
                if(gp.ui.commandNum == 2 && gp.soundEffect.volumeScale < 5) {
                    gp.soundEffect.volumeScale++;
                    gp.playSoundEffect(9);
                }
            }
        }
    }
    public void gameOverState(int code) {
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSoundEffect(9);
            if(gp.ui.commandNum < 0) gp.ui.commandNum = 1;
        }

        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSoundEffect(9);
            if(gp.ui.commandNum > 1) gp.ui.commandNum = 0;
        }

        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.gameState = gp.PLAY_STATE;
                gp.retry();
            } else if(gp.ui.commandNum == 1) {
                gp.gameState = gp.TITLE_STATE;
                gp.stopMusic();
                gp.restart();
            }
        }
    }
    public void tradeState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(gp.ui.subState == 0) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
                gp.playSoundEffect(9);
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
                gp.playSoundEffect(9);
            }
        }
        if(gp.ui.subState == 1) {
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }

    //// INVENTAR NAVIGATION ////
    public void playerInventory(int code) {
        if(code == KeyEvent.VK_W && gp.ui.playerSlotRow > 0) {
            gp.ui.playerSlotRow--;
            gp.playSoundEffect(9);
        }
        if(code == KeyEvent.VK_S && gp.ui.playerSlotRow < 3) {
            gp.ui.playerSlotRow++;
            gp.playSoundEffect(9);
        }
        if(code == KeyEvent.VK_A && gp.ui.playerSlotCol > 0) {
            gp.ui.playerSlotCol--;
            gp.playSoundEffect(9);
        }
        if(code == KeyEvent.VK_D && gp.ui.playerSlotCol < 6) {
            gp.ui.playerSlotCol++;
            gp.playSoundEffect(9);
        }
    }
    public void npcInventory(int code) {
        if(code == KeyEvent.VK_W && gp.ui.npcSlotRow > 0) {
            gp.ui.npcSlotRow--;
            gp.playSoundEffect(9);
        }
        if(code == KeyEvent.VK_S && gp.ui.npcSlotRow < 3) {
            gp.ui.npcSlotRow++;
            gp.playSoundEffect(9);
        }
        if(code == KeyEvent.VK_A && gp.ui.npcSlotCol > 0) {
            gp.ui.npcSlotCol--;
            gp.playSoundEffect(9);
        }
        if(code == KeyEvent.VK_D && gp.ui.npcSlotCol < 6) {
            gp.ui.npcSlotCol++;
            gp.playSoundEffect(9);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) gp.getPlayer1().setUpPressed(false);
        if(code == KeyEvent.VK_S) gp.getPlayer1().setDownPressed(false);
        if(code == KeyEvent.VK_A) gp.getPlayer1().setLeftPressed(false);
        if(code == KeyEvent.VK_D) gp.getPlayer1().setRightPressed(false);
        if(code == KeyEvent.VK_E) gp.getPlayer1().setAttackPressed(false);
        if(code == KeyEvent.VK_F) gp.getPlayer1().setItem1KeyPressed(false);
        if(code == KeyEvent.VK_G) gp.getPlayer1().setItem2KeyPressed(false);

        if(code == KeyEvent.VK_UP) gp.getPlayer2().setUpPressed(false);
        if(code == KeyEvent.VK_DOWN) gp.getPlayer2().setDownPressed(false);
        if(code == KeyEvent.VK_LEFT) gp.getPlayer2().setLeftPressed(false);
        if(code == KeyEvent.VK_RIGHT) gp.getPlayer2().setRightPressed(false);
        if(code == KeyEvent.VK_NUMPAD0) gp.getPlayer2().setAttackPressed(false);
        if(code == KeyEvent.VK_NUMPAD1) gp.getPlayer2().setItem1KeyPressed(false);
        if(code == KeyEvent.VK_NUMPAD2) gp.getPlayer2().setItem2KeyPressed(false);

        if(code == KeyEvent.VK_ENTER) enterPressed = false;
    }

    //// GETTER ////
    public boolean isEnterPressed() { return enterPressed; }
    public void setEnterPressed(boolean enterPressed) { this.enterPressed = enterPressed; }
}
