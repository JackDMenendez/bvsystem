#pragma once
#include <exception>
namespace bv {
    class SecurityException :
        public std::exception
    {
    public:
        SecurityException(const char * const msg) : exception(msg) {}
        virtual ~SecurityException();
    };
}

