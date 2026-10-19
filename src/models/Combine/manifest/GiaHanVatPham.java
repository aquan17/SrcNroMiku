/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Combine.manifest;

import consts.ConstNpc;
import item.Item;
import item.Item.ItemOption;
import java.util.Arrays;
import java.util.List;
import models.Combine.CombineService;
import player.Player;
import services.InventoryService;
import services.Service;
import utils.Util;

/**
 *
 * @author Administrator
 */
public class GiaHanVatPham {

    public static void showInfoCombine(Player player) {
        if (player.combine.itemsCombine.size() == 2) {
            Item daHacHoa = null;
            Item itemHacHoa = null;
            for (Item item_ : player.combine.itemsCombine) {
                if (item_.template != null) {
                    if (item_.template.id == 1853) {
                        daHacHoa = item_;
                    } else if (item_.isTrangBiHSD()) {
                        itemHacHoa = item_;
                    }
                }
            }
            if (daHacHoa == null || itemHacHoa == null) {
                Service.gI().sendThongBaoOK(player, "Cần 1 trang bị có hạn sử dụng và 1 phiếu Gia hạn");
                return;
            }
            boolean hasHSD = false;
            for (ItemOption itopt : itemHacHoa.itemOptions) {
                if (itopt.optionTemplate.id == 93) {
                    if (itopt.param >= 0) {
                        hasHSD = true;
                        break;
                    }
                }
            }

            if (!hasHSD) {
                Service.gI().sendThongBaoOK(player, "Trang bị này không phải trang bị có Hạn Sử Dụng");
                return;
            }

            String npcSay = "Trang bị được gia hạn \"" + itemHacHoa.template.name + "\"";
            npcSay += "\n|0|Sau khi gia hạn +1 ngày\n";
            npcSay += "|0|Tỉ lệ thành công: 100%\n";

            if (player.inventory.gold >= 200000000) {
                npcSay += "|2|Cần 200Tr vàng";
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Nâng cấp", "Từ chối");
            } else {
                int SoVangThieu2 = (int) (200000000 - player.inventory.gold);
                Service.gI().sendThongBaoOK(player, "Bạn còn thiếu " + SoVangThieu2 + " vàng");
            }
        }
    }

    public static void GiaHanVatPham(Player player) {
        if (player.combine.itemsCombine.size() != 2) {
            Service.gI().sendThongBao(player, "Thiếu nguyên liệu");
            return;
        }
        if (player.combine.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isTrangBiHSD()).count() != 1) {
            Service.gI().sendThongBao(player, "Thiếu trang bị HSD");
            return;
        }
        if (player.combine.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 1853).count() != 1) {
            Service.gI().sendThongBao(player, "Thiếu Bùa Gia Hạn");
            return;
        }
        if (InventoryService.gI().getCountEmptyBag(player) > 0) {
            Item daHacHoa = player.combine.itemsCombine.stream().filter(item -> item.template.id == 1853).findFirst().get();
            Item trangBiHSD = player.combine.itemsCombine.stream().filter(Item::isTrangBiHSD).findFirst().get();
            if (daHacHoa == null) {
                Service.gI().sendThongBao(player, "Thiếu Bùa Gia Hạn");
                return;
            }
            if (trangBiHSD == null) {
                Service.gI().sendThongBao(player, "Thiếu trang bị HSD");
                return;
            }

            if (trangBiHSD != null) {
                for (ItemOption itopt : trangBiHSD.itemOptions) {
                    if (itopt.optionTemplate.id == 93) {
                        if (itopt.param < 0 && itopt == null) {
                            Service.gI().sendThongBao(player, "Không Phải Trang Bị Có HSD");
                            return;
                        }
                    }
                }
            }
            if (Util.isTrue(100, 100)) {
                CombineService.gI().sendEffectSuccessCombine(player);
                List<Integer> idOptionHacHoa = Arrays.asList(219, 220, 221, 222);
                int randomOption = idOptionHacHoa.get(Util.nextInt(0, 3));

                for (ItemOption itopt : trangBiHSD.itemOptions) {
                    if (itopt.optionTemplate.id == 93) {
                        itopt.param += 1;
                        break;
                    }
                }
            } else {
                CombineService.gI().sendEffectFailCombine(player);
            }
            InventoryService.gI().subQuantityItemsBag(player, daHacHoa, 1);
            InventoryService.gI().sendItemBag(player);
            Service.gI().sendMoney(player);

        }
    }
}
