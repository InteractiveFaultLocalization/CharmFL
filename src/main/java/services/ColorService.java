package services;

import java.awt.*;
import java.util.ArrayList;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.TextAttributes;

import models.bean.FileLineScoreData;
import models.bean.TestData;
import modules.PluginModule;

public class ColorService {
    private Color[] errorColors = new Color[10];
    private Editor editor = null;

    public ColorService() {
        errorColors[0] = new Color(102, 0, 0);
        errorColors[1] = new Color(142, 35, 35);
        errorColors[2] = new Color(202, 0, 0);
        errorColors[3] = new Color(204, 17, 0);
        errorColors[4] = new Color(205, 92, 92);
        errorColors[5] = new Color(198, 93, 87);
        errorColors[6] = new Color(255, 48, 48);
        errorColors[7] = new Color(255, 102, 102);
        errorColors[8] = new Color(255, 130, 130);
        errorColors[9] = new Color(255, 163, 163);
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public Color getColorByScore(double score) {
        if(score < 0) {
            return null;
        }
        if(score < 0.1) {
            return errorColors[9];
        }
        else if(score < 0.2) {
            return errorColors[8];
        }
        else if(score < 0.3) {
            return errorColors[7];
        }
        else if(score < 0.4) {
            return errorColors[6];
        }
        else if(score < 0.5) {
            return errorColors[5];
        }
        else if(score < 0.6) {
            return errorColors[4];
        }
        else if(score < 0.7) {
            return errorColors[3];
        }
        else if(score < 0.8) {
            return errorColors[2];
        }
        else if(score < 0.9) {
            return errorColors[1];
        }
        else if(score <= 1) {
            return errorColors[0];
        }
        else{
            return null;
        }
    }

    public void setColorsByEditor(TestData testData, String path) {
        int line = 0;
        double score = 0;
        if(testData != null) {
            for(int i = 0; i < testData.getClasses().size(); i++) {
                if(testData.getClasses().get(i).getPath().equals(path)) {
                    for(int j = 0; j < testData.getClasses().get(i).getMethods().size(); j++) {
                        for(int k = 0; k < testData.getClasses().get(i).getMethods().get(j).getStatements().size(); k++) {
                            line = testData.getClasses().get(i).getMethods().get(j).getStatements().get(k).getLine();
                            if(PluginModule.isTarantulaSelected()) {
                                score = testData.getClasses().get(i).getMethods().get(j).getStatements().get(k).getTarantula();
                            }
                            else if(PluginModule.isOchiaiSelected()) {
                                score = testData.getClasses().get(i).getMethods().get(j).getStatements().get(k).getOchiai();
                            }
                            else if(PluginModule.isDStarSelected() || PluginModule.isWongIISelected()) {
                                score = testData.getClasses().get(i).getMethods().get(j).getStatements().get(k).getWong2();
                            }
                            setLineColorByScore(line, score);
                        }
                    }
                    break;
                }
            }
        }
    }

    public void setLineColorByScore(int line, double score) {
        if(editor != null) {
            TextAttributes textattributes = new TextAttributes(null, getColorByScore(score), null, EffectType.LINE_UNDERSCORE, Font.PLAIN);
            editor.getMarkupModel().addLineHighlighter(line - 1, HighlighterLayer.ERROR, textattributes);
        }
    }

    public void removeColorsByEditor() {
        if(editor != null) {
            editor.getMarkupModel().removeAllHighlighters();
        }
    }
}
