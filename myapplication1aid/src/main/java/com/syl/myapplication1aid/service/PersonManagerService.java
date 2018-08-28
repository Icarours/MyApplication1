package com.syl.myapplication1aid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.syl.myapplication1aid.PersonManager;
import com.syl.myapplication1aid.aidl.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonManagerService extends Service {
    private List<Person> mPersonList = new ArrayList<>();

    public PersonManagerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new PersonBinder();
    }

    class PersonBinder extends PersonManager.Stub {

        @Override
        public void addPerson(Person p) throws RemoteException {
            mPersonList.add(p);
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return mPersonList;
        }
    }
}
