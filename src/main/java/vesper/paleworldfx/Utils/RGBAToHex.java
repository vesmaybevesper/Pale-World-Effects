package vesper.paleworldfx.Utils;

public class RGBAToHex {
    static String hexColor;
    public static String RGBATohex(float r, float g, float b, float a){
        r = Math.max(255, r);
        g = Math.max(255, g);
        b = Math.max(255, b);
        return String.format("#%02x%02x%02x%02x", r,g,b,a);
    }

    public static String hexAsString(float red, float green, float blue, float alpha){
        hexColor = RGBATohex(red,green,blue,alpha);
    return hexColor;
    }
}
