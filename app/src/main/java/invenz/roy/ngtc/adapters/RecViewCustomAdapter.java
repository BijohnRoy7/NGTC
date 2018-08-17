package invenz.roy.ngtc.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import invenz.roy.ngtc.R;
import invenz.roy.ngtc.activities.OrderActivity;
import invenz.roy.ngtc.models.Item;
import invenz.roy.ngtc.models.MyItem;
import invenz.roy.ngtc.utils.MySqliteDbHelper;

public class RecViewCustomAdapter extends RecyclerView.Adapter<RecViewCustomAdapter.MyViewHolder> {

    private static final String TAG = "ROY";
    private Context context;
    private List<Item> itemList;
    private Dialog dialog;
    private ProgressDialog progressDialog;

    public RecViewCustomAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecViewCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecViewCustomAdapter.MyViewHolder holder, int position) {

        final Item item = itemList.get(position);
        final String name = item.getName();

        String imageTitle = item.getImagePath();
        final String imagePath = "http://invenz-it.com/ngtc/images/"+imageTitle+".jpg";

        holder.tvName.setText(name);
        Picasso.get().load(imagePath).into(holder.imageViewItem);

        /*##                    Click the image                         ###*/
        holder.imageViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

                /*####                 Animation when cliked image                 ####*/
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));


                /*######              Creating custom dialog               ######*/
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.pop_up_layout);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.pop_up_bg);

                /*###                giving custom dialog custom size                ###*/
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.90);
                lp.height = (int)(context.getResources().getDisplayMetrics().heightPixels*0.80);
                dialog.getWindow().setAttributes(lp);


                /*###                  Initializing Dialog components             #####*/
                ImageView imageViewClose = dialog.findViewById(R.id.idClose_popUp);
                ImageView imageViewItemImage = dialog.findViewById(R.id.idItemImage_popUp);
                TextView tvItemName = dialog.findViewById(R.id.idItemName_popUp);
                TextView btOrder = dialog.findViewById(R.id.idOrder_popup);


                /*###                   Setting values to the components             ####*/
                Picasso.get().load(imagePath).into(imageViewItemImage);
                tvItemName.setText(name);


                /*###                   when order button is clicked                  ###*/
                btOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));

                        Intent intent = new Intent(context, OrderActivity.class);
                        intent.putExtra("item_id", item.getItemId());
                        intent.putExtra("item_name", item.getName());
                        //Toast.makeText(context, "yes: "+item.getItemId(), Toast.LENGTH_SHORT).show();

                        context.startActivity(intent);
                    }
                });


                dialog.show();

                imageViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        /*####              Click to download image              #####*/
        holder.imageViewDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Toast.makeText(context, "Downloading: "+holder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();

                /*###                   progressDialog                          ###*/
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Downloading...");
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                /*####                 Animation when cliked image                 ####*/
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));

                /*####                 Volley request for image                ####*/
                ImageRequest request = new ImageRequest(imagePath,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                //mImageView.setImageBitmap(bitmap);
                                byte[] imgByte = getBytes(bitmap);
                                //Toast.makeText(context, ""+imgByte, Toast.LENGTH_SHORT).show();

                                MyItem myItem = new MyItem(name, imgByte);
                                MySqliteDbHelper mySqliteDbHelper = new MySqliteDbHelper(context);

                                int id = mySqliteDbHelper.insertItem(myItem);

                                progressDialog.dismiss();
                                Snackbar.make(v,"downloaded Successfully to your app", Snackbar.LENGTH_SHORT ).setAction("action", null).show();

                                //Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();
                            }
                        }, 0, 0, null,
                        new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(context, "Failed to download", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Snackbar.make(v,"Failed to download", Snackbar.LENGTH_SHORT ).setAction("action", null).show();

                            }
                        });

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(request);



            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView imageViewItem, imageViewDownload;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.idName_onlineFrag);
            imageViewItem = itemView.findViewById(R.id.idImage_onlineFrag);
            imageViewDownload = itemView.findViewById(R.id.idDownloadImage_onlineFrag);
        }
    }


    /*######                           ######*/
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        return stream.toByteArray();
    }



}
