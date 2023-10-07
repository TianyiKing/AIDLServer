package cn.itoo.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

// 对外提供的服务要在Manifest中注册
public class AidlTestService extends Service {
    public static final String TAG = "AidlTestService";

    private String hello;

    private Map<Integer, Student> studentMap;

    @Override
    public void onCreate() {
        super.onCreate();
        hello = "hello";
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private final IDeviceInterface.Stub binder = new IDeviceInterface.Stub() {

        @Override
        public void setHelloString(String string) throws RemoteException {
            hello = string;
        }

        @Override
        public String getHelloString() throws RemoteException {
            return hello;
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            Log.i(TAG, "addStudent: " + student);
            if (studentMap == null) {
                studentMap = new HashMap<>();
            }
            studentMap.put(student.getId(), student);
        }

        @Override
        public Student getStudent(int id) throws RemoteException {
            if (studentMap != null) {
                Student student = studentMap.get(id);
                Log.i(TAG, id + " -> getStudent: " + student);
                return student;
            }
            Log.i(TAG, id + " -> getStudent: null");
            return null;
        }
    };


}