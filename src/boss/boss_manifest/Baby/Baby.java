package boss.boss_manifest.Baby;

import boss.Boss;
import boss.BossID;
import boss.BossStatus;
import static boss.BossType.BABY;
import boss.BossesData;
import consts.ConstPlayer;
import item.Item;
import java.util.Random;
import map.ItemMap;
import player.Player;
import server.Manager;
import services.EffectSkillService;
import services.PlayerService;
import services.Service;
import services.TaskService;
import services.func.ChangeMapService;
import utils.Util;

/**
 *
 * @author Administrator
 */
public class Baby extends Boss {

    public boolean callBaby;

    public Baby() throws Exception {
        super(BABY, BossID.BABY, BossesData.BABY, BossesData.BABY_VEGETA);
    }

    @Override
    protected void resetBase() {
        super.resetBase();
        this.callBaby = false;
    }

    public void callBaby() {
        try {
            this.changeStatus(BossStatus.AFK);
            this.changeToTypeNonPK();
            this.recoverHP();
            this.callBaby = true;
            for (Boss boss : this.bossAppearTogether[this.currentLevel]) {
                if (boss.id == BossID.BABY_MONKEY) {
                    boss.changeStatus(BossStatus.RESPAWN);
                }
            }
            this.setDie(this);
            this.die(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recoverHP() {
        PlayerService.gI().hoiPhuc(this, this.nPoint.hpMax, 0);
    }

    @Override
    public void reward(Player plKill) {
        plKill.effect.addPointTrumSanBoss();
        plKill.playerTask.kolTask.addCount();
        byte randomDo = (byte) new Random().nextInt(Manager.itemIds_tl_GN.length - 1);
        byte randomDo1 = (byte) new Random().nextInt(Manager.itemIds_tl_AWJ.length - 1);
        if (Util.isTrue(8, 30)) {
            Service.gI().dropItemMap(this.zone,
                    Util.ratiDTL(zone, Manager.itemIds_tl_GN[randomDo], 1, this.location.x, this.location.y, plKill.id));
        } else if (Util.isTrue(15, 50)) {
            Service.gI().dropItemMap(this.zone,
                    Util.ratiDTL(zone, Manager.itemIds_tl_AWJ[randomDo1], 1, this.location.x, this.location.y, plKill.id));
        } else if (Util.isTrue(15, 100)) {
            ItemMap it = new ItemMap(this.zone, 1763, 1, this.location.x,
                    this.zone.map.yPhysicInTop(this.location.x, this.location.y), playerReward.id);
            it.options.add(new Item.ItemOption(50, 28));
            it.options.add(new Item.ItemOption(77, 28));
            it.options.add(new Item.ItemOption(103, 28));
            it.options.add(new Item.ItemOption(94, 12));
            it.options.add(new Item.ItemOption(5, 12));
            it.options.add(new Item.ItemOption(204, 17));
        }
    }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.attack();
    }

    @Override
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.callBaby && this.currentLevel == 1 && damage >= this.nPoint.hp && Util.isTrue(50, 100)) {
            this.callBaby();
            return 0;
        }
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage / 7);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage / 7;
            }

            this.nPoint.subHP(damage);

            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }

            return (int) damage;
        } else {
            return 0;
        }
    }

}
