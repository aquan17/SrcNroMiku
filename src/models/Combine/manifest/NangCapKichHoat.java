package models.Combine.manifest;

import consts.ConstFont;
import consts.ConstNpc;
import item.Item;
import models.Combine.CombineService;
import player.Player;
import server.Manager;
import services.InventoryService;
import services.ItemService;
import services.RewardService;
import services.Service;
import utils.Util;

/**
 *
 * @author HBT
 */
public class NangCapKichHoat {

    public static boolean isDoHuyDiet(Item item) {
        return item.template.id >= 650 && item.template.id <= 662;
    }

    public static void showInfoCombine(Player player) {
        if (player.combine != null && player.combine.itemsCombine != null && player.combine.itemsCombine.size() == 1) {
            Item trangbiHuyDiet = null;
            for (Item item : player.combine.itemsCombine) {
                if (isDoHuyDiet(item)) {
                    trangbiHuyDiet = item;
                }
            }
            player.combine.goldCombine = 500_000_000;
            int goldCombie = player.combine.goldCombine;
            if (trangbiHuyDiet != null) {
                String npcSay = "Sau khi cường hoá, sẽ được nâng cấp trang bị Huỷ Diệt thành trang bị Kích hoạt";
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                        "Cường hoá\n" + Util.numberToMoney(goldCombie) + " vàng", "Từ chối");
            } else {
                Service.gI().sendThongBaoOK(player, "Cần 1 trang bị huỷ diệt");
            }
        } else {
            Service.gI().sendThongBaoOK(player, "Cần 1 trang bị huỷ diệt");
        }
    }

    public static void startCombine(Player player) {
        if (player.combine.itemsCombine.size() == 1) {
            int gold = player.combine.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Bạn không đủ vàng, còn thiếu " + Util.numberToMoney(gold - player.inventory.gold) + " vàng nữa");
                Service.gI().sendMoney(player);
                return;
            }

            Item trangbiHuyDiet = null;
            for (Item item : player.combine.itemsCombine) {
                if (isDoHuyDiet(item)) {
                    trangbiHuyDiet = item;
                }
            }
            int gender = trangbiHuyDiet.template.gender;
            int playerGender = player.gender;
            Item newItem;
            if (trangbiHuyDiet.template.type == 4) {
                newItem = ItemService.gI().createNewItem((short) 12);
            } else {
                newItem = ItemService.gI().createNewItem(Manager.trangBiKichHoat[gender][trangbiHuyDiet.template.type]);
            }
            RewardService.gI().initBaseOptionClothes(newItem.template.id, newItem.template.type, newItem.itemOptions);
            int random = Util.nextInt(100);
            if (gender == 0 || (gender == 3 && playerGender == 0)) {
                newItem.itemOptions.add(new Item.ItemOption(127, 0));
                if (random < 40) {
                    newItem.itemOptions.add(new Item.ItemOption(139, 0));
                } else if (random < 80) {
                    newItem.itemOptions.add(new Item.ItemOption(140, 0));
                } else {
                    newItem.itemOptions.add(new Item.ItemOption(141, 0));
                }
            } else if (gender == 1 || (gender == 3 && playerGender == 1)) {
                newItem.itemOptions.add(new Item.ItemOption(130, 0));
                if (random < 60) {
                    newItem.itemOptions.add(new Item.ItemOption(144, 0));
                } else if (random < 80) {
                    newItem.itemOptions.add(new Item.ItemOption(143, 0));
                } else {
                    newItem.itemOptions.add(new Item.ItemOption(142, 0));
                }
            } else {
                newItem.itemOptions.add(new Item.ItemOption(133, 0));
                if (random < 60) {
                    newItem.itemOptions.add(new Item.ItemOption(137, 0));
                } else if (random < 80) {
                    newItem.itemOptions.add(new Item.ItemOption(136, 0));
                } else {
                    newItem.itemOptions.add(new Item.ItemOption(138, 0));
                }
            }
            InventoryService.gI().addItemBag(player, newItem);
            InventoryService.gI().subQuantityItemsBag(player, trangbiHuyDiet, 1);
            CombineService.gI().sendEffectSuccessCombine(player);
            InventoryService.gI().sendItemBag(player);
            Service.gI().sendMoney(player);
            CombineService.gI().reOpenItemCombine(player);
        }
    }
}
