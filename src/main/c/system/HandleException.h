#pragma once
#include "../pch.h"
#include <exception>
#include <string>
class HandleException :
    public std::exception
{
public:
    HandleException(const char * const msg) :
        exception(msg) {
    }
};

