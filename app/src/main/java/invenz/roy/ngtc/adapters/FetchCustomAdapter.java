package invenz.roy.ngtc.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import invenz.roy.ngtc.R;
import invenz.roy.ngtc.fragments.WallpapersFragment;
import invenz.roy.ngtc.models.FetchItem;
import invenz.roy.ngtc.utils.MySqliteDbHelper;

public class FetchCustomAdapter extends RecyclerView.Adapter<FetchCustomAdapter.MyViewHolder> {

    private static final String TAG = "ROY";
    private Context context;
    private List<FetchItem> itemList;
    private Dialog dialog;

    public FetchCustomAdapter(Context context, List<FetchItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public FetchCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_single_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FetchCustomAdapter.MyViewHolder holder, final int position) {

        final FetchItem fetchItem = itemList.get(position);
        final String name = fetchItem.getItemName();
        final Bitmap imageBitmap = fetchItem.getImageBitmap();


        holder.imageViewItem.setImageBitmap(imageBitmap);
        holder.tvName.setText(name);



        holder.tvName.setOnClickListener(new View.OnClickListener() {
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

                btOrder.setVisibility(View.GONE);


                /*###                   Setting values to the components             ####*/
                //Picasso.get().load(imagePath).into(imageViewItemImage);
                imageViewItemImage.setImageBitmap(imageBitmap);
                tvItemName.setText(name);


                dialog.show();

                imageViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });


        /*###                     When image clicked                    ###*/
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

                btOrder.setVisibility(View.GONE);



                /*###                   Setting values to the components             ####*/
                //Picasso.get().load(imagePath).into(imageViewItemImage);
                imageViewItemImage.setImageBitmap(imageBitmap);
                tvItemName.setText(name);


                dialog.show();

                imageViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });


        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Toast.makeText(context, ""+fetchItem.getId(), Toast.LENGTH_SHORT).show();

                //View view = v;

                /*####                 Animation when cliked delete imageview                 ####*/
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));

                /*########              AlertDialog for confirmation              ########*/
                new AlertDialog.Builder(context)
                        .setTitle("Delete Information")
                        .setMessage("Do you really want to Detete this item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                /*######              deleteing from sqlite              #######*/
                                MySqliteDbHelper mySqliteDbHelper = new MySqliteDbHelper(context);
                                int i = mySqliteDbHelper.deleteItem(fetchItem.getId());


                                /*##                 updateing custom adapter           ###*/
                                if (i==1){
                                    //Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    Snackbar.make(v,"Deleted Successfully", Snackbar.LENGTH_SHORT ).setAction("action", null).show();


                                    itemList.remove(position);
                                    notifyDataSetChanged();

                                }else {
                                    //Toast.makeText(context, "Failed To delete", Toast.LENGTH_SHORT).show();
                                    Snackbar.make(v,"Failed To delete", Snackbar.LENGTH_SHORT ).setAction("action", null).show();


                                }


                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });

    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imageViewItem, imageViewDelete;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.idName_onlineFrag);
            imageViewItem = itemView.findViewById(R.id.idImage_onlineFrag);
            imageViewDelete = itemView.findViewById(R.id.idDeleteImage_onlineFrag);
        }
    }
}
