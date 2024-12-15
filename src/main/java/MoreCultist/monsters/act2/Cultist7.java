package MoreCultist.monsters.act2;



//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//




import MoreCultist.actions.AnimateRoatateAction;
import MoreCultist.actions.CultistEnterAction;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class Cultist7 extends AbstractMonster {
    public static final String ID = "Cultist";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String MURDER_ENCOUNTER_KEY = "Murder of Cultists";
    private static final String INCANTATION_NAME;
    private static final int HP_MIN = 48;
    private static final int HP_MAX = 54;
    private static final int A_2_HP_MIN = 50;
    private static final int A_2_HP_MAX = 56;
    private static final float HB_X = -8.0F;
    private static final float HB_Y = 10.0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;
    private static final int ATTACK_DMG = 6;
    private boolean firstMove;
    private boolean saidPower;
    private static final int RITUAL_AMT = 3;
    private static final int A_2_RITUAL_AMT = 4;
    private int ritualAmount;
    private static final byte DARK_STRIKE = 1;
    private static final byte INCANTATION = 3;
    private boolean talky;
    int movenum=0;
    private int position=1;

    public Cultist7(float x, float y, boolean talk) {
        super(NAME, "Cultist", 81, -8.0F, 10.0F, 230.0F, 240.0F, (String)null, x, y);
        this.name="MJ邪教徒";
        this.firstMove = true;
        this.saidPower = false;
        this.ritualAmount = 0;
        this.talky = true;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(81-5, 81);
        } else {
            this.setHp(81-5, 81);
        }
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.ritualAmount = 4;
        } else {
            this.ritualAmount = 3;
        }

        this.damage.add(new DamageInfo(this, 6));
        this.talky = talk;
        if (Settings.FAST_MODE) {
            this.talky = false;
        }
        this.flipHorizontal=false;
        this.loadAnimation("images/monsters/theBottom/cultist7/skeleton.atlas", "images/monsters/theBottom/cultist7/skeleton37.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "waving", false);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction()
    {
        this.loadAnimation("images/monsters/theBottom/cultist7/skeleton.atlas", "images/monsters/theBottom/cultist7/skeleton37.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "waving", false);
        AbstractDungeon.actionManager.addToBottom(new CultistEnterAction(this));
    }
    public Cultist7(float x, float y) {
        this(x, y, true);
    }

    public void takeTurn() {

        if(this.hasPower("Strength")){
            float pf=(float) 5*6/(this.getPower("Strength").amount+25);
            if (pf<0.05f){
                pf=0.05f;
            }
            //this.loadAnimation("images/monsters/theBottom/cultist3/skeleton.atlas", "images/monsters/theBottom/cultist3/skeleton.json", pf);

        }


        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.SLASH_HORIZONTAL));


                break;
            case 2:
                position+=1;
                for(int i=0;i<2;i++) {
                    AbstractMonster am=new BanWuCultist (0, 0,i);

                    double a = i * 3.14 / 2 + 3.14 / 4 + 3.14 * i / 6;
                    am.drawX= (float) (this.drawX+350*cos(a));
                    am.drawY= (float) (this.drawY+350*sin(a));
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(am,true));
                    AbstractDungeon.actionManager.addToBottom(new AnimateRoatateAction(am,this,3.14/2/0.5F,0.5F));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(am,am,new RitualPower(am,3,false)));


                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.SLASH_HORIZONTAL));
                break;
            case 3:
                int temp = MathUtils.random(1, 10);
                if (this.talky) {
                    this.playSfx();
                    if (temp < 4) {
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5f, 1.0F));
                        this.saidPower = true;
                    } else if (temp < 7) {
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 0.5F, 1.0F));
                    }
                }
                for(int i=0;i<4;i++) {
                AbstractMonster am=new BanWuCultist (0, 0,i);

                am.drawX= (float) (this.drawX+350*cos(i*3.14/2+3.14/4));
                am.drawY= (float) (this.drawY+350*sin(i*3.14/2+3.14/4));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(am,true));
                AbstractDungeon.actionManager.addToBottom(new AnimateRoatateAction(am,this,3.14/2/0.5F,0.5F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(am,am,new RitualPower(am,3,false)));


            }
                this.loadAnimation("images/monsters/theBottom/cultist7/skeleton.atlas", "images/monsters/theBottom/cultist7/skeleton37.json", 1.0F);
                AnimationState.TrackEntry e1 = this.state.setAnimation(1, "waving2", true);
                if(AbstractDungeon.aiRng.randomBoolean())
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new RitualPower(this,2,false)));
                }
                else{
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new FlightPower(this,1)));
                }
        }


        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
        this.powers.forEach(
                AbstractPower::atStartOfTurnPostDraw

        );
    }

    private void playSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1B"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1C"));
        }

    }

    private void playDeathSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_CULTIST_2A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_CULTIST_2B");
        } else {
            CardCrawlGame.sound.play("VO_CULTIST_2C");
        }

    }

    public void die() {
        this.playDeathSfx();
        this.state.setTimeScale(0.1F);
        this.useShakeAnimation(5.0F);
        if (this.talky && this.saidPower) {
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5F, DIALOG[2], false));
            ++this.deathTimer;
        }
        for(AbstractMonster m:AbstractDungeon.getMonsters().monsters)
        {
            if(m instanceof BanWuCultist)
            {
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(m));
            }}
        super.die();
    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove(INCANTATION_NAME, (byte)3, Intent.BUFF);
        } else {
            int cj=0;
            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters)
            {
                if(m instanceof BanWuCultist)
                {

                   cj++;
                }
            }
            if(cj>=3) {
                this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base);
            }
            else{
                this.setMove((byte) 2, Intent.UNKNOWN);
            }
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Cultist");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        INCANTATION_NAME = MOVES[2];
    }
}


