package edu.ntnu.idatt2003.boardgame.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class PropertyHolder {
    HashMap<Integer, Player> properties;
    ImageView houseImage;

    public PropertyHolder() {
        properties = new HashMap<>();
    }

    public void setOwner(Integer integer, Player player) {
        properties.put(integer, player);
    }
    public void removeOwner(Integer integer) {
        properties.put(integer, null);
    }

    public void makeImage(Integer integer) {
        houseImage = new ImageView(new Image("/images/houses/"+properties.get(integer).getColor()+"house.png"));

    }

    public ImageView getImage(Integer integer) {
        makeImage(integer);
        return houseImage;
    }


    public void setProperties(int size) {
        for (int i = 1; i <= size; i++) {
            properties.put(i, null);
        }
    }

    public int getPrice(Integer integer) {
        if(integer<= properties.size()/4){
            return 140;
        }
        else if(integer<= properties.size()/2){
            return 200;
        }
        else if(integer<= 3*properties.size()/4){
            return 300;
        }
        else{
            return 360;
        }
    }

    public int getFee(Integer integer){
        return getPrice(integer)/4;
    }

    public HashMap<Integer, Player> getProperties() {
        return properties;
    }

}
