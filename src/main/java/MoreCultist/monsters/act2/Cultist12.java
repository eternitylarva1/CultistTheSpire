package MoreCultist.monsters.act2;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import java.util.ArrayList;

public class Cultist12 extends AbstractMonster {
    public static final String ID = "Champ";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final int HP = 420;
    public static final int A_9_HP = 440;
    private static final byte HEAVY_SLASH = 1;
    private static final byte DEFENSIVE_STANCE = 2;
    private static final byte EXECUTE = 3;
    private static final byte FACE_SLAP = 4;
    private static final byte GLOAT = 5;
    private static final byte TAUNT = 6;
    private static final byte ANGER = 7;
    private static final String STANCE_NAME;
    private static final String EXECUTE_NAME;
    private static final String SLAP_NAME;
    public static final int SLASH_DMG = 16;
    public static final int EXECUTE_DMG = 10;
    public static final int SLAP_DMG = 12;
    public static final int A_2_SLASH_DMG = 18;
    public static final int A_2_SLAP_DMG = 14;
    private int slashDmg;
    private int executeDmg;
    private int slapDmg;
    private int blockAmt;
    private static final int DEBUFF_AMT = 2;
    private static final int EXEC_COUNT = 2;
    private static final int FORGE_AMT = 5;
    private static final int BLOCK_AMT = 15;
    private static final int A_9_FORGE_AMT = 6;
    private static final int A_9_BLOCK_AMT = 18;
    private static final int A_19_FORGE_AMT = 7;
    private static final int A_19_BLOCK_AMT = 20;
    private static final int STR_AMT = 2;
    private static final int A_4_STR_AMT = 3;
    private static final int A_19_STR_AMT = 4;
    private int strAmt;
    private int forgeAmt;
    private int numTurns = 0;
    private int forgeTimes = 0;
    private int forgeThreshold = 2;
    private boolean thresholdReached = false;
    private boolean firstTurn = true;

    public
    Cultist12() {
        super(NAME, "Champ", 420, -8.0F, 10.0F, 230.0F, 240.0F, (String)null, -90.0F, 0.0F);
        this.name="Champ邪教徒";
        this.type = EnemyType.BOSS;
        this.dialogX = -100.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.loadAnimation("images/monsters/theBottom/cultist12/skeleton.atlas", "images/monsters/theBottom/cultist12/skeleton37.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "waving", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(0.8F);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(220);
        } else {
            this.setHp(220);
        }


            this.slashDmg = 6;
            this.executeDmg = 3;
            this.slapDmg = 12;
            this.strAmt = 2;
            this.forgeAmt = 2;
            this.blockAmt = 3;


        this.damage.add(new DamageInfo(this, this.slashDmg));
        this.damage.add(new DamageInfo(this, this.executeDmg));
        this.damage.add(new DamageInfo(this, this.slapDmg));
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");
        UnlockTracker.markBossAsSeen("CHAMP");
    }

    public void takeTurn() {
        float vfxSpeed = 0.1F;
        if (Settings.FAST_MODE) {
            vfxSpeed = 0.0F;
        }

        if (this.firstTurn) {
            this.firstTurn = false;
            if (AbstractDungeon.player.hasRelic("Champion Belt")) {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[8], 0.5F, 2.0F));
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new RitualPower(this,5,false)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, this.forgeAmt), this.forgeAmt));
            return;
        }

        switch (this.nextMove) {
            case 1://普通攻击
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, false), vfxSpeed));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this,this,StrengthPower.POWER_ID,2));
                break;
            case 2://防御
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, this.forgeAmt), this.forgeAmt));
                break;
            case 3://处决
                AbstractDungeon.actionManager.addToBottom(new AnimateJumpAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, true), vfxSpeed));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX + 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, true), vfxSpeed));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.NONE));
                break;
            case 4://重击
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_CHAMP_SLAP"));
                AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(2), AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this,this,RitualPower.POWER_ID,1));

                break;
            case 5://强化
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.strAmt), this.strAmt));
                break;
            case 6://嘲讽
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CHAMP_2A"));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, "Kakaa!"));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                break;
            case 7://惹火
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_CHAMP_CHARGE"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, "突破极限！Kakaa！", 2.0F, 3.0F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
                AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Shackled"));
                if(this.hasPower(StrengthPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.getPower(StrengthPower.POWER_ID).amount), this.getPower(StrengthPower.POWER_ID).amount));
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void changeState(String key) {
        switch (key) {
            case "ATTACK":
                this.state.setAnimation(0, "rally", false);
                this.state.addAnimation(0, "rally", true, 0.0F);
            default:
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "waving", false);
            this.state.addAnimation(0, "waving", true, 0.0F);
        }

    }

    private String getTaunt() {
        ArrayList<String> derp = new ArrayList();
        derp.add(DIALOG[0]);
        derp.add(DIALOG[1]);
        derp.add(DIALOG[2]);
        derp.add(DIALOG[3]);
        return (String)derp.get(MathUtils.random(derp.size() - 1));
    }

    private String getLimitBreak() {
        ArrayList<String> derp = new ArrayList();
        derp.add(DIALOG[4]);
        derp.add(DIALOG[5]);
        return (String)derp.get(MathUtils.random(derp.size() - 1));
    }

    private String getDeathQuote() {
        ArrayList<String> derp = new ArrayList();
        derp.add(DIALOG[6]);
        derp.add(DIALOG[7]);
        return (String)derp.get(MathUtils.random(derp.size() - 1));
    }

    protected void getMove(int num) {
        if (this.firstTurn) {
            this.setMove((byte)7, Intent.BUFF);
        }
        ++this.numTurns;
        if (this.currentHealth < this.maxHealth / 2 && !this.thresholdReached) {
            this.thresholdReached = true;
            this.setMove((byte)7, Intent.BUFF);
        } else if (!this.lastMove((byte)3) && !this.lastMoveBefore((byte)3) && this.thresholdReached) {
            AbstractDungeon.actionManager.addToTop(new TalkAction(this, this.getDeathQuote(), 2.0F, 2.0F));
            this.setMove(EXECUTE_NAME, (byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, 2, true);
        } else if (this.numTurns == 4 && !this.thresholdReached) {
            this.setMove((byte)6, Intent.DEBUFF);
            this.numTurns = 0;
        } else {
            if (AbstractDungeon.ascensionLevel >= 19) {
                if (!this.lastMove((byte)2) && this.forgeTimes < this.forgeThreshold && num <= 30) {
                    ++this.forgeTimes;
                    this.setMove(STANCE_NAME, (byte)2, Intent.DEFEND_BUFF);
                    return;
                }
            } else if (!this.lastMove((byte)2) && this.forgeTimes < this.forgeThreshold && num <= 15) {
                ++this.forgeTimes;
                this.setMove(STANCE_NAME, (byte)2, Intent.DEFEND_BUFF);
                return;
            }

            if (!this.lastMove((byte)5) && !this.lastMove((byte)2) && num <= 30) {
                this.setMove((byte)5, Intent.BUFF);
            } else if (!this.lastMove((byte)4) && num <= 55) {
                this.setMove(SLAP_NAME, (byte)4, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(2)).base);
            } else {
                if (!this.lastMove((byte)1)) {
                    this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                } else {
                    this.setMove(SLAP_NAME, (byte)4, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(2)).base);
                }

            }
        }
    }

    public void die() {
        this.useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play("VO_CHAMP_3A");
        } else {
            CardCrawlGame.sound.play("VO_CHAMP_3B");
        }

        AbstractDungeon.scene.fadeInAmbiance();
        this.onBossVictoryLogic();
        UnlockTracker.hardUnlockOverride("CHAMP");
        UnlockTracker.unlockAchievement("CHAMP");
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Champ");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        STANCE_NAME = MOVES[0];
        EXECUTE_NAME = MOVES[1];
        SLAP_NAME = MOVES[2];
    }
}
