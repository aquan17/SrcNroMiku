package models.Combine.manifest;

import consts.ConstFont;
import consts.ConstNpc;
import item.Item;
import models.Combine.CombineService;
import player.Player;
import services.InventoryService;
import services.ItemService;
import services.Service;
import utils.Util;

public class NangCapBongTai {

    public static void showInfoCombine(Player player) {
        if (player.combine.itemsCombine.size() != 2) {
            Service.gI().sendDialogMessage(player, "Cần 1 bông tai cấp 1 và 9999 mảnh vỡ bông tai.");
            return;
        }

        Item bongTai = null;
        Item manhVo = null;

        for (Item item : player.combine.itemsCombine) {
            if (item.template.id == 454 || item.template.id == 921) {
                bongTai = item;
            } else if (item.template.id == 933) {
                manhVo = item;
            }
        }

        if (bongTai == null || manhVo == null) {
            Service.gI().sendDialogMessage(player, "Cần 1 bông tai cấp 1 và 9999 mảnh vỡ bông tai.");
            return;
        }

        int nextLevel = bongTai.template.id == 454 ? 2 : (bongTai.template.id == 921 ? 3 : -1);
        if (nextLevel == -1) {
            Service.gI().sendDialogMessage(player, "Không thể nâng cấp loại bông tai này.");
            return;
        }

        int quantityManhVo = manhVo.getOptionParam(31);
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_BLUE).append("Bông tai Porata [+").append(nextLevel).append("]\n\n");
        text.append(ConstFont.BOLD_BLUE).append("Tỉ lệ thành công: 50%\n");
        text.append(quantityManhVo >= 9999 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Cần 9999 Mảnh vỡ bông tai\n");
        text.append(player.inventory.gold >= 5_000_000 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Cần 5 Tr vàng\n");
        text.append(player.inventory.getGemAndRuby() >= 20 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Cần 20 ngọc\n");
        text.append(ConstFont.BOLD_RED).append("Thất bại -99 mảnh vỡ bông tai\n");

        if (player.inventory.getGemAndRuby() < 20) {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(),
                    "Còn thiếu\n" + Util.numberToMoney(20 - player.inventory.getGemAndRuby()) + " ngọc");
            return;
        }

        if (player.inventory.gold < 5_000_000) {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(),
                    "Còn thiếu\n" + Util.numberToMoney(5_000_000 - player.inventory.gold) + " vàng");
            return;
        }

        if (quantityManhVo < 9999) {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(),
                    "Còn thiếu\n" + (9999 - quantityManhVo) + " Mảnh vỡ bông tai");
            return;
        }

        CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, text.toString(),
                "Nâng cấp\n5 Tr vàng\n20 ngọc", "Từ chối");
    }

    public static void nangCapBongTai(Player player) {
        if (player.combine.itemsCombine.size() != 2) {
            return;
        }

        Item bongTai = null;
        Item manhVo = null;

        for (Item item : player.combine.itemsCombine) {
            if (item.template.id == 454 || item.template.id == 921) {
                bongTai = item;
            } else if (item.template.id == 933) {
                manhVo = item;
            }
        }

        if (bongTai == null || manhVo == null) {
            return;
        }

        int quantityManhVo = manhVo.getOptionParam(31);
        if (quantityManhVo < 9999 || player.inventory.gold < 5_000_000 || player.inventory.getGemAndRuby() < 20) {
            return;
        }

        player.inventory.gold -= 5_000_000;
        player.inventory.subGemAndRuby(20);

        boolean success = Util.isTrue(50, 100);
        if (success) {
            short newItemId;
            int newOptionValue;

            if (bongTai.template.id == 454) {
                newItemId = 921;
                newOptionValue = 2;
            } else if (bongTai.template.id == 921) {
                newItemId = 1852;
                newOptionValue = 3;
            } else {
                return; // không hợp lệ
            }

            Item newItem = ItemService.gI().createNewItem(newItemId);
            newItem.itemOptions.add(new Item.ItemOption(72, newOptionValue));

            InventoryService.gI().subQuantityItemsBag(player, bongTai, 1);
            InventoryService.gI().addItemBag(player, newItem);
            CombineService.gI().sendEffectSuccessCombine(player);
            InventoryService.gI().subParamItemsBag(player, 933, 31, 9999);
        } else {
            CombineService.gI().sendEffectFailCombine(player);
            InventoryService.gI().subParamItemsBag(player, 933, 31, 99);
        }

        InventoryService.gI().sendItemBag(player);
        Service.gI().sendMoney(player);
        CombineService.gI().reOpenItemCombine(player);
    }
}
