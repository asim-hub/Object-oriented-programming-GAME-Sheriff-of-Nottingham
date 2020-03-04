package com.tema1.player;
import com.tema1.common.Constants;
import com.tema1.goods.GoodsFactory;

public class Help {
    /**
    * @param player lista de playeri
    * @param instance instanta la GoodsFactory
    *aflu cel mai frecvent element
    */
    public void frequentElem(final Player player, final GoodsFactory instance) {
        Integer f = -1;
        Integer c = -1;
        for (int i = 0; i < player.getHandcards().size(); i++) {
            Integer nr = player.getHandcards().get(i);
            Integer count = 0;
            for (int j = 0; j < player.getHandcards().size(); j++) {
                if (player.getHandcards().get(j).equals(nr)) {
                    count++;
                }
            }
            /**
             * verific si daca e legal
             */
            if (count > c && nr >= 0 && nr <= Constants.TEN - 1) {
                c = count;
                f = nr;
            } else if (count.equals(c) && nr >= 0 && nr <= (Constants.TEN - 1)
                    && instance.getAllGoods().get(nr).getProfit()
                    > instance.getAllGoods().get(f).getProfit()) {
                /**
                 * verific daca are profit mai mare
                 */
                c = count;
                f = nr;
            } else if (count.equals(c) && nr >= 0 && nr <= (Constants.TEN - 1)
                    && instance.getAllGoods().get(nr).getProfit()
                    == instance.getAllGoods().get(f).getProfit()
                    && f < nr) {
                c = count;
                f = nr;
            }

        }
        /**
         * verific daca exista numai ilegale
         */
        if (f != -1) {
            /**
             * sterg toate cartile alea din mana
             */
            for (int i = 0; i < player.getHandcards().size(); i++) {
                if (player.getHandcards().get(i).equals(f)) {
                    player.getHandcards().remove(i);
                }
            }
        }

        player.setCard(f);
        player.setCount(c);
    }

    /**
    * @param player lista de playeri
    * @param instance instanta la clasa GoodsFactory
    */
    public void takeillegal(final Player player, final GoodsFactory instance) {
        Integer card = -1;
        Integer profit = -1;
        int indx = -1;
        for (int j = 0; j < player.getHandcards().size(); j++) {
            if (player.getHandcards().get(j) >= Constants.TWENTIETH && instance.getAllGoods()
                    .get(player.getHandcards().get(j)).getProfit() > profit) {
                card = player.getHandcards().get(j);
                profit = instance.getAllGoods().get(player.getHandcards().get(j)).getProfit();
                indx = j;
            }
        }
        if (card != -1) {
            player.getBagcards().add(card);
            player.getHandcards().remove(indx);

        }

    }

    /**
     * @param player lista de playeri
     * @param instance instanta la clasa GoodsFactory
     * sortez cartile
     */
    public void sorting(final Player player, final GoodsFactory instance) {
        for (int i = 0; i < player.getHandcards().size() - 1; i++) {
            for (int j = i + 1; j < player.getHandcards().size(); j++) {
                Integer a;
                Integer b;
                Integer aux;
                a = instance.getAllGoods().get(player.getHandcards().get(i)).getProfit();
                b = instance.getAllGoods().get(player.getHandcards().get(j)).getProfit();
                if ((a < b) || (a.equals(b) && player.getHandcards().get(i)
                        < player.getHandcards().get(j))) {
                    aux = player.getHandcards().get(i);
                    player.getHandcards().set(i, player.getHandcards().get(j));
                    player.getHandcards().set(j, aux);
                }
            }
        }
    }
}
