package com.belac.ines.foodie.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.OrderResult;
import com.belac.ines.foodie.helper.SessionManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * Created by Korisnik on 21.01.2018..
 */

public class ArchiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
         {
             private static final int USER = 0;
             private static final int RESTAURANT = 1;

             private List<OrderResult> items;
             private Context context;

             public ArchiveAdapter(List<OrderResult> results, Context context) {
                 this.items = results;
                 this.context = context;
             }

             @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                 switch (viewType) {
                     case USER:
                         return new ArchiveAdapter.ViewHolderUser(LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.list_row_archive_user, parent, false));
                     case RESTAURANT:
                         return new ArchiveAdapter.ViewHolderRestaurant(LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.list_row_archive_restaurant, parent, false));
                 }

                 throw new IllegalArgumentException("Illegal state!");
             }

             @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                 switch (holder.getItemViewType()) {
                     case USER:
                         ViewHolderUser vhUser = (ViewHolderUser) holder;

                         OrderResult usrOrder = items.get(position);

                         vhUser.restaurant.setText(usrOrder.getRestoran());

                         vhUser.date.setText(setDate(usrOrder.getDate()));

                         vhUser.price.setText(String.format("Price: %s$", usrOrder.getPrice()));
                         vhUser.delivery.setText(String.format("Address: %s", usrOrder.getDelivery()));

                         vhUser.firstMeal.setText(String.format("Appetizer: %s", usrOrder.getFirstMeal()));
                         vhUser.secondMeal.setText(String.format("Main course: %s", usrOrder.getSecondMeal()));
                         vhUser.thirdMeal.setText(String.format("Dessert: %s", usrOrder.getThirdMeal()));

                         break;

                     case RESTAURANT:
                         ViewHolderRestaurant vhRestaurant = (ViewHolderRestaurant) holder;

                         OrderResult order = items.get(position);

                         vhRestaurant.date.setText(setDate(order.getDate()));

                         vhRestaurant.name.setText(String.format("%s %s", order.getName(), order.getSurname()));

                         vhRestaurant.price.setText(String.format("Price: %s$", order.getPrice()));
                         vhRestaurant.delivery.setText(String.format("Address: %s", order.getDelivery()));

                         vhRestaurant.firstMeal.setText(String.format("Appetizer: %s", order.getFirstMeal()));
                         vhRestaurant.secondMeal.setText(String.format("Main course: %s", order.getSecondMeal()));
                         vhRestaurant.thirdMeal.setText(String.format("Dessert: %s", order.getThirdMeal()));
                         break;
                 }
             }

             @Override public int getItemViewType(int position) {
                 int type = SessionManager.getType(context);

                 if (type == 1) {
                     return USER;
                 } else if (type == 2) {
                     return RESTAURANT;
                 } else {
                     throw new IllegalArgumentException("Illegal state!");
                 }
             }

             private String setDate(String orderTime) {
                 try {
                     SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     java.util.Date date = parser.parse(orderTime);
                     SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY HH:mm");
                     String formattedDate = formatter.format(date);
                     return String.format("Date: %s", formattedDate);
                 } catch (ParseException e) {
                     e.printStackTrace();
                     return String.format("Date: %s", orderTime);
                 }
             }

             @Override public int getItemCount() {
                 return items.size();
             }

             public class ViewHolderUser extends RecyclerView.ViewHolder {

                 @BindView(R.id.firstMeal) public TextView firstMeal;
                 @BindView(R.id.secondMeal) public TextView secondMeal;
                 @BindView(R.id.thirdMeal) public TextView thirdMeal;

                 @BindView(R.id.restaurant) public TextView restaurant;
                 @BindView(R.id.price) public TextView price;
                 @BindView(R.id.date) public TextView date;
                 @BindView(R.id.delivery) public TextView delivery;

                 public ViewHolderUser(View itemView) {
                     super(itemView);

                     ButterKnife.bind(this, itemView);
                 }
             }

             public class ViewHolderRestaurant extends RecyclerView.ViewHolder {

                 @BindView(R.id.firstMeal) public TextView firstMeal;
                 @BindView(R.id.secondMeal) public TextView secondMeal;
                 @BindView(R.id.thirdMeal) public TextView thirdMeal;

                 @BindView(R.id.name) public TextView name;
                 @BindView(R.id.price) public TextView price;
                 @BindView(R.id.date) public TextView date;
                 @BindView(R.id.delivery) public TextView delivery;

                 public ViewHolderRestaurant(View itemView) {
                     super(itemView);

                     ButterKnife.bind(this, itemView);
                 }
             }
}
