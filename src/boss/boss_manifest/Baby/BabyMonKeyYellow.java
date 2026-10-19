/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boss.boss_manifest.Baby;

import boss.Boss;
import boss.BossID;
import boss.BossStatus;
import static boss.BossType.BABY;
import boss.BossesData;
import item.Item;
import java.util.Random;
import map.ItemMap;
import player.Player;
import server.Manager;
import services.EffectSkillService;
import services.Service;
import services.TaskService;
import services.func.ChangeMapService;
import utils.Util;

/**
 *
 * @author Administrator
 */
public class BabyMonKeyYellow extends Boss {

    private long st;

    public BabyMonKeyYellow() throws Exception {
        super(BABY, BossID.BABY_MONKEY, BossesData.BABY_MONKEY);
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
                    this.zone.map.yPhysicInTop(this.location.x,
                            this.location.y - 24),
                    playerReward.id);
            it.options.add(new Item.ItemOption(50, 28));
            it.options.add(new Item.ItemOption(77, 28));
            it.options.add(new Item.ItemOption(103, 28));
            it.options.add(new Item.ItemOption(94, 12));
            it.options.add(new Item.ItemOption(5, 12));
            it.options.add(new Item.ItemOption(204, 17));
        }
    }

    @Override
    public void joinMap() {
        st = System.currentTimeMillis();
        this.zone = this.parentBoss.zone;
        ChangeMapService.gI().changeMap(this, this.zone,
                this.parentBoss.location.x + Util.nextInt(-100, 100), this.parentBoss.location.y);
        Service.gI().sendFlagBag(this);
        this.notifyJoinMap();
        this.changeStatus(BossStatus.CHAT_S);
    }

    @Override
    public void doneChatE() {
        if (this.parentBoss == null || this.parentBoss.bossAppearTogether == null
                || this.parentBoss.bossAppearTogether[this.parentBoss.currentLevel] == null) {
            return;
        }
        this.parentBoss.changeStatus(BossStatus.ACTIVE);
    }

    @Override
    public void leaveMap() {
        ChangeMapService.gI().exitMap(this);
        this.lastZone = null;
        this.lastTimeRest = System.currentTimeMillis();
        this.changeStatus(BossStatus.REST);
//        BossManager.gI().removeBoss(this);
    }

    @Override
    public void autoLeaveMap() {
        if (Util.canDoWithTime(st, 900000)) {
            this.leaveMapNew();
        }
        if (this.zone != null && this.zone.getNumOfPlayers() > 0) {
            st = System.currentTimeMillis();
        }
    }

    @Override
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {

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
