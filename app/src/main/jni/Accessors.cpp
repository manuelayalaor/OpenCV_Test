//
// Created by Familia on 3/28/2018.
//

#include <exception>

#include <jni.h>

#include <android/bitmap.h>

template< class T, class TCpp >
class JavaArrayAccessor
{
public:
    JavaArrayAccessor(JNIEnv* env, T array) :
        env(env),
        array(array),
        data(reinterpret_cast< TCpp* >(env->GetPrimitiveArrayCritical(array, NULL))) // never returns NULL
    {}

    ~JavaArrayAccessor()
    {
        env->ReleasePrimitiveArrayCritical(array, data, 0);
    }

    TCpp* getData()
    {
        return data;
    }

private:
    JNIEnv* env;
    T array;
    TCpp* data;
};

class AndroidBitmapAccessorException : public std::exception
{
public:
    AndroidBitmapAccessorException(int code) :
        code(code)
    {}

    virtual ~AndroidBitmapAccessorException()
    {}

    const int code;
};

class AndroidBitmapAccessor
{
public:
    AndroidBitmapAccessor(JNIEnv* env, jobject bitmap) throw(AndroidBitmapAccessorException):
        env(env),
        bitmap(bitmap),
        data(NULL)
    {
        int rv = AndroidBitmap_lockPixels(env, bitmap, reinterpret_cast< void** >(&data));
        if(rv != ANDROID_BITMAP_RESULT_SUCCESS)
        {
            throw AndroidBitmapAccessorException(rv);
        }
    }

    ~AndroidBitmapAccessor()
    {
        if(data)
        {
            AndroidBitmap_unlockPixels(env, bitmap);
        }
    }

    uchar* getData()
    {
        return data;
    }

private:
    JNIEnv* env;
    jobject bitmap;
    uchar* data;
};