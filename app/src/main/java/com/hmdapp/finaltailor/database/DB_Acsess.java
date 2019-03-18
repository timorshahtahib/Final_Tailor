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
import com.hmdapp.finaltailor.Models.Model;
import com.hmdapp.finaltailor.Models.Order;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.Models.payment_report;

import java.util.ArrayList;
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

        db.rawQuery("PRAGMA foreign_keys = ON",new String[]{});
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

            list.add(con);
            Log.d("idddddddddddddddddd", con.getId() + "");
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

            list.add(con);
            Log.d("idddddddddddddddddd", con.getId() + "");
        }
        return list;
    }

    public List<Order> getAllTask() {

        ArrayList<Order> list = new ArrayList<>();

        //ORDER BY deliver_date DESC

        Cursor d = db.rawQuery("select * from 'order' ", new String[]{});

        Log.d("Get All order : size", d.getCount() + "");
        while (d.moveToNext()) {
            Order order = new Order();
            order.setId(d.getInt(0));
            order.setCount(d.getInt(1));
            order.setColor(d.getString(2));
            order.setPrice(d.getFloat(3));
            order.setOrder_Date(d.getString(4));
            order.setDeliverDate(d.getString(5));
            order.setIsExist(d.getInt(6));
            order.setCom_state(d.getInt(7));
            Cloth cloth = getCloth(d.getInt(8));
            // Cloth cloth = getCloth(1);
            order.setCloth(cloth);
            list.add(order);
            Log.d("Get Order ....  id : ", order.getId() + "");
        }
        return list;
    }

    public List<Payment> getListOfRemainderReport(Context context, int id) {

        ArrayList<Payment> list = new ArrayList<>();

        Cursor d = db.rawQuery("select * from payment  where   ( DATE(date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) AND fk_order ='" + id + "'  ORDER BY date DESC ", new String[]{});
        //Toast.makeText(context, "Get All Order : size : " + c.getCount(), Toast.LENGTH_LONG).show();
        Log.d("Get All Payment : size", d.getCount() + "");
        Log.i("Get All payment : size", d.getCount() + "");
        System.out.println("Get All payment : size : " + d.getCount() + "");
        while (c.moveToNext()) {
            Payment payment = new Payment();
            payment.setId(d.getInt(0));
            payment.setDate(d.getString(1));
            payment.setId(d.getInt(3));
            Order order = getOrder(d.getInt(5));
            payment.setOrder(order);
            list.add(payment);
            Log.d("Get Order ....  id : ", payment.getId() + "");
        }
        return list;
    }

    public List<Payment> getAllRemainderReport(Context context) {

        ArrayList<Payment> list = new ArrayList<>();

        Cursor d = db.rawQuery("select SUM(amount) as total, id, date,fk_order from payment  where ( DATE(date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) GROUP BY fk_order ORDER BY date DESC", new String[]{});
        //    Cursor d = db.rawQuery("SELECT `customer_id`,`full_name`,`order_id`,`count`,`color`,`price`,`order_date`,`deliver_date`,`is_exist`,`com_state`,`payment_id`,`date`,`cr_db`,SUM(amount)AS total_amount,`desc` FROM payment_report  GROUP BY customer_id   HAVING total_amount>0 ORDER BY `date` DESC", new String[]{});
        //Toast.makeText(context, "Get All Order : size : " + c.getCount(), Toast.LENGTH_LONG).show();
        Log.d("Get All reminder : size", d.getCount() + "");


        while (d.moveToNext()) {

            Payment payment = new Payment();
            payment.setTotal(d.getInt(0));
            payment.setId(d.getInt(1));
            payment.setDate(d.getString(2));
            Order order = getOrder(d.getInt(3));
            payment.setOrder(order);
            Log.d("Get Order ....  id : ", payment.getId() + "");
            list.add(payment);
        }
        return list;
    }

    public List<payment_report> getreminder_customer(Context context) {

        ArrayList<payment_report> list = new ArrayList<>();

        //  Cursor d = db.rawQuery("select SUM(amount) as total, id, date,fk_order from payment  where ( DATE(date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) GROUP BY fk_order ORDER BY date DESC", new String[]{});
        Cursor d = db.rawQuery("SELECT `customer_id`,`full_name`,SUM(amount)AS total_amount FROM payment_report  GROUP BY customer_id   HAVING total_amount>0 ORDER BY `date` DESC", new String[]{});
        //Toast.makeText(context, "Get All Order : size : " + c.getCount(), Toast.LENGTH_LONG).show();
        Log.d("Get All reminder : size", d.getCount() + "");


        while (d.moveToNext()) {

            payment_report payment = new payment_report();

            payment.setId(d.getInt(0));
            payment.setName(d.getString(1));
            payment.setAmount(d.getInt(2));

            Log.d("Get Order ....  id : ", payment.getId() + "");
            list.add(payment);
        }
        return list;
    }

    public List<Order> getreminder_customer_order(Context context, int id) {

        ArrayList<Order> list = new ArrayList<>();

        //  Cursor d = db.rawQuery("select SUM(amount) as total, id, date,fk_order from payment  where ( DATE(date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) GROUP BY fk_order ORDER BY date DESC", new String[]{});
        Cursor d = db.rawQuery(" SELECT `order_id`,`count`,`color`,`price`,`deliver_date`,`is_exist`,`com_state`, `fk_cloth`,SUM(amount)AS total_amount FROM payment_report where customer_id ='" + id + "'  GROUP BY customer_id,order_id  HAVING total_amount>0", new String[]{});
        //Toast.makeText(context, "Get All Order : size : " + c.getCount(), Toast.LENGTH_LONG).show();
        Log.d("Get All reminder : size", d.getCount() + "");


        while (d.moveToNext()) {

            Order order = new Order();
            order.setId(d.getInt(0));
            order.setCount(d.getInt(1));
            order.setColor(d.getString(2));
            order.setPrice(d.getFloat(3));

            order.setDeliverDate(d.getString(4));
            order.setIsExist(d.getInt(5));
            order.setCom_state(d.getInt(6));
            Cloth cloth = getCloth(d.getInt(7));
            order.setCloth(cloth);


            Log.d("Get Order ....  id : ", order.getId() + "");
            list.add(order);
        }
        return list;
    }

    public List<Payment> getAllPaymentReport(String type, Context context) {
        Cursor d = null;
        ArrayList<Payment> list = new ArrayList<>();
        switch (type) {
            case "Daily":
                d = db.rawQuery("select id,date,amount,'desc',fk_order from payment where date = date('now') and amount<0 ORDER BY  date DESC  ", new String[]{});
                break;
            case "Weekly":

                d = db.rawQuery("select id,date,amount,'desc',fk_order from payment  where  ( DATE(date ) between DATE('now', 'weekday 0', '-7 days') AND DATE('now', 'weekday 0', '-1 days'))  and amount<0 ORDER BY date DESC ", new String[]{});
                //c = db.rawQuery("SELECT * from tasks  where DATE(deliver_date ) >(SELECT DATE('now', '-7 day'))", new String[]{});
                break;
            case "Monthly":

                d = db.rawQuery("select id,date,amount,'desc',fk_order from payment  where ( DATE(date ) between  date('now','start of month','+1 month','-31 day') and date('now','start of month','+1 month','-1 day')) and amount<0 ORDER BY date DESC ", new String[]{});
                break;
            case "Yearly":

                d = db.rawQuery("select id,date,amount,'desc',fk_order from payment  where  ( DATE(date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) and amount<0 ORDER BY date DESC ", new String[]{});
                break;
        }

        //Toast.makeText(context, "Get All Order : size : " + c.getCount(), Toast.LENGTH_LONG).show();
        Log.d("Get All Payment : size", d.getCount() + "");

        System.out.println("Get All payment : size : " + d.getCount() + "");
        while (d.moveToNext()) {
            Payment payment = new Payment();
            payment.setId(d.getInt(0));
            payment.setDate(d.getString(1));
            payment.setAmount((d.getInt(2)) * -1);
            Order order = getOrder(d.getInt(4));
            payment.setOrder(order);


            list.add(payment);
            Log.d("Get Order ....  id : ", payment.getId() + "");
        }
        return list;
    }

    public int getDailyPayment() {
        c = db.rawQuery("select SUM(amount) as Total from payment where date = date('now')  and amount<0", new String[]{});
        c.moveToFirst();
        return (c.getInt(c.getColumnIndex("Total")) * -1);
    }

    public int getMonthlyPayment() {
        c = db.rawQuery("select SUM(amount) as Total from payment where ( DATE(date ) between  date('now','start of month','+1 month','-31 day') and date('now','start of month','+1 month','-1 day')) and amount<0 ", new String[]{});
        c.moveToFirst();
        return (c.getInt(c.getColumnIndex("Total")) * -1);
    }

    public int getWeeklyPayment() {
        Cursor d = db.rawQuery("select SUM(amount) as Total from payment where ( DATE(date ) between DATE('now', 'weekday 0', '-7 days') AND DATE('now', 'weekday 0', '-1 days'))  and amount<0 ", new String[]{});
        d.moveToFirst();
        return (d.getInt(d.getColumnIndex("Total")) * -1);
    }

    public int getYearlyPayment() {
        c = db.rawQuery("select SUM(amount) as Total from payment where  ( DATE(date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) and amount<0 ", new String[]{});
        c.moveToFirst();
        return (c.getInt(c.getColumnIndex("Total")) * -1);
    }

    public int getAllPayment() {
        c = db.rawQuery("select SUM(amount) as Total from payment where  amount<0", new String[]{});
        c.moveToFirst();
        return (c.getInt(c.getColumnIndex("Total")) * -1);
    }

    public int getRemainderPayment() {
        c = db.rawQuery("select SUM(amount) as Total from payment ", new String[]{});
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
        // con.setCl_id(c.getInt(4));

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
    public Cloth getCloth(int id, int idcl) {


        c = db.rawQuery("select * from cu_cl_info where fk_customer='" + id + "'  and id ='" + idcl + "'", new String[]{});
        c.moveToNext();
        Cloth con = new Cloth();
        con.setId(c.getInt(0));
        con.setQad(c.getInt(1));
        con.setShana(c.getInt(2));
        con.setBaqal(c.getInt(3));
        con.setDaman(c.getInt(4));
        con.setShalwar(c.getInt(5));
        con.setPacha(c.getInt(6));
        con.setBar_shalwar(c.getInt(7));
        con.setModel(c.getString(8));
        con.setModel_dam_astin(c.getString(9));
        con.setModel_qot_astin(c.getString(10));
        con.setQad_paty(c.getString(11));
        con.setModel_astin(c.getString(12));
        con.setYakhan(c.getInt(13));
        con.setDam_astin(c.getInt(14));
        con.setModel_yaqa(c.getString(15));
        con.setAstin(c.getInt(16));
        Customer customer = getCustomer(c.getInt(17));
        con.setCustomer(customer);
        Log.d("inter", id + "");


        return con;
    }

    public Cloth getCloth(int id) {

        Cursor c = db.rawQuery("select * from cu_cl_info where    id ='" + id + "'", new String[]{});
        c.moveToNext();
        Cloth con = new Cloth();
        con.setId(c.getInt(0));
        con.setQad(c.getInt(1));
        con.setShana(c.getInt(2));
        con.setBaqal(c.getInt(3));
        con.setDaman(c.getInt(4));
        con.setShalwar(c.getInt(5));
        con.setPacha(c.getInt(6));
        con.setBar_shalwar(c.getInt(7));
        con.setModel(c.getString(8));
        con.setModel_dam_astin(c.getString(9));
        con.setModel_qot_astin(c.getString(10));
        con.setQad_paty(c.getString(11));
        con.setModel_astin(c.getString(12));
        con.setYakhan(c.getInt(13));
        con.setDam_astin(c.getInt(14));
        con.setModel_yaqa(c.getString(15));
        con.setAstin(c.getInt(16));
        Customer customer = getCustomer(c.getInt(17));
        con.setDes(c.getString(18));
        con.setCustomer(customer);

        //Log.d("bagal ", c.getInt(4) + "");
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

        val_cloth.put("fk_customer", cloth.getCustomer().getId());
        val_cloth.put("desc", cloth.getDes());


        int r = db.update("cu_cl_info", val_cloth, "id" + " = ?",
                new String[]{cloth.getId() + ""});
        Log.d("update cloth ", "tag " + r);
        // updating row
        return r;
    }

    public int updatePerson(Customer customer) {
        ContentValues val_customer = new ContentValues();


        val_customer.put("full_name", customer.getName()); // Contact Phone
        val_customer.put("phon", customer.getPhone()); // Contact Phone
        val_customer.put("job", customer.getJob()); // Contact Phone
        int r = db.update("customer", val_customer, "id" + " = ?",
                new String[]{customer.getId() + ""});


        Log.d("update customer", "tag " + r);
        return r;
    }


    public long insert_cloth(Cloth cloth) {


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

        val_cloth.put("fk_customer", cloth.getCustomer().getId());
        val_cloth.put("desc", cloth.getDes());


        long b2 = db.insert("cu_cl_info", null, val_cloth);


        db.close(); // Closing database connection
        return b2;

    }

    public long insert_customer(Customer contact) {

        ContentValues val_customer = new ContentValues();
        val_customer.put("full_name", contact.getName()); // Contact Phone
        val_customer.put("phon", contact.getPhone()); // Contact Phone
        val_customer.put("job", contact.getJob()); // Contact Phone

        // Inserting Row
        long b1 = db.insert("customer", null, val_customer);

        db.close(); // Closing database connection
        return b1;

    }


    public long add_task(Order order, Payment py) {

        ContentValues val_py = new ContentValues();

        ContentValues val_task = new ContentValues();


        val_task.put("fk_cu_cl_info", order.getCloth().getId());

        val_task.put("count", order.getCount());

        val_task.put("color", order.getColor());
        val_task.put("price", order.getPrice());
        val_task.put("order_date", order.getOrder_Date());


        val_task.put("deliver_date", order.getDeliverDate());
        val_task.put("is_exist", order.getIsExist());
        val_task.put("com_state", order.getCom_state());

        long b2 = db.insert("'order'", null, val_task);


        val_py.put("fk_order", getLastId_task());
        val_py.put("date", py.getDate());
        val_py.put("amount", -py.getAmount());
        val_py.put("cr_db", py.getCr_db() + "");
        val_py.put("desc", py.getDes());


        // Inserting Row

        if (py.getAmount() > 0) {
            db.insert("payment", null, val_py);

        }


        ContentValues val_py2 = new ContentValues();
        val_py2.put("fk_order", getLastId_task());
        val_py2.put("date", py.getDate());
        val_py2.put("amount", order.getPrice());
        val_py2.put("cr_db", py.getCr_db() + "");
        val_py2.put("desc", "debet");

        long b1 = db.insert("payment", null, val_py2);


        db.close(); // Closing database connection
        return b1;

    }

    public int getLastId() {

        c = db.rawQuery("select id from cloth", new String[]{});

        c.moveToLast();

        return c.getInt(0);
    }

    public int getLastId_task() {

        c = db.rawQuery("select id from 'order'", new String[]{});

        c.moveToLast();
        try {
            return c.getInt(0);

        } catch (Exception e) {
            return 1;

        }
    }


    public boolean delete_cloth(int id) {


        int b = db.delete("cu_cl_info", "id" + " = ?",
                new String[]{id + ""});


        return b > 0;

    }

    public boolean delete_Cutomer(int id) {


        ArrayList<Cloth> cloths = get_cLoth(id);
        for (Cloth c : cloths) {
            try {
                Order order = get_order_of_spicific_cloth(c.getId());
                deleteTask(order.getId());
                delete_cloth(c.getId());

            }catch (Exception e){

            }

        }


        int b2 = db.delete("customer", "id" + " = ?",
                new String[]{id + ""});
        return b2 > 0;

    }

    private Order get_order_of_spicific_cloth(int id) {

        c = db.rawQuery("select * from 'order'  where fk_cu_cl_info='" + id + "'", new String[]{});
        Log.d("size", c.getCount() + "");
        c.moveToNext();
        Order order = new Order();
        order.setId(c.getInt(0));
        order.setCount(c.getInt(1));
        order.setColor(c.getString(2));
        order.setPrice(c.getFloat(3));
        order.setOrder_Date(c.getString(4));
        order.setDeliverDate(c.getString(5));
        order.setIsExist(c.getInt(6));
        order.setCom_state(c.getInt(7));
        Cloth cloth = getCloth(c.getInt(8));
        order.setCloth(cloth);
        return order;
    }

    ArrayList<Cloth> get_cLoth(int cu_id) {
        ArrayList<Cloth> list = new ArrayList<>();
        c = db.rawQuery("select id from cu_cl_info where fk_customer='" + cu_id + "'", new String[]{});
        Log.d("size", c.getCount() + "");
        while (c.moveToNext()) {
            Cloth con = getCloth(c.getInt(0));


            list.add(con);
        }
        return list;
    }

    public boolean deleteTask(int id) {
        Order order = getOrder(id);
        deletePayment_of_custom_order(order.getId());
        int result = db.delete("'order'", "id" + " = ?", new String[]{id + ""});

        return result > 0;

    }

    public boolean deletePayment_of_custom_order(int id) {
        int result = db.delete("payment", "fk_order" + " = ?", new String[]{id + ""});
        return result > 0;

    }

    public boolean deletePayment(int id) {
        int result = db.delete("payment", "id" + " = ?", new String[]{id + ""});
        return result > 0;

    }

    public boolean deletePaymentByType(String type) {
        int result = 0;
        switch (type) {
            case "Daily":
                // c = db.rawQuery("select * from payment where deliver_date = date('now') and ts_state =1 ORDER BY deliver_date DESC ", new String[]{});

                result = db.delete("payment", "ate = date('now') AND ts_state =1 ", null);
                break;
            case "Weekly":

                // c = db.rawQuery("select * from payment  where ts_state = 1 AND ( DATE(deliver_date ) between DATE('now', 'weekday 0', '-7 days') AND DATE('now', 'weekday 0', '-1 days')) ORDER BY deliver_date DESC ", new String[]{});
                result = db.delete("payment", " ( DATE(date ) between DATE('now', 'weekday 0', '-7 days') AND DATE('now', 'weekday 0', '-1 days')) ", null);
                break;
            case "Monthly":

                // c = db.rawQuery("select * from payment  where ts_state = 1 AND ( DATE(deliver_date ) between  date('now','start of month','+1 month','-31 day') and date('now','start of month','+1 month','-1 day')) ORDER BY deliver_date DESC ", new String[]{});
                result = db.delete("payment", " ( DATE(date ) between  date('now','start of month','+1 month','-31 day') and date('now','start of month','+1 month','-1 day')) ", null);
                break;
            case "Yearly":

                //c = db.rawQuery("select * from payment  where ts_state = 1 AND ( DATE(deliver_date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) ORDER BY deliver_date DESC ", new String[]{});
                result = db.delete("payment", "( DATE(date ) between date('now','start of year','+1 month','-31 day') and date('now','start of year','+12 month','-1 day')) ", null);
                break;
        }


        return result > 0;

    }

    public int updateTask(Order order) {
        ContentValues taskValues = new ContentValues();
        taskValues.put("com_state", order.getCom_state());

        int result = db.update("'order'", taskValues, "id" + " = ?", new String[]{order.getId() + ""});
        //    int result2 = db.update("payment", paymentValues, "py_ts_id" + " = ?", new String[]{payment.getTaskID() + ""});
        //  Toast.makeText(context, "ID : " + payment.getTaskID(), Toast.LENGTH_LONG).show();
        Log.d("update tasks", "tag " + result);
        return result;
    }

    public int updateTaskClothState(Order order) {
        ContentValues taskValues = new ContentValues();
        taskValues.put("is_exist", order.getIsExist());
        int result = db.update("'order'", taskValues, "id" + " = ?", new String[]{order.getId() + ""});


        //Toast.makeText(context, "ID : " + order.getId(), Toast.LENGTH_LONG).show();
        Log.d("update tasks", "tag " + result);
        return result;
    }

    public long Add_Rasid(Payment py) {


        ContentValues val_py = new ContentValues();

        val_py.put("fk_order", py.getOrder().getId());
        val_py.put("date", py.getDate());
        val_py.put("amount", -py.getAmount());
        val_py.put("cr_db", "caridet");
        val_py.put("desc", py.getDes() + "");


        // Inserting Row
        long b1 = db.insert("payment", null, val_py);
        if (b1 > 0) {
            Log.d("payment added", "amount :" + py.getAmount() + "");
        }

        return b1;
    }

    public int updateTaskPaymentReport(Context context, Order order, Payment payment) {
        ContentValues taskValues = new ContentValues();


        ContentValues paymentValues = new ContentValues();
//        paymentValues.put("remainder", payment.getRemainder());
//        paymentValues.put("cu_payment", payment.getPayment());

        int result = db.update("tasks", taskValues, "id" + " = ?", new String[]{order.getId() + ""});
        int result2 = db.update("payment", paymentValues, "id" + " = ?", new String[]{payment.getId() + ""});
        //  Toast.makeText(context, "ID Order : " + order.getId(), Toast.LENGTH_LONG).show();

        // Toast.makeText(context, "ID Payment : " + payment.getId(), Toast.LENGTH_LONG).show();
        Log.d("update tasks", "tag " + result);
        Log.d("update payment", "tag " + result2);
        return result2;
    }

    public ArrayList<Model> get_Model_cloth(int id) {

        ArrayList<Model> list = new ArrayList<>();
        c = db.rawQuery("select id,desc from cu_cl_info where fk_customer='" + id + "'", new String[]{});
        Log.d("size", c.getCount() + "");
        while (c.moveToNext()) {
            Model con = new Model();
            con.setId(c.getInt(0));
            con.setName(c.getString(1));
            list.add(con);
        }
        return list;
    }

    public Order getOrder(int id) {

        c = db.rawQuery("select * from 'order'  where id='" + id + "'", new String[]{});
        Log.d("size", c.getCount() + "");
        c.moveToNext();
        Order order = new Order();
        order.setId(c.getInt(0));
        order.setCount(c.getInt(1));
        order.setColor(c.getString(2));
        order.setPrice(c.getFloat(3));
        order.setOrder_Date(c.getString(4));
        order.setDeliverDate(c.getString(5));
        order.setIsExist(c.getInt(6));
        order.setCom_state(c.getInt(7));
        Cloth cloth = getCloth(c.getInt(8));
        order.setCloth(cloth);
        return order;


    }

    public Payment getPayment(int id) {

        Cursor d = db.rawQuery("select * from payment  where fk_order='" + id + "'", new String[]{});
        Log.d("size_payment", c.getCount() + "");
        d.moveToNext();
        Payment payment = new Payment();
        payment.setId(d.getInt(0));
        payment.setDate(d.getString(1));
        payment.setAmount((d.getInt(2) * -1));
        payment.setDes(d.getString(4));
        Order order = getOrder(d.getInt(5));
        payment.setReminder(getTotal(id));
        try {
            payment.setPish_pardakht(get_rasid(id));

        } catch (Exception e) {
            payment.setPish_pardakht(0);

        }


        Log.d("payment_s", payment.toString());
        payment.setOrder(order);

        return payment;
    }

    public int get_rasid(int id) {
        c = db.rawQuery("select sum(amount) from payment  where fk_order='" + id + "' and amount<0 ", new String[]{});
        Log.d("size_pish", c.getCount() + "");
        c.moveToNext();
        int m = c.getInt(0);
        return m;
    }

    public int getTotal(int id) {
        c = db.rawQuery("select sum(amount) from payment  where fk_order='" + id + "'", new String[]{});
        Log.d("size", c.getCount() + "");
        c.moveToNext();
        int m = c.getInt(0);
        return m;
    }

    public ArrayList<Payment> getPayment_of_roder(int id) {

        c.close();
        ArrayList<Payment> list = new ArrayList<>();
        //c = db.rawQuery("select * from payment where fk_order='" + id + "'  and amount<0", new String[]{});
        c = db.rawQuery("select id,date,amount,'desc',fk_order from payment where fk_order='" + id + "'  and amount<0", new String[]{});
        Log.d("size_payment", c.getCount() + "");
        while (c.moveToNext()) {
            Payment payment = new Payment();
            payment.setId(c.getInt(0));
            payment.setDate(c.getString(1));
            Log.d("amount_", c.getInt(2) + "");
            payment.setAmount(c.getInt(2));
            payment.setDes(c.getString(3));
            list.add(payment);
        }
        return list;
    }
}
