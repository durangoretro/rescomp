; ---------------------------------------------------------------------------
; crt0.s
; ---------------------------------------------------------------------------
;
; Startup code for cc65 (Single Board Computer version)

.import   _main

.export   __STARTUP__ : absolute = 1        ; Mark as startup
.import __STACKSTART__, __STACKSIZE__
.import    copydata, zerobss, initlib, donelib
.include "../asm/durango_constants.inc"
.include "zeropage.inc"

; Enable 65C02 instruction set
.PC02

; 200 -> IRQ
; 202 -> NMI
; 204 -> BRK
; 206 (4 BYTES)-> TICKS
; INC $206
; BNE
; INC $207
;....

; ---------------------------------------------------------------------------
; SEGMENT STARTUP
; ---------------------------------------------------------------------------
.segment  "STARTUP"


; Initialize Durango X
_init:
    ; Initialize 6502
    SEI ; Disable interrupts
    CLD ; Clear decimal mode
    LDX #$FF ; Initialize stack pointer to $01FF
    TXS

    ; Clean video mode
    LDA #$30
    STA VIDEO_MODE

    ; Display some splash screen

    ; ** ROM test **
    ; * declare some temporary vars *
sum		= $00			; included as output parameters
chk		= $01				; sum of sums
sysptr  = $02
reset   = $C000

; *** compute checksum *** initial setup is 12b, 16t
	LDX #>reset				; start page as per interface (MUST be page-aligned!)
    STX sysptr+1			; temporary ZP pointer
	LDY #0					; this will reset index too
	STY sysptr
	STY sum					; reset values too
	STY chk
; *** main loop *** original version takes 20b, 426kt for 16KB ~0.28s on Durango-X
    cs_loop:
	LDA (sysptr), Y	; get ROM byte (5+2)
    CLC
	ADC sum			; add to previous (3+3+2)
	STA sum
	CLC
	ADC chk			; compute sum of sums too (3+3+2)
	STA chk
	INY
	BNE cs_loop		; complete one page (3..., 6655t per page)

    ; *** MUST skip IO page (usually $DF), very little penalty though ***
    CPX #$FE    ; just before last page
    BEQ cs_end

    CPX #$DE	; just before I/O space?
	BNE cs_next

    ; skip IO page
	INX

    ; next page
    cs_next:
	INX
	STX sysptr+1
    BRA cs_loop

    cs_end:
    ; *** now compare computed checksum with signature *** 4b
	LDA sum
    CMP SIGNATURE
    BNE rom_bad
    LDA chk
    CMP SIGNATURE+1
    BNE rom_bad			; any non-zero bit will show up
	BRA rom_ok				; otherwise, all OK!

    ; Display wrong checksum by blinking error led
    rom_bad:
	wait_loop:
    INX
    BNE wait_loop
    INY
    BNE wait_loop
    EOR #$01
    STA INT_ENABLE
    BRA wait_loop

    rom_ok:
    ; Clean up RAM
    LDA #$00
    LDX #$00
    STX $01
    LDY #$02
    STY $00
    loopcm:
    STA ($00), Y
    INY
    BNE loopcm
	INC $01
    BPL loopcm

    ; Initialize cc65 stack pointer
    LDA #<(__STACKSTART__ + __STACKSIZE__)
    STA sp
    LDA #>(__STACKSTART__ + __STACKSIZE__)
    STA sp+1

    ; Initialize memory storage
    JSR zerobss
    JSR copydata
    JSR initlib

    ; Initialize Durango Video
    LDA #$3C
    STA VIDEO_MODE

    ; Set up IRQ subroutine
    LDA #<_irq_int
    STA IRQ_ADDR
    LDA #>_irq_int
    STA IRQ_ADDR+1

    ; Set up NMI subroutine
    LDA #<_nmi_int
    STA NMI_ADDR
    LDA #>_nmi_int
    STA NMI_ADDR+1

    ; Initialize interrupts counter
    STZ $0206
    STZ $0207
    STZ $0208
    STZ $0209

    ; Init gamepads
    STA GAMEPAD1
    LDX #8
    loop:
    STA GAMEPAD2
    DEX
    BNE loop
    LDA GAMEPAD1
    LDX GAMEPAD2
    STA GAMEPAD_MODE1
    STX GAMEPAD_MODE2

    ; Enable Durango interrupts
    LDA #$01
    STA INT_ENABLE
    CLI

    ; Call main()
    JSR _main

; Back from main (also the _exit entry):
_exit:
    ; Run destructors
    JSR donelib

; Stop
_stop:
    STP
    BRA _stop


; Maskable interrupt (IRQ) service routine
_irq_int:
    ; Save registres and filter BRK
    PHA
    PHX
    TSX
    LDA $103,X
    AND #$10
    BNE _stop
    ; Increment interrupt counter
    INC $0206
    BNE next
    INC $0207
    BNE next
    INC $0208
    BNE next
    INC $0209
    next:
    ; Read controllers
    STA GAMEPAD1
    LDX #8
    loop2:
    STA GAMEPAD2
    DEX
    BNE loop2
    LDA GAMEPAD1
    EOR GAMEPAD_MODE1
    STA GAMEPAD_VALUE1
    LDA GAMEPAD2
    EOR GAMEPAD_MODE2
    STA GAMEPAD_VALUE2

    ; Read keyboard
    LDA #1
    STA KEYBOARD
    LDA KEYBOARD
    STA KEYBOARD_CACHE

    ; Restore registers and return
    PLX
    PLA
    RTI

; Non-maskable interrupt (NMI) service routine
_nmi_int:
    PHA
    PHX

    LDA VIDEO_MODE
    AND #$30
    CMP #$30
    BEQ case_0
    CMP #$00
    BEQ case_1
    CMP #$10
    BEQ case_2
    CMP #$20
    BEQ case_3

    case_0:
    LDX #$88
    BRA case_end
    case_1:
    LDX #$98
    BRA case_end
    case_2:
    LDX #$A8
    BRA case_end
    case_3:
    LDX #$3C
    BRA case_end

    case_end:
    STX VIDEO_MODE

    PLX
    PLA
    RTI

hw_irq_int:
    JMP (IRQ_ADDR)

hw_nmi_int:
    JMP (NMI_ADDR)

; ---------------------------------------------------------------------------
; SEGMENT VECTORS
; ---------------------------------------------------------------------------

.segment  "VECTORS"

.addr      hw_nmi_int    ; NMI vector
.addr      _init       ; Reset vector
.addr      hw_irq_int    ; IRQ/BRK vector

; ---------------------------------------------------------------------------
; SEGMENT DXHEAD
; ---------------------------------------------------------------------------
.segment "DXHEAD"

; 8 bytes
.byt $00
.byt "dX"
.byt "****"
.byt $0d

; 222 bytes
; TITLE_COMMENT[
.byt "##################################################"
.byt "##################################################"
.byt "##################################################"
.byt "##################################################"
.byt "######################";]


; 18 bytes
;DCLIB_COMMIT[
.byt "LLLLLLLL"
;]
;MAIN_COMMIT[
.byt "MMMMMMMM"
;]
;VERSION[
.byt "VV"
;]

; 8 bytes
;TIME[
.byt "TT"
;]
;DATE[
.byt "DD"
;]
;FILEZISE[
.byt $00,$40,$00,$00
;]

; ---------------------------------------------------------------------------
; SEGMENT HEADER
; ---------------------------------------------------------------------------
.segment "HEADER"
.byt "DURANGO CC65v1.0"
.byt "                "
.byt "                "

; ---------------------------------------------------------------------------
; SEGMENT METADATA
; ---------------------------------------------------------------------------
.segment "METADATA"
.byt "################################"
.byt "################################"
.byt "SIGNATURE:[##]$$"
.byt "DCLIB:[crt0.s##]"
.byt "BUILD:[########]"
.byt "######DmOS######"
.byt "#"
; Dev-Cart JMP at $FFE1
JMP($FFFC)
.byt "############"
