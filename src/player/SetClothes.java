package player;

/*
 *
 *
 * @author HBT
 */
import data.ItemData;
import item.Item;
import services.InventoryService;

public class SetClothes {

    private Player player;

    public SetClothes(Player player) {
        this.player = player;
    }

    public byte songoku;
    public byte thienXinHang;
    public byte kirin;

    public byte ocTieu;
    public byte pikkoroDaimao;
    public byte picolo;

    public byte kakarot;
    public byte cadic;
    public byte nappa;

    public byte thanVuTruKaio;
    public byte nail;
    public byte cadicM;

    public byte worldcup;
    public byte setDHD;

    public boolean godClothes;
    public int ctHaiTac = -1;

    public void setup() {
        setDefault();
        setupSKT();
        this.godClothes = true;
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id > 567 || item.template.id < 555) {
                    this.godClothes = false;
                    break;
                }
            } else {
                this.godClothes = false;
                break;
            }
        }
        Item ct = this.player.inventory.itemsBody.get(5);
        if (ct.isNotNullItem()) {
            switch (ct.template.id) {
                case 618:
                case 619:
                case 620:
                case 621:
                case 622:
                case 623:
                case 624:
                case 626:
                case 628:
                    this.ctHaiTac = ct.template.id;
                    break;

            }
        }
    }

    private void setupSKT() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                boolean isActSet = false;
                for (Item.ItemOption io : item.itemOptions) {
                    switch (io.optionTemplate.id) {
                        case 129:
                        case 141:
                            isActSet = true;
                            songoku++;
                            break;
                        case 127:
                        case 139:
                            isActSet = true;
                            thienXinHang++;
                            break;
                        case 128:
                        case 140:
                            isActSet = true;
                            kirin++;
                            break;
                        case 131:
                        case 143:
                            isActSet = true;
                            ocTieu++;
                            break;
                        case 132:
                        case 144:
                            isActSet = true;
                            pikkoroDaimao++;
                            break;
                        case 130:
                        case 142:
                            isActSet = true;
                            picolo++;
                            break;
                        case 135:
                        case 138:
                            isActSet = true;
                            nappa++;
                            break;
                        case 133:
                        case 136:
                            isActSet = true;
                            kakarot++;
                            break;
                        case 134:
                        case 137:
                            isActSet = true;
                            cadic++;
                            break;
                        case 237, 238, 239, 240:
                            isActSet = true;
                            nail++;
                            break;
                        case 241, 242, 243, 244:
                            isActSet = true;
                            cadicM++;
                            break;
                        case 245, 246, 247, 248:
                            isActSet = true;
                            thanVuTruKaio++;
                            break;
                        case 21:
                            if (io.param == 80) {
                                setDHD++;
                            }
                            break;
                    }

                    if (isActSet) {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        if (kakarot == 5 || cadic == 5 || nappa == 5 || thienXinHang == 5 || kirin == 5 || songoku == 5 || picolo == 5 || ocTieu == 5 || pikkoroDaimao == 5) {
            ItemData.updateItemOptionItemplate2(player);
            InventoryService.gI().sendItemBag(player);
            InventoryService.gI().sendItemBody(player);
        }
    }

    public boolean isActivedSet(Player player, int id) {
        switch (id) {
            case 136 -> {
                if (player.setClothes.kakarot == 5) {
                    return true;
                }
            }
            case 137 -> {
                if (player.setClothes.cadic == 5) {
                    return true;
                }
            }
            case 138 -> {
                if (player.setClothes.nappa == 5) {
                    return true;
                }
            }
            case 139 -> {
                if (player.setClothes.thienXinHang == 5) {
                    return true;
                }
            }
            case 140 -> {
                if (player.setClothes.kirin == 5) {
                    return true;
                }
            }
            case 141 -> {
                if (player.setClothes.songoku == 5) {
                    return true;
                }
            }
            case 142 -> {
                if (player.setClothes.picolo == 5) {
                    return true;
                }
            }
            case 143 -> {
                if (player.setClothes.ocTieu == 5) {
                    return true;
                }
            }
            case 144 -> {
                if (player.setClothes.pikkoroDaimao == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setDefault() {
        this.songoku = 0;
        this.thienXinHang = 0;
        this.kirin = 0;
        this.ocTieu = 0;
        this.pikkoroDaimao = 0;
        this.picolo = 0;
        this.kakarot = 0;
        this.cadic = 0;
        this.nappa = 0;
        this.setDHD = 0;
        this.nail = 0;
        this.cadicM = 0;
        this.thanVuTruKaio = 0;
        this.worldcup = 0;
        this.godClothes = false;
        this.ctHaiTac = -1;
    }

    public boolean checkSetGod() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id < 555 || item.template.id > 567) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean checkSetDes() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id < 650 || item.template.id > 662) {

                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public void dispose() {
        this.player = null;
    }
}
