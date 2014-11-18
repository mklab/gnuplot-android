LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := jmatx-android-graph

LOCAL_C_INCLUDES := $(LOCAL_PATH)/term $(LOCAL_PATH)/src

LOCAL_SRC_FILES := src/alloc.c src/axis.c src/binary.c \
        src/breaders.c src/bitmap.c src/color.c \
        src/command.c src/contour.c src/datafile.c \
        src/dynarray.c src/eval.c src/fit.c src/gadgets.c \
        src/getcolor.c \
        src/graph3d.c src/graphics.c src/help.c \
        src/hidden3d.c src/history.c src/internal.c \
        src/interpol.c src/matrix.c src/misc.c src/mouse.c \
        src/parse.c src/plot.c \
        src/plot2d.c src/plot3d.c src/pm3d.c src/readline.c \
        src/save.c src/scanner.c src/set.c \
        src/show.c src/specfun.c src/standard.c src/stdfn.c \
        src/tables.c src/tabulate.c \
        src/term.c src/time.c src/unset.c src/util.c \
        src/util3d.c src/variable.c src/version.c \
        jmatx-android-graph.cpp
        
LOCAL_CFLAGS := -DHAVE_CONFIG_H=1 -DX11_DRIVER_DIR="C:\Users\kawabata\gp440win32\gnuplot"

LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog

include $(BUILD_SHARED_LIBRARY)
