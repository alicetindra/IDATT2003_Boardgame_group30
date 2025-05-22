package edu.ntnu.idatt2003.boardgame.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

/**
 * Manages ownership and visual representation of properties in the board game.
 * Each property is identified by an integer ID and may be owned by a player.
 */
public class PropertyHolder {
    /** A map linking property IDs to their owners. */
    private final HashMap<Integer, Player> properties;

    /** ImageView used to visually represent a property (e.g. a house). */
    private ImageView houseImage;

    /**
     * Constructs an empty PropertyHolder with no owned properties.
     */
    public PropertyHolder() {
        properties = new HashMap<>();
    }


    /**
     * Set owner of a house.
     * @param integer integer of tile id where the house is.
     * @param player the owner of the house.
     */
    public void setOwner(Integer integer, Player player) {
        properties.put(integer, player);
    }

    /**
     * Removes ownership of a specific property by setting the owner to null.
     *
     * @param integer the property ID
     */
    public void removeOwner(Integer integer) {
        properties.put(integer, null);
    }

    /**
     * Creates an image of the house representing the property owned by a player.
     * The image is selected based on the player's color.
     *
     * @param integer the property ID
     */
    public void makeImage(Integer integer) {
        houseImage = new ImageView(new Image("/images/houses/"+properties.get(integer).getColor()+"house.png"));

    }

    /**
     * Returns an ImageView representing the house image for the given property.
     *
     * @param integer the property ID
     * @return an {@link ImageView} of the house
     */
    public ImageView getImage(Integer integer) {
        makeImage(integer);
        return houseImage;
    }

    /**
     * Initializes the property map with the given number of properties,
     * setting all properties to have no owner initially.
     *
     * @param size the number of properties to initialize
     */
    public void setProperties(int size) {
        for (int i = 1; i <= size; i++) {
            properties.put(i, null);
        }
    }

    /**
     * Returns the price of the property based on its ID.
     * Properties are divided into quartiles with increasing price.
     *
     * @param integer the property ID
     * @return the price of the property
     */
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

    /**
     * Returns the fee for landing on a property, calculated as one fourth of its price.
     *
     * @param integer the property ID
     * @return the fee for the property
     */
    public int getFee(Integer integer){
        return getPrice(integer)/4;
    }


    public HashMap<Integer, Player> getProperties() {
        return properties;
    }

}
