//
// Created by n.maletskiy on 23.07.2018.
//

#ifndef MYJNACALLBACKTEST_OBSERVERCHAINFACTORY_H
#define MYJNACALLBACKTEST_OBSERVERCHAINFACTORY_H


#include <jni.h>

class ObserverChain {
public:
    ObserverChain(jweak pJobject, jmethodID pID, jmethodID pJmethodID);

    jweak store_Wlistener=NULL;
    jmethodID store_method = NULL;
    jmethodID store_methodVAL = NULL;

};


#endif //MYJNACALLBACKTEST_OBSERVERCHAINFACTORY_H
