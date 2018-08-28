// PersonManager.aidl
package com.syl.myapplication1aid;

import com.syl.myapplication1aid.aidl.Person;
interface PersonManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void addPerson(in Person p);//添加person
    List<Person> getPersonList();//获得用户集合
}
