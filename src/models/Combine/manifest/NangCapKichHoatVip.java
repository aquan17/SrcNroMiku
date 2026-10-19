package models.Combine.manifest;

import consts.ConstFont;
import consts.ConstNpc;
import item.Item;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
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
public class NangCapKichHoatVip {

    private static final int COST = 500000000;

    public static void showInfoCombine(Player player) {
        if (player.combine.itemsCombine.size() == 0) {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy đưa ta 3 món huỷ diệt....", "Đóng");
            return;
        }
        if (player.combine.itemsCombine.size() == 3) {
            if (player.combine.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDHD()).count() != 3) {
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ huỷ điệt rồi", "Đóng");
                return;
            }
            Item item1 = player.combine.itemsCombine.get(0);
            Item item2 = player.combine.itemsCombine.get(1);
            Item item3 = player.combine.itemsCombine.get(2);

            String npcSay = "|2|Con có muốn đổi các món nguyên liệu ?\n|7|"
                    + "Và nhận được " + player.combine.itemsCombine.stream().filter(Item::isDHD).findFirst().get().typeName() + " kích hoạt VIP tương ứng\n"
                    + "|1|Cần " + Util.numberToMoney(COST) + " vàng";

            if (player.inventory.gold < COST) {
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
                return;
            }
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                    npcSay, "Nâng cấp\n" + Util.numberToMoney(500000000) + " vàng", "Từ chối");
        } else {
            if (player.combine.itemsCombine.size() > 4) {
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Nguyên liệu không phù hợp", "Đóng");
                return;
            }
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
        }
    }

    public static void startCombine(Player player) {
        if (player.combine.itemsCombine.size() == 3) {
            Item item1 = player.combine.itemsCombine.get(0);
            Item item2 = player.combine.itemsCombine.get(1);
            Item item3 = player.combine.itemsCombine.get(2);
            if ((item1.isDHD() && item2.isDHD() && item3.isDHD())) {
                if (InventoryService.gI().getCountEmptyBag(player) > 0) {
                    if (player.inventory.gold < 500000000) {
                        Service.gI().sendThongBao(player, "Con cần thêm vàng để đổi...");
                        return;
                    }
                    player.inventory.gold -= 500000000;
                    int tile = 100; // tỉ lệ
                    if (Util.isTrue(tile, 100)) {
                        int type = item1.template.type;
                        int[][][] items = {{{0, 3, 33, 34, 136, 137, 138, 139, 230, 231, 232, 233, 555},// áo
                        {6, 9, 35, 36, 140, 141, 142, 143, 242, 243, 244, 245, 556}, // quần
                        {21, 24, 37, 38, 144, 145, 146, 147, 254, 255, 256, 257, 562}, // găng
                        {27, 30, 39, 40, 148, 149, 150, 151, 266, 267, 268, 269, 563}, // giày
                        {12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281, 561}}, // rada

                        {{1, 4, 41, 42, 152, 153, 154, 155, 234, 235, 236, 237, 557}, // nm
                        {7, 10, 43, 44, 156, 157, 158, 159, 246, 247, 248, 249, 558},
                        {22, 25, 45, 46, 160, 161, 162, 163, 258, 259, 260, 261, 564},
                        {28, 31, 47, 48, 164, 165, 166, 167, 270, 271, 272, 273, 565},
                        {12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281, 561}},
                        {{2, 5, 49, 50, 168, 169, 170, 171, 238, 239, 240, 241, 559}, // xd
                        {8, 11, 51, 52, 172, 173, 174, 175, 250, 251, 252, 253, 560},
                        {23, 26, 53, 54, 176, 177, 178, 179, 262, 263, 264, 265, 566},
                        {29, 32, 55, 56, 180, 181, 182, 183, 274, 275, 276, 277, 567},
                        {12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281, 561}}};
                        int[][] options = {{128, 129, 127}, {130, 131, 132}, {133, 135, 134}};
                        int skhv1 = 25;// ti le
                        int skhv2 = 35;//ti le
                        int skhc = 40;//ti le
                        int skhId = -1;
                        int rd = Util.nextInt(1, 100);
                        if (rd <= skhv1) {
                            skhId = 0;
                        } else if (rd <= skhv1 + skhv2) {
                            skhId = 1;
                        } else if (rd <= skhv1 + skhv2 + skhc) {
                            skhId = 2;
                        }
                        Item item = null;
                        switch (player.gender) {
                            case 0:
                                Random rand0 = new Random();
                                int i0 = rand0.nextInt(items.length);
                                int j0 = rand0.nextInt(items[0].length);
                                int k0 = rand0.nextInt(items[0][item1.template.type].length);
                                int randomThirdElement0 = items[0][item1.template.type][k0];
                                item = ItemService.gI().itemSKH(randomThirdElement0, options[0][skhId]);
                                break;
                            case 1:
                                Random rand1 = new Random();
                                int i1 = rand1.nextInt(items.length);
                                int j1 = rand1.nextInt(items[1].length);
                                int k1 = rand1.nextInt(items[1][item1.template.type].length);
                                int randomThirdElement1 = items[1][item1.template.type][k1];
                                item = ItemService.gI().itemSKH(randomThirdElement1, options[1][skhId]);
                                break;
                            case 2:
                                Random rand2 = new Random();
                                int i2 = rand2.nextInt(items.length);
                                int j2 = rand2.nextInt(items[2].length);
                                int k2 = rand2.nextInt(items[2][item1.template.type].length);
                                int randomThirdElement2 = items[2][item1.template.type][k2];
                                item = ItemService.gI().itemSKH(randomThirdElement2, options[2][skhId]);
                                break;
                        }
                        if (item != null) {
                            CombineService.gI().sendEffectSuccessCombine(player);
                            InventoryService.gI().addItemBag(player, item);
                            InventoryService.gI().sendItemBag(player);
                            Service.gI().sendThongBao(player, "Bạn đã nhận được " + item.template.name);
                            InventoryService.gI().subQuantityItemsBag(player, item1, 1);
                            InventoryService.gI().subQuantityItemsBag(player, item2, 1);
                            InventoryService.gI().subQuantityItemsBag(player, item3, 1);
                            InventoryService.gI().sendItemBag(player);
                        }
                    } else {
                        CombineService.gI().sendEffectFailCombine(player);
                    }
                    player.combine.itemsCombine.clear();
                    CombineService.gI().reOpenItemCombine(player);
                } else {
                    Service.gI().sendThongBao(player, "Cần 1 o tromg trong hanh trang");
                }
                InventoryService.gI().sendItemBag(player);
                Service.gI().sendMoney(player);

            } else {
                Service.gI().sendThongBao(player, "Cần 3 món huỷ diệt cùng loại");
            }
        }
    }
}
