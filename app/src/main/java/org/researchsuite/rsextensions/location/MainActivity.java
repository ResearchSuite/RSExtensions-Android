package org.researchsuite.rsextensions.location;

import android.os.Bundle;

import org.researchsuite.rsextensions.R;


public class MainActivity extends LocationActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RSActivityManager.get().queueActivity(this, "test", true);

//        mContext = getApplicationContext();
//
//        mResourcePathManager = new ResourcePathManager() {
//            @Override
//            public String generatePath(int type, String name) {
//                String dir;
//                switch (type) {
//                    default:
//                        dir = null;
//
//                        break;
//                    case Resource.TYPE_JSON:
//                        dir = "json";
//                        break;
//                }
//
//                StringBuilder path = new StringBuilder();
//                if (!TextUtils.isEmpty(dir)) {
//                    path.append(dir).append("/");
//                }
//
//                return path.append(name).append(".").append(getFileExtension(type)).toString();
//            }
//        };
//
//        ResourcePathManager.init(mResourcePathManager);
//
//        UnencryptedProvider encryptionProvider = new UnencryptedProvider();
//
//        AppDatabase dbAccess = createAppDatabaseImplementation(mContext);
//        dbAccess.setEncryptionKey(encryptionProvider.getEncrypter().getDbKey());
//
//        LocationFileAccess fileAccess = createFileAccessImplementation(mContext);
//        fileAccess.setEncrypter(encryptionProvider.getEncrypter());
//
//
//        mBuilder = new RSTBTaskBuilder(mContext,mResourcePathManager,fileAccess);
//
//        //RSActivityManager.get().resourcePathManager = mResourcePathManager;
//        RSActivityManager.get().taskBuilder = mBuilder;
//        RSActivityManager.get().queueActivity(this,"test",true);


    }

//    protected LocationFileAccess createFileAccessImplementation(Context context)
//    {
//        String pathName = "/yadl";
//        return new LocationFileAccess(pathName);
//    }
//
//    protected AppDatabase createAppDatabaseImplementation(Context context) {
//        SQLiteDatabase.loadLibs(context);
//
//        return new SqlCipherDatabaseHelper(
//                context,
//                SqlCipherDatabaseHelper.DEFAULT_NAME,
//                null,
//                SqlCipherDatabaseHelper.DEFAULT_VERSION,
//                new UpdatablePassphraseProvider()
//        );
//    }
}
