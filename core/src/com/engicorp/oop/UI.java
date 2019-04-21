package com.engicorp.oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.engicorp.oop.exception.IndexNotValid;
import com.engicorp.oop.generik.Tas;
import com.engicorp.oop.livingbeing.Player;
import com.engicorp.oop.product.Product;

import java.util.ArrayList;

public class UI {
    static private UI instance = new UI();
    private Skin skin;
    private Stage msgStage, playerDataStage;
    private Container<Table> msgContainer = new Container<Table>();
    private Container<Table> playerDataContainer = new Container<Table>();
    private Table msgTable, playerDataTable;

    private UI()
    {
        skin = new Skin(Gdx.files.internal("ui/skin-composer/skin/skin-composer-ui.json"));
        msgStage = new Stage();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        msgContainer.setSize(1280-950, 340);
        msgContainer.setPosition(900, 20);
        msgContainer.fill();
        msgTable = new Table(skin);
        msgContainer.setActor(msgTable);
        msgStage.addActor(msgContainer);

        playerDataStage = new Stage();
        playerDataContainer.setSize(1280-950, 260);
        playerDataContainer.setPosition(900, 360);
        playerDataContainer.fill();
        playerDataTable= new Table(skin);
        playerDataContainer.setActor(playerDataTable);
        playerDataStage.addActor(playerDataContainer);
    }

    public void updateTableDataPlayer()
    {
        playerDataTable.reset();
        Label u1 = label("Money : ", Color.BLACK);
        Label u2 = label(""+Player.getInstance().getMoney(),Color.GOLD);
        Label w1 = label(", Water : ", Color.BLACK);
        Label w2 = label(""+Player.getInstance().getWater(), Color.BLUE);
        u1.setFontScale(1.3f);
        w2.setFontScale(1.3f);
        w1.setFontScale(1.3f);
        w2.setFontScale(1.3f);
        playerDataTable.add(u1);
        playerDataTable.add(u2);
        playerDataTable.add(w1);
        playerDataTable.add(w2);
        playerDataTable.row().fillX();
        playerDataTable.add(label("Inventory : ", Color.BLACK));
        playerDataTable.row().fillX();
        Tas<Product> tas = Player.getInstance().getTas();
        Table invenKiri = new Table(skin);
        Table invenKanan = new Table(skin);
        invenKiri.setSize(165, 260);
        invenKanan.setSize(165, 260);
        for(int i = 0; i < tas.getSize(); i++)
        {
            try {
                Label isiInven = label("- " + tas.getBarang(i).getName(), Color.BLACK);
                isiInven.setFontScale(1f);
                invenKiri.add(isiInven);
                invenKiri.row().fillX();
                if (i >= 9) {
                    break;
                }
            }catch(IndexNotValid inv)
            {
                break;
            }
        }
        for(int i = 10; i < tas.getSize(); i++)
        {
            try {
                Label isiInven = label("- " + tas.getBarang(i).getName(), Color.BLACK);
                isiInven.setFontScale(1f);
                invenKanan.add(isiInven);
                invenKanan.row().fillX();
            }catch(IndexNotValid inv)
            {
                break;
            }
        }
        playerDataTable.add(invenKiri);
        playerDataTable.add(invenKanan);
        playerDataContainer.setActor(playerDataTable);
    }

    public void updateTableMsg(ArrayList<String> msg)
    {
        msgTable.reset();
        for (String s : msg) {
            msgTable.add(label("<Message> : ", Color.MAGENTA));
            msgTable.add(label(s, Color.BLACK));
            msgTable.row().fillX();
        }
        for (int i = 0; i < 20-msg.size(); i++) {
            msgTable.add(label("", Color.BLACK));
            msgTable.row().fillX();
        }
        msgContainer.setActor(msgTable);
    }

    private Label label(String text, Color color) {
        Label label = new Label(text, skin);
        label.setAlignment(Align.center);
        label.setColor(color);
        return label;
    }

    public static UI getInstance() {
        return instance;
    }

    public void render()
    {
        updateTableDataPlayer();
        msgStage.act();
        playerDataStage.act();
        msgStage.draw();
        playerDataStage.draw();
    }

    public void dispose()
    {
        msgStage.dispose();
        playerDataStage.dispose();
        skin.dispose();
    }
}
