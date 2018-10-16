package io.nbs.ipfs.mm.ui.components.validators;

import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 * <p>
 * Author   : lanbery
 * Created  : 2018/10/16
 */
public class MQDocument extends PlainDocument {
    private String  regex           = null;
    private int     maxLength       = -1;
    private double  maxValue        = 0;
    private boolean isMaxValue      = false;
    private Toolkit toolkit         = null;
    private boolean beep            = false;

    public MQDocument(){
        super();
        this.init();
    }

    public MQDocument(Content content){
        super(content);
        this.init();
    }

    private void init(){
        toolkit = Toolkit.getDefaultToolkit();
    }


}
