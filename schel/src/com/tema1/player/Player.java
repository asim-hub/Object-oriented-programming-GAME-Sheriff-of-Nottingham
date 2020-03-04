package com.tema1.player;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.tema1.goods.LegalGoods;
import com.tema1.goods.GoodsFactory;
import com.tema1.main.GameInput;
import com.tema1.common.Constants;

public class Player {
    private Integer treasury;
    private List<Integer> handcards = new ArrayList<Integer>();
    private List<Integer> bagcards = new ArrayList<Integer>();
    private List<Integer> mycards = new ArrayList<Integer>();
    private String strategy;
    private String rol;
    private Integer objDeclar = -Constants.ONE;
    private Integer card;
    private Integer count = -Constants.ONE;
    private Integer mita = Constants.ZERO;
    private Integer id;

    /**
     * @return
     */
    public Integer getTreasury() {
        return treasury;
    }

    /**
     * @param treasury Visteria jucatorului
     */
    public void setTreasury(final Integer treasury) {
        this.treasury = treasury;
    }

    /**
     * @return
     */
    public List<Integer> getHandcards() {
        return handcards;
    }

    /**
     * @param handcards cartile din mana
     */
    public void setHandcards(final List<Integer> handcards) {
        this.handcards = handcards;
    }

    /**
     * @return
     */
    public List<Integer> getBagcards() {
        return bagcards;
    }

    /**
     * @param bagcards cartile din sac
     */
    public void setBagcards(final List<Integer> bagcards) {
        this.bagcards = bagcards;
    }

    /**
     * @return
     */
    public List<Integer> getMycards() {
        return mycards;
    }

    /**
     * @param mycards cartile de pe taraba
     */
    public void setMycards(final List<Integer> mycards) {
        this.mycards = mycards;
    }

    /**
     * @return
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     * @param strategy strategia jucatorului
     */
    public void setStrategy(final String strategy) {
        this.strategy = strategy;
    }

    /**
     * @return
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol rolul jucatorului de comerciant sau serif
     */
    public void setRol(final String rol) {
        this.rol = rol;
    }

    /**
     * @return
     */
    public Integer getObjDeclar() {
        return objDeclar;
    }

    /**
     * @param objDeclar obiectul declarat
     */
    public void setObjDeclar(final Integer objDeclar) {
        this.objDeclar = objDeclar;
    }

    /**
     * @return
     */
    public Integer getCard() {
        return card;
    }

    /**
     * @param card carte din mana predominanta
     */
    public void setCard(final Integer card) {
        this.card = card;
    }

    /**
     * @return
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count numarotor pentru carte predominanta
     */
    public void setCount(final Integer count) {
        this.count = count;
    }

    /**
     * @return
     */
    public Integer getMita() {
        return mita;
    }

    /**
     * @param mita mita
     */
    public void setMita(final Integer mita) {
    this.mita = mita;
    }

    /**
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id id jucator
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    public Player(final GameInput gameInput, final int i) {
        this.treasury = Constants.WALLET;
        this.strategy = gameInput.getPlayerNames().get(i);
        this.id = i;
    }

    /**
     * @param players lista de jucatori
     */
    public void role(final Player[] players) {
        for (int i = 0; i < players.length; i++) {
            players[i].rol = "Comerciant";
        }
        this.rol = "Serif";
    }

    /**
     * @param players   lista de playeri
     * @param gameInput date de intrare
     */
    public void distributes(final Player[] players, final GameInput gameInput) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].rol.equals("Comerciant")) {
                players[i].handcards.addAll(gameInput.getAssetIds().
                        subList(Constants.ZERO, Constants.TEN));
                gameInput.getAssetIds().subList(Constants.ZERO, Constants.TEN).
                        clear();
            }
        }
    }
    public void distributes(final Integer k) {
    }

    /**
     *
     */
    public void deletecard() {
        this.handcards.clear();
    }
    public void declare() {
    }

    /**
     * @param players   lista de playeri
     * @param gameInput date de intrare
     */
    public void bagchack(final Player[] players, final GameInput gameInput) {
    }

    /**
     * @param players lista de playeri
     */
    public void addTaraba(final Player[] players) {
        for (int i = 0; i < players.length; i++) {
            players[i].mycards.addAll(players[i].bagcards);
            players[i].bagcards.removeAll(players[i].bagcards);
        }
    }

    /**
     *
     */
    public void score() {
        GoodsFactory instance = GoodsFactory.getInstance();
        for (int i = 0; i < this.mycards.size(); i++) {
            if (this.mycards.get(i) == Constants.TWENTIETH) {
                this.mycards.add(Constants.ONE);
                this.mycards.add(Constants.ONE);
                this.mycards.add(Constants.ONE);
            }
            if (this.mycards.get(i) == Constants.TWENTYONE) {
                this.mycards.add(Constants.THREE);
                this.mycards.add(Constants.THREE);
            }
            if (this.mycards.get(i) == Constants.TWENTYTTWO) {
                this.mycards.add(Constants.ONE + 1);
                this.mycards.add(Constants.ONE + 1);
            }
            if (this.mycards.get(i) == Constants.TWENTYTHREE) {
                this.mycards.add(Constants.SEVEN);
                this.mycards.add(Constants.SEVEN);
                this.mycards.add(Constants.SEVEN);
                this.mycards.add(Constants.SEVEN);
            }
            if (this.mycards.get(i) == Constants.TWENTYFOUR) {
                this.mycards.add(Constants.FOUR);
                this.mycards.add(Constants.FOUR);
                this.mycards.add(Constants.SEVEN - 1);
                this.mycards.add(Constants.SEVEN - 1);
                this.mycards.add(Constants.SEVEN - 1);
                this.mycards.add(Constants.THREE);
            }
            this.treasury = this.treasury + instance.getAllGoods().
                    get(this.mycards.get(i)).getProfit();
        }
    }

    /**
     * @param players lista de playeri
     */
    public void kingQuinScore(final Player[] players) {
        GoodsFactory instance = GoodsFactory.getInstance();
        Integer[] cnt = new Integer[players.length];
        for (int i = 0; i < Constants.TEN; i++) {
            Arrays.fill(cnt, 0);
            for (int j = 0; j < players.length; j++) {
                for (Integer x : players[j].mycards) {
                    if (x.equals(instance.getAllGoods().get(i).getId())) {
                        cnt[j]++;
                    }
                }
            }
            Integer max;
            Integer ok;
            max = 0;
            ok = 0;
            for (int j = 0; j < players.length; j++) {
                max = 0;
                for (int k = 0; k < cnt.length; k++) {
                    if (cnt[k] > max) {
                        max = cnt[k];
                    }
                }
                if (max != 0) {
                    if (cnt[j] == max) {
                        if (ok == 0) {
                            Integer ind = instance.getAllGoods().get(i).getId();
                            players[j].treasury += ((LegalGoods) instance.getGoodsById(ind)).
                                    getKingBonus();
                            cnt[j] = 0;
                            j = -1;
                            max = 0;
                        }
                        if (ok == 1) {
                            Integer ind = instance.getAllGoods().get(i).getId();
                            players[j].treasury += ((LegalGoods) instance.getGoodsById(ind)).
                                    getQueenBonus();
                            j = cnt.length;
                        }
                        ok++;

                    }
                }
            }
        }
    }

    /**
     * @param players lista de jucatori
     */
    public void output(final Player[] players) {
        for (int i = 0; i < players.length; i++) {
            Integer max = -1;
            for (int j = 0; j < players.length; j++) {
                if (players[j].treasury > max) {
                    max = players[j].treasury;
                }
            }
            for (int j = 0; j < players.length; j++) {
                if (players[j].treasury.equals(max)) {
                    System.out.println(j
                            + " "
                            + players[j].strategy.toUpperCase()
                            + " "
                            + players[j].treasury);
                    players[j].treasury = -Constants.THREE + 1;
                }
            }

        }

    }
}
