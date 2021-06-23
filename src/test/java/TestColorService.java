import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import java.awt.*;

import services.ColorService;

class TestColorService {
    private static ColorService colorService;

    @BeforeAll
    public static void beforeAll() {
        colorService = new ColorService();
    }

    @Test
    public void getColorByScoreTest() {
        Color c = null;
        c = colorService.getColorByScore(0.09);
        assertEquals(255, c.getRed());
        assertEquals(229, c.getGreen());
        assertEquals(229, c.getBlue());

        c = colorService.getColorByScore(0.1);
        assertEquals(255, c.getRed());
        assertEquals(204, c.getGreen());
        assertEquals(204, c.getBlue());

        c = colorService.getColorByScore(0.11);
        assertEquals(255, c.getRed());
        assertEquals(204, c.getGreen());
        assertEquals(204, c.getBlue());

        c = colorService.getColorByScore(0.19);
        assertEquals(255, c.getRed());
        assertEquals(204, c.getGreen());
        assertEquals(204, c.getBlue());

        c = colorService.getColorByScore(0.2);
        assertEquals(255, c.getRed());
        assertEquals(178, c.getGreen());
        assertEquals(178, c.getBlue());

        c = colorService.getColorByScore(0.21);
        assertEquals(255, c.getRed());
        assertEquals(178, c.getGreen());
        assertEquals(178, c.getBlue());

        c = colorService.getColorByScore(0.29);
        assertEquals(255, c.getRed());
        assertEquals(178, c.getGreen());
        assertEquals(178, c.getBlue());

        c = colorService.getColorByScore(0.3);
        assertEquals(255, c.getRed());
        assertEquals(153, c.getGreen());
        assertEquals(153, c.getBlue());

        c = colorService.getColorByScore(0.31);
        assertEquals(255, c.getRed());
        assertEquals(153, c.getGreen());
        assertEquals(153, c.getBlue());

        c = colorService.getColorByScore(0.39);
        assertEquals(255, c.getRed());
        assertEquals(153, c.getGreen());
        assertEquals(153, c.getBlue());

        c = colorService.getColorByScore(0.4);
        assertEquals(255, c.getRed());
        assertEquals(127, c.getGreen());
        assertEquals(127, c.getBlue());

        c = colorService.getColorByScore(0.41);
        assertEquals(255, c.getRed());
        assertEquals(127, c.getGreen());
        assertEquals(127, c.getBlue());

        c = colorService.getColorByScore(0.49);
        assertEquals(255, c.getRed());
        assertEquals(127, c.getGreen());
        assertEquals(127, c.getBlue());

        c = colorService.getColorByScore(0.5);
        assertEquals(255, c.getRed());
        assertEquals(102, c.getGreen());
        assertEquals(102, c.getBlue());

        c = colorService.getColorByScore(0.51);
        assertEquals(255, c.getRed());
        assertEquals(102, c.getGreen());
        assertEquals(102, c.getBlue());

        c = colorService.getColorByScore(0.59);
        assertEquals(255, c.getRed());
        assertEquals(102, c.getGreen());
        assertEquals(102, c.getBlue());

        c = colorService.getColorByScore(0.6);
        assertEquals(255, c.getRed());
        assertEquals(76, c.getGreen());
        assertEquals(76, c.getBlue());

        c = colorService.getColorByScore(0.61);
        assertEquals(255, c.getRed());
        assertEquals(76, c.getGreen());
        assertEquals(76, c.getBlue());

        c = colorService.getColorByScore(0.69);
        assertEquals(255, c.getRed());
        assertEquals(76, c.getGreen());
        assertEquals(76, c.getBlue());

        c = colorService.getColorByScore(0.7);
        assertEquals(255, c.getRed());
        assertEquals(50, c.getGreen());
        assertEquals(50, c.getBlue());

        c = colorService.getColorByScore(0.71);
        assertEquals(255, c.getRed());
        assertEquals(50, c.getGreen());
        assertEquals(50, c.getBlue());

        c = colorService.getColorByScore(0.79);
        assertEquals(255, c.getRed());
        assertEquals(50, c.getGreen());
        assertEquals(50, c.getBlue());

        c = colorService.getColorByScore(0.8);
        assertEquals(255, c.getRed());
        assertEquals(25, c.getGreen());
        assertEquals(25, c.getBlue());

        c = colorService.getColorByScore(0.81);
        assertEquals(255, c.getRed());
        assertEquals(25, c.getGreen());
        assertEquals(25, c.getBlue());

        c = colorService.getColorByScore(0.89);
        assertEquals(255, c.getRed());
        assertEquals(25, c.getGreen());
        assertEquals(25, c.getBlue());

        c = colorService.getColorByScore(0.9);
        assertEquals(255, c.getRed());
        assertEquals(0, c.getGreen());
        assertEquals(0, c.getBlue());

        c = colorService.getColorByScore(0.91);
        assertEquals(255, c.getRed());
        assertEquals(0, c.getGreen());
        assertEquals(0, c.getBlue());

        c = colorService.getColorByScore(0.99);
        assertEquals(255, c.getRed());
        assertEquals(0, c.getGreen());
        assertEquals(0, c.getBlue());

        c = colorService.getColorByScore(1);
        assertEquals(255, c.getRed());
        assertEquals(0, c.getGreen());
        assertEquals(0, c.getBlue());

        c = colorService.getColorByScore(1.1);
        assertEquals(null, c);

        c = colorService.getColorByScore(2);
        assertEquals(null, c);

        c = colorService.getColorByScore(-0.1);
        assertEquals(null, c);

        c = colorService.getColorByScore(-1);
        assertEquals(null, c);
    }

    @AfterAll
    public static void afterAll() {

    }
}
