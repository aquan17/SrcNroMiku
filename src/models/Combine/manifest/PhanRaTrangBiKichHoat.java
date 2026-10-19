/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Combine.manifest;

import consts.ConstNpc;
import item.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class PhanRaTrangBiKichHoat {

    public static void showInfoCombine(Player player) {
        if (player.combine.itemsCombine.size() == 0) {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 1 trang bị kích hoạt", "Đóng");
            return;
        }
        if (player.combine.itemsCombine.size() == 1) {
            Item item = player.combine.itemsCombine.get(0);
            if (item.isNotNullItem() && item.isSKH()) {
                String npcSay = "Con có chắc muốn rã 1 trang bị kích hoạt và nhận 1 [Khoáng tái chế] ?";

                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                        npcSay, "Đồng ý", "Từ chối");
            } else {
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 1 trang bị kích hoạt", "Đóng");
            }
        } else {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 1 trang bị kích hoạt", "Đóng");
        }
    }

    public static void startCombine(Player player) {
        if (player.combine.itemsCombine.size() == 1) {
            Item item = player.combine.itemsCombine.get(0);
            if (item.isNotNullItem() && item.isSKH()) {
                CombineService.gI().sendEffectSuccessCombine(player);
                Item khoang = ItemService.gI().createNewItem((short) 1656);
                khoang.itemOptions.add(new Item.ItemOption(30, 0));
                InventoryService.gI().addItemBag(player, khoang);
                InventoryService.gI().sendItemBag(player);
                Service.gI().sendThongBaoOK(player, "Bạn nhận được 1 Khoáng Tái Chế");
                InventoryService.gI().subQuantityItemsBag(player, item, 1);
                player.combine.itemsCombine.clear();
                InventoryService.gI().sendItemBag(player);
                Service.gI().sendMoney(player);
                CombineService.gI().reOpenItemCombine(player);
            } else {
                CombineService.gI().baHatMit.npcChat(player, "Vật phẩm không hợp lệ để phân rã");
            }
        }
    }
}
