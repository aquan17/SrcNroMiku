/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Combine.manifest;

import consts.ConstNpc;
import item.Item;
import models.Combine.CombineService;
import player.Player;
import services.InventoryService;
import services.ItemService;
import services.Service;
import utils.Util;

/**
 *
 * @author Administrator
 */
public class NangCapDoHuyDiet {

    public static void showInfoCombine(Player player) {
        if (player.combine.itemsCombine.size() == 0) {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Hãy đưa ta 1 món thần linh, ta sẽ cho 1 món huỷ diệt tương ứng", "Đóng");
            return;
        }
        if (player.combine.itemsCombine.size() == 1) {
            if (player.combine.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTL()).count() != 1) {
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                        "Thiếu đồ thần linh rồi", "Đóng");
                return;
            }
            String npcSay = "|2|Con có muốn đổi các món nguyên liệu ?\n|7|"
                    + "Và nhận được " + player.combine.itemsCombine.stream().filter(Item::isDTL).findFirst().get().typeName() + " Huỷ diệt tương ứng\n"
                    + "|1|Cần " + Util.numberToMoney(500000000) + " vàng";

            if (player.inventory.gold < 500000000) {
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                        "Hết tiền rồi\nẢo ít thôi con", "Đóng");
                return;
            }

            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                    npcSay, "Nâng cấp\n" + Util.numberToMoney(500000000) + " vàng", "Từ chối");
        } else {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Nguyên liệu không phù hợp", "Đóng");
        }
    }

    public static void startCombine(Player player) {
        if (player.combine.itemsCombine.size() == 1) {
            Item dtl = player.combine.itemsCombine.get(0);
            if (dtl != null && dtl.isDTL()) {
                if (InventoryService.gI().getCountEmptyBag(player) > 0
                        && player.inventory.gold >= 500000000) {
                    player.inventory.gold -= 500000000;
                    int tiLe = 100;

                    if (Util.isTrue(tiLe, 100)) {
                        CombineService.gI().sendEffectSuccessCombine(player);
                        int usedGender = dtl.template.id == 561 ? player.gender : dtl.template.gender;
                        ItemService.gI().OpenDHD2(player, usedGender, dtl.template.type);
                        InventoryService.gI().subQuantityItemsBag(player, dtl, 1);
                    } else {
                        CombineService.gI().sendEffectFailCombine(player);
                    }

                    InventoryService.gI().sendItemBag(player);
                    Service.gI().sendMoney(player);
                    CombineService.gI().reOpenItemCombine(player);
                }
            }
        }
    }
}
