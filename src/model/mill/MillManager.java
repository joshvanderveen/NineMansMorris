package model.mill;

import model.Player;
import model.board.Intersection;

import java.util.ArrayList;

public final class MillManager {
    private static final ArrayList<Mill> mills = new ArrayList<>();

    /**
     * Add a mill to the list of mills
     * @param mill The mill to add
     */
    public static void addMill(Mill mill) {
        for (Mill existingMill : mills) {
            if (existingMill.compareMill(mill)) return;
        }
        mills.add(mill);
    }

    /**
     * Check whether a player has a mill that hasn't been used to remove a piece
     * @param player The player to check
     * @return Whether the player has an unused mill
     */
    public static boolean playerHasUnusedMill(Player player) {
        for (Mill mill : mills) {
            if (!mill.getIsUsed() && mill.getPlayer() == player) {
                return true;
            }
        }
        return false;
    }

    /**
     * Use a mill to remove a piece
     * @param player The player to use a mill from
     * @return Whether a mill was used or not
     */
    public static boolean useMill(Player player) {
        for (Mill mill : mills) {
            if (!mill.getIsUsed() && mill.getPlayer() == player) {
                mill.setIsUsed(true);
                return true;
            }
        }
        return false;
    }


    /**
     * Check whether an intersection is part of a mill or not
     * @param location The intersection to check
     * @return Whether the intersection is part of a mill
     */
    public static boolean intersectionIsInMill(Intersection location) {
        for (Mill mill : mills) {
            if (mill.containsIntersection(location)) return true;
        }
        return false;
    }

    /**
     * Check whether an intersection is part of an unused mill or not
     * @param location The intersection to check
     * @return Whether the intersection is part of an unused mill
     */
    public static boolean intersectionIsInUnusedMill(Intersection location) {
        for (Mill mill : mills) {
            if (!mill.getIsUsed() && mill.containsIntersection(location)) return true;
        }
        return false;
    }

    /**
     * Update the list of mills and remove any mills that haven't been used
     * @param newMills The new list of mills
     */
    public static void updateMills(ArrayList<Mill> newMills) {
        if (newMills.size() == 0) return;
        System.out.println(MillManager.mills);
        ArrayList<Mill> oldMills = new ArrayList<>();

        for (Mill mill : MillManager.mills) {
            oldMills.add(mill);
        }

        MillManager.mills.clear();

        for (Mill newMill : oldMills) {
            if (!newMill.getIsUsed()) break;

            boolean inNewMill = false;

            for (Mill mill : newMills) {
                if (mill.compareMill(newMill)) {
                    inNewMill = true;
                }
            }

            if (inNewMill) MillManager.addMill(newMill);
        }

        for (Mill oldMill : newMills) {
            MillManager.addMill(oldMill);
        }
    }
}