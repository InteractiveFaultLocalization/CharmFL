package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import modules.PluginModule;
import org.jetbrains.annotations.NotNull;

import services.FlService;
import services.FlServiceImpl;
import ui.AdvancedOptions;

public class PluginAdvancedOptions extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        AdvancedOptions dialog = new AdvancedOptions();
        if(dialog.showAndGet()) {
            PluginModule.setTarantulaSelected(dialog.isTarantulaRadioButton());
            PluginModule.setOchiaiSelected(dialog.isOchiaiRadioButton());
            PluginModule.setDStarSelected(dialog.isDStarRadioButton());
            PluginModule.setWongIISelected(dialog.getWong2RadioButton());

            PluginModule.setMaximumSelected(dialog.isMaximumRadioButton());
            PluginModule.setMinimumSelected(dialog.isMinimumRadioButton());
            PluginModule.setAverageSelected(dialog.isAverageRadioButton());
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        FlService flService = new FlServiceImpl();
        if(flService.isViewResultTableDialogOpened() || flService.isTestDataCollecting()) {
            e.getPresentation().setEnabled(false);
        }
        else if(!flService.isViewResultTableDialogOpened() && !flService.isTestDataCollecting()){
            e.getPresentation().setEnabled(true);
        }
    }
}