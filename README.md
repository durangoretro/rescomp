# Rescomp

Rescomp is a java application Based on [SGDK](https://github.com/Stephane-D/SGDK)'s rescomp. This Java Based tool allows to generate all the binary information to import any resource (image, sprite...) also, this tool allows you to sign any ROM created for Durango.

To Run _rescomp_ you can use the next approach.

```bash
java -jar rescomp.jar -n name - i file.png -o out.h -m BACKGROUND
```

Where the options are:

* ```-n name```: Resource Name.
* ```-i inputfile```: Input File.
* ```-o outputfile```: Output File.
* ```-m MODE```: Mode; to select the operation Mode:

    * BACKGROUND: Background mode; for generate a binary from a background Image.
    * SPRITESHEET: Sprite Sheet mode; for generate binary from a Sprite Sheet and cut each frame.
    * SCREENSHOT: Generate information for a screenshot.
    * BINARY: Allow to store Binary Information.
    * FONT: Allow to generate information from a Font in a Image File.
    * SIGNER: Allow to Sign a Durango Rom.
    * STAMP: Allow to STAMP a Durango Rom.
    
* ```-w width```: set the Sprite Frame With.
* ```-h height```: set the Sprite Frame Height.
* ```-t title```:set the Title Information for signer.
* ```-d description```: set the Description Information for Signer.
