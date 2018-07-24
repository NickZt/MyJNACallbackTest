//
// Created by n.maletskiy on 23.07.2018.
//

#include "ObserverChain.h"

ObserverChain::ObserverChain(jweak pJobject, jmethodID pID, jmethodID pJmethodID) {
     store_Wlistener=pJobject;
    store_method = pID;
     store_methodVAL = pJmethodID;
}
