; Listing generated by Microsoft (R) Optimizing Compiler Version 19.28.29335.0 

include listing.inc

INCLUDELIB MSVCRTD
INCLUDELIB OLDNAMES

msvcjmc	SEGMENT
__D0D318F8_pch@h DB 01H
__BD00935D_windows@pch DB 01H
__AF77712E_bvnative@cpp DB 01H
msvcjmc	ENDS
PUBLIC	?ticks@@YA_KXZ					; ticks
PUBLIC	__JustMyCode_Default
EXTRN	__imp_GetTickCount64:PROC
EXTRN	_RTC_InitBase:PROC
EXTRN	_RTC_Shutdown:PROC
EXTRN	__CheckForDebuggerJustMyCode:PROC
;	COMDAT pdata
pdata	SEGMENT
$pdata$?ticks@@YA_KXZ DD imagerel $LN3
	DD	imagerel $LN3+58
	DD	imagerel $unwind$?ticks@@YA_KXZ
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
$unwind$?ticks@@YA_KXZ DD 025051e01H
	DD	010a230fH
	DD	07003001dH
	DD	05002H
xdata	ENDS
; Function compile flags: /Odt
;	COMDAT __JustMyCode_Default
_TEXT	SEGMENT
__JustMyCode_Default PROC				; COMDAT
	ret	0
__JustMyCode_Default ENDP
_TEXT	ENDS
; Function compile flags: /Odtp /RTCsu /ZI
;	COMDAT ?ticks@@YA_KXZ
_TEXT	SEGMENT
?ticks@@YA_KXZ PROC					; ticks, COMDAT
; File G:\Users\jackd\eclipse-workspace\windows\bvnative\bvnative.cpp
; Line 5
$LN3:
	push	rbp
	push	rdi
	sub	rsp, 232				; 000000e8H
	lea	rbp, QWORD PTR [rsp+32]
	mov	rdi, rsp
	mov	ecx, 58					; 0000003aH
	mov	eax, -858993460				; ccccccccH
	rep stosd
	lea	rcx, OFFSET FLAT:__AF77712E_bvnative@cpp
	call	__CheckForDebuggerJustMyCode
; Line 6
	call	QWORD PTR __imp_GetTickCount64
; Line 7
	lea	rsp, QWORD PTR [rbp+200]
	pop	rdi
	pop	rbp
	ret	0
?ticks@@YA_KXZ ENDP					; ticks
_TEXT	ENDS
END
