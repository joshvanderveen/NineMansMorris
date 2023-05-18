package model;

import model.board.Intersection;

import java.util.ArrayList;

public final class MillManager {
    private static final ArrayList<Mill> mills = new ArrayList<>();

    public static void addMill(Mill mill) {
        for (Mill existingMill : mills) {
            if (existingMill.compareMill(mill)) return;
        }
        mills.add(mill);
    }

    public static void removeMill(Mill mill) {
        mills.remove(mill);
    }

    public static boolean playerHasUnusedMill(Player player) {
        for (Mill mill : mills) {
            if (!mill.getIsUsed()) {
                return true;
            }
        }
        return false;
    }

    private static void useMill(Mill mill) {
        mill.toggleIsUsed();
    }

    public static boolean useMill(Player player) {
        for (Mill mill : mills) {
            if (!mill.getIsUsed() && mill.getPlayer() == player) {
                useMill(mill);
                return true;
            }
        }
        return false;
    }


    public static boolean intersectionIsInMill(Intersection location) {
        for (Mill mill : mills) {
            if (mill.containsIntersection(location)) return true;
        }
        return false;
    }

    public static boolean intersectionIsInUnusedMill(Intersection location) {
        for (Mill mill : mills) {
            if (!mill.getIsUsed() && mill.containsIntersection(location)) return true;
        }
        return false;
    }

    public static void updateMills(ArrayList<Mill> newMills) {
        ArrayList<Mill> oldMills = (ArrayList<Mill>) MillManager.mills.clone();

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