package com.vivian.apputil.view;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vivian.apputil.R;
import com.vivian.apputil.adapter.ContactAdapter;
import com.vivian.apputil.bean.ContactInfo;
import com.vivian.apputil.constants.AppConstants;
import com.vivian.apputil.util.AppPermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends BaseActivity {
    private RecyclerView rvList;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
    }

    @Override
    public void initView() {
        initTitle("选择联系人");
        rvList = findViewById(R.id.rvList);


    }

    @Override
    public void initEventData() {
        contactAdapter = new ContactAdapter(mContext);
        rvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvList.setAdapter(contactAdapter);

        appPermissionUtil.requestPermissions(mContext, AppConstants.REQUEST_CONTACT, new String[]{Manifest.permission.READ_CONTACTS}, new AppPermissionUtil.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                List<ContactInfo> contactList = getContactList();
                if (contactList != null && contactList.size() > 0) {
                    contactAdapter.setDatas(contactList);
                }

            }

            @Override
            public void onPermissionDenied() {

            }
        });

    }


    private List<ContactInfo> getContactList()

    {
        // 获取联系人数据
        ContentResolver cr = mContext.getContentResolver();
        //获取所有电话信息（而不是联系人信息），这样方便展示
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,// 姓名
                ContactsContract.CommonDataKinds.Phone.NUMBER,// 电话号码
        };
        Cursor cursor = cr.query(uri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        //最终要返回的数据
        List<ContactInfo> result = new ArrayList<ContactInfo>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String number = cursor.getString(1);
            //保存到对象里
            ContactInfo info = new ContactInfo();
            info.setName(name);
            info.setNumber(number);
            //保存到集合里
            result.add(info);
        }
        //用完记得关闭
        cursor.close();
        return result;
    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void loadData() {

    }
}
