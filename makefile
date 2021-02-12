###############################################################################
# windows project makefile |
#--------------------------+---------------------------------------------------
#                 XX
#                  X
#                  X
# XXX X    XXXX    X  XX   XXXXX
#  X X X       X   X  X   X     X
#  X X X   XXXXX   X X    XXXXXXX
#  X X X  X    X   XXX    X
#  X X X  X    X   X  X   X     X
# XX X XX  XXXX X XX   XX  XXXXX
#                                            Author: Jack Menendez
#                                           Created: 1/5/2021
#                                           Version: 1.0.0
#------------------------------------------------------------------------------

PROJECT_ROOT=$(dir $(abspath $(lastword $MAKEFILE_LIST)))
PLATFORM = X64
PROJECT = windows
SUBPROJECT = src/main/c
WORK = temp
LNK = link.exe
CXX = cl.exe
CC = cl.exe
SRC = $(PROJECT)
BIN = target
DOC = doc/assembly/$(PLATFORM)/$(BUILD_MODE)
TDIR = $(WORK)/$(PROJECT)/$(PLATFORM)/$(BUILD_MODE)
OBJ = $(TDIR)
FTDIR = $(PROJECT_ROOT)$(WORK)
FBIN = $(PROJECT_ROOT)$(BIN)
JNIHEADER = $(SUBPROJECT)/org_bv_com_windows_jni_BVNativeJNI.h
JNIVSAM = $(SUBPROJECT)/org_bv_com_vsam_jni_VSAMJNI.h
JNISOURCE = src/main/java/org/bv/com/windows/jni/BVNativeJNI.java
JNIVSAMSOURCE = src/main/java/org/bv/com/vsam/jni/VSAMJNI.java
JAVAC = javac.exe
JAVACF = -h

.SUFFIXES : .obj .dll


default : 
	cd $(SUBPROJECT) && $(MAKE)

all : clean default

clean :
	cd $(SUBPROJECT) && $(MAKE) clean

$(JNIHEADER) : $(JNISOURCE)
	$(JAVAC) $(JAVACF) $(SUBPROJECT) $(JNISOURCE)

$(JNIVSAM) : $(JNIVSAMSOURCE)
	$(JAVAC) $(JAVACF) $(SUBPROJECT) $(JNIVSAMSOURCE)
	