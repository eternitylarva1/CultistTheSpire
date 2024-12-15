package MoreCultist.actions;



import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class AnimateRoatateAction extends AbstractGameAction {
    private boolean called = false;

    private float targetX, targetY,oX,oY;
    private AbstractMonster p;

    private Float time;

    private double ax;
    private double ay;
    private double bx;
    private double by;
    private double radiansPerSecond; // 每秒旋转的弧度数
    private double currentAngle;

    public AnimateRoatateAction(AbstractMonster owner, AbstractCreature target,double radiansPerSecond, float duration) {
        this.setValues(null, owner, 0);
        this.duration = 1.0F;
        this.actionType = ActionType.WAIT;
        this.bx = target.drawX;
        this.by = target.drawY;

        this.p=owner;
        this.ax=owner.drawX;
        this.ay=owner.drawY;
        this.radiansPerSecond = radiansPerSecond;
        this.duration = duration;
        this.isDone = false;
        this.currentAngle = 0.0;

    }
    public static double[] rotatePoint(double ax, double ay, double bx, double by, double radians) {
        // 将点a平移到以点b为原点的坐标系
        double translatedX = ax - bx;
        double translatedY = ay - by;

        // 旋转点a
        double rotatedX = translatedX * Math.cos(radians) - translatedY * Math.sin(radians);
        double rotatedY = translatedX * Math.sin(radians) + translatedY * Math.cos(radians);

        // 将旋转后的点平移回原来的坐标系
        double finalX = rotatedX + bx;
        double finalY = rotatedY + by;

        return new double[]{finalX, finalY};
    }

    public void update() {
        if (this.duration >= 0.0F) {
            double deltaTime = Gdx.graphics.getDeltaTime();
            double deltaAngle = radiansPerSecond * deltaTime;
            currentAngle += deltaAngle;

            double[] rotatedPoint = rotatePoint(ax, ay, bx, by, currentAngle);
            double rotatedAx = rotatedPoint[0];
            double rotatedAy = rotatedPoint[1];

            System.out.println("旋转后的点a坐标: (" + rotatedAx + ", " + rotatedAy + ")");
            this.p.drawX= (float) rotatedAx;
            this.p.drawY=(float) rotatedAy;

        }

        this.tickDuration();

}

}
