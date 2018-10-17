package io.nbs.ipfs.mm.ui.filters;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 * <p>
 * Author   : lanbery
 * Created  : 2018/10/17
 */
public class ImageChooserFilter extends FileFilter {
    private String ext;
    public ImageChooserFilter(String ext) {
        this.ext = ext;
    }

    @Override
    public boolean accept(File f) {
        if(f.isDirectory())return true;
        String fileName = f.getName();
        int index = fileName.lastIndexOf(".");
        if(index > 0 && index < fileName.length() -1 ){
            String extension = fileName.substring(index+1);
            if(extension.equalsIgnoreCase(ext))
                return true;
        }
        if(index<=0 && (ext==null||"".equals(ext)))return true;
        return false;
    }

    @Override
    public String getDescription() {
        String desc = "";
        if(ext.equalsIgnoreCase("png")){
            desc = "*.png";
        }
        if(ext.equalsIgnoreCase("jpg")){
            desc = "*.jpg";
        }
        if(ext.equalsIgnoreCase("jpeg")){
            desc = "*.jpeg";
        }
        if(ext.equalsIgnoreCase("gif")){
            desc = "*.gif";
        }
        return desc;
    }
}
