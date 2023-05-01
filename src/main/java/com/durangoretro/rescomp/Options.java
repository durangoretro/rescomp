package com.durangoretro.rescomp;


/**
 * Command Line Options
 */
public enum Options {

    /**
     * Name Option
     */
    NAME("n","name",true,"Resource name",true),
    /**
     * Input File Option
     */
    INPUT("i","input",true, "File Input",true),
    /**
     * Output File Option
     */
    OUTPUT("o","output",true,"File Output",true),
    /**
     * Mode Option
     */
    MODE("m","mode",true, "Operation mode. Example BACKGROUND",true),
    /**
     * Width Option (optional)
     */
    WIDTH("w","width",true,"Sprite Width",false),
    /**
     * Height Option (Optional)
     */
    HEIGHT("h","height",true,"Sprite Height",false),
    /**
     * Title Option (Optional)
     */
    TITLE("t","title",true,"Game Title",false),
    /**
     * Description Option (Optional)
     */
    DESCRIPTION("d","description",true,"Game Description",false);


    /**
     * Option
     */
    private final String opt;
    /**
     * Long Version Option
     */
    private final String longOpt;
    /**
     * Has Arguments
     */
    private final Boolean hasArgs;
    /**
     * Option Description
     */
    private final String description;
    /**
     * Option Required
     */
    private final Boolean required;

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public Boolean getHasArgs() {
        return hasArgs;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getRequired() {
        return required;
    }

    Options(String opt, String longOpt, Boolean hasArgs, String description, Boolean required) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.hasArgs = hasArgs;
        this.description = description;
        this.required = required;
    }


}
