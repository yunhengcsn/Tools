package tools.verifycode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @BelongsProject: Tools
 * @BelongsPackage: tools.verifycode
 * @Author: csn
 * @Description: generate verifycode
 */
public class Vcode {
    private int width = 70;
    private int height = 35;
    private Random random = new Random();

    private String[] fonts = {"宋体","黑体","楷体_GB2312","SERIF","MONOSPACED","DIALOG"};//宋体", 华文楷体, 黑体, 华文新魏, 华文隶书, 微软雅黑,楷体_GB2312,Serif、SansSerif、Monospaced、Dialog 和 DialogInput
    private String chars = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
    private Color bgcolor = new Color(255,255,255);
    private String text;

    private Color randomColor() {
        int r = random.nextInt(150);
        int g = random.nextInt(150);
        int b = random.nextInt(150);
        return new Color(r,g,b);
    }

    private Font randomFont() {
        int fontId = random.nextInt(fonts.length);
        int style = random.nextInt(4);//Font.PLAIN（普通） Font.BOLD（加粗） Font.ITALIC（斜体）Font.BOLD+ Font.ITALIC（粗斜体）
        int size = random.nextInt(5) + 24;//以磅值为单位 pt
        return new Font(fonts[fontId],style,size);
    }

    private void drawLine(BufferedImage image) {
        int cnt = 2;
        Graphics2D g = (Graphics2D) image.getGraphics();
        while(cnt-- > 0 ) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.setStroke(new BasicStroke(1.5F));
            g.setColor(Color.PINK);
            g.drawLine(x1,y1,x2,y2);
        }
    }

    private char randomChar(){
        int charId = random.nextInt(chars.length());
        return chars.charAt(charId);
    }

    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(this.bgcolor);
        g.fillRect(0,0,width,height);
        return image;
    }

    /*
     * @Description: 获得随机产生的验证码
     * @Param: []
     * @return java.awt.image.BufferedImage
     **/
    public BufferedImage getImage() {
        BufferedImage image = createImage();
        Graphics2D g = (Graphics2D)image.getGraphics();
        StringBuilder sb =  new StringBuilder();
        for(int i = 0; i < 4; ++i) {
            String s = String.valueOf(randomChar());
            sb.append(s);
            float x = i * width / 4.0f;
            g.setFont(randomFont());
            g.setColor(randomColor());
            g.drawString(s,x,height - 5);
        }
        this.text = sb.toString();
        drawLine(image);
        return image;
    }

    public String getText(){
        return text;
    }

    /*
     * @description:将图片写入指定输出流
     * @Param: [image, out]
     * @return void
     **/
    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image,"JPEG",out);
    }
}
