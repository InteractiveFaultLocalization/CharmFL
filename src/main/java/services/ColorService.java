package services;

import java.awt.*;
import java.util.ArrayList;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.TextAttributes;

import models.bean.FileLineScoreData;

public class ColorService {
    private Color[] errorColors = new Color[10];
    private Editor editor = null;

    public ColorService() {
        errorColors[0] = new Color(255, 0, 0);
        errorColors[1] = new Color(255, 25, 25);
        errorColors[2] = new Color(255, 50, 50);
        errorColors[3] = new Color(255, 76, 76);
        errorColors[4] = new Color(255, 102, 102);
        errorColors[5] = new Color(255, 127, 127);
        errorColors[6] = new Color(255, 153, 153);
        errorColors[7] = new Color(255, 178, 178);
        errorColors[8] = new Color(255, 204, 204);
        errorColors[9] = new Color(255, 229, 229);
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

    public void setColorsByEditor(ArrayList<FileLineScoreData> fileTestData) {
        if(fileTestData != null) {
            for (int i = 0; i < fileTestData.size(); i++) {
                setLineColorByScore(fileTestData.get(i).getLineNumber(), fileTestData.get(i).getLineScore());
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
