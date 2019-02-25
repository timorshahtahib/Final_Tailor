package com.hmdapp.finaltailor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.hmdapp.finaltailor.Models.Cloth;
import com.hmdapp.finaltailor.Models.Customer;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.Models.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DB_Acsess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DB_Acsess instance;
    Cursor c = null;


    private DB_Acsess(Context context) {
        this.openHelper = new DBMS(context);
    }


    public static DB_Acsess getInstans(Context context) {

        if (instance == null) {
            instance = new DB_Acsess(context);

        }
        return instance;

    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public List<Customer> get_All_Customer() {

        ArrayList<Customer> list = new ArrayList<>();
        c = db.rawQuery("select * from customer", new String[]{});
        Log.d("size", c.getCount() + "");
        while (c.moveToNext()) {
            Customer con = new Customer();
            con.setId(c.getInt(0));
            con.setName(c.getString(1));
            con.setPhone(c.getString(2));
            con.setJob(c.getString(3));
            con.setCl_id(c.getInt(4));
            list.add(con);
            Log.d("idddddddddddddddddd", con.getCl_id() + "");
        }
        return list;
    }

    public List<Customer> searchCustomer(String name) {

        ArrayList<Customer> list = new ArrayList<>();
        c = db.rawQuery("select * from customer where full_name like '%" + name + "%'", new String[]{});
        Log.d("size", c.getCount() + "");
        while (c.moveToNext()) {
            Customer con = new Customer();
            con.setId(c.getInt(0));
            con.setName(c.getString(1));
            con.setPhone(c.getString(2));
            con.setJob(c.getString(3));
            con.setCl_id(c.getInt(4));
            list.add(con);
            Log.d("idddddddddddddddddd", con.getCl_id() + "");
        }
        return list;
    }
    public List<Task> getAllTask() {

        ArrayList<Task> list = new ArrayList<>();
        c = db.rawQuery("select * from tasks ORDER BY deliver_date DESC", new String[]{});
        Log.d("Get All Task : size", c.getCount() + "");
        while (c.moveToNext()) {
            Task task = new Task();

            Customer customer = new Customer();
            // customer.setName(getCustomerNameById(c.getInt(0)));

            task.setId(c.getInt(0));
            task.setCustomerId(c.getInt(1));
            customer.setName(c.getString(2));
            task.setState(c.getInt(3));
            task.setCount(c.getInt(4));
            task.setColor(c.getString(5));
            task.setPrice(c.getFloat(6));
            task.setPayment(c.getFloat(7));
            task.setRemainder(c.getFloat(8));
            task.setRegDate(c.getString(9));
            task.setDeliverDate(c.getString(10));
            task.setIsExist(c.getInt(11));
            task.setCustomer(customer);


            list.add(task);
            Log.d("Get Task ....  id : ", task.getId() + "");
        }
        return list;
    }

    public List<Payment> getListOfRemainderReport( Context context,int id) {

        ArrayList<Payment> list = new ArrayList<>();

        c = db.rawQuery("select * from payment  where ((ts_state = 1 AND remainder > 0) AND ( DATE(deliver_date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day'))) AND cu_id ='" + id + "'  ORDER BY deliver_date DESC ", new String[]{});
        //Toast.makeText(context, "Get All Task : size : " + c.getCount(), Toast.LENGTH_LONG).show();
        Log.d("Get All Payment : size", c.getCount() + "");
        Log.i("Get All payment : size", c.getCount() + "");
        System.out.println("Get All payment : size : " + c.getCount() + "");
        while (c.moveToNext()) {
            Payment payment = new Payment();

            Customer customer = new Customer();

//            payment.setCustomerId(c.getInt(1));
//            customer.setName(c.getString(2));
//            payment.setRemainder(c.getFloat(0));
            payment.setId(c.getInt(0));
            payment.setTaskID(c.getInt(1));
            payment.setCustomerId(c.getInt(2));
            customer.setName(c.getString(3));
            payment.setState(c.getInt(4));
            payment.setCount(c.getInt(5));
            payment.setColor(c.getString(6));
            payment.setPrice(c.getFloat(7));
            payment.setPayment(c.getFloat(8));
            payment.setRemainder(c.getFloat(9));
            payment.setRegDate(c.getString(10));
            payment.setDeliverDate(c.getString(11));
            payment.setCustomer(customer);

            payment.setCustomer(customer);


            list.add(payment);
            Log.d("Get Task ....  id : ", payment.getId() + "");
        }
        return list;
    }

    public List<Payment> getAllRemainderReport(Context context) {

        ArrayList<Payment> list = new ArrayList<>();

        c = db.rawQuery("select SUM(remainder) , cu_id, cu_name from payment  where (ts_state = 1 AND remainder > 0) AND ( DATE(deliver_date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) GROUP BY cu_id ORDER BY deliver_date DESC ", new String[]{});
        //Toast.makeText(context, "Get All Task : size : " + c.getCount(), Toast.LENGTH_LONG).show();
        Log.d("Get All Payment : size", c.getCount() + "");
        Log.i("Get All payment : size", c.getCount() + "");
        System.out.println("Get All payment : size : " + c.getCount() + "");
        while (c.moveToNext()) {
            Payment payment = new Payment();

            Customer customer = new Customer();

            payment.setCustomerId(c.getInt(1));
            customer.setName(c.getString(2));
            payment.setRemainder(c.getFloat(0));

//            payment.setRemainder(c.getColumnIndex("Total"));
//            payment.setCustomerId(c.getColumnIndex("cu_id"));
//            customer.setName(c.getColumnIndex("cu_name")+"");
            payment.setCustomer(customer);


            list.add(payment);
            Log.d("Get Task ....  id : ", payment.getId() + "");
        }
        return list;
    }

    public List<Payment> getAllPaymentReport(String type, Context context) {

        ArrayList<Payment> list = new ArrayList<>();
        switch (type) {
            case "Daily":
                c = db.rawQuery("select * from payment where deliver_date = date('now') and ts_state =1 ORDER BY deliver_date DESC ", new String[]{});
                break;
            case "Weekly":

                c = db.rawQuery("select * from payment  where ts_state = 1 AND ( DATE(deliver_date ) between DATE('now', 'weekday 0', '-7 days') AND DATE('now', 'weekday 0', '-1 days')) ORDER BY deliver_date DESC ", new String[]{});
                //c = db.rawQuery("SELECT * from tasks  where DATE(deliver_date ) >(SELECT DATE('now', '-7 day'))", new String[]{});
                break;
            case "Monthly":

                c = db.rawQuery("select * from payment  where ts_state = 1 AND ( DATE(deliver_date ) between  date('now','start of month','+1 month','-31 day') and date('now','start of month','+1 month','-1 day')) ORDER BY deliver_date DESC ", new String[]{});
                break;
            case "Yearly":

                c = db.rawQuery("select * from payment  where ts_state = 1 AND ( DATE(deliver_date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) ORDER BY deliver_date DESC ", new String[]{});
                break;
        }

       //Toast.makeText(context, "Get All Task : size : " + c.getCount(), Toast.LENGTH_LONG).show();
        Log.d("Get All Payment : size", c.getCount() + "");
        Log.i("Get All payment : size", c.getCount() + "");
        System.out.println("Get All payment : size : " + c.getCount() + "");
        while (c.moveToNext()) {
            Payment payment = new Payment();

            Customer customer = new Customer();
            payment.setId(c.getInt(0));
            payment.setTaskID(c.getInt(1));
            payment.setCustomerId(c.getInt(2));
            customer.setName(c.getString(3));
            payment.setState(c.getInt(4));
            payment.setCount(c.getInt(5));
            payment.setColor(c.getString(6));
            payment.setPrice(c.getFloat(7));
            payment.setPayment(c.getFloat(8));
            payment.setRemainder(c.getFloat(9));
            payment.setRegDate(c.getString(10));
            payment.setDeliverDate(c.getString(11));
            payment.setCustomer(customer);


            list.add(payment);
            Log.d("Get Task ....  id : ", payment.getId() + "");
        }
        return list;
    }

    public int getDailyPayment() {
        c = db.rawQuery("select SUM(cu_payment) as Total from payment where deliver_date = date('now') and ts_state =1", new String[]{});
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Total"));
    }

    public int getMonthlyPayment() {
        c = db.rawQuery("select SUM(cu_payment) as Total from payment where ts_state = 1 AND ( DATE(deliver_date ) between  date('now','start of month','+1 month','-31 day') and date('now','start of month','+1 month','-1 day')) ", new String[]{});
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Total"));
    }

    public int getWeeklyPayment() {
        c = db.rawQuery("select SUM(cu_payment) as Total from payment where ts_state = 1 AND ( DATE(deliver_date ) between DATE('now', 'weekday 0', '-7 days') AND DATE('now', 'weekday 0', '-1 days')) ", new String[]{});
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Total"));
    }

    public int getYearlyPayment() {
        c = db.rawQuery("select SUM(cu_payment) as Total from payment where ts_state = 1 AND ( DATE(deliver_date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) ", new String[]{});
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Total"));
    }
    public int getRemainderPayment() {
        c = db.rawQuery("select SUM(remainder) as Total from payment where ts_state = 1 AND ( DATE(deliver_date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) ", new String[]{});
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Total"));
    }

    public Customer getCustomer(int id) {

        ArrayList<Customer> list = new ArrayList<>();
        c = db.rawQuery("select * from customer  where id='" + id + "'", new String[]{});
        Log.d("size", c.getCount() + "");
        c.moveToNext();
        Customer con = new Customer();
        con.setId(c.getInt(0));
        con.setName(c.getString(1));
        con.setPhone(c.getString(2));
        con.setJob(c.getString(3));
        con.setCl_id(c.getInt(4));

        return con;
    }


    //
//
//    public List<Tables> getCat() {
//
//
//        ArrayList<Tables> list = new ArrayList<>();
//        c = db.rawQuery("select * from tables ", new String[]{});
//        int i = 0;
//        while (c.moveToNext()) {
//
//            Tables con = new Tables();
//            con.setId(c.getInt(0));
//
//
//            con.setTitle(c.getString(1));
//
//            list.add(con);
//
//        }
//
//
//        return list;
//    }
//
//    public List<Contact> getcontacct_by_cat(int id) {
//
//        ArrayList<Contact> list = new ArrayList<>();
//        c = db.rawQuery("select contacts.id,contacts.first_name,contacts.last_name,tables.table_name,Jobs.job_title,contacts.phone,contacts.phone_no,contacts.digital_no,contacts.gender,contacts .isfaov ,contacts.scientific_degree from contacts,Jobs,tables where contacts.fk_job=Jobs.id and  contacts.fk_catgory=tables.id and contacts.fk_catgory='" + id + "'", new String[]{});
//        int i = 0;
//        while (c.moveToNext()) {
//            Contact con = new Contact();
//            Jobs jobs = new Jobs();
//
//            Tables cat = new Tables();
//            con.setId(c.getInt(0));
//            con.setName(c.getString(1));
//            con.setLast_name(c.getString(2));
//            cat.setTitle(c.getString(3));
//            jobs.setTitle(c.getString(4));
//            con.setEmail(c.getString(5));
//            con.setPhone(c.getString(6));
//            con.setDigita_num(c.getString(7));
//            con.setGender(c.getInt(8));
//            con.setIsfavo(c.getInt(9));
//            con.setSintific_degree(c.getString(10));
//
//            con.setTables(cat);
//            con.setJobs(jobs);
//
//
//            list.add(con);
//            Log.d("database", list.get(i).getName() + "");
//            Log.d("names", c.toString());
//            i++;
//        }
//
//        Log.d("inter", id + "");
//
//        return list;
//    }
//
    public Cloth getCloth(int id) {


        c = db.rawQuery("select * from cloth where id='" + id + "'", new String[]{});
        c.moveToNext();
        Cloth con = new Cloth();

        con.setId(c.getInt(1));
        con.setAstin(c.getInt(0));
        con.setQad(c.getInt(2));
        con.setShana(c.getInt(3));
        con.setBaqal(c.getInt(4));
        con.setDaman(c.getInt(5));
        con.setShalwar(c.getInt(6));
        con.setPacha(c.getInt(7));
        con.setBar_shalwar(c.getInt(8));
        con.setModel(c.getString(9));

        con.setModel_dam_astin(c.getString(10));
        con.setModel_qot_astin(c.getString(11));
        con.setQad_paty(c.getString(12));
        con.setModel_astin(c.getString(13));
        con.setYakhan(c.getInt(14));
        con.setDam_astin(c.getInt(15));


        con.setModel_yaqa(c.getString(16));

        Log.d("inter", id + "");

        Log.d("bagal ", c.getInt(4) + "");
        return con;
    }


//
//    public List<Contact> search(String qury) {
//
//        ArrayList<Contact> list = new ArrayList<>();
//       // c = db.rawQuery("select * from cloth,customer where cloth.id = customer.cl_id  contacts.fk_catgory=tables.id and  first_name like '%" + qury + "%'", new String[]{});
//        c = db.rawQuery("select * cloth where  '%" + qury + "%'", new String[]{});
//        int i = 0;
//        while (c.moveToNext()) {
//            Contact con = new Contact();
//            Jobs jobs = new Jobs();
//
//            Tables cat = new Tables();
//            con.setId(c.getInt(0));
//            con.setName(c.getString(1));
//            con.setLast_name(c.getString(2));
//            cat.setTitle(c.getString(3));
//            jobs.setTitle(c.getString(4));
//            con.setEmail(c.getString(5));
//            con.setPhone(c.getString(6));
//            con.setDigita_num(c.getString(7));
//            con.setGender(c.getInt(8));
//            con.setIsfavo(c.getInt(9));
//            con.setSintific_degree(c.getString(10));
//
//            con.setTables(cat);
//            con.setJobs(jobs);
//
//            list.add(con);
//            Log.d("database", list.get(i).getName() + "");
//
//            i++;
//        }
//
//        try {
//
//        } catch (Exception e) {
//
//        }
//
//        return list;
//    }

    //
//    public ArrayList<Tables> getFavoriteCatgory() {
//        String q = "SELECT distinct   table_name,tables.id FROM tables INNER JOIN `contacts` ON contacts.fk_catgory = tables .id where isfaov=1";
//
//        ArrayList<Tables> arrayList = new ArrayList<>();
//
//        c = db.rawQuery(q, new String[]{});
//        // looping through all rows and adding to list
//
//
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                Tables category = new Tables();
//                category.setTitle(c.getString(0));
//
//                category.setId(c.getInt(1));
//
//                //   Log.d("category", getcontacct_by_cat(Integer.parseInt(c.getString(1))).size() + "oooooo");
//
//                // category.setSize(getcontacct_by_cat(Integer.parseInt(c.getString(1))).size());
//                // Adding contact to list
//
//                arrayList.add(category);
//            } while (c.moveToNext());
//        }
//        return arrayList;
//    }
//
//
    // code to update the forite culum contact
    public int updateCloth(Cloth cloth) {


        ContentValues val_cloth = new ContentValues();
        val_cloth.put("cl_qad", cloth.getQad());
        val_cloth.put("cl_shana", cloth.getShana());
        val_cloth.put("cl_baqal", cloth.getBaqal());
        val_cloth.put("cl_daman", cloth.getDaman());
        val_cloth.put("cl_shalwar", cloth.getShalwar());
        val_cloth.put("cl_pacha", cloth.getPacha());
        val_cloth.put("cl_bar_shalwar", cloth.getBar_shalwar());
        val_cloth.put("cl_model", cloth.getModel());
        val_cloth.put("cl_model_dam_astin", cloth.getModel_dam_astin());
        val_cloth.put("cl_model_qot_astin", cloth.getModel_qot_astin());
        val_cloth.put("cl_qad_paty", cloth.getQad_paty());
        val_cloth.put("cl_model_astin", cloth.getModel_astin());
        val_cloth.put("cl_model_yaqa", cloth.getModel_yaqa());
        val_cloth.put("cl_yakhan", cloth.getYakhan());
        val_cloth.put("cl_astin", cloth.getAstin());
        val_cloth.put("cl_dam_astin", cloth.getDam_astin());


        int r = db.update("cloth", val_cloth, "id" + " = ?",
                new String[]{cloth.getId() + ""});
        Log.d("update cloth ", "tag " + r);
        // updating row
        return r;
    }

    public int updatePerson(Customer customer) {
        ContentValues val_customer = new ContentValues();
        ContentValues valPayment = new ContentValues();
        ContentValues valTask = new ContentValues();

        val_customer.put("full_name", customer.getName()); // Contact Phone
        val_customer.put("phon", customer.getPhone()); // Contact Phone
        val_customer.put("job", customer.getJob()); // Contact Phone
        val_customer.put("cl_id", getLastId()); // Contact Phone

        valPayment.put("cu_name",customer.getName());
        valTask.put("cu_name",customer.getName());

        int r = db.update("customer", val_customer, "id" + " = ?",
                new String[]{customer.getId() + ""});

        db.update("payment", valPayment, "cu_id" + " = ?",
                new String[]{customer.getId() + ""});

        db.update("tasks", valTask, "ts_cu_id" + " = ?",
                new String[]{customer.getId() + ""});
        // updating row


        Log.d("update customer", "tag " + r);
        return r;
    }


    public long insert(Customer contact, Cloth cloth) {

        ContentValues val_customer = new ContentValues();


        ContentValues val_cloth = new ContentValues();
        val_cloth.put("cl_qad", cloth.getQad());
        val_cloth.put("cl_shana", cloth.getShana());
        val_cloth.put("cl_baqal", cloth.getBaqal());
        val_cloth.put("cl_daman", cloth.getDaman());
        val_cloth.put("cl_shalwar", cloth.getShalwar());
        val_cloth.put("cl_pacha", cloth.getPacha());
        val_cloth.put("cl_bar_shalwar", cloth.getBar_shalwar());
        val_cloth.put("cl_model", cloth.getModel());
        val_cloth.put("cl_model_dam_astin", cloth.getModel_dam_astin());
        val_cloth.put("cl_model_qot_astin", cloth.getModel_qot_astin());
        val_cloth.put("cl_qad_paty", cloth.getQad_paty());
        val_cloth.put("cl_model_astin", cloth.getModel_astin());
        val_cloth.put("cl_model_yaqa", cloth.getModel_yaqa());
        val_cloth.put("cl_yakhan", cloth.getYakhan());
        val_cloth.put("cl_astin", cloth.getAstin());
        val_cloth.put("cl_dam_astin", cloth.getDam_astin());

        long b2 = db.insert("cloth", null, val_cloth);


        val_customer.put("full_name", contact.getName()); // Contact Phone
        val_customer.put("phon", contact.getPhone()); // Contact Phone
        val_customer.put("job", contact.getJob()); // Contact Phone
        val_customer.put("cl_id", getLastId()); // Contact Phone
        // Inserting Row
        long b1 = db.insert("customer", null, val_customer);

        db.close(); // Closing database connection
        return b1;

    }


    public long add_task(Task task, Payment py) {

        ContentValues val_py = new ContentValues();

        ContentValues val_task = new ContentValues();


        val_task.put("ts_cu_id", task.getCustomer().getId());
        val_task.put("cu_name", task.getCustomer().getName());
        val_task.put("ts_state", task.getState());
        val_task.put("count", task.getCount());
        val_task.put("color", task.getColor());
        val_task.put("price", task.getPrice());
        val_task.put("cu_payment", task.getPayment());
        val_task.put("remainder", task.getRemainder());
        val_task.put("reg_date", task.getRegDate());
        val_task.put("deliver_date", task.getDeliverDate());

        long b2 = db.insert("tasks", null, val_task);

        //val_py.put("py_ts_id", getLastId_task());
        val_py.put("py_ts_id", py.getTaskID());
        val_py.put("cu_id", py.getCustomer().getId());

        val_py.put("cu_name", py.getCustomer().getName());
        val_py.put("count", py.getCount());
        val_py.put("color", py.getColor());
        val_py.put("price", py.getPrice());

        val_py.put("cu_payment", py.getPayment());
        val_py.put("remainder", py.getRemainder());

        val_py.put("reg_date", py.getRegDate());
        val_py.put("deliver_date", py.getDeliverDate());
        // Inserting Row
        long b1 = db.insert("payment", null, val_py);


        db.close(); // Closing database connection
        return b1;

    }

    public int getLastId() {

        c = db.rawQuery("select id from cloth", new String[]{});

        c.moveToLast();

        return c.getInt(0);
    }

    public int getLastId_task() {

        c = db.rawQuery("select id from tasks", new String[]{});

        c.moveToLast();

        return c.getInt(0);
    }


    public boolean delete_cloth(int id) {


        int b = db.delete("cloth", "id" + " = ?",
                new String[]{id + ""});


        if (b > 0) {
            return true;
        }
        return false;

    }

    public boolean delete_Cutomer(int id) {


        int b2 = db.delete("customer", "id" + " = ?",
                new String[]{id + ""});
        if (b2 > 0) {
            return true;
        }
        return false;

    }

    public boolean deleteTask(int id) {
        int result = db.delete("tasks", "id" + " = ?", new String[]{id + ""});
        if (result > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean deletePayment(int id) {
        int result = db.delete("payment", "id" + " = ?", new String[]{id + ""});
        if (result > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean deletePaymentByType(String type) {
        int result = 0;
        switch (type) {
            case "Daily":
               // c = db.rawQuery("select * from payment where deliver_date = date('now') and ts_state =1 ORDER BY deliver_date DESC ", new String[]{});

                result = db.delete("payment", "deliver_date = date('now') AND ts_state =1 ", null);
                break;
            case "Weekly":

               // c = db.rawQuery("select * from payment  where ts_state = 1 AND ( DATE(deliver_date ) between DATE('now', 'weekday 0', '-7 days') AND DATE('now', 'weekday 0', '-1 days')) ORDER BY deliver_date DESC ", new String[]{});
                result = db.delete("payment", "ts_state = 1  AND ( DATE(deliver_date ) between DATE('now', 'weekday 0', '-7 days') AND DATE('now', 'weekday 0', '-1 days')) ", null);
                break;
            case "Monthly":

               // c = db.rawQuery("select * from payment  where ts_state = 1 AND ( DATE(deliver_date ) between  date('now','start of month','+1 month','-31 day') and date('now','start of month','+1 month','-1 day')) ORDER BY deliver_date DESC ", new String[]{});
                result = db.delete("payment", "ts_state = 1 AND ( DATE(deliver_date ) between  date('now','start of month','+1 month','-31 day') and date('now','start of month','+1 month','-1 day')) ", null);
                break;
            case "Yearly":

                //c = db.rawQuery("select * from payment  where ts_state = 1 AND ( DATE(deliver_date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) ORDER BY deliver_date DESC ", new String[]{});
                result = db.delete("payment", "ts_state = 1 AND ( DATE(deliver_date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) ", null);
                break;
        }


        if (result > 0) {
            return true;
        } else {
            return false;
        }

    }

    public int updateTask(Context context, Task task,Payment payment) {
        ContentValues taskValues = new ContentValues();
        taskValues.put("ts_state", task.getState());

        ContentValues paymentValues = new ContentValues();
        paymentValues.put("ts_state", payment.getState());


        int result = db.update("tasks", taskValues, "id" + " = ?", new String[]{task.getId() + ""});
        int result2 = db.update("payment", paymentValues, "py_ts_id" + " = ?", new String[]{payment.getTaskID() + ""});
      //  Toast.makeText(context, "ID : " + payment.getTaskID(), Toast.LENGTH_LONG).show();
        Log.d("update tasks", "tag " + result);
        return result;
    }

    public int updateTaskClothState(Context context, Task task) {
        ContentValues taskValues = new ContentValues();
        taskValues.put("is_exist", task.getIsExist());
        int result = db.update("tasks", taskValues, "id" + " = ?", new String[]{task.getId() + ""});


        //Toast.makeText(context, "ID : " + task.getId(), Toast.LENGTH_LONG).show();
        Log.d("update tasks", "tag " + result);
        return result;
    }

    public int updateTaskPayment(Context context, Task task, Payment payment) {
        ContentValues taskValues = new ContentValues();
        taskValues.put("remainder", task.getRemainder());
        taskValues.put("cu_payment", task.getPayment());

        ContentValues paymentValues = new ContentValues();
        paymentValues.put("remainder", payment.getRemainder());
        paymentValues.put("cu_payment", payment.getPayment());

        int result = db.update("tasks", taskValues, "id" + " = ?", new String[]{task.getId() + ""});
        int result2 = db.update("payment", paymentValues, "py_ts_id" + " = ?", new String[]{payment.getTaskID() + ""});
       //Toast.makeText(context, "ID Task : " + task.getId(), Toast.LENGTH_LONG).show();

     //   Toast.makeText(context, "ID Payment : " + payment.getTaskID(), Toast.LENGTH_LONG).show();
        Log.d("update tasks", "tag " + result);
        Log.d("update payment", "tag " + result2);
        return result2;
    }

    public int updateTaskPaymentReport(Context context, Task task, Payment payment) {
        ContentValues taskValues = new ContentValues();
        taskValues.put("remainder", task.getRemainder());
        taskValues.put("cu_payment", task.getPayment());

        ContentValues paymentValues = new ContentValues();
        paymentValues.put("remainder", payment.getRemainder());
        paymentValues.put("cu_payment", payment.getPayment());

        int result = db.update("tasks", taskValues, "id" + " = ?", new String[]{task.getId() + ""});
        int result2 = db.update("payment", paymentValues, "id" + " = ?", new String[]{payment.getId() + ""});
      //  Toast.makeText(context, "ID Task : " + task.getId(), Toast.LENGTH_LONG).show();

       // Toast.makeText(context, "ID Payment : " + payment.getId(), Toast.LENGTH_LONG).show();
        Log.d("update tasks", "tag " + result);
        Log.d("update payment", "tag " + result2);
        return result2;
    }
}
