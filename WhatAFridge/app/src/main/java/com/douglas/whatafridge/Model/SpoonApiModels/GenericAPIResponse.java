package com.douglas.whatafridge.Model.SpoonApiModels;

import java.util.ArrayList;

public interface GenericAPIResponse <T>{
    void onSuccess(ArrayList<T> ListObject);
    void onFail(String ErrorMessage);
}
