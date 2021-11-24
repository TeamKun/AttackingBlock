package net.kunmc.lab.attackingblock.util;

import net.kunmc.lab.attackingblock.AttackingBlock;

public class RandomCalculator {
    /**
     * 抽選する
     * @param probability 当選確率(%)
     *
     * @return 当選したか
     * */
    public static boolean lottery(double probability) {
        // 抽選
        Double num = Math.random();

        if (num >= probability / 100) {
            return false;
        }

        return true;
    }
}
