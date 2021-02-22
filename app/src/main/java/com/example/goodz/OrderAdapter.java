package com.example.goodz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goodz.databaseClasses.Order;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<Order> orderList;
    public ArrayList<String> orderKeyList;

    public OrderAdapter(Context context, ArrayList<Order> orderList, ArrayList<String> orderKeyList){
        this.context = context;
        this.orderList = orderList;
        this.orderKeyList = orderKeyList;
    }


    @Override
    public int getCount() {
        return this.orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.order_layout, null);//set layout for displaying items

        TextView orderID = (TextView) view.findViewById(R.id.orderID);
        TextView orderCustomerName = (TextView) view.findViewById(R.id.orderCustomerName);
        TextView deliveryDate = (TextView) view.findViewById(R.id.orderDateTextView);
        TextView orderDriverID = (TextView) view.findViewById(R.id.orderDriverID);

        orderID.setText("ORDER: " + orderKeyList.get(i));
        orderCustomerName.setText("Customer Name: " + orderList.get(i).customerName);
        deliveryDate.setText("Delivery Date: " + orderList.get(i).deliveryDate);
        orderDriverID.setText("Driver ID: " + orderList.get(i).driverUserID);

        Button acceptButton = (Button) view.findViewById(R.id.acceptOrder);
        acceptButton.setVisibility(View.GONE);

        if (orderList.get(i).driverUserID.equals("") && context instanceof DriverActivity){

            acceptButton.setVisibility(View.VISIBLE);
        }


        return view;
    }
}
