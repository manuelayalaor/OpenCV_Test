
#include"com_example_thetardis_opencv_test_OpencvClass.h"
#include <exception>
#include <jni.h>
#include <android/bitmap.h>

JNIEXPORT void JNICALL Java_com_example_thetardis_opencv_1test_OpencvClass_faceDetection
  (JNIEnv *, jclass, jlong addrRgba){
  Mat& frame  = *(Mat*)addrRgba;

  detect(frame);


  }


  void handleFrame(JNIEnv *env,jobject thiz,jint width, jint height, jbyteArray nv21Data, jobject bitm)
  {
        try{
                        // create output rgba-formatted output Mat object using the raw Java data
                        // allocated by the Bitmap object to prevent an extra memcpy. note that
                        // the bitmap must be created in ARGB_8888 format
                        AndroidBitmapAccessor bitmapAccessor(env, bitm);
                        cv::Mat rgba(height, width, CV_8UC4, bitmapAccessor.getData());

                        // create input nv21-formatted input Mat object using the raw Java data to
                        // prevent extraneous allocations. note the use of height*1.5 to account
                        // for the nv21 (YUV420) formatting
                        JavaArrayAccessor< jbyteArray, uchar > nv21Accessor(env, nv21Data);
                        cv::Mat nv21(height * 1.5, width, CV_8UC1, nv21Accessor.getData());

                        // initialize the rgba output using the nv21 data
                        cv::cvtColor(nv21, rgba, CV_YUV2RGBA_NV21);

                        // convert the nv21 image to grayscale by lopping off the extra 0.5*height bits. note
                        // this this ctor is smart enough to not actually copy the data
                        cv::Mat gray(nv21, cv::Rect(0, 0, width, height));

                        // do your processing on the nv21 and/or grayscale image here, making sure to update the
                        // rgba mat with the appropriate output
        }catch(const AndroidBitmapAccessorException& e)
        {
            LOGE("error locking bitmap: %d", e.code);
        }
  }



    void detect(Mat& frame)
    {
        String face_cascade_name = "/storage/emulated/0/data/haarcascade_frontalface_alt.xml";
        String eyes_cascade_name = "/storage/emulated/0/data/haarcascade_eye_tree_eyeglasses.xml";
        CascadeClassifier face_cascade;
        CascadeClassifier eyes_cascade;

        if( !face_cascade.load( face_cascade_name ) ){ printf("--(!)Error loading\n"); return; };
        if( !eyes_cascade.load( eyes_cascade_name ) ){ printf("--(!)Error loading\n"); return; };

        std::vector<Rect> faces;
          Mat frame_gray;

          cvtColor( frame, frame_gray, CV_BGR2GRAY );
          equalizeHist( frame_gray, frame_gray );

          //-- Detect faces
          face_cascade.detectMultiScale( frame_gray, faces, 1.1, 2, 0|CV_HAAR_SCALE_IMAGE, Size(30, 30) );

          for( size_t i = 0; i < faces.size(); i++ )
          {
            Point center( faces[i].x + faces[i].width*0.5, faces[i].y + faces[i].height*0.5 );
            ellipse( frame, center, Size( faces[i].width*0.5, faces[i].height*0.5), 0, 0, 360, Scalar( 255, 0, 255 ), 4, 8, 0 );

            Mat faceROI = frame_gray( faces[i] );
            std::vector<Rect> eyes;

            //-- In each face, detect eyes
            eyes_cascade.detectMultiScale( faceROI, eyes, 1.1, 2, 0 |CV_HAAR_SCALE_IMAGE, Size(30, 30) );

            for( size_t j = 0; j < eyes.size(); j++ )
             {
               Point center( faces[i].x + eyes[j].x + eyes[j].width*0.5, faces[i].y + eyes[j].y + eyes[j].height*0.5 );
               int radius = cvRound( (eyes[j].width + eyes[j].height)*0.25 );
               circle( frame, center, radius, Scalar( 255, 0, 0 ), 4, 8, 0 );
             }
          }

    }