package npc.npc_manifest;

/**
 * @author HBT
 */
import consts.ConstDailyGift;
import consts.ConstNpc;
import consts.ConstTask;
import consts.ConstTaskBadges;
import item.Item;

import java.util.ArrayList;
import java.util.List;

import jdbc.daos.PlayerDAO;
import npc.Npc;
import player.Player;
import player.dailyGift.DailyGiftService;
import services.InventoryService;
import services.ItemService;
import services.NpcService;
import services.PetService;
import services.Service;
import services.TaskService;
import services.func.Input;
import shop.ShopService;
import task.Badges.BadgesTaskService;
import utils.Util;

public class OngGohan extends Npc {

    public OngGohan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    int costNapVang = 1;

    int[][] napVang = {{20000, 40}, {50000, 105}, {100000, 250}, {500000, 1500}, {1000000, 3100},
    {2000000, 6500}, {5000000, 17000}};

    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player)) {
            if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                if (DailyGiftService.checkDailyGift(player, ConstDailyGift.NHAN_NGOC_XANH)) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Con cần ta giúp gì nào",
                            "Nạp tiền",
                            "Nhận ngọc\nMiễn phí",
                            "Hỗ trợ\nNhiệm vụ",
                            "Hộp thư",
                            "Mở Thành Viên",
                            "Từ chối");
                } else {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Con cần ta giúp gì nào",
                            "Nạp tiền",
                            "Hỗ trợ\nNhiệm vụ",
                            "Hộp thư",
                            "Mở Thành Viên",
                            "Từ chối");
                }
            }
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {
            if (player.iDMark.isBaseMenu()) {
                if (DailyGiftService.checkDailyGift(player, ConstDailyGift.NHAN_NGOC_XANH)) {
                    switch (select) {
                        case 0:
                            String npcSay = "Số dư của con là: " + Util.mumberToLouis(player.getSession().cash)
                                    + " VND dùng để nạp qua đơn vị khác\n"
                                    + "Ta đang giữ giúp con " + Util.mumberToLouis(player.getSession().goldBar)
                                    + " thỏi vàng";
                            createOtherMenu(player, ConstNpc.NAP_TIEN, npcSay,
                                    "Nạp vàng",
                                    "Nhận\nThỏi vàng",
                                    "Đóng");
                            break;
                        case 1:// quà
                            int soLuongNgoc = 100;
                            player.inventory.gem += soLuongNgoc;
                            Service.gI().sendMoney(player);
                            DailyGiftService.updateDailyGift(player, ConstDailyGift.NHAN_NGOC_XANH);
                            Service.gI().sendThongBao(player,
                                    "Bạn vừa nhận " + Util.mumberToLouis(soLuongNgoc) + " ngọc xanh");
                            break;
                        case 2:
                            if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_9_0
                                    && TaskService.gI().getIdTask(player) < ConstTask.TASK_11_0) {
                                player.playerTask.taskMain.id = 10;
                                player.playerTask.taskMain.index = 0;
                                TaskService.gI().sendNextTaskMain(player);
                                Service.gI().sendThongBao(player, "Bạn đã được hỗ trợ nhiệm vụ thành công");
                            } else if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_18_0
                                    && TaskService.gI().getIdTask(player) < ConstTask.TASK_20_0) {
                                player.playerTask.taskMain.id = 19;
                                player.playerTask.taskMain.index = 0;
                                TaskService.gI().sendNextTaskMain(player);
                                Service.gI().sendThongBao(player, "Bạn đã được hỗ trợ nhiệm vụ thành công");
                            } else {
                                Service.gI().sendThongBao(player,
                                        "Chỉ hỗ trợ nhiệm vụ tàu pảy pảy và nhiệm vụ DHVT, Trung úy trắng");
                            }
                            break;
                        case 3:
                            this.createOtherMenu(player, ConstNpc.MAIL_BOX,
                                    "|0|Tình yêu như một dây đàn\n"
                                    + "Tình vừa được thì đàn đứt dây\n"
                                    + "Đứt dây này anh thay dây khác\n"
                                    + "Mất em rồi anh biết thay ai?",
                                    "Hòm Thư\n(" + (player.inventory.itemsMailBox.size()
                                    - InventoryService.gI().getCountEmptyListItem(player.inventory.itemsMailBox))
                                    + " món)",
                                    "Xóa Hết\nHòm Thư", "Đóng");
                            break;
                        case 4:
                            this.createOtherMenu(player, ConstNpc.MTV, "Mở Thành Viên"
                                    + "\n|2|Số tiền hiện tại : " + Util.numberFormatLouis(player.getSession().cash)
                                    + " VNĐ", "MTV\n[10.000]", " MTV Free");
                            break;
                    }
                } else {
                    switch (select) {
                        case 0:
                            String npcSay = "Số dư của con là: " + Util.mumberToLouis(player.getSession().cash)
                                    + " VND dùng để nạp qua đơn vị khác\n"
                                    + "Ta đang giữ giúp con " + Util.mumberToLouis(player.getSession().goldBar)
                                    + " thỏi vàng";
                            createOtherMenu(player, ConstNpc.NAP_TIEN, npcSay,
                                    "Nạp vàng",
                                    "Nhận\nThỏi vàng",
                                    "Đóng");
                            break;
                        case 1:// quà
                            if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_9_0
                                    && TaskService.gI().getIdTask(player) < ConstTask.TASK_11_0) {
                                player.playerTask.taskMain.id = 10;
                                player.playerTask.taskMain.index = 0;
                                TaskService.gI().sendNextTaskMain(player);
                                Service.gI().sendThongBao(player, "Bạn đã được hỗ trợ nhiệm vụ thành công");
                            } else if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_18_0
                                    && TaskService.gI().getIdTask(player) < ConstTask.TASK_20_0) {
                                player.playerTask.taskMain.id = 19;
                                player.playerTask.taskMain.index = 0;
                                TaskService.gI().sendNextTaskMain(player);
                                Service.gI().sendThongBao(player, "Bạn đã được hỗ trợ nhiệm vụ thành công");
                            } else {
                                Service.gI().sendThongBao(player,
                                        "Chỉ hỗ trợ nhiệm vụ tàu pảy pảy và nhiệm vụ DHVT, Trung úy trắng");
                            }
                            break;
                        case 2:
                            this.createOtherMenu(player, ConstNpc.MAIL_BOX,
                                    "|0|Tình yêu như một dây đàn\n"
                                    + "Tình vừa được thì đàn đứt dây\n"
                                    + "Đứt dây này anh thay dây khác\n"
                                    + "Mất em rồi anh biết thay ai?",
                                    "Hòm Thư\n(" + (player.inventory.itemsMailBox.size()
                                    - InventoryService.gI().getCountEmptyListItem(player.inventory.itemsMailBox))
                                    + " món)",
                                    "Xóa Hết\nHòm Thư", "Đóng");
                            break;
                        case 3:
                            this.createOtherMenu(player, ConstNpc.MTV, "Mở Thành Viên"
                                    + "\n|2|Số tiền hiện tại : " + Util.numberFormatLouis(player.getSession().cash)
                                    + " VNĐ", "MTV\n[10.000]", " MTV Free");
                            break;
                    }
                }
            } else if (player.iDMark.getIndexMenu() == ConstNpc.MTV) {
                switch (select) {
                    case 0:
                        if (!player.getSession().actived) {
                            if (player.getSession().cash >= 10000) {
                                player.getSession().actived = true;
                                if (PlayerDAO.subcashactive(player, 10000))
                                    ;
                                Service.gI().sendMoney(player);
                                Service.gI().sendThongBao(player, "|7|Kích hoạt thành công");
                            } else {
                                this.npcChat(player, "Không Đủ Tiền Mở Thành Viên...!");
                            }
                        } else {
                            this.npcChat(player, "Bạn đã mở thành viên rồi!");
                        }
                        break;
                    case 1:
                        if (!player.getSession().actived) {
                            if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_28_0) {
                                player.getSession().actived = true;
                                if (PlayerDAO.subcashactive(player, 0));
                                Service.gI().sendMoney(player);
                                Service.gI().sendThongBao(player, "|7|Kích hoạt thành công");
                            } else {
                                this.npcChat(player, "Bạn Phải Tới Nhiệm Vụ 28 Mở Thành Viên...!");
                            }
                        } else {
                            this.npcChat(player, "Bạn đã mở thành viên rồi!");
                        }
                        break;
                }
            } else if (player.iDMark.getIndexMenu() == ConstNpc.MAIL_BOX) {
                switch (select) {
                    case 0:
                        // player.inventory.itemsMailBox.clear();
                        // player.inventory.itemsMailBox.addAll(GodGK.getMailBox(player));
                        ShopService.gI().opendShop(player, "ITEMS_MAIL_BOX", true);
                        break;
                    case 1:
                        NpcService.gI().createMenuConMeo(player,
                                ConstNpc.CONFIRM_REMOVE_ALL_ITEM_MAIL_BOX, this.avartar,
                                "|3|Bạn chắc muốn xóa hết vật phẩm trong hòm thư?\n"
                                + "|7|Sau khi xóa sẽ không thể khôi phục!",
                                "Đồng ý", "Hủy bỏ");
                        break;
                    case 2:
                        break;
                }
            } else if (player.iDMark.getIndexMenu() == ConstNpc.NAP_TIEN) {
                switch (select) {
                    case 0:
                        List<String> menu = new ArrayList<>();
                        for (int i = 0; i < napVang.length; i++) {
                            menu.add(i, Util.mumberToLouis(napVang[i][0]) + "\n"
                                    + Util.mumberToLouis(napVang[i][1] * costNapVang) + " Thỏi\nvàng");
                        }
                        String[] menus = menu.toArray(new String[0]);
                        createOtherMenu(player, ConstNpc.NAP_VANG, "Ta sẽ giữ giúp con\n"
                                + "Nếu con cần dùng tới hãy quay lại đây gặp ta!", menus);
                        break;
                    case 1:
                        if (player.getSession().goldBar > 0) {
                            List<Item> listItem = new ArrayList<>();
                            Item thoiVang = ItemService.gI().createNewItem((short) 457, player.getSession().goldBar);
                            listItem.add(thoiVang);
                            if (InventoryService.gI().getCountEmptyBag(player) < listItem.size()) {
                                Service.gI().sendThongBao(player,
                                        "Cần ít nhất " + listItem.size() + " ô trống trong hành trang");
                            }
                            for (Item it : listItem) {
                                InventoryService.gI().addItemBag(player, it);
                                InventoryService.gI().sendItemBag(player);
                            }
                            Service.gI().sendThongBao(player,
                                    "Bạn đã nhận được " + player.getSession().goldBar + " thỏi vàng");
                            PlayerDAO.subGoldBar(player, -(napVang[select][1] * costNapVang));
                            PlayerDAO.subGoldBar(player, player.getSession().goldBar);
                        }
                        break;
                }
            } else if (player.iDMark.getIndexMenu() == ConstNpc.NAP_VANG) {
                if (player.getSession().cash >= napVang[select][0]) {
                    List<Item> listItem = new ArrayList<>();
                    if (InventoryService.gI().getCountEmptyBag(player) < listItem.size()) {
                        Service.gI().sendThongBao(player,
                                "Cần ít nhất " + listItem.size() + " ô trống trong hành trang");
                    }
                    for (Item it : listItem) {
                        InventoryService.gI().addItemBag(player, it);
                        InventoryService.gI().sendItemBag(player);
                    }
                    PlayerDAO.subcash(player, napVang[select][0]);
                    BadgesTaskService.updateCountBagesTask(player, ConstTaskBadges.DAI_GIA_MOI_NHU, napVang[select][0]);
                    PlayerDAO.subGoldBar(player, -(napVang[select][1] * costNapVang));
                    Service.gI().sendThongBao(player,
                            "Bạn có thêm " + Util.mumberToLouis(napVang[select][1] * costNapVang) + " thỏi vàng");
                } else {
                    Service.gI().sendThongBao(player, "Không đủ số dư");
                }
            }
        }
    }
}
