; Listing generated by Microsoft (R) Optimizing Compiler Version 19.28.29335.0 

include listing.inc

INCLUDELIB MSVCRTD
INCLUDELIB OLDNAMES

msvcjmc	SEGMENT
__D0D318F8_pch@h DB 01H
__BD00935D_windows@pch DB 01H
__ECC46B65_dllmain@cpp DB 01H
msvcjmc	ENDS
PUBLIC	DllMain
PUBLIC	__JustMyCode_Default
EXTRN	_RTC_InitBase:PROC
EXTRN	_RTC_Shutdown:PROC
EXTRN	__CheckForDebuggerJustMyCode:PROC
;	COMDAT pdata
pdata	SEGMENT
$pdata$DllMain DD imagerel $LN6
	DD	imagerel $LN6+90
	DD	imagerel $unwind$DllMain
pdata	ENDS
;	COMDAT rtc$TMZ
rtc$TMZ	SEGMENT
_RTC_Shutdown.rtc$TMZ DQ FLAT:_RTC_Shutdown
rtc$TMZ	ENDS
;	COMDAT rtc$IMZ
rtc$IMZ	SEGMENT
_RTC_InitBase.rtc$IMZ DQ FLAT:_RTC_InitBase
rtc$IMZ	ENDS
;	COMDAT xdata
xdata	SEGMENT
$unwind$DllMain DD 025053301H
	DD	0117231cH
	DD	07010001fH
	DD	0500fH
xdata	ENDS
; Function compile flags: /Odt
;	COMDAT __JustMyCode_Default
_TEXT	SEGMENT
__JustMyCode_Default PROC				; COMDAT
	ret	0
__JustMyCode_Default ENDP
_TEXT	ENDS
; Function compile flags: /Odtp /RTCsu /ZI
;	COMDAT DllMain
_TEXT	SEGMENT
tv64 = 192
hModule$ = 240
ul_reason_for_call$ = 248
lpReserved$ = 256
DllMain	PROC						; COMDAT
; File G:\Users\jackd\eclipse-workspace\windows\bvnative\dllmain.cpp
; Line 8
$LN6:
	mov	QWORD PTR [rsp+24], r8
	mov	DWORD PTR [rsp+16], edx
	mov	QWORD PTR [rsp+8], rcx
	push	rbp
	push	rdi
	sub	rsp, 248				; 000000f8H
	lea	rbp, QWORD PTR [rsp+32]
	mov	rdi, rsp
	mov	ecx, 62					; 0000003eH
	mov	eax, -858993460				; ccccccccH
	rep stosd
	mov	rcx, QWORD PTR [rsp+280]
	lea	rcx, OFFSET FLAT:__ECC46B65_dllmain@cpp
	call	__CheckForDebuggerJustMyCode
; Line 9
	mov	eax, DWORD PTR ul_reason_for_call$[rbp]
	mov	DWORD PTR tv64[rbp], eax
; Line 17
	mov	eax, 1
; Line 18
	lea	rsp, QWORD PTR [rbp+216]
	pop	rdi
	pop	rbp
	ret	0
DllMain	ENDP
_TEXT	ENDS
END
