package com.german.kapitzapendulum;

import com.german.Displayable;
import com.german.physic.TimeVarying;
import com.jogamp.opengl.GLAutoDrawable;

public class KapitzaPendulum implements Displayable, TimeVarying {
    private final SliderCrankLinkage sliderCrankLinkage;

    public KapitzaPendulum() {
        sliderCrankLinkage = new SliderCrankLinkage();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        sliderCrankLinkage.display(glAutoDrawable);
    }

    @Override
    public void vary() {
        sliderCrankLinkage.vary();
    }

    /**
     * Увеличить угловую скорость кривошипа
     *
     * @param sign знак инкремента
     */
    public void increaseCrankR(int sign) {
        sliderCrankLinkage.increaseCrankW(sign);
    }
}
