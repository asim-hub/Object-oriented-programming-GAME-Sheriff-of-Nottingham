package com.tema1.player;

import com.tema1.common.Constants;
import com.tema1.goods.GoodsFactory;
import com.tema1.main.GameInput;

public class BribedPlayer extends Player {
    public BribedPlayer(final GameInput gameInput, final int i) {
        super(gameInput, i);
    }
    /**
    * @param k index player
    */
    public void distributes(final Integer k) {
        GoodsFactory instance = GoodsFactory.getInstance();
        Help object = new Help();
        this.setMita(0);
        if (this.getRol().equals("Comerciant")) {
            object.sorting(this, instance);
            if (this.getHandcards().get(0) < Constants.TEN
                    || (this.getHandcards().get(0) > Constants.TEN
                    && this.getTreasury() < Constants.SEVEN + 1)
                    || (this.getTreasury() < Constants.THREE - 1)) {
                /**
                 * daca are numai carti legale/nu are buget suficient si aplica strategia de baza
                 */
                this.setMita(0);
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
                }
            } else if (this.getHandcards().get(0) > Constants.TEN) {
                /**
                 * cazul in care nu aplica strategia de baza si sigur triseaza
                 */
                this.setMita(Constants.FOUR + 1);
                this.setCard(Constants.ZERO);
                int illegal, legal;
                int i = Constants.ZERO;
                illegal = Constants.ZERO;
                legal = Constants.ZERO;
                Integer aux = this.getTreasury();
                /**
                 * adaug bunuri ilegale daca se poate
                 */
                while (aux > Constants.FOUR && i < this.getHandcards().size()
                        && this.getBagcards().size() < Constants.SEVEN + 1) {
                    if (illegal > 0 && illegal < Constants.THREE) {
                        this.setMita(Constants.FOUR + 1);
                    } else if (illegal > Constants.THREE - 1) {
                        this.setMita(Constants.TEN);
                    }
                    if (this.getHandcards().get(i) > Constants.TEN && (aux - Constants.FOUR) > 0) {
                        this.getBagcards().add(this.getHandcards().get(i));
                        this.getHandcards().remove(i);
                        aux = aux - 2;
                        illegal++;
                    } else if (this.getHandcards().get(i) < Constants.TEN
                            && (this.getTreasury() - illegal * Constants.FOUR
                            - (legal + 1) * (Constants.THREE - 1)) > 0) {
                        this.getBagcards().add(this.getHandcards().get(i));
                        this.getHandcards().remove(i);
                        aux = aux - 2;
                        legal++;
                    } else {
                        i++;
                    }

                }


                while (this.getTreasury() - illegal * Constants.FOUR <= 0) {
                    illegal--;
                    this.getBagcards().remove(this.getBagcards().size() - 1);
                    if (illegal > 0 && illegal < Constants.THREE) {
                        this.setMita(Constants.FOUR + 1);
                    } else if (illegal > Constants.THREE - 1) {
                        this.setMita(Constants.TEN);
                    }
                }
                int l = 0;
                while ((this.getTreasury() - illegal * Constants.FOUR - (legal + 1)
                        * (Constants.THREE - 1)) > 0 && this.getBagcards().size()
                        < (Constants.SEVEN + 1) && l < this.getHandcards().size()) {
                    legal++;
                    if (this.getHandcards().get(l) < Constants.TEN) {
                        this.getBagcards().add(this.getHandcards().get(l));
                        l = this.getHandcards().size();
                    }
                    l++;
                }

                this.setObjDeclar(0);
                this.setTreasury(this.getTreasury() - this.getMita());
            }
        }
    }

    /**
     */
    public void declare() {
        if (this.getBagcards().size() > 0 && this.getBagcards().get(0) > Constants.TEN) {
            this.setObjDeclar(0);
        } else {
            this.setObjDeclar(this.getCard());
        }
    }

    /**
     * @param players   lista de jucatori
     * @param gameInput date de intrare
     */
    public void bagchack(final Player[] players, final GameInput gameInput) {

        int ok = 0;
        /**
         * in caz ca nu are bani sa verifice
         */
        if (this.getTreasury() < Constants.MIN) {
            for (int i = 0; i < players.length; i++) {
                if (players[i].getRol().equals("Comerciant") && players[i].getMita() > 0) {
                    players[i].setTreasury(players[i].getTreasury() + players[i].getMita());
                    players[i].setMita(0);
                }
            }
        }
        if (this.getTreasury() >= Constants.MIN) {
            /**
             * verific doar pe vecini
             */
            for (int i = 0; i < players.length; i++) {
                ok = 0;
                int vecin = 0;
                if (this.getId() == 0 && (players[i].getId() == 1
                        || players[i].getId() == (players.length - 1))) {
                    vecin = 1;
                } else if (this.getId() == (players.length - 1)
                        && (players[i].getId() == 0
                        || players[i].getId() == (players.length - 2))) {
                    vecin = 1;
                } else if ((this.getId() == players[i].getId() + 1)
                        || (this.getId() == players[i].getId() - 1)) {
                    vecin = 1;
                }
                if (players[i].getRol().equals("Comerciant") && vecin == 1) {
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
                         *  nu e mincinos si cel care a verificat ii plateste
                         */
                        this.setTreasury(this.getTreasury()
                                - 2 * (players[i].getBagcards().size()));
                        players[i].setTreasury(players[i].getTreasury()
                                + 2 * (players[i].getBagcards().size()));

                    } else {
                        /**
                         * daca e mincinos plateste pt obiectele nedeclarate
                         */
                        for (int p = 0; p < players[i].getBagcards().size(); p++) {
                            if (!(players[i].getBagcards().get(p).equals(players[i].getObjDeclar()))
                                    && players[i].getBagcards().get(p) > Constants.TEN) {
                                players[i].setTreasury(players[i].getTreasury() - Constants.FOUR);
                                this.setTreasury(this.getTreasury() + Constants.FOUR);
                            } else if (!(players[i].getBagcards().get(p)
                                    .equals(players[i].getObjDeclar()))
                                    && players[i].getBagcards().get(p) < Constants.TEN) {
                                players[i].setTreasury(players[i].getTreasury() - 2);
                                this.setTreasury(this.getTreasury() + 2);
                            }
                        }
                        for (int j = 0; j < players[i].getBagcards().size(); j++) {
                            if (!(players[i].getBagcards().get(j)
                                    .equals(players[i].getObjDeclar()))) {
                                gameInput.getAssetIds().add(players[i].getBagcards().get(j));
                                players[i].getBagcards().remove(j);
                                j--;
                            }
                        }
                    }
                } else if (players[i].getRol().equals("Comerciant") && vecin == 0) {
                    /**
                     * daca nu e vecin nu il verific da iau mita
                     */
                    if (players[i].getMita() != 0) {
                        this.setTreasury(this.getTreasury() + players[i].getMita());
                        players[i].setMita(0);
                    }
                }
            }

        }
    }

}
