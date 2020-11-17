package com.mountainbreaker.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Image {
    private static final ArrayList<Image> imageList = new ArrayList<>();

    public static Image getImage(String id) {
        if(imageList.size() == 0) {
            imageList.add(new Image());
        }

        for(Image image : imageList) {
            if(image.id.equals(id)) {
                return image;
            }
        }

        Image loadedImage = new Image(id);
        if(loadedImage.baseImage != null) {
            imageList.add(loadedImage);
            return loadedImage;
        }

        return imageList.get(0);
    }

    public static Image create(String id, int width, int height) {
        Image createdImage = new Image();

        createdImage.id = id;
        createdImage.baseImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        imageList.add(createdImage);
        return createdImage;
    }

    private static String genPath(String id) {
        return "/graphics/" + id + ".png";
    }

    public String id;
    protected int tileSize;
    public BufferedImage baseImage = null;

    private Image() {
        id = "null";
        baseImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        int width = baseImage.getWidth();
        int height = baseImage.getHeight();

        tileSize = Math.min(width, height);

        Graphics g = baseImage.getGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.GRAY);
        g.drawLine(0, 0, width - 1, height - 1);
        g.drawLine(width - 1, 0, 0, height - 1);
        g.dispose();
    }

    public Image(String id) {

        try {
            baseImage = ImageIO.read(Image.class.getResourceAsStream(genPath(id)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if(baseImage == null) return;

        tileSize = Math.min(baseImage.getWidth(), baseImage.getHeight());

        this.id = id;
    }

    public void setTiled(int tileSize) {
        int width = baseImage.getWidth();
        int height = baseImage.getHeight();

        if(tileSize == 0 || tileSize > Math.min(width, height)) {
            this.tileSize = Math.min(width, height);
        }
        else {
            this.tileSize = tileSize;
        }
    }

    public BufferedImage getSection(Rectangle bounds) {
        return baseImage.getSubimage(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public BufferedImage getTile(int index) {
        int width = baseImage.getWidth();
        int height = baseImage.getHeight();
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
