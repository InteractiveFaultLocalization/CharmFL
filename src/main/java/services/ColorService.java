package services;

import java.awt.*;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.TextAttributes;

import models.bean.TestData;
import modules.PluginModule;

public class ColorService {
    private Color[] errorColors = new Color[10];
    private Editor editor = null;

    /**
     * This initializes the colors
     */
    public ColorService() {
        errorColors[0] = new Color(255, 163, 163);
        errorColors[1] = new Color(255, 130, 130);
        errorColors[2] = new Color(255, 102, 102);
        errorColors[3] = new Color(255, 48, 48);
        errorColors[4] = new Color(198, 93, 87);
        errorColors[5] = new Color(205, 92, 92);
        errorColors[6] = new Color(204, 17, 0);
        errorColors[7] = new Color(202, 0, 0);
        errorColors[8] = new Color(142, 35, 35);
        errorColors[9] = new Color(102, 0, 0);
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    /**
     * For each score this provides a matching color
     * @param score
     * @return the appropriate color
     */
    public Color getColorByScore(double score) {
        Color color = null;
        double counter = 0;
        for (int i = 0; i < this.errorColors.length; i++) {
            counter = 0.1 + i * 0.1;
            if (score < counter){
                color = this.errorColors[i];
                break;
            }
        }
        if (counter == 1){
            color = errorColors[9];
        }
        return color;
    }

    /**
     * @param testData
     * @param path     Sets the color by score and the selected formula.
     */
    public void setColorsByEditor(TestData testData, String path) {
        int statementNumber = 0;
        double score = 0;
        if (testData != null) {
            for (int i = 0; i < testData.getClasses().size(); i++) {
                if (testData.getClasses().get(i).getRelativePath().equals(path)) {
                    for (int j = 0; j < testData.getClasses().get(i).getElements().size(); j++) {
                        for (int k = 0; k < testData.getClasses().get(i).getElements().get(j).getElements().size(); k++) {
                            var statement = testData.getClasses().get(i).getElements().get(j).getElements().get(k);
                            statementNumber = statement.getLine();
                            if (PluginModule.isTarantulaSelected()) {
                                score = statement.getTarantula();
                            } else if (PluginModule.isOchiaiSelected()) {
                                score = statement.getOchiai();
                            } else if (PluginModule.isDStarSelected() || PluginModule.isWongIISelected()) {
                                score = statement.getWong2();
                            }
                            setLineColorByScore(statementNumber, score);
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * Sets the statements color according to how likely it is to be faulty.
     * @param line
     * @param score
     */
    public void setLineColorByScore(int line, double score) {
        if (editor != null) {
            TextAttributes textattributes = new TextAttributes(null, getColorByScore(score), null, EffectType.LINE_UNDERSCORE, Font.PLAIN);
            editor.getMarkupModel().addLineHighlighter(line - 1, HighlighterLayer.ERROR, textattributes);
        }
    }

    public void removeColorsByEditor() {
        if (editor != null) {
            editor.getMarkupModel().removeAllHighlighters();
        }
    }
}
