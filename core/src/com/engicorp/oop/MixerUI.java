package com.engicorp.oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.engicorp.oop.exception.IndexNotValid;
import com.engicorp.oop.exception.TasKepenuhan;
import com.engicorp.oop.generik.Tas;
import com.engicorp.oop.livingbeing.Player;
import com.engicorp.oop.product.FarmProduct;
import com.engicorp.oop.product.Product;
import com.engicorp.oop.product.SideProduct;
import com.engicorp.oop.cell.Mixer;

import java.util.ArrayDeque;
import java.util.Vector;

public class MixerUI {
    static private MixerUI instance = new MixerUI();
    private boolean show;
    static public MixerUI getInstance() {return instance;}
    private Skin skin;
    private Stage stageBahan, stageHasil;
    private Container<Table> pilihBahanContainer = new Container<Table>();
    private Container<Table> bahanTerpilihContainer = new Container<Table>();
    private Container<Table> hasilContainer = new Container<Table>();
    private Table tablePilih, tableTerpilih, tableHasil;
    private int select, selectHasil;
    private Vector<Product> bahanTerpilih = new Vector<Product>();
    private ArrayDeque<SideProduct> hasil = new ArrayDeque<SideProduct>();
    private boolean selectBahan = true;

    public MixerUI()
    {
        show = false;
        skin = new Skin(Gdx.files.internal("ui/skin-composer/skin/skin-composer-ui.json"));
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        stageBahan = new Stage();
        pilihBahanContainer.setSize(640, 360);
        pilihBahanContainer.setPosition(0,360);
        pilihBahanContainer.fill();

        tablePilih = new Table(skin);
        pilihBahanContainer.setActor(tablePilih);
        stageBahan.addActor(pilihBahanContainer);

        bahanTerpilihContainer.setSize(640, 360);
        bahanTerpilihContainer.setPosition(0, 0);
        bahanTerpilihContainer.fill();

        tableTerpilih = new Table(skin);
        bahanTerpilihContainer.setActor(tableTerpilih);
        stageBahan.addActor(bahanTerpilihContainer);
        stageBahan.addAction(Actions.fadeIn(1.5f));
        stageHasil = new Stage();
        hasilContainer.setSize(640, 720);
        hasilContainer.setPosition(0,0);
        hasilContainer.fill();

        tableHasil = new Table(skin);
        hasilContainer.setActor(tableHasil);
        stageHasil.addActor(hasilContainer);
        stageHasil.addAction(Actions.fadeOut(0.1f));
    }

    private Label label(String text, Color color) {
        Label label = new Label(text, skin);
        label.setSize(100, 20);
        label.setAlignment(Align.center);
        label.setColor(color);
        return label;
    }

    private Label loadFromTas(Tas<Product>tas, int idx)
    {
        try{
            return label(tas.getBarang(idx).getName(), Color.BLACK);
        }catch(IndexNotValid i)
        {
            return label("", Color.BLACK);
        }
    }

    private Label[] loadFromHasil(int idx)
    {
        Label[] temp = new Label[2];
        if(idx >= 0 && idx < hasil.size())
        {
            SideProduct sp = (SideProduct)hasil.toArray()[idx];

            temp[0] = label(sp.getName(), Color.BLACK);
            temp[1] = label(" : "+sp.getPrice(), Color.BLACK);
        }else{
            temp[0] = label("", Color.BLACK);
            temp[1] = label("", Color.BLACK);
        }
        return temp;
    }

    public int getSelectHasil() {
        return selectHasil;
    }

    public void setSelectHasil(int selectHasil) {
        if (selectHasil >= 0 && selectHasil < hasil.size()){
            this.selectHasil = selectHasil;
        }
    }

    public boolean isSelectBahan() {
        return selectBahan;
    }

    public void setSelectBahan(boolean selectBahan) {
        this.selectBahan = selectBahan;
        if(selectBahan)
        {
            stageBahan.addAction(Actions.fadeIn(1.5f));
            stageHasil.addAction(Actions.fadeOut(1.5f));
        }else{
            stageBahan.addAction(Actions.fadeOut(1.5f));
            stageHasil.addAction(Actions.fadeIn(1.5f));

        }
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int sel) {
        if(sel >= 0 && sel < Player.getInstance().getTas().getSize())
        {
            select = sel;
        }
    }

    public void addToTerpilih(){
        try{
            bahanTerpilih.add(Player.getInstance().getTas().getBarang(select));
            Player.getInstance().getTas().removeBarang(select);
        }catch(IndexNotValid i)
        {}
    }

    public void removeFromTerpilih()
    {
        if(bahanTerpilih.size() > 0) {
            try {
                Player.getInstance().getTas().addBarang(bahanTerpilih.get(bahanTerpilih.size() - 1));
                bahanTerpilih.remove(bahanTerpilih.size() - 1);
            } catch (TasKepenuhan T) {
            }
        }
    }

    public void mixBahanTerpilih()
    {
        hasil.clear();
        hasil = new ArrayDeque<SideProduct>(Mixer.mix(bahanTerpilih));
    }

    public void kasihPlayerHasilnya()
    {
        if(hasil.size() > 0) {
            SideProduct sp;
            int geser = selectHasil;
            int idx = selectHasil;
            while (idx > 0) {
                hasil.offerLast(hasil.pollFirst());
                idx -= 1;
            }
            //Head adalah item yang dicari
            sp = hasil.pollFirst();
            try {
                Player.getInstance().getTas().addBarang(sp);
                World.getInstance().addMsg("Success mix, mendapat 1 "+sp.getName());
            } catch (TasKepenuhan T) {
                World.getInstance().addMsg("Tas kepenuhan, hasil mix gagal dimasukkan");
                hasil.offerFirst(sp);
            }
            //Balikkan hasil seperti semula
            while (geser > 0) {
                hasil.offerFirst(hasil.pollLast());
                geser -= 1;
            }
            //Hapus bahan yang dipakai
            for (FarmProduct fp : sp.getIngredients()) {
                boolean ketemu = false;
                int idxHapus = -1;
                for (int i = 0; i < bahanTerpilih.size() && !ketemu; i++) {
                    if (bahanTerpilih.get(i).compareTo(fp) == 0) {
                        ketemu = true;
                        idxHapus = i;
                    }
                }
                if (ketemu) {
                    bahanTerpilih.remove(idxHapus);
                }
            }
        }
    }

    private void updateHasil()
    {
        tableHasil.reset();
        Label[] itemAtas1, itemTengah1, itemBawah1;
        itemAtas1 = loadFromHasil(selectHasil-1);
        itemTengah1= loadFromHasil(selectHasil);
        itemBawah1 = loadFromHasil(selectHasil+1);

        itemTengah1[0].setFontScale(2);
        itemTengah1[1].setFontScale(2);

        if(selectHasil > 0) {
            Label textHasil = label("Hasil : ", Color.SCARLET);
            tableHasil.add(textHasil);
            tableHasil.add(itemAtas1[0]);
            tableHasil.add(itemAtas1[1]);
            tableHasil.row().fillX();
        }
        if(hasil.size() > 0) {
            Label textHasilBig = label("Hasil : ", Color.SCARLET);
            textHasilBig.setFontScale(2);
            tableHasil.add(textHasilBig);
            tableHasil.add(itemTengah1[0]);
            tableHasil.add(itemTengah1[1]);
        }else{
            tableHasil.add(label("Tidak ada yang bisa digabung", Color.SCARLET));
        }
        if(selectHasil < hasil.size()-1) {
            tableHasil.row().fillX();
            Label textHasil = label("Hasil : ", Color.SCARLET);
            tableHasil.add(textHasil);
            tableHasil.add(itemBawah1[0]);
            tableHasil.add(itemBawah1[1]);
            tableHasil.row().fillX();
        }
        hasilContainer.setActor(tableHasil);
    }

    private void updateTerpilih()
    {
        tableTerpilih.reset();
        for(Product sp : bahanTerpilih)
        {
            tableTerpilih.add(label(sp.getName(), Color.BLACK));
            tableTerpilih.row();
        }

        bahanTerpilihContainer.setActor(tableTerpilih);
    }

    private void updatePilihan()
    {
        Tas<Product> tas = Player.getInstance().getTas();
        tablePilih.reset();
        Label itemAtas1, itemTengah1, itemBawah1;
        itemAtas1 = loadFromTas(tas, select-1);
        itemTengah1= loadFromTas(tas, select);
        itemBawah1 = loadFromTas(tas, select+1);

        itemTengah1.setFontScale(2);

        tablePilih.add(itemAtas1);
        tablePilih.row();
        tablePilih.add(itemTengah1);
        tablePilih.row();
        tablePilih.add(itemBawah1);
        tablePilih.row();

        pilihBahanContainer.setActor(tablePilih);
    }

    public void setShow(boolean b)
    {
        show = b;
        select = 0;
        selectHasil = 0;
        selectBahan = true;
        try {
            for (Product p : bahanTerpilih) {
                Player.getInstance().getTas().addBarang(p);
            }
        }catch(TasKepenuhan T)
        {}
        bahanTerpilih.clear();
        hasil.clear();
    }

    public boolean isShowing()
    {
        return show;
    }

    public void render()
    {
        if(show)
        {
            updatePilihan();
            updateTerpilih();
            updateHasil();
            stageBahan.act();
            stageHasil.act();
            stageBahan.draw();
            stageHasil.draw();
        }
    }

    public void dispose()
    {
        stageBahan.dispose();
        stageHasil.dispose();
        skin.dispose();
    }
}
