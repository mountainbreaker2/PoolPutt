package com.mountainbreaker.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class TiledImage {

    private static final ArrayList<TiledImage> imageList = new ArrayList<>();

    public static TiledImage getImage(String id) {
        if(imageList.size() == 0) {
            imageList.add(new TiledImage());
        }

        for(TiledImage image : imageList) {
            if(image.id.equals(id)) {
                return image;
            }
        }

        TiledImage loadedImage = new TiledImage(id, 16);
        if(loadedImage.baseImage != null) {
            imageList.add(loadedImage);
            return loadedImage;
        }

        return imageList.get(0);
    }

    private static String genPath(String id) {
        return "/graphics/" + id + ".png";
    }

    public String id;
    public int width, height;
    public int tileSize;

    public BufferedImage baseImage = null;

    public TiledImage() {
        id = "null";
        baseImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);

        width = baseImage.getWidth();
        height = baseImage.getHeight();

        tileSize = width;

        Graphics g = baseImage.getGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.GRAY);
        g.drawLine(0, 0, width - 1, height - 1);
        g.drawLine(width - 1, 0, 0, height - 1);
        g.dispose();
    }

    public TiledImage(String id, int tileSize) {

        try {
            baseImage = ImageIO.read(TiledImage.class.getResourceAsStream(genPath(id)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if(baseImage == null) return;

        this.id = id;
        this.width = baseImage.getWidth();
        this.height = baseImage.getHeight();


        if(tileSize == 0 || tileSize > Math.min(width, height)) {
            this.tileSize = Math.min(width, height);
        }
        else {
            this.tileSize = tileSize;
        }
    }

    public BufferedImage getSprite(int index) {
        int hRun = width / tileSize;
        int vRun = height / tileSize;

        if(index <= hRun * vRun) {
            int x = (index % hRun) * tileSize;
            int y = (index / hRun) * tileSize;
            return baseImage.getSubimage(x, y, tileSize, tileSize);
        }

        return baseImage.getSubimage(0, 0, tileSize, tileSize);
    }
}
