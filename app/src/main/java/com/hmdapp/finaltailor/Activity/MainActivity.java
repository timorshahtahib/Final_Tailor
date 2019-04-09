package com.hmdapp.finaltailor.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.content.res.Configuration;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;
import com.hmdapp.finaltailor.Activity.Customer.Person_Activity;
import com.hmdapp.finaltailor.Activity.Order.Tasks_Activity;
import com.hmdapp.finaltailor.Activity.Report_Dashbord.DashboradReportActivity;
import com.hmdapp.finaltailor.Models.Image;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Seting_Activity;
import com.hmdapp.finaltailor.Utlity.Tools;
import com.hmdapp.finaltailor.Utlity.Utilities;
import com.hmdapp.finaltailor.database.DB_Acsess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private String version_code;

    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.
    String[] permissions = new String[]{

            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,

    };

    private View parent_view;
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private AdapterImageSlider adapterImageSlider;
    private Runnable runnable = null;
    private Handler handler = new Handler();

    private static int[] array_image_place = {
            R.drawable.img,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
    };

    private static String[] array_title_place = {
            " ",
            " ",
            " ",
            " ",
            " ",
    };

    private static String[] array_brief_place = {
            " ",
            " ",
            " ",
            " ",
            " ",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale locale = new Locale("En");
        Locale.setDefault(locale);

     //   checkVersion();

        try {

            Configuration configuration = new Configuration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(locale);
            }
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        } catch (Exception w) {

        }
        TextView task_siz=findViewById(R.id.task_size);
        DB_Acsess D=DB_Acsess.getInstans(this);
        D.open();
        int s=D.getTaskSize();

        if (s>0){
            task_siz.setText(s+"");
        }
        setUpToolbar();
        setUpNavigationView();
        initComponent();

        try {
            TelephonyManager tManager = (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            final String uid = tManager.getDeviceId();

            FirebaseFirestore.getInstance().collection("users").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (!queryDocumentSnapshots.isEmpty()) {


                        //  DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);

                        for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                            if (uid.contains(String.valueOf(doc.getLong("serial")))) {
                                boolean b = doc.getBoolean("isactive");
                                // Toast.makeText(MainActivity.this, "b = "+ b, Toast.LENGTH_SHORT).show();
                                if (!b) {

                                    Toast.makeText(MainActivity.this, "مشتری عزیز حساب شما بسته شده است  ۰۷۹۳۲۴۰۱۷۸", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {

                                }
                            } else {
                            }
                        }


                    } else {
                        Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch (Exception e){

        }

       try{
           checkVersion();
       }catch (Exception e){

       }


        Toast.makeText(this, ""+getCurrentShamsidate(), Toast.LENGTH_LONG).show();


    }

    private void checkVersion() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("version");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                version_code = (String) dataSnapshot.getValue();
                String appVersion = "";
                try {
                    appVersion = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;

                    appVersion = appVersion.replaceAll("a-zA-Z|-", "");
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                checkPoint(version_code, appVersion);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkPoint(String version_code, String appVersion) {


        if (!TextUtils.equals(version_code, appVersion)) {
            updateDialog();
        }

    }

    private void updateDialog() {
        startActivity(new Intent(this, UPDATE.class));
        Toast.makeText(this, "plz update", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void initComponent() {



        layout_dots = findViewById(R.id.layout_dots);
        viewPager = findViewById(R.id.pager);
        adapterImageSlider = new AdapterImageSlider(this, new ArrayList<Image>());

        final List<Image> items = new ArrayList<>();
        for (int i = 0; i < array_image_place.length; i++) {
            Image obj = new Image();
            obj.image = array_image_place[i];
            obj.imageDrw = getResources().getDrawable(obj.image);
            obj.name = array_title_place[i];
            obj.brief = array_brief_place[i];
            items.add(obj);
        }

        adapterImageSlider.setItems(items);
        viewPager.setAdapter(adapterImageSlider);

        // displaying selected image first
        viewPager.setCurrentItem(0);
        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);
        ((TextView) findViewById(R.id.title)).setText(items.get(0).name);
        ((TextView) findViewById(R.id.brief)).setText(items.get(0).brief);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                ((TextView) findViewById(R.id.title)).setText(items.get(pos).name);
                ((TextView) findViewById(R.id.brief)).setText(items.get(pos).brief);
                addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        startAutoSlider(adapterImageSlider.getCount());
    }

    public void person_Action(View view) {
        startActivity(new Intent(this, Person_Activity.class));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void task_action(View view) {
        startActivity(new Intent(this, Tasks_Activity.class));
        finish();

    }

    public void onReportClick(View view) {
        startActivity(new Intent(this, DashboradReportActivity.class));
        finish();

    }

    private void setUpNavigationView() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {


//                    case R.id.close_the_app:
//                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
//                        finish();
//                        break;
//                    case R.id.nav_share:
//
//                        Intent sendIntent = new Intent();
//                        sendIntent.setAction(Intent.ACTION_SEND);
//                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Afghan Tailor App is coming online ");
//                        sendIntent.setType("text/plain");
//                        startActivity(sendIntent);
//                        break;
//
//                    case R.id.nav_send:
//                        ApplicationInfo app = getApplicationContext().getApplicationInfo();
//                        String filePath = app.sourceDir;
//
//                        Intent intent = new Intent(Intent.ACTION_SEND);
//
//                        intent.setType("*/*");
//
//                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
//                        startActivity(Intent.createChooser(intent, "Afghan Tailor App Shared"));
//
//                        break;


                    case R.id.customer_item:
                        startActivity(new Intent(MainActivity.this, Person_Activity.class));
                        finish();
                        break;
                    case R.id.task_item:
                        startActivity(new Intent(MainActivity.this, Tasks_Activity.class));
                        finish();
                        break;
                    case R.id.report_item:
                        startActivity(new Intent(MainActivity.this, DashboradReportActivity.class));
                        finish();
                        break;
                    case R.id.nav_about:
                        startActivity(new Intent(MainActivity.this, AboutCompanyImage.class));

                        break;
                    case R.id.back_up:
                        exportDatabase();
                        break;
                    case R.id.back_up_res:
                        importDatabase();
                        break;

                }

                return true;
            }
        });
    }


    private void setUpToolbar() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white_color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("خیاط");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    public void exportDatabase() {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            String currentDBPath = "//data//com.hmdapp.finaltailor//databases//newdb.db";
            String backupDBPath = "MY_DATABASE_FILE.db";
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);
            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            Toast.makeText(this, this.getResources().getString(R.string.exporterenToast), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, this.getResources().getString(R.string.portError), Toast.LENGTH_SHORT).show();
            Log.d("Main", e.toString());
        }
    }

    public void importDatabase() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            String currentDBPath = "//data//" + "com.hmdapp.finaltailor" + "//databases//" + "newdb.db";
            String backupDBPath = "MY_DATABASE_FILE.db";
            File backupDB = new File(data, currentDBPath);
            File currentDB = new File(sd, backupDBPath);
            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            Toast.makeText(this, this.getResources().getString(R.string.importerenToast), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, this.getResources().getString(R.string.portError), Toast.LENGTH_SHORT).show();
        }
    }


    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
        }
    }

    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    public void On_Setting(View view) {

        startActivity(new Intent(this, Seting_Activity.class));

    }

    public void gallary(View view) {

        startActivity(new Intent(this, Gallary.class));
    }

    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<Image> items;

        private AdapterImageSlider.OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, Image obj);
        }

        public void setOnItemClickListener(AdapterImageSlider.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, List<Image> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public Image getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<Image> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final Image o = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            ImageView image = v.findViewById(R.id.image);
            View lyt_parent = v.findViewById(R.id.lyt_parent);
            Tools.displayImageOriginal(act, image, o.image);
            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, o);
                    }
                }
            });

            container.addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);

        }

    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {


                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {


                }


                return;
            }
        }
    }




    public  String getCurrentShamsidate() {

        Utilities util = new Utilities();

        return util. getCurrentShamsidate();
    }

}
