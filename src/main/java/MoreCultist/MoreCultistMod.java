package MoreCultist;

import MoreCultist.monsters.Finisher1;
import MoreCultist.monsters.act1.Cultist16;
import MoreCultist.monsters.act1.Cultist19;
import MoreCultist.monsters.act2.*;
import MoreCultist.monsters.act3.Cultist13;
import MoreCultist.monsters.act3.Cultist18;
import MoreCultist.monsters.act3.Cultist21;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ModToggleButton;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.green.Finisher;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.scenes.TheBottomScene;

import java.io.IOException;
import java.util.function.Consumer;

import static basemod.BaseMod.registerModBadge;

@SpireInitializer
public class MoreCultistMod implements PostInitializeSubscriber, EditCardsSubscriber, EditStringsSubscriber { // 实现接口
    public MoreCultistMod() {
        BaseMod.subscribe(this); // 告诉basemod你要订阅事件
    }

    public static void initialize() throws IOException {
        new MoreCultistMod();
        config=new SpireConfig("MoreCultist","MoreCultist");
        config.load();
    }

    // 当basemod开始注册mod卡牌时，便会调用这个函数
    public static SpireConfig config;
    @Override
    public void receivePostInitialize() {
        receiveEditMonsters();
        ModPanel settingsPanel = new ModPanel();
        //settingsPanel.addUIElement(buttonLabel);
        if(config.getString("Miao")==null)
        {
            config.setString("Miao",(Settings.language == Settings.GameLanguage.ZHS) ? "喵~":"Meow~");
            try {
                config.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        Consumer<ModToggleButton> clickAction = (button) -> {
            config.setBool("Background",button.enabled);
            System.out.println("已经将替换背景设置为！"+button.enabled);
            try {
                config.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };


// 创建一个带有标签的按钮，并将其添加到 ModPanel 中
        ModLabeledToggleButton labeledButton = new   ModLabeledToggleButton("你可以修改%LOCALAPPDATA%/ModTheSpire/Catgirl/Catgirl.properties来修改对话结尾", 400.0f, 700.0f-16.0f, Color.GREEN, FontHelper.buttonLabelFont, config.getBool("Background"),settingsPanel, (label) -> {
        },clickAction);

        settingsPanel.addUIElement(labeledButton);

        Texture badgeTexture = new Texture(Gdx.files.internal("images/Catgirl.png"));
        registerModBadge(badgeTexture, "PalMod", "Butterfly Norm", "修改%LOCALAPPDATA%\\ModTheSpire\\Catgirl", settingsPanel);
    }
    @Override
    public void receiveEditCards()
    {

    }

    private void receiveEditMonsters() {
        // 注册怪物组合，你可以多添加几个怪物
        BaseMod.addMonster("CultistMod:GiantHead", Cultist13.NAME, () -> new Cultist13(0.0F, 0.0F));
        BaseMod.addMonster("CultistMod:Chanrao", Cultist21.NAME, () -> new Cultist21(0.0F, 0.0F));
        BaseMod.addMonster("CultistMod:threesmall", Cultist16.NAME, () -> new Cultist16(0.0F, 0.0F));
        BaseMod.addMonster("CultistMod:threehead", Cultist19.NAME, () -> new Cultist19(0.0F, 0.0F));
        BaseMod.addMonster("CultistMod:MJ", Cultist7.NAME, () -> new Cultist7(0.0F, 0.0F));
        BaseMod.addMonster("CultistMod:Awaken", Cultist10.NAME, () -> new Cultist10(0.0F, 0.0F));
        BaseMod.addMonster("CultistMod:Champ", Cultist12.NAME, () -> new Cultist12());
        BaseMod.addMonster("CultistMod:Repeator", Cultist18.NAME, () -> new Cultist18(0,0));
        BaseMod.addMonster("CultistMod:3Cultist3","Cultist", () ->new MonsterGroup(new AbstractMonster[] { new Cultist2(-465.0F, -20.0F, false), new Cultist3(-130.0F, 15.0F, false), new Cultist4(200.0F, -5.0F) })
        );
        //new MonsterGroup(new AbstractMonster[] { new Cultist(-465.0F, -20.0F, false), new Cultist(-130.0F, 15.0F, false), new Cultist(200.0F, -5.0F) });

        // 两个异鸟
        // BaseMod.addMonster("ExampleMod:2 Byrds", "", () -> new MonsterGroup(new AbstractMonster[] { new Byrd(-80.0F, MathUtils.random(25.0F, 70.0F)), new Byrd(200.0F, MathUtils.random(25.0F, 70.0F)) }));

        // 添加战斗遭遇
        // 在第二章添加精英遭遇，权重为1.0，权重越高越可能遇到

        BaseMod.addEliteEncounter("TheBeyond", new MonsterInfo("CultistMod:GiantHead", 1.0F));

        BaseMod.addStrongMonsterEncounter("TheBeyond", new MonsterInfo("CultistMod:Chanrao", 1.0F));
        BaseMod.addStrongMonsterEncounter("TheBeyond", new MonsterInfo("CultistMod:Repeator", 1.0F));

        BaseMod.addStrongMonsterEncounter("TheCity", new MonsterInfo("CultistMod:MJ", 1.0F));

        BaseMod.addEliteEncounter("TheCity", new MonsterInfo("CultistMod:Awaken", 1.0F));
        BaseMod.addEliteEncounter("TheCity", new MonsterInfo("CultistMod:Champ", 1.0F));
        BaseMod.addEliteEncounter("TheCity", new MonsterInfo("CultistMod:3Cultist3", 1.0F));

        BaseMod.addStrongMonsterEncounter("Exordium", new MonsterInfo("CultistMod:threesmall", 1.0F));
        BaseMod.addStrongMonsterEncounter("Exordium", new MonsterInfo("CultistMod:threehead", 1.0F));

    }
    @Override
    public void receiveEditStrings() {
        String lang;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "ZHS"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
        } else {
            lang = "ENG"; // 如果没有相应语言的版本，默认加载英语
        }
        BaseMod.loadCustomStringsFile(PowerStrings.class, "Cultistlocalizations/" + lang + "/PowerStrings.json"); // 加载相应语言的卡牌本地化内容。
        // 如果是中文，加载的就是"ExampleResources/localization/ZHS/cards.json"
    }
    }
