package org.piotrwyrw.flappy;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class GameAssets {

    static GameAssets instance = null;

    private HashMap<String, BufferedImage> assets;

    public GameAssets() {
        this.assets = new HashMap<>();
    }

    public HashMap<String, BufferedImage> assets() {
        return assets;
    }

    public BufferedImage getAsset(String id) {
        BufferedImage img = assets.get(id);
        if (img == null)
            System.out.println("Warn: Asset '" + id + "' does not exist.");
        return img;
    }

    public BufferedImage getAssetScaled(String id, int width, int height) {
        BufferedImage asset = getAsset(id);
        return Tools.toBufferedImage(asset.getScaledInstance(
                (width == -1) ? asset.getWidth() : width,
                (height == -1) ? asset.getHeight() : height,
                BufferedImage.SCALE_SMOOTH));
    }

    public void addAsset(String id, BufferedImage img) {
        if (assets.get(id) != null) {
            System.out.println("Warn: Asset '" + id + "' already exists. Ignoring.");
            return;
        }
        System.out.println("Registered new game asset '" + id + "'.");
        assets.put(id, img);
    }

    public static GameAssets getInstance() {
        if (instance == null) {
            instance = new GameAssets();
        }
        return instance;
    }


}
