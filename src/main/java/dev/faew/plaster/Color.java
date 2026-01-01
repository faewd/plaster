package dev.faew.plaster;

public class Color {

    public static final Color TRANSPARENT = Color.of(0x00000000);
    public static final Color BLACK = Color.of(0xff000000);
    public static final Color DARK_GRAY = Color.of(0xff494949);
    public static final Color LIGHT_GRAY = Color.of(0xff828282);
    public static final Color WHITE = Color.of(0xffffffff);

    public static final Color BLUE = Color.of(0xFF4841CC);
    public static final Color CYAN = Color.of(0xFF2CA399);
    public static final Color GREEN = Color.of(0xFF35A52B);
    public static final Color YELLOW = Color.of(0xFFD3A739);
    public static final Color RED = Color.of(0xFFA02B2B);
    public static final Color MAGENTA = Color.of(0xFF9E2A9E);

    public static final Color DARK_BLUE = Color.of(0xFF25105E);
    public static final Color DARK_CYAN = Color.of(0xFF105953);
    public static final Color DARK_GREEN = Color.of(0xFF165B10);
    public static final Color DARK_YELLOW = Color.of(0xFF604911);
    public static final Color DARK_RED = Color.of(0xFF560F0F);
    public static final Color DARK_MAGENTA = Color.of(0xFF540F4D);

    private static final int RED_MASK   = 0x00ff0000;
    private static final int GREEN_MASK = 0x0000ff00;
    private static final int BLUE_MASK  = 0x000000ff;

    private final int color;

    private Color(int color) {
        this.color = color;
    }

    public static Color of(int col) {
        return new Color(col);
    }

    public static Color fromRGB(int r, int g, int b) {
        return fromARGB(0xff, r, g, b);
    }

    public static Color fromARGB(int a, int r, int g, int b) {
        return new Color((a << 24) + (r << 16) + (g << 8 ) +  b);
    }

    public static Color fromHSLA(int h, int s, int l, int a) {
        final var S = s / 100d;
        final var L = l / 100d;

        final var C = (1 - Math.abs(2 * L - 1)) * S;
        final var X = C * (1 - Math.abs((h / 60d) % 2 - 1));
        final var m = L - C / 2d;

        double r, g, b;

        if (h < 60)  { r = C; g = X; b = 0; }
        else if (h < 120) { r = X; g = C; b = 0; }
        else if (h < 180) { r = 0; g = C; b = X; }
        else if (h < 240) { r = 0; g = X; b = C; }
        else if (h < 300) { r = X; g = 0; b = C; }
        else              { r = C; g = 0; b = X; }

        return fromARGB(
                a,
                (int)Math.ceil((r + m) * 0xff),
                (int)Math.ceil((g + m) * 0xff),
                (int)Math.ceil((b + m) * 0xff)
        );
    }

    public static Color fromHSL(int h, int s, int l) {
        return fromHSLA(h, s, l, 0xff);
    }

    public static Color fromHex(String hex) {
        final var r = hex.length() == 6 ? hex.substring(0, 2) : hex.substring(0, 1).repeat(2);
        final var g = hex.length() == 6 ? hex.substring(2, 4) : hex.substring(1, 2).repeat(2);
        final var b = hex.length() == 6 ? hex.substring(4, 6) : hex.substring(2, 3).repeat(2);
        return Color.fromRGB(
                Integer.parseInt(r, 16),
                Integer.parseInt(g, 16),
                Integer.parseInt(b, 16)
        );
    }

    public static Color gray(int k) {
        return fromRGB(k, k, k);
    }

    public static Color gray(double d) {
        return gray((int)(0xff * d));
    }

    public static Color composite(Color A, Color B) {
        if (B.isOpaque()) return B;

        final var ratio = B.alpha() / 255d;

        final var a = Math.min(255, A.alpha() + B.alpha());
        final var r = (int)Math.round(B.red() * ratio) + (int)Math.round(A.red() * (1 - ratio));
        final var g = (int)Math.round(B.green() * ratio) + (int)Math.round(A.green() * (1 - ratio));
        final var b = (int)Math.round(B.blue() * ratio) + (int)Math.round(A.blue() * (1 - ratio));

        return Color.fromARGB(a, r, g, b);
    }

    public static Color multiply(Color A, Color B, double strength) {
        final var a = A.alpha();
        final var r = (int)Math.round((A.red() / 255d) * (B.red() / 255d) * strength * 255);
        final var g = (int)Math.round((A.green() / 255d) * (B.green() / 255d) * strength * 255);
        final var b = (int)Math.round((A.blue() / 255d) * (B.blue() / 255d) * strength * 255);

        return Color.fromARGB(a, r, g, b);
    }

    public int value() {
        return this.color;
    }

    public int alpha() {
        return this.color >>> 24;
    }

    public double opacity() {
        return this.alpha() / 255d;
    }

    public boolean isOpaque() {
        return this.alpha() == 255;
    }

    public boolean isTransparent() {
        return this.alpha() == 0;
    }

    public Color opaque() {
        return Color.of(0xff000000 | this.value());
    }

    public Color withOpacity(double o) {
        return Color.fromARGB(
                (int)(o * 0xff),
                this.red(),
                this.green(),
                this.blue()
        );
    }

    public String toHex3() {
        final var r = Integer.toString((int)Math.round(red() / 16d) * 16, 16).substring(0, 1);
        final var g = Integer.toString((int)Math.round(green() / 16d) * 16, 16).substring(0, 1);
        final var b = Integer.toString((int)Math.round(blue() / 16d) * 16, 16).substring(0, 1);
        return r + g + b;
    }

    public int red() {
        return (this.color & RED_MASK) >> 16;
    }

    public int green() {
        return (this.color & GREEN_MASK) >> 8;
    }

    public int blue() {
        return this.color & BLUE_MASK;
    }

    public int hue() {
        final var r = red() / 255d;
        final var g = green() / 255d;
        final var b = blue() / 255d;
        final var min = Math.min(r, Math.min(g, b));
        final var max = Math.max(r, Math.max(g, b));

        if (min == max) return 0;

        final var h =
                max == r ?    (g - b)      / (max - min)
                        :    max == g ?    (2 + (b - r) / (max - min))
                        : /* max == b ? */ (4 + (r - g) / (max - min));

        final var scaled = (int)Math.round(h * 60);

        return scaled < 0 ? 360 + scaled : scaled;
    }

    public Color hueRotate(int deg) {
        final var h = (this.hue() + deg) % 360;
        final var s = this.saturation();
        final var l = this.luminosity();
        final var a = this.alpha();

        return fromHSLA(h, s, l, a);
    }

    public int saturation() {
        final var r = red() / 255d;
        final var g = green() / 255d;
        final var b = blue() / 255d;
        final var min = Math.min(r, Math.min(g, b));
        final var max = Math.max(r, Math.max(g, b));
        final var L = luminosity() / 100d;
        if (L == 1) return 0;

        final var s = (max - min) / (1 - Math.abs(L * 2 - 1));

        return (int)Math.round(s * 100);
    }

    public int luminosity() {
        final var r = red() / 255d;
        final var g = green() / 255d;
        final var b = blue() / 255d;
        final var min = Math.min(r, Math.min(g, b));
        final var max = Math.max(r, Math.max(g, b));

        return (int)Math.round(50 * (min + max));
    }

    public Color darker() {
        return Color.fromHSLA(hue(), saturation(), (int)(luminosity() * 0.7), alpha());
    }

    public Color tint(Color tintCol) {
        final var h = tintCol.hue();
        final var s = (int)(tintCol.saturation() * (this.saturation() / 100d));
        final var l = (int)(this.luminosity() * (this.luminosity() / 100d));
        final var a = (int)(tintCol.alpha() * (this.alpha() / 255d));
        return Color.fromHSLA(h, s, l, a);
    }

    public Color recolor(Color newCol) {
        final var h = newCol.hue();
        final var s = newCol.saturation();
        final var l = this.luminosity();
        final var a = (int)(newCol.alpha() * (this.alpha() / 255d));
        return Color.fromHSLA(h, s, l, a);
    }
}