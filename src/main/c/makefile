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
PLATFORM = $(Platform)
PROJECT = windows
WORK = tempc
LNK = link.exe
CXX = cl.exe
CC = cl.exe
SRC = .
BIN = ../resources
DOC = doc/assembly/$(PLATFORM)/$(BUILD_MODE)
TDIR = $(WORK)/$(PROJECT)/$(PLATFORM)/$(BUILD_MODE)
OBJ = $(TDIR)
FTDIR = $(PROJECT_ROOT)$(WORK)
FBIN = $(PROJECT_ROOT)$(BIN)

###############################################################################
# The precompiled-header options are /Yc (Create Precompiled Header File) and 
# /Yu (Use Precompiled Header File). Use /Yc to create a precompiled header. 
# When used with the optional hdrstop pragma, /Yc lets you precompile both 
# header files and source code. Select /Yu to use an existing precompiled 
# header in the existing compilation. You can also use /Fp with the /Yc 
# and /Yu options to provide an alternative name for the precompiled header.

# Create precompiled headers
CXXFLAGS_CREATE_PCH = /Yc"pch.h"

# Use precompiled headers
CXXFLAGS_USE_PCH = /Yu"pch.h"

# preprocessor options
##########  C O M M O N  B U I L D  F L A G S  ###########
### Preprocessor Flags
######################
CXXPPFLAGS = /D "BVNATIVE_EXPORTS"
CXXPPFLAGS += /D "_WINDOWS"
CXXPPFLAGS += /D "_USRDLL" 
CXXPPFLAGS += /D "_WINDLL" 
CXXPPFLAGS += /D "_UNICODE" 
CXXPPFLAGS += /D "UNICODE"
CXXPPFLAGS += /I"C:\PROGRAM FILES\JAVA\JDK-15.0.1\INCLUDE" 
CXXPPFLAGS += /I"C:\PROGRAM FILES\JAVA\JDK-15.0.1\INCLUDE\WIN32"
# Compiler Flags
################
# Compile only *
CXXFLAGS = /c
# control format of diagnostics *
CXXFLAGS += /diagnostics:column 
# Specifies the model of exception handling.
# s: standard C++ stack unwinding
# c: no exceptions cross extern function boundaries. *
CXXFLAGS += /EHsc
# deprecated. Report internal compiler errors to Microsoft
CXXFLAGS += /errorReport:prompt
# path and name of assembly listing file
CXXFLAGS += /Fa"$(subst /,\,$(DOC)//)"
# Use full pathname in diagnostics
CXXFLAGS += /FC
# name of the .pdb file
CXXFLAGS += /Fd"$(subst /,\,$(BIN)/vc142.pdb)"
# path and name of object file
CXXFLAGS += /Fo"$(subst /,\,$(TDIR)//)"
# Floating point model *
CXXFLAGS += /fp:precise
# Precompiled header file
CXXFLAGS += /Fp"$(subst /,\,$(TDIR)/$(PROJECT).pch)"
# C++ module output
CXXFLAGS += /ifcOutput"$(subst /,\,$(TDIR)//)"
# Uses the __cdecl calling convention
CXXFLAGS += /Gd
# Deprecated. Enables minimal rebuild. *
CXXFLAGS += /Gm-
# Buffers security check. *
CXXFLAGS += /GS
# Suppresses display of sign-on banner. *
CXXFLAGS += /nologo
# Set standard-conformance mode. *
CXXFLAGS += /permissive-
# Enables additional security features and warnings. *
CXXFLAGS += /sdl
# Sets which warning level to output. *
CXXFLAGS += /W3
# Treats all warnings as errors. *
CXXFLAGS += /WX-
# Specifies standard behaviors. wchar_t is a native type, not a typedef 
# (on by default). *
CXXFLAGS += /Zc:wchar_t
# Specifies standard behaviors. Remove unreferenced function or 
# data if it is COMDAT or has internal linkage only (off by default). *
CXXFLAGS += /Zc:inline 
# Specifies standard behaviors. for scoping rules *
CXXFLAGS += /Zc:forScope 
# Linker flags
##############   
# Builds a DLL. 
LFLAGS = /DLL 
# Specifies whether to generate an executable image that's rebased at 
# load time by using the address space layout randomization (ASLR) feature.  
LFLAGS += /DYNAMICBASE "kernel32.lib" "user32.lib" \
 "gdi32.lib" "winspool.lib" "comdlg32.lib" "advapi32.lib" "shell32.lib" \
 "ole32.lib" "oleaut32.lib" "uuid.lib" "odbc32.lib" "odbccp32.lib" 
# Deprecated. Error reporting is controlled by Windows Error 
# Reporting (WER) settings.  
LFLAGS += /ERRORREPORT:PROMPT
# Overrides the default import library name.  
LFLAGS += /IMPLIB:"$(subst /,\,$(FBIN)/$(PROJECT).lib)"
# path to the jni libs if any 
LFLAGS += /LIBPATH:"C:\PROGRAM FILES\JAVA\JDK-15.0.1\LIB"
# Specifies the target platform.  
LFLAGS += /MACHINE:X64 
# Creates a side-by-side manifest file and optionally embeds it 
# in the binary.  
LFLAGS += /MANIFEST 
# Changes the default name of the manifest file.  
LFLAGS += /ManifestFile:"$(subst /,\,$(FBIN)/$(PROJECT).dll.intermediate.manifest)"
# Specifies whether User Account Control (UAC) information is 
# embedded in the program manifest.  
LFLAGS += /MANIFESTUAC:NO 
# Suppresses the startup banner.   
LFLAGS += /NOLOGO 
# Marks an executable as verified to be compatible with the Windows 
# Data Execution Prevention feature.  
LFLAGS += /NXCOMPAT 
# Specifies the output file name.  
LFLAGS += /OUT:"$(subst /,\,$(FBIN)/$(PROJECT).dll)"
# Creates a PDB file.  
LFLAGS += /PDB:"$(subst /,\,$(FBIN)/$(PROJECT).pdb)" 
# Tells the operating system how to run the executeable file.  
LFLAGS += /SUBSYSTEM:WINDOWS 
# Specifies the resource ID of the linker-generated type library.  
LFLAGS += /TLBID:1 

ifeq ($(BUILD_MODE),debug)
#####  D E B U G   B U I L D  #########
# Debug preprocessor flags
##########################
# Debug preprocessor flag *
CXXPPFLAGS += /D "_DEBUG"
# Debug compiler flags
######################
# enable native just my code *
CXXFLAGS += /JMC
# Defines _DEBUG, _MT, and _DLL and causes the application to use the 
# debug multithread-specific and DLL-specific version of the run-time 
# library. It also causes the compiler to place the library name 
# MSVCRTD.lib into the .obj file. *
CXXFLAGS += /MDd
# Disables optimization. *
CXXFLAGS += /Od
# Used to enable and disable the run-time error checks feature, in 
# conjunction with the runtime_checks pragma. *
CXXFLAGS += /RTC1
# All source files are C++
CXXFLAGS += /TP
# Generates complete debugging information.
CXXFLAGS += /ZI
# Debug Link flags
##################
# Creates debugging information. 
LFLAGS += /DEBUG:FASTLINK
# Controls incremental linking.
LFLAGS += /INCREMENTAL

else ifeq ($(BUILD_MODE),release)
########  R E L E A S E  F L A G S #########
# Release linker flags
######################
# Creates debugging information.
LFLAGS += /DEBUG  
# Controls incremental linking.
/INCREMENTAL:NO
# Specifies link-time code generation.
LFLAGS += /LTCG:incremental 
# Control optimizations, fold common data
LFLAGS += /OPT:ICF
#  eliminates functions and data that are 
# never referenced; /OPT:NOREF keeps functions and data that are 
# never referenced.
LFLAGS += /OPT:REF 
# The /PGD option is deprecated. Starting in Visual Studio 2015, 
# prefer the /GENPROFILE or /FASTGENPROFILE linker options instead. 
# This option is used to specify the name of the .pgd file used by the 
# profile-guided optimization process.
LFLAGS += /PGD:"$(subst /,\,$(BIN)/$(PROJECT).pgd)" 
# Preprocessor release mode
CXXPPFLAGS += /D "NDEBUG"  
# Enables whole program optimization.
CXXFLAGS += /GL 
# Enables function-level linking.
CXXFLAGS += /Gy 
# Creates a multithreaded DLL using MSVCRT.lib.
CXXFLAGS += /MD
# Creates fast code.
CXXFLAGS += /O2  
# Generates intrinsic functions.
CXXFLAGS += /Oi 
# Generates complete debugging information.
CXXFLAGS += /Zi 
else
	$(error Build mode $(BUILD_MODE) not supported by this makefile)
endif
CXXFLAGS += $(CXXPPFLAGS)
SOURCEFILES = $(wildcard *.cpp)
SOURCEFILES += $(wildcard *.c)
# OBJECTFILES =$(patsubst %.cpp,$(OBJ)/%.obj,$(SOURCEFILES))
OBJECTFILES = $(OBJ)/bvnative.obj $(OBJ)/dllmain.obj \
	$(OBJ)/org_bv_com_windows_jni_BVNativeJNI.obj $(OBJ)/pch.obj
HEADERFILES = $(wildcard *.h)
DLL = $(BIN)/$(PROJECT).dll

.SUFFIXES : .obj .dll

default: $(BIN) $(TDIR) $(DOC) $(DLL) 

all: clean default 

clean:
	-rm -rfv $(TDIR)/*.*
	-rm -fv $(BIN)/$(PROJECT).*
	-rm -rfv $(DOC)/*.*
	
$(DLL) : $(OBJECTFILES)
	$(LNK) $(LFLAGS) $(subst /,\,$^)
	
$(DOC) : ../doc/assembly/$(PLATFORM)
	-mkdir $(subst /,\,$@)
	
../doc/assembly/$(PLATFORM) : ../doc/assembly
	-mkdir $(subst /,\,$@)
	
../doc/assembly : ../doc
	-mkdir $(subst /,\,$@)
	
../doc :
	-mkdir $(subst /,\,$@)
	
$(TDIR) : $(WORK)/$(PROJECT)/$(PLATFORM)
	-mkdir $(subst /,\,$@)
	
$(WORK)/$(PROJECT)/$(PLATFORM) : $(WORK)/$(PROJECT)
	-mkdir $(subst /,\,$@)
	
$(WORK)/$(PROJECT) : $(WORK)
	-mkdir $(subst /,\,$@)
	
$(WORK) :
	-mkdir $(subst /,\,$@)
	
$(BIN) :  
	-mkdir $(subst /,\,$@)

%.pdb :

$(OBJ)/org_bv_com_windows_jni_BVNativeJNI.obj : org_bv_com_windows_jni_BVNativeJNI.cpp \
						org_bv_com_windows_jni_BVNativeJNI.h bvnative.h pch.h
		$(CXX) $(CXXFLAGS) $(CXXFLAGS_USE_PCH) org_bv_com_windows_jni_BVNativeJNI.cpp
						
# depend on dllmain first to create precompiled headers.		
$(OBJ)/bvnative.obj : $(OBJ)/$(PROJECT).pch bvnative.cpp bvnative.h 
		$(CXX) $(CXXFLAGS) $(CXXFLAGS_USE_PCH) bvnative.cpp

$(OBJ)/dllmain.obj :  $(OBJ)/$(PROJECT).pch dllmain.cpp pch.cpp pch.h framework.h
		$(CXX) $(CXXFLAGS) $(CXXFLAGS_USE_PCH) dllmain.cpp
		
$(OBJ)/$(PROJECT).pch : $(OBJ)/pch.obj 

$(OBJ)/pch.obj : pch.cpp pch.h framework.h
		$(CXX) $(CXXFLAGS) $(CXXFLAGS_CREATE_PCH) pch.cpp


	    