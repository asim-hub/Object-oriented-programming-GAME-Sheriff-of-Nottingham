package com.tema1.player;

import com.tema1.common.Constants;
import com.tema1.main.GameInput;
import com.tema1.goods.GoodsFactory;

public class BasicPlayer extends Player {
    public BasicPlayer(final GameInput gameInput, final int i) {
        super(gameInput, i);
    }

    public final void distributes(final Integer k) {
            this.setMita(Constants.ZERO);
            Help object = new Help();
            GoodsFactory instance = GoodsFactory.getInstance();

            if (this.getRol().equals("Comerciant")) {
                this.setCount(-Constants.ONE);
                object.frequentElem(this, instance);
                if (this.getCount().equals(-Constants.ONE)) {
                    object.takeillegal(this, instance);
                    this.setCard(Constants.ZERO);
                    this.setCount(-Constants.ONE);
                } else {
                    for (int j = 0; j < this.getCount(); j++) {
                        this.getBagcards().add(this.getCard());
                    }
                }
            }
        }

    /**
     */
    public void declare() {
        this.setObjDeclar(this.getCard());
    }

    /**
     * @param players lista de jucatori
     * @param gameInput date de intrare
     */
    public void bagchack(final Player[] players, final GameInput gameInput) {
        Integer ok = 0;
        if (this.getTreasury() <= Constants.MIN) {
            for (int i = 0; i < players.length; i++) {
                /**
                 * daca verific si este mita se intoarce mita inapoi
                 */
                if (players[i].getRol().equals("Comerciant") && players[i].getMita() > 0) {
                    players[i].setTreasury(players[i].getTreasury() + players[i].getMita());
                    players[i].setMita(Constants.ZERO);
                }
            }
        }
        if (this.getTreasury() > Constants.MIN) {
            for (int i = 0; i < players.length; i++) {
                ok = 0;
                if (players[i].getRol().equals("Comerciant")) {
                    /**
                     * se intoarce mita daca a pus
                     */
                    players[i].setTreasury(players[i].getTreasury() + players[i].getMita());
                    players[i].setMita(0);
                    for (int j = 0; j < players[i].getBagcards().size(); j++) {
                        if (players[i].getBagcards().get(j).equals(players[i].getObjDeclar())) {
                            ok++;
                        }
                    }
                if (((Integer) players[i].getBagcards().size()).equals(ok)) {
                    /**
                     * nu e mincinos si cel care a verificat ii plateste
                     */
                    this.setTreasury(this.getTreasury() - 2 * (players[i].getBagcards().size()));
                    players[i].setTreasury(players[i].getTreasury()
                            + 2 * (players[i].getBagcards().size()));
                } else {
                    for (int p = 0; p < players[i].getBagcards().size(); p++) {
                        if (!(players[i].getBagcards().get(p).equals(players[i].getObjDeclar()))
                                && players[i].getBagcards().get(p) > Constants.TEN) {
                            players[i].setTreasury(players[i].getTreasury() - Constants.FOUR);
                            this.setTreasury(this.getTreasury() + Constants.FOUR);
                        } else if (!(players[i].getBagcards().get(p)
                                .equals(players[i].getObjDeclar()))
                                && players[i].getBagcards().get(p) < Constants.TEN) {
                            players[i].setTreasury(players[i].getTreasury() - Constants.THREE + 1);
                            this.setTreasury(this.getTreasury() + Constants.THREE - 1);
                        }
                    }
                    /**
                     * se confisca cartile nedeclarate
                     */
                    for (int j = 0; j < players[i].getBagcards().size(); j++) {
                        if (!(players[i].getBagcards().get(j).equals(players[i].getObjDeclar()))) {
                            gameInput.getAssetIds().add(players[i].getBagcards().get(j));
                            players[i].getBagcards().remove(j);
                            j--;
                        }
                    }
                }
            }
        }
    }

    }
}
