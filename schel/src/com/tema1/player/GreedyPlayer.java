package com.tema1.player;

import com.tema1.common.Constants;
import com.tema1.goods.GoodsFactory;
import com.tema1.main.GameInput;

public class GreedyPlayer extends Player {

    public GreedyPlayer(final GameInput gameInput, final int i) {
        super(gameInput, i);
    }
    /**
    * @param k index player
    */
    public void distributes(final Integer k) {

        Help object = new Help();
        GoodsFactory instance = GoodsFactory.getInstance();

        if (this.getRol().equals("Comerciant")) {
            this.setCount(-1);
            object.frequentElem(this, instance);
            if (this.getCount().equals(-1)) {
                object.takeillegal(this, instance);
                this.setCard(0);
                this.setCount(-1);
            } else {
                for (int j = 0; j < this.getCount(); j++) {
                    this.getBagcards().add(this.getCard());
                }
            }

            if (k % 2 == 0 && this.getBagcards().size() < Constants.SEVEN + 1) {
                object.takeillegal(this, instance);
            }
        }

    }

    /**
    */
    public void declare() {
        this.setObjDeclar(this.getCard());
    }

    /**
    * @param players   lista de playeri
    * @param gameInput date de intrare
    */
    public void bagchack(final Player[] players, final GameInput gameInput) {
        for (int i = 0; i < players.length; i++) {
            Integer ok = 0;
            if (players[i].getRol().equals("Comerciant") && players[i].getMita() > 0) {
                this.setTreasury(this.getTreasury() + players[i].getMita());
                players[i].setMita(0);
            } else {
                /**
                 * daca nu a pus mita , il verific
                 */
                if (this.getTreasury() > Constants.MIN) {
                    ok = 0;
                    if (players[i].getRol().equals("Comerciant")) {
                        for (int j = 0; j < players[i].getBagcards().size(); j++) {
                            if (players[i].getBagcards().get(j).equals(players[i].getObjDeclar())) {
                                ok++;
                            }
                        }
                        if (((Integer) players[i].getBagcards().size()).equals(ok)) {
                            /**
                             * nu e mincinos si cel care a verificat ii plateste
                             */
                            this.setTreasury(this.getTreasury()
                                    - 2 * (players[i].getBagcards().size()));
                            players[i].setTreasury(players[i].getTreasury()
                                    + 2 * (players[i].getBagcards().size()));
                        } else {
                            /**
                             * daca e mincinos plateste pentru obiectele nedeclarate
                             */
                            for (int p = 0; p < players[i].getBagcards().size(); p++) {
                                if (!(players[i].getBagcards().get(p)
                                        .equals(players[i].getObjDeclar()))
                                    && players[i].getBagcards().get(p) > Constants.TEN) {
                                    players[i].setTreasury(players[i]
                                            .getTreasury() - Constants.FOUR);
                                    this.setTreasury(this.getTreasury() + Constants.FOUR);
                                } else if (!(players[i].getBagcards().get(p).equals(players[i]
                                        .getObjDeclar())) && players[i].getBagcards().get(p)
                                            < Constants.TEN) {
                                    players[i].setTreasury(players[i].getTreasury() - 2);
                                    this.setTreasury(this.getTreasury() + 2);
                                }
                            }
                            /**
                             * i se confica cartile nedeclarate si se adauga in coada pechetului
                             */
                            for (int j = 0; j < players[i].getBagcards().size(); j++) {
                                if (!(players[i].getBagcards().get(j)
                                        .equals(players[i].getObjDeclar()))) {
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
}
