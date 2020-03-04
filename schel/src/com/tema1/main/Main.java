package com.tema1.main;
import com.tema1.player.Player;
import com.tema1.player.BasicPlayer;
import com.tema1.player.BribedPlayer;
import com.tema1.player.GreedyPlayer;

public final class Main {

    private Main() {

    }

    public static void main(final String[] args) {

        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        /**
         * creez un vector de playeri
         */
        Player[] players;
        players = new Player[gameInput.getPlayerNames().size()];
        for (int i = 0; i < players.length; i++) {
            if (gameInput.getPlayerNames().get(i).equals("basic")) {
                players[i] = new BasicPlayer(gameInput, i);
            }
            if (gameInput.getPlayerNames().get(i).equals("greedy")) {
                players[i] = new GreedyPlayer(gameInput, i);
            }
            if (gameInput.getPlayerNames().get(i).equals("bribed")) {
                players[i] = new BribedPlayer(gameInput, i);
            }
        }

        for (int i = 1; i <= gameInput.getRounds(); i++) {
            for (int j = 0; j < gameInput.getPlayerNames().size(); j++) {
                /**
                 * adaug roluri
                 */
                players[j].role(players);
                /**
                 * distribui cartile in mana
                 */
                players[j].distributes(players, gameInput);
                /**
                 * bag cartile in sac dupa strategie,declar bunul si sterg cartile din mana
                 */
                for (int k = 0; k < gameInput.getPlayerNames().size(); k++) {
                    if (players[k].getRol().equals("Comerciant")) {
                        players[k].distributes(i);
                        players[k].declare();
                        players[k].deletecard();
                    }
                }
                /**
                 * verific sacul
                 */
                players[j].bagchack(players, gameInput);
                /**
                 * adaug sacul pe taraba
                 */
                players[j].addTaraba(players);
            }
        }
        /**
         * calculez profitul pentru fiecare jucator
         */
        for (int i = 0; i < gameInput.getPlayerNames().size(); i++) {
            players[i].score();
        }
        /**
         * adaug bonusuri de rege si regina
         */
        players[0].kingQuinScore(players);
        /**
         * afisez
         */
        players[0].output(players);
    }
}

