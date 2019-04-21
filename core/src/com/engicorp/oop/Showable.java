package com.engicorp.oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.engicorp.oop.livingbeing.Animal;
import com.engicorp.oop.misc.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.engicorp.oop.misc.UpdatePosition;

import javax.xml.soap.Text;

public class Showable {
    private static int width, height, tileSize;
    protected Point pos, projPos, goalPos, offsetPos;
    protected String texturePath;
    protected Texture tex;
    protected Sprite spr;
    protected TextureRegion[][] animFrame;
    protected Animation<TextureRegion> kiri, kanan, atas, bawah;
    protected TextureRegion[] kiriReg, kananReg, atasReg, bawahReg;
    protected boolean isAnimated, isRunningAnimation;
    protected float epsTime, animTime, durasiPerFrame;
    protected int dir, len;
    protected UpdatePosition updateX, updateY, updateZ;
    protected boolean animatedHungry;

    public Showable(Point _pos, String texPath, boolean isAnim, int wTile, int hTile)
    {
        pos = _pos;
        goalPos = _pos;
        projPos = new Point(-1,-1);
        offsetPos = new Point(0,0);
        setPos(pos);
        texturePath = texPath;
        tex = new Texture(texturePath);
        isAnimated = isAnim;
        if(isAnimated)
        {
            epsTime = 0;
            isRunningAnimation = false;
            durasiPerFrame = tex.getWidth()/wTile;
            durasiPerFrame = 1/(float)durasiPerFrame;
            System.out.println(durasiPerFrame);
            processAnimFromTex(wTile, hTile);
            setDefaultWalk();

        }else{
            spr = new Sprite(tex);
        }
    }

    public boolean isAnimatedHungry() {
        return animatedHungry;
    }

    public void setAnimatedHungry(boolean animatedHungry) {
        this.animatedHungry = animatedHungry;
    }

    public void processAnimFromTex(int wTile, int hTile)
    {
        animFrame = TextureRegion.split(tex, wTile, hTile);
        //Urutan
        int index = 0;
        int maxFrame = (int)(tex.getWidth()/wTile);
//        System.out.println("Max F : "+maxFrame);
        bawahReg = new TextureRegion[maxFrame];
        kananReg = new TextureRegion[maxFrame];
        kiriReg = new TextureRegion[maxFrame];
        atasReg = new TextureRegion[maxFrame];
        //Bawah
        for (int i = 0; i < maxFrame; i++) {
            bawahReg[index++] = animFrame[0][i];
        }
        bawah = new Animation<TextureRegion>(durasiPerFrame, bawahReg);
        //Kanan
        index = 0;
        for (int i = 0; i < maxFrame; i++) {
            kananReg[index++] = animFrame[1][i];
        }
        kanan = new Animation<TextureRegion>(durasiPerFrame, kananReg);
        //Kiri
        index = 0;
        for (int i = 0; i < maxFrame; i++) {
            kiriReg[index++] = animFrame[2][i];
        }
        kiri = new Animation<TextureRegion>(durasiPerFrame, kiriReg);
        //Atas
        index = 0;
        for (int i = 0; i < maxFrame; i++) {
            atasReg[index++] = animFrame[3][i];
        }
        atas = new Animation<TextureRegion>(durasiPerFrame, atasReg);
    }

    public void setDefaultWalk()
    {
        if(!isRunningAnimation) {
            animTime = 0.5f;
            updateX = new UpdatePosition() {
                @Override
                public Point update(Point before, float dT, float epsTime, float animTime) {
                    return before.add(new Point(dT, 0));
                }
            };
            updateY = new UpdatePosition() {
                @Override
                public Point update(Point before, float dT, float epsTime, float animTime) {
                    return before.add(new Point(0, dT));
                }
            };
            updateZ = new UpdatePosition() {
                @Override
                public Point update(Point before, float dT, float epsTime, float animTime) {
                    return before.add(new Point(0, 0));
                }
            };
        }
    }

    public void setDefaultJump()
    {
        if(!isRunningAnimation) {
            animTime = 1;
            updateX = new UpdatePosition() {
                @Override
                public Point update(Point before, float dT, float epsTime, float animTime) {
                    return before.add(new Point(dT, 0, 0));
                }
            };
            updateY = new UpdatePosition() {
                @Override
                public Point update(Point before, float dT, float epsTime, float animTime) {
                    return before.add(new Point(0, dT, 0));
                }
            };
            updateZ = new UpdatePosition() {
                @Override
                public Point update(Point before, float dT, float epsTime, float animTime) {
                    return before.add(new Point(0, 0, 0.07 * Math.cos(epsTime / animTime * Math.PI)));
                }
            };
        }
    }

    public void setDefaultJumpInPlace()
    {
        if(!isRunningAnimation) {
            animTime = 1;
            updateX = new UpdatePosition() {
                @Override
                public Point update(Point before, float dT, float epsTime, float animTime) {
                    return before.add(new Point(0, 0, 0));
                }
            };
            updateY = new UpdatePosition() {
                @Override
                public Point update(Point before, float dT, float epsTime, float animTime) {
                    return before.add(new Point(0, 0, 0));
                }
            };
            updateZ = new UpdatePosition() {
                @Override
                public Point update(Point before, float dT, float epsTime, float animTime) {
                    return before.add(new Point(0, 0, 0.07 * Math.cos(epsTime / animTime * Math.PI)));
                }
            };
        }
    }

    public void setLenMove(int _len)
    {
        len = _len;
    }

    public static void setWidth(int w)
    {
        width = w;
    }

    public static void setHeight(int h)
    {
        height = h;
    }

    public static void setTileSize(int ts) {tileSize = ts;}

    public void dispose()
    {
        tex.dispose();
    }

    public void render(SpriteBatch batch)
    {
        spr.draw(batch);
    }

    public boolean isAnimating()
    {
        return isRunningAnimation;
    }

    private void drawIdle(SpriteBatch batch)
    {
        pos = new Point(goalPos.x, goalPos.y, goalPos.z);
        setPos(pos);
        switch (dir)
        {
            case 0:
                batch.draw(atas.getKeyFrames()[1], (int)projPos.x, (int)projPos.y);
                break;
            case 1:
                batch.draw(bawah.getKeyFrames()[1], (int)projPos.x, (int)projPos.y);
                break;
            case 2:
                batch.draw(kiri.getKeyFrames()[1], (int)projPos.x, (int)projPos.y);
                break;
            case 3:
                batch.draw(kanan.getKeyFrames()[1], (int)projPos.x, (int)projPos.y);
                break;
        }
    }

    public void updatePos(UpdatePosition u, float dT)
    {
        pos = u.update(pos, dT, epsTime, animTime);
//        System.out.println("Z : "+pos.z);
        setPos(pos);
    }

    public UpdatePosition getUpdateX() {
        return updateX;
    }

    public void setUpdateX(UpdatePosition updateX) {
        this.updateX = updateX;
    }

    public UpdatePosition getUpdateY() {
        return updateY;
    }

    public void setUpdateY(UpdatePosition updateY) {
        this.updateY = updateY;
    }

    public float getAnimTime() {
        return animTime;
    }

    public void setAnimTime(float animTime) {
        this.animTime = animTime;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public Point getPointDir()
    {
        return Animal.createPointTujuan(pos, dir);
    }

    public void animate(SpriteBatch batch)
    {
        if(isRunningAnimation) {
//            System.out.println("Anim, " + epsTime);
            float inc = Gdx.graphics.getDeltaTime()/animTime*len;
            epsTime += inc*animTime/len;
            //Update posisi
            switch (dir) {
                case 0:
                    updatePos(updateX, inc);
                    updatePos(updateZ, inc);
                    if(animatedHungry) {
                        batch.draw(atas.getKeyFrame(epsTime, true), (int) projPos.x, (int) projPos.y);
                    }else{
                        batch.draw(atas.getKeyFrames()[1], (int)projPos.x, (int)projPos.y);
                    }
                    break;
                case 1:
                    updatePos(updateX, -inc);
                    updatePos(updateZ, inc);
                    if(animatedHungry) {
                        batch.draw(bawah.getKeyFrame(epsTime, true), (int) projPos.x, (int) projPos.y);
                    }else{
                        batch.draw(bawah.getKeyFrames()[1], (int)projPos.x, (int)projPos.y);
                    }
                    break;
                case 2:
                    updatePos(updateY, inc);
                    updatePos(updateZ, inc);
                    if(animatedHungry) {
                        batch.draw(kiri.getKeyFrame(epsTime, true), (int) projPos.x, (int) projPos.y);
                    }else{
                        batch.draw(kiri.getKeyFrames()[1], (int)projPos.x, (int)projPos.y);
                    }
                    break;
                case 3:
                    updatePos(updateY, -inc);
                    updatePos(updateZ, inc);
                    if(animatedHungry) {
                        batch.draw(kanan.getKeyFrame(epsTime, true), (int) projPos.x, (int) projPos.y);
                    }else{
                        batch.draw(kanan.getKeyFrames()[1], (int)projPos.x, (int)projPos.y);
                    }
                    break;
            }
//            System.out.println("Proj : "+(int)projPos.x+", "+(int)projPos.y);
            //Cek apakah sudah saatnya stop
            if(epsTime >= animTime)
            {
                drawIdle(batch);
//                System.out.println("Stop anim");
//                System.out.println("Pos : "+pos.x+", "+pos.y);
                isRunningAnimation = false;
                epsTime = 0;
            }
        }else{
//            System.out.println("Idle");
            drawIdle(batch);
        }
    }

    public void startAnimate(Point posBaru)
    {
        isRunningAnimation = true;
        goalPos = new Point(posBaru.x, posBaru.y, posBaru.z);
        if(goalPos.compareTo(pos) == 0 && len == 0)
        {
            isRunningAnimation = false;
        }
        epsTime = 0;
    }

    public void stopAnimate()
    {
        isRunningAnimation = false;
        epsTime = 0;
    }

    public void setTexPath(String _texPath)
    {
        texturePath = _texPath;
        tex.dispose();
        tex = new Texture(_texPath);
        spr = new Sprite(tex);
    }

    public void setPos(Point real)
    {
        pos = new Point(real.x, real.y, real.z);
        Point temp = new Point((real.x-width/2)*tileSize/2, (real.y-height/2)*tileSize/2, real.z*tileSize/2);
        temp = temp.add(offsetPos.multiply(tileSize/2));
        //https://en.wikipedia.org/wiki/Isometric_projection
        projPos.x = (int)Math.round(Math.sqrt(3)*(temp.x-temp.y)/Math.sqrt(6)) + 400;
        projPos.y = (int)Math.round((temp.x+2*temp.z+temp.y)/Math.sqrt(6)) + 360;
        projPos.z = 0;//(int)Math.round(Math.sqrt(2)*(real.x-real.y+real.z)/Math.sqrt(6));
        if(spr != null) {
            spr.setPosition((int) projPos.x, (int) projPos.y);
        }
    }

    public Point getPos()
    {
        return pos;
    }

    public Point getProjPos()
    {
        return projPos;
    }

    public void setProjPos(Point proj)
    {
        projPos = proj;
    }

    public Point getOffsetPos() {
        return offsetPos;
    }

    public void setOffsetPos(Point offsetPos) {
        this.offsetPos = offsetPos;
        setPos(pos);
    }
}
